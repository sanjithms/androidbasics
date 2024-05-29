package com.example.studyapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detailed_view_activity extends AppCompatActivity {
    TextView detaildesc,detailtitle;
    ImageView detailimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        detaildesc=findViewById(R.id.detaildesc);
        detailimage=findViewById(R.id.detailimage);
        detailtitle=findViewById(R.id.deataititle);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            detailtitle.setText(bundle.getString("Title"));
            detaildesc.setText(bundle.getString("Description"));
            Glide.with(this).load(bundle.getString("Image")).into(detailimage);
        }

    }
}