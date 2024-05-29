package com.example.studyapp1;

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

public class editprofileactivity extends AppCompatActivity {
    EditText editusername,editphonenumber;
    Button editbtn;
    String username,phonenumber;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileactivity);
        editusername=findViewById(R.id.editusername);
        editphonenumber=findViewById(R.id.editNumber);
        editbtn=findViewById(R.id.editButton);
        reference= FirebaseDatabase.getInstance().getReference();
        showdata();
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editusername.getText().toString().isEmpty() && !editphonenumber.getText().toString().isEmpty()) {
                    if (isNameChanged() || isphonechanged()) {
                        Toast.makeText(editprofileactivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(editprofileactivity.this, "No changes made", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(editprofileactivity.this, profilefragment.class));
                    finish();
                } else {
                    Toast.makeText(editprofileactivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public boolean isNameChanged(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        if (!username.equals(editusername.getText().toString())){
            reference.child("user").child(userId).child("name").setValue(editusername.getText().toString());
            username = editusername.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public boolean isphonechanged(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        if (!phonenumber.equals(editphonenumber.getText().toString())){
            reference.child("user").child(userId).child("phonenumber").setValue(editphonenumber.getText().toString());
            phonenumber = editphonenumber.getText().toString();
            return true;
        } else{
            return false;
        }
    }
    public void showdata(){
        Intent intent=getIntent();
        username=intent.getStringExtra("name");
        phonenumber=intent.getStringExtra("phonenumber");

        editusername.setText(username);
        editphonenumber.setText(phonenumber);
    }


}