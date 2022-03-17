package com.tamara.zivototekrug;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RequestActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int mins;
    private int secs;

    Calendar mCalendar;
    TextView date;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePicker timePicker;

    RadioGroup radioGroup1;
    RadioButton radioButton1;

    EditText description;
    CheckBox repeated;

    RadioGroup radioGroup2;
    RadioButton radioButton2;

    EditText location;
    Button btnSendRequest;

    DatabaseReference mDatabase;
    private HelpRequest helpRequest;

    TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent startingIntent = getIntent();
        String mEmail = startingIntent.getStringExtra("mEmail");
        String userId = startingIntent.getStringExtra("userID");
        String rating = startingIntent.getStringExtra("userRating");
        String phone = startingIntent.getStringExtra("userPhone");

        tv = findViewById(R.id.email_request);
        tv.setText(mEmail);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("HelpRequests");

        radioGroup1 = findViewById(R.id.help_type);
        radioGroup2 = findViewById(R.id.emergency_level);
        repeated = findViewById(R.id.repeated_checkbox);
        description = findViewById(R.id.activity_descr);
        location = findViewById(R.id.help_location);
        btnSendRequest = findViewById(R.id.send_request_btn);

        mCalendar = Calendar.getInstance();
        mCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Skopje"));
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH);
        day = mCalendar.get(Calendar.DAY_OF_MONTH);
        hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mins = mCalendar.get(Calendar.MINUTE);
        secs = mCalendar.get(Calendar.SECOND);

        date = findViewById(R.id.date_picker);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RequestActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String d = day + "/" + month + "/" + year;
                date.setText(d);
            }
        };

        timePicker = findViewById(R.id.time_picker);
        timePicker.setHour(this.hour);
        timePicker.setMinute(this.mins);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int mins) {
                RequestActivity.this.hour = hour;
                RequestActivity.this.mins = mins;
            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(userId, rating, phone);
                Intent intent = new Intent(RequestActivity.this, ElderlyHomeActivity.class);
                Toast.makeText(RequestActivity.this, "Help Request Sent", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendRequest(String userId, String rating, String phone) {
        String mEmail = tv.getText().toString();
        int radioId1 = radioGroup1.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioId1);
        String helpType = radioButton1.getText().toString();
        int radioId2 = radioGroup2.getCheckedRadioButtonId();
        radioButton2 = findViewById(radioId2);
        String emergencyLevel = radioButton2.getText().toString();
        String descriptionActivity = description.getText().toString();
        String locationActivity = location.getText().toString();
        String repeatedActivity="no";

        if (repeated.isChecked()){
            repeatedActivity = "yes";
        }

        String dateString = date.getText().toString();

        String timeString = timePicker.getHour() + ":" + timePicker.getMinute();

        //ova ne se cuva ko so treba, treba nekako datata i vremeto
        //da se izvlecat od date i time picker za da moze da se postavi
        //vo databaza kako string
        //Date date = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //String dateActitivy = dateFormat.format(date);

        //String dateString = datePicker.toString();

        //Toast.makeText(RequestActivity.this, mEmail, Toast.LENGTH_SHORT).show(); //go pokazuva korektniot mail
        //Toast.makeText(RequestActivity.this, dateString, Toast.LENGTH_SHORT).show(); //okej e
        //Toast.makeText(RequestActivity.this, timeString, Toast.LENGTH_SHORT).show(); //okej e

        String ratingStr = rating;

        String volName = "";
        String volNumber ="";
        String volEmail = "";
        String volRating = "";
        String volId = "";
        String status = "Active";
        String userIdStr = userId;
        String phoneStr = phone;

//        String id = mDatabase.child("HelpRequests").push().getKey();
//        helpRequest = new HelpRequest(id, mEmail, helpType, descriptionActivity, emergencyLevel, repeatedActivity, locationActivity, dateString, timeString, volName, volNumber, volEmail, status);
//        mDatabase.push().setValue(helpRequest);

        String id = mDatabase.push().getKey();
        helpRequest = new HelpRequest(id, mEmail, phoneStr, helpType, descriptionActivity, emergencyLevel, repeatedActivity, locationActivity, ratingStr, dateString, timeString, volName, volNumber, volEmail, volRating, status, userIdStr, volId);
        mDatabase.child(id).setValue(helpRequest);

    }
}