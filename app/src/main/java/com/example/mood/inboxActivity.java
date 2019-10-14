package com.example.mood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;

import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mood.walkThrough.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class inboxActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0;
    private static inboxActivity inst;
    SmsManager smsManager,smsManager1;
    IntentFilter intentFilter;

    Toolbar toolbar;
    EditText editText;
    FloatingActionButton fab;
    CircleImageView imageView;

    String username,Number;
    int pic;
    String myMessage;

    String messo,add;

    String textRcvd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);




        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        username = extras.getString("Name");
        Number = extras.getString("Number");
        pic=extras.getInt("pic");

        final String nn=Number;
        final String name=username;

        //tool Bar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle(nn);
        //getSupportActionBar().setLogo(pic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActivityCompat.requestPermissions(inboxActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);


        //Finding views by Id
        fab=findViewById(R.id.sendbtn);
        editText=findViewById(R.id.typeText);
        imageView=findViewById(R.id.image);
        //imageView.setImageResource(pic);


        //setting  Action to the button
        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String SENT="Message Sent";
                 String DELIVERED="Message Delivered";

                 PendingIntent SentpendingIntent=PendingIntent.getBroadcast(inboxActivity.this,0,new Intent(SENT),0);
                 PendingIntent DelpendingIntent=PendingIntent.getBroadcast(inboxActivity.this,0,new Intent(DELIVERED),0);


                 myMessage=editText.getText().toString();

                 //If edit text is Empty don't send the message
                 if (TextUtils.isEmpty(myMessage)){

                     editText.setError("Can't be empty");
                     Toast.makeText(inboxActivity.this,"Can't send a blank sms",Toast.LENGTH_SHORT).show();

                     //but if its typed you can send a message
                 } else{

                     smsManager=SmsManager.getDefault();
                     smsManager.sendTextMessage(Number,null,myMessage,SentpendingIntent,DelpendingIntent);

                     Toast.makeText(inboxActivity.this,"Message Sent \n",Toast.LENGTH_SHORT).show();

                     //clearing the text from the inbox
                     editText.getText().clear();

                 }

             }
         });

        //t1.setVisibility(View.INVISIBLE);
       // t2.setVisibility(View.INVISIBLE);
       // t2.setText(messo);

    }



}


