package com.example.mood.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;


import com.example.mood.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    Toolbar toolbar;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        toolbar=findViewById(R.id.bar);
        circleImageView=findViewById(R.id.myprofile);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();

        assert bundle != null;
        String number= bundle.getString("Number");
        assert bundle != null;
        String name= bundle.getString("Name");


        Bitmap bitmap = getIntent().getParcelableExtra("Bitmap");

       t1=findViewById(R.id.nametextview);
       t2=findViewById(R.id.namba);
       //circleImageView.setImageBitmap(bitmap);


       t1.setText(name);
       t2.setText(number);





    }
}
