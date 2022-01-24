package com.tamara.zivototekrug;

import android.content.Context;
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

public class assignedEldAdapter extends RecyclerView.Adapter<assignedEldAdapter.MyViewHolder> {
    ArrayList<HelpRequest> mList;
    Context context;
    public assignedEldAdapter(Context context, ArrayList<HelpRequest> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public assignedEldAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.assigned_requests_eld, parent, false);
        return new assignedEldAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull assignedEldAdapter.MyViewHolder holder, int position) {
        HelpRequest helpRequest = mList.get(position);
        holder.description.setText(helpRequest.getDescription());
        holder.emergencyLevel.setText(helpRequest.getEmergencyLevel());
        holder.type.setText(helpRequest.getHelpType());
        holder.loc.setText(helpRequest.getLocation());
        holder.repeat.setText(helpRequest.getRepeated());
        holder.date.setText(helpRequest.getDate());
        holder.time.setText(helpRequest.getTime());
        holder.status.setText(helpRequest.getStatus());
        holder.volName.setText(helpRequest.getVolunteerName());
        holder.volPhone.setText(helpRequest.getVolunteerPhone());
        holder.volEmail.setText(helpRequest.getVolunteerEmail());
        holder.volRating.setText(helpRequest.getVolunteerRating());
        holder.id.setText(helpRequest.getId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView description, emergencyLevel, type, loc, repeat, date, time, status, volName, volEmail, volPhone, volRating, id;

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
            volName = itemView.findViewById(R.id.volunteer_name_show);
            volEmail = itemView.findViewById(R.id.volunteer_email_show);
            volPhone = itemView.findViewById(R.id.volunteer_phone_show);
            volRating = itemView.findViewById(R.id.vol_rating_show);
            id = itemView.findViewById(R.id.id_show);
        }
    }
}
