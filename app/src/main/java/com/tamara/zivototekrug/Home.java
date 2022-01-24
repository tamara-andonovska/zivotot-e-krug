package com.tamara.zivototekrug;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user");
        if (mUser != null) {
            String uid = mUser.getUid();

            ref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    String type = user.getUserType();
                    //Toast.makeText(MainActivity.this, type, Toast.LENGTH_LONG).show(); //type tuka e kako sto treba!!!!
                    sendUserToNextActivity(type);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void sendUserToNextActivity(String typeOfUser) {
        //treba soodveten Home da se pojavuva spored userType = "volunteer", "organizer", "elderly"
        Intent intent;
        if (typeOfUser.equals("Organizer")){
            intent = new Intent(Home.this, OrganizerHomeActivity.class);
        } else if (typeOfUser.equals("Volunteer")){
            intent = new Intent(Home.this, VolunteerHomeActivity.class);
        } else {
            intent = new Intent(Home.this, ElderlyHomeActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
