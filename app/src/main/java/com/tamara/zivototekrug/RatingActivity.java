package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {
    TextView tvRating, tvElderlyEmail, tvElderlyId;
    Button btnSubmitRating;
    RatingBar ratingBar;

    DatabaseReference ref, ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Intent startingIntent = getIntent();
        String eEmail = startingIntent.getStringExtra("toBeRatedEmail"); //na korisnikot koj treba da e ocenet
        String eId = startingIntent.getStringExtra("userID"); //na korisnikot koj treba da e ocenet
        String reqId = startingIntent.getStringExtra("requestId");

        //Toast.makeText(this, " "+eEmail+ " " + eId, Toast.LENGTH_SHORT).show();

        tvElderlyEmail = findViewById(R.id.elderly_email_text_view);
        tvElderlyEmail.setText(eEmail);

        tvElderlyId = findViewById(R.id.elderly_id_text_view);
        tvElderlyId.setText(eId);

        tvRating = findViewById(R.id.rating_elderly_text_view);
        ratingBar = findViewById(R.id.rating_bar);
        btnSubmitRating = findViewById(R.id.submit_rating_for_elderly_btn);
        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRating.setText("" + ratingBar.getRating());
                ref = FirebaseDatabase.getInstance().getReference().child("user").child(eId);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        String rating = user.getRating();
                        calculateRating(user.getEmail(), user.getFullName(), user.getPassword(), user.getPhone(), user.getRatedNo(), rating, user.getUserType(), eId, ratingBar.getRating(), reqId, eEmail);
                        //Toast.makeText(view.getContext(), user.getUserType(), Toast.LENGTH_SHORT).show();
                        // elderly e kaj volonter, volunteer e kaj elderly
                        Intent intent;
                        if (user.getUserType().equals("Elderly")){
                            intent = new Intent(RatingActivity.this, VolunteerHomeActivity.class);
                        } else {
                            intent = new Intent(RatingActivity.this, ElderlyHomeActivity.class);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void calculateRating(String mail, String name, String pass, String phone, String ratedNo, String rating, String type, String eId, float currRating, String reqId, String email) {
        int rated = Integer.parseInt(ratedNo);
        DecimalFormat df = new DecimalFormat("0.00");
        float prev_rating = Float.parseFloat(rating);
        float curr_rating = currRating;
        float final_rating = ((prev_rating * rated) + curr_rating)/(rated + 1);
        final_rating = Float.parseFloat(df.format(final_rating));
        Toast.makeText(this, ""+final_rating, Toast.LENGTH_SHORT).show();

        rated += 1;
        String ratedStr = ""+rated;
        updateData(mail, name, pass, phone, ""+final_rating, type, eId, ratedStr, reqId, email);
    }

    private void updateData(String emailStr, String nameStr, String passStr, String phoneStr, String ratingStr, String typeStr, String eId, String ratedStr, String reqId, String eEmail) {
        HashMap user = new HashMap();
        user.put("email", emailStr);
        user.put("fullName", nameStr);
        user.put("password", passStr);
        user.put("phone", phoneStr);
        user.put("rating", ratingStr);
        user.put("userType", typeStr);
        user.put("ratedNo", ratedStr);

        ref = FirebaseDatabase.getInstance().getReference("user");
        ref.child(eId).updateChildren(user);

        ref1 = FirebaseDatabase.getInstance().getReference().child("HelpRequests");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HelpRequest helpRequest = dataSnapshot.getValue(HelpRequest.class);
                    if (typeStr.equals("Elderly")){
                        if (helpRequest.getEmail().equals(eEmail))
                                updateReqData(helpRequest.getDate(), helpRequest.getDescription(), helpRequest.getEmail(), helpRequest.getPhone(),
                                helpRequest.getEmergencyLevel(), helpRequest.getHelpType(), helpRequest.getId(), helpRequest.getLocation(),
                                ratingStr, helpRequest.getRepeated(), helpRequest.getStatus(), helpRequest.getTime(),
                                helpRequest.getUserId(), helpRequest.getVolunteerEmail(), helpRequest.getVolunteerName(), helpRequest.getVolunteerPhone(),
                                helpRequest.getVolunteerRating(), helpRequest.getVolunteerId());
                    } else {
                        if (helpRequest.getVolunteerEmail().equals(eEmail))
                                updateReqData(helpRequest.getDate(), helpRequest.getDescription(), helpRequest.getEmail(), helpRequest.getPhone(),
                                helpRequest.getEmergencyLevel(), helpRequest.getHelpType(), helpRequest.getId(), helpRequest.getLocation(),
                                helpRequest.getRating(), helpRequest.getRepeated(), helpRequest.getStatus(), helpRequest.getTime(),
                                helpRequest.getUserId(), helpRequest.getVolunteerEmail(), helpRequest.getVolunteerName(), helpRequest.getVolunteerPhone(),
                                ratingStr, helpRequest.getVolunteerId());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateReqData(String dateStr, String descriptionStr, String emailStr, String phoneStr, String emergencyLevelStr, String typeStr, String id, String locStr, String ratingStr, String repStr, String statusStr, String timeStr, String userIdStr, String volEmailStr, String volNameStr, String volPhoneStr, String volRatingStr, String volIdStr) {
        HashMap request = new HashMap();
        request.put("date", dateStr);
        request.put("description", descriptionStr);
        request.put("email", emailStr);
        request.put("emergencyLevel", emergencyLevelStr);
        request.put("helpType", typeStr);
        request.put("id", id);
        request.put("location", locStr);
        request.put("rating", ratingStr);
        request.put("repeated", repStr);
        request.put("status", statusStr);
        request.put("time", timeStr);
        request.put("userId", userIdStr);
        request.put("volunteerEmail", volEmailStr);
        request.put("volunteerId", volIdStr);
        request.put("volunteerName", volNameStr);
        request.put("volunteerPhone", volPhoneStr);
        request.put("volunteerRating", volRatingStr);
        request.put("phone", phoneStr);

        ref = FirebaseDatabase.getInstance().getReference("HelpRequests");
        ref.child(id).updateChildren(request);
    }
}