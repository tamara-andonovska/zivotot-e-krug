package com.tamara.zivototekrug;

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

public class doneVolAdapter extends RecyclerView.Adapter<doneVolAdapter.MyViewHolder>{
    ArrayList<HelpRequest> mList;
    Context context;
    public doneVolAdapter(Context context, ArrayList<HelpRequest> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public doneVolAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.done_requests_vol, parent, false);
        return new doneVolAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull doneVolAdapter.MyViewHolder holder, int position) {
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
        Button btnRateElderly, btnWriteReport;

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

            btnRateElderly = itemView.findViewById(R.id.rate_elderly_btn);
            btnRateElderly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String statusStr = status.getText().toString();
                    if (statusStr.equals("Done")){
                        String emailStr = email.getText().toString();
                        //Toast.makeText(view.getContext(), emailStr, Toast.LENGTH_SHORT).show();
                        String userIdStr = userId.getText().toString();
                        String idStr = id.getText().toString();
                        Intent intent = new Intent(itemView.getContext(), RatingActivity.class);
                        intent.putExtra("toBeRatedEmail", emailStr);
                        intent.putExtra("userID", userIdStr);
                        intent.putExtra("requestId", idStr);
                        itemView.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(view.getContext(), "Must be checked as done first.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnWriteReport = itemView.findViewById(R.id.write_report_btn);
            btnWriteReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailStr = email.getText().toString();
                    String userIdStr = userId.getText().toString();
                    //Toast.makeText(view.getContext(), emailStr, Toast.LENGTH_SHORT).show();
                    String reqId = id.getText().toString();
                    Intent intent = new Intent(itemView.getContext(), ReportActivity.class);
                    intent.putExtra("elderlyEmail", emailStr);
                    intent.putExtra("userID", userIdStr);
                    intent.putExtra("requestID", reqId);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
