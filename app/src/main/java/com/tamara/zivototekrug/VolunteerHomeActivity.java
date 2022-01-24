package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerHomeActivity extends AppCompatActivity {

    Button btnLogOut;
    Button btnActiveRequests, btnAssignedRequests, btnDoneRequests, btnPendingRequests;
    TextView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        home = findViewById(R.id.volunteer_text_home);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user");
        String uid = mUser.getUid();

        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                home.setText("Welcome, " + user.getFullName()+"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
            });

        btnActiveRequests = findViewById(R.id.see_active_requests_volunteer_btn);
        btnActiveRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerHomeActivity.this, AllRequestsVolunteerActivity.class);
                startActivity(intent);
            }
        });

        btnPendingRequests = findViewById(R.id.see_pending_requests_volunteer_btn);
        btnPendingRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerHomeActivity.this, PendingVolActivity.class);
                startActivity(intent);
            }
        });

        btnAssignedRequests = findViewById(R.id.see_assigned_requests_volunteer_btn);
        btnAssignedRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerHomeActivity.this, AssignedVolunteerActivity.class);
                startActivity(intent);
            }
        });

        btnDoneRequests = findViewById(R.id.see_done_requests_volunteer_btn);
        btnDoneRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerHomeActivity.this, DoneVolunteerActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut = findViewById(R.id.logout_btn);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(VolunteerHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}