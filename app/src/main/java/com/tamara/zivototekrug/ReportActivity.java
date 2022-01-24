package com.tamara.zivototekrug;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

public class ReportActivity extends AppCompatActivity {

    EditText reported;
    Button btnSendReport;

    DatabaseReference ref;
    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent startingIntent = getIntent();
        String eEmail = startingIntent.getStringExtra("elderlyEmail"); //na korisnikot koj treba da e ocenet
        String eId = startingIntent.getStringExtra("userID"); //na korisnikot koj treba da e ocenet
        String reqId = startingIntent.getStringExtra("requestID");

        ref = FirebaseDatabase.getInstance().getReference().child("Reports");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String volId = mUser.getUid();
        String volEmail = mUser.getEmail();
        //Toast.makeText(this, eEmail, Toast.LENGTH_SHORT).show();

        reported = findViewById(R.id.report_edit_text);
        btnSendReport = findViewById(R.id.send_report_btn);
        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rep = reported.getText().toString();
                sendReport(volId, volEmail, eId, eEmail, reqId, rep);
                Intent intent = new Intent(ReportActivity.this, DoneVolunteerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void sendReport(String volId, String volEmail, String eId, String eEmail, String reqId, String rep) {
        report = new Report(volId, volEmail, eId, eEmail, reqId, rep);
        ref.push().setValue(report);
        Toast.makeText(ReportActivity.this, "Report sent.", Toast.LENGTH_SHORT).show();
    }
}