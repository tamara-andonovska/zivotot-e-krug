package com.tamara.zivototekrug;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText inputEmail, inputPassword, inputConfirmPassword, inputPhoneNumber, inputName;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnRegister;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    static final String USER = "user";

    private User user;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount = findViewById(R.id.already_have_account);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirm_password);
        inputPhoneNumber = findViewById(R.id.input_phone_number);
        inputName = findViewById(R.id.input_name);
        btnRegister = findViewById(R.id.register_button);
        radioGroup = findViewById(R.id.select_type_user);

        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });

    }

    private void PerformAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
        String fullName = inputName.getText().toString();
        String phone = inputPhoneNumber.getText().toString();

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        userType = radioButton.getText().toString();

        if (!email.matches(emailPattern)){
            inputEmail.setError("Enter Correct Email Address");
        } else if (password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter proper password");
        } else if (!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Password does not match");
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            user = new User(email, password, fullName, phone, userType, "0.0", "0");

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity(mUser);
                        Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity(FirebaseUser currentUser) {
        String keyId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent intent;
        if (userType.equals("Elderly")){
            intent = new Intent(RegisterActivity.this, ElderlyHomeActivity.class);
        } else if (userType.equals("Volunteer")){
            intent = new Intent(RegisterActivity.this, VolunteerHomeActivity.class);
        } else {
            intent = new Intent(RegisterActivity.this, OrganizerHomeActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}