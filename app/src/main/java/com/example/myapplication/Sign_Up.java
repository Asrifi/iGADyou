package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Sign_Up extends AppCompatActivity implements View.OnClickListener {

    private TextView regnow, signinn;
    private EditText editusername, editemaili, editpassword, editconfirmpassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signinn = (TextView) findViewById(R.id.signinn);
        signinn.setOnClickListener(this);

        regnow = (Button) findViewById(R.id.regnow);
        regnow.setOnClickListener(this);

        editusername = (EditText) findViewById(R.id.username);
        editemaili = (EditText) findViewById(R.id.emaili);
        editpassword = (EditText) findViewById(R.id.password);
        editconfirmpassword = (EditText) findViewById(R.id.passs);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.signinn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.regnow:
                regnow();
                break;
        }
    }

    private void regnow() {
        String emaili= editemaili.getText().toString().trim();
        String password= editpassword.getText().toString().trim();
        String username= editusername.getText().toString().trim();
        String confirmpass= editconfirmpassword.getText().toString().trim();
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern digitcase = Pattern.compile("[0-9]");
        Pattern lowercase = Pattern.compile("[a-z]");

        if(username.isEmpty()){
            editusername.setError("Username is required!");
            editusername.requestFocus();
            return;
        }

        if(emaili.isEmpty()){
            editemaili.setError("Email is required!");
            editemaili.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emaili).matches()){
            editemaili.setError("Please provide a valid email!");
            editemaili.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editpassword.setError("Password is required!");
            editpassword.requestFocus();
            return;
        }
        if(!uppercase.matcher(password).find()){
            editpassword.setError("1 or more uppercase is required!");
            editpassword.requestFocus();
            return;
        }

        if(!digitcase.matcher(password).find()){
            editpassword.setError("1 or more number is required!");
            editpassword.requestFocus();
            return;
        }

        if(!lowercase.matcher(password).find()){
            editpassword.setError("1 or more lowercase is required!");
            editpassword.requestFocus();
            return;
        }
        if(password.length() < 8){
            editpassword.setError("Minimum password length should be 8 characters!");
            editpassword.requestFocus();
            return;
        }
        if (confirmpass.isEmpty()) {
            editconfirmpassword.setError("Confirm Password is required!");
            editconfirmpassword.requestFocus();
            return;

        }
            if (!confirmpass.equals(password)) {
                editconfirmpassword.setError("Password do not match!");
                editconfirmpassword.requestFocus();
                return;

        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emaili, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(username, emaili);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(Sign_Up.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }else{
                                        Toast.makeText(Sign_Up.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }else{
                    Toast.makeText(Sign_Up.this, "Failed to register!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}