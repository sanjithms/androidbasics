package com.example.studyapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class loginactivity extends AppCompatActivity {
    EditText loguername,logpass;
    Button loginbtn,loggoogle,logfacebook;
    TextView regintxt;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public GoogleSignInClient client;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        loggoogle=findViewById(R.id.loggoglebtn);
        loginbtn=findViewById(R.id.loginButton);
        regintxt=findViewById(R.id.regiterclicktxt);
        loguername=findViewById(R.id.logusername);
        logpass=findViewById(R.id.logpassword);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(loginactivity.this);
        progressDialog.setTitle("Sign in ....");
        progressDialog.setMessage(" please wait");
        progressDialog.setCancelable(false);

        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client= GoogleSignIn.getClient(this,options);
        regintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginactivity.this,signupactivity.class));
                finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt=loguername.getText().toString();
                String passtxt=logpass.getText().toString();
                if (emailtxt.isEmpty()||passtxt.isEmpty()){
                    Toast.makeText(loginactivity.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    loginfun(emailtxt,passtxt);
                }
            }

            private void loginfun(String emailtxt, String passtxt) {
                auth.signInWithEmailAndPassword(emailtxt,passtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            Toast.makeText(loginactivity.this, "logined successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(loginactivity.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(loginactivity.this, "Sign in first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        loggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                sign();
            }
        });
    }
    int RC_SIGN_IN=40;
    private void sign() {
        Intent intent=client.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth (account.getIdToken());

            } catch (ApiException e) {
                progressDialog.dismiss();
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,  null);
        auth.signInWithCredential (credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        googleuser users = new googleuser();
                        users.setName(user.getDisplayName());
                        users.setUid(user.getUid());
                        users.setPhonenumber("1234567890");
                        users.setEmail(user.getEmail());

                        // Store user data using UID as the key
                        FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).setValue(users)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(loginactivity.this, "Google login successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginactivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(loginactivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(loginactivity.this, "Authentication Failed. User is null.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(loginactivity.this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}