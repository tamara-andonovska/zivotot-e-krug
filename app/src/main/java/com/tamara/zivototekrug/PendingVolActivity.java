package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingVolActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference root = mDatabase.getReference().child("HelpRequests");
    private pendingVolAdapter adapter;
    private ArrayList<HelpRequest> list;

    FirebaseUser mUser;
    FirebaseAuth mAuth;

    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_vol);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mEmail = mUser.getEmail();

        recyclerView = findViewById(R.id.recyclerview_volunteer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new pendingVolAdapter(this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HelpRequest helpRequest = dataSnapshot.getValue(HelpRequest.class);
                    if (helpRequest.getStatus().equals("Pending") && helpRequest.getVolunteerEmail().equals(mEmail)){
                        list.add(helpRequest);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}