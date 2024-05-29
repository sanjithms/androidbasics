package com.example.studyapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signupactivity extends AppCompatActivity {
    EditText regusername,regemail,regphone,regconfirpass,regpass;
    Button regisbtn;
    TextView logintxt;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public GoogleSignInClient client;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        regusername=findViewById(R.id.regusername);
        regemail=findViewById(R.id.regemail);
        regphone=findViewById(R.id.regNumber);
        regconfirpass=findViewById(R.id.regconfirmpassword);
        regpass=findViewById(R.id.regpassword);
        regisbtn=findViewById(R.id.regButton);
        logintxt=findViewById(R.id.loginclickText);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signupactivity.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage(" please wait");
        progressDialog.setCancelable(false);
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        client= GoogleSignIn.getClient(this,options);
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupactivity.this,loginactivity.class));
                finish();
            }
        });

        regisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=regusername.getText().toString();
                String emailtxt=regemail.getText().toString();
                String phonenum=regphone.getText().toString();
                String passtxt=regpass.getText().toString();
                String confirmtxt=regconfirpass.getText().toString();
                if (TextUtils.isEmpty(emailtxt) ||TextUtils.isEmpty(passtxt)||TextUtils.isEmpty(confirmtxt)){
                    Toast.makeText(signupactivity.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                } else if (passtxt.length()<6||confirmtxt.length()<6) {
                    Toast.makeText(signupactivity.this, "Enter strong password", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.show();
                    registerfun(emailtxt,passtxt);
                }
            }

            private void registerfun(String emailtxt, String passtxt) {
                auth.createUserWithEmailAndPassword(emailtxt,passtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Get the currently signed-in user
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if (firebaseUser != null) {
                                String uid = firebaseUser.getUid();
                                String username = regusername.getText().toString();
                                String emailtxt = regemail.getText().toString();
                                String phonenum = regphone.getText().toString();
                                String passtxt = regpass.getText().toString();


                                reguser user = new reguser(username, emailtxt, phonenum, passtxt, uid);


                                database.getReference().child("user").child(uid).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(signupactivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(signupactivity.this, MainActivity.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(signupactivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(signupactivity.this, "Registration failed: User is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(signupactivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}