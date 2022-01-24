package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ElderlyHomeActivity extends AppCompatActivity {

    TextView userEmail;
    Button btnRequestHelp;
    Button btnActiveRequests, btnAssignedRequests, btnDoneRequests, btnPendingRequests;
    Button btnLogOut;
    TextView home;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference ref;

    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly_home);

        home = findViewById(R.id.elderly_text_home);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userEmail = findViewById(R.id.user_email_elderly);
        userEmail.setText(mUser.getEmail());
        mEmail = userEmail.getText().toString();

        String userId = mUser.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("user").child(userId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String userRating = user.getRating();
                String userPhone = user.getPhone();
                home.setText("Welcome, " + user.getFullName() + "!");
                btnRequestHelp = findViewById(R.id.request_help_btn);
                btnRequestHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ElderlyHomeActivity.this, RequestActivity.class);
                        intent.putExtra("mEmail", mEmail);
                        intent.putExtra("userID", mUser.getUid());
                        intent.putExtra("userRating", userRating);
                        intent.putExtra("userPhone", userPhone);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnActiveRequests = findViewById(R.id.active_requests_btn);
        btnActiveRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElderlyHomeActivity.this, ShowRequestsActivity.class);
                startActivity(intent);
            }
        });

        btnPendingRequests = findViewById(R.id.pending_requests_btn);
        btnPendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElderlyHomeActivity.this, PendingEldActivity.class);
                startActivity(intent);
            }
        });

        btnAssignedRequests = findViewById(R.id.assigned_requests_btn);
        btnAssignedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElderlyHomeActivity.this, AssignedEldActivity.class);
                startActivity(intent);
            }
        });

        btnDoneRequests = findViewById(R.id.done_requests_btn);
        btnDoneRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElderlyHomeActivity.this, DoneEldActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut = findViewById(R.id.logout_btn);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ElderlyHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}