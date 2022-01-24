package com.tamara.zivototekrug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class assignedVolAdapter extends RecyclerView.Adapter<assignedVolAdapter.MyViewHolder> {
    ArrayList<HelpRequest> mList;
    Context context;
    public assignedVolAdapter(Context context, ArrayList<HelpRequest> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public assignedVolAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.assigned_requests_vol, parent, false);
        return new assignedVolAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull assignedVolAdapter.MyViewHolder holder, int position) {
        HelpRequest helpRequest = mList.get(position);
        holder.description.setText(helpRequest.getDescription());
        holder.emergencyLevel.setText(helpRequest.getEmergencyLevel());
        holder.type.setText(helpRequest.getHelpType());
        holder.loc.setText(helpRequest.getLocation());
        holder.repeat.setText(helpRequest.getRepeated());
        holder.date.setText(helpRequest.getDate());
        holder.time.setText(helpRequest.getTime());
        holder.status.setText(helpRequest.getStatus());
        holder.volPhone.setText(helpRequest.getVolunteerPhone());
        holder.volName.setText(helpRequest.getVolunteerName());
        holder.volEmail.setText(helpRequest.getVolunteerEmail());
        holder.id.setText(helpRequest.getId());
        holder.email.setText(helpRequest.getEmail());
        holder.userId.setText(helpRequest.getUserId());
        holder.volRating.setText(helpRequest.getVolunteerRating());
        holder.rating.setText(helpRequest.getRating());
        holder.volId.setText(helpRequest.getVolunteerId());
        holder.phone.setText(helpRequest.getPhone());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        DatabaseReference ref;

        TextView description, emergencyLevel, type, loc, repeat, date, time, status, volEmail, id, volName, volPhone, email, userId, volRating, rating, volId, phone;
        Button btnCheckAsDone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description_show);
            emergencyLevel = itemView.findViewById(R.id.emergency_level_show);
            type = itemView.findViewById(R.id.help_type_show);
            loc = itemView.findViewById(R.id.location_show);
            repeat = itemView.findViewById(R.id.repeated_show);
            date = itemView.findViewById(R.id.date_show);
            time = itemView.findViewById(R.id.time_show);
            status = itemView.findViewById(R.id.status_show);
            email = itemView.findViewById(R.id.email_show);
            volEmail = itemView.findViewById(R.id.volunteer_email_show);
            id = itemView.findViewById(R.id.id_show);
            volName = itemView.findViewById(R.id.volunteer_name_show);
            volPhone = itemView.findViewById(R.id.volunteer_phone_show);
            userId = itemView.findViewById(R.id.userid_show);
            volRating = itemView.findViewById(R.id.vol_rating_show);
            rating = itemView.findViewById(R.id.rating_show);
            volId = itemView.findViewById(R.id.vol_id_show);
            phone = itemView.findViewById(R.id.phone_show);

            btnCheckAsDone = itemView.findViewById(R.id.done_btn);
            btnCheckAsDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String statusStr = status.getText().toString();
                    if (statusStr.equals("Assigned")) {
                        status.setText("Done");
                        String dateStr = date.getText().toString();
                        String descriptionStr = description.getText().toString();
                        String emailStr = email.getText().toString();
                        String emergencyLevelStr = emergencyLevel.getText().toString();
                        String typeStr = type.getText().toString();
                        String idStr = id.getText().toString();
                        String locStr = loc.getText().toString();
                        String repStr = repeat.getText().toString();
                        statusStr = status.getText().toString();
                        String timeStr = time.getText().toString();
                        String userIdStr = userId.getText().toString();
                        String volEmailStr = volEmail.getText().toString();
                        String volNameStr = volName.getText().toString();
                        String volPhoneStr = volPhone.getText().toString();
                        String volRatingStr = volRating.getText().toString();
                        String ratingStr = rating.getText().toString();
                        String volIdStr = volId.getText().toString();
                        String phoneStr = phone.getText().toString();

                        updateData(dateStr, descriptionStr, emailStr, phoneStr, emergencyLevelStr, typeStr, idStr,
                                locStr, ratingStr, repStr, statusStr, timeStr, userIdStr, volEmailStr, volNameStr, volPhoneStr, volRatingStr, volIdStr);

                    } else if (statusStr.equals("Active")) {
                        Toast.makeText(view.getContext(), "Must be assigned first.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Help already provided by a volunteer", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(itemView.getContext(), AssignedVolunteerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    itemView.getContext().startActivity(intent);
                    Activity mContext = (Activity) itemView.getContext();
                    mContext.overridePendingTransition(0,0);
                }
            });
        }

        private void updateData(String dateStr, String descriptionStr, String emailStr, String phoneStr, String emergencyLevelStr, String typeStr, String id, String locStr, String ratingStr, String repStr, String statusStr, String timeStr, String userIdStr, String volEmailStr, String volNameStr, String volPhoneStr, String volRatingStr, String volIdStr) {
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

}
