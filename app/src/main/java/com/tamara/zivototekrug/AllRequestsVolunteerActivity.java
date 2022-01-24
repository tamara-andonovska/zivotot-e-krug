package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllRequestsVolunteerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference root = mDatabase.getReference().child("HelpRequests");
    private requestsVolunteerAdapter adapter;
    private ArrayList<HelpRequest> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_requests_volunteer);

        recyclerView = findViewById(R.id.recyclerview_volunteer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new requestsVolunteerAdapter(this, list);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HelpRequest helpRequest = dataSnapshot.getValue(HelpRequest.class);
                    if (helpRequest.getStatus().equals("Active")){
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

//    @Override
//    protected void onStart() {
//        overridePendingTransition(0,0);
//        super.onStart();
//
//    }
}