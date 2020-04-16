package com.example.mood.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;

import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class inboxActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0;
    private static inboxActivity inst;
    SmsManager smsManager,smsManager1;
    PendingIntent SentpendingIntent;
    PendingIntent DeliverypendingIntent;
    String SENT="Message Sent";
    String DELIVERED="Message Delivered";

    Toolbar toolbar;
    EditText editText;
    ImageButton fab;
    CircleImageView imageView;

    String username,Number;
    int pic;
    String myMessage;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

        if (!(!username.equals(null) || !Number.equals(null))){

            Toast.makeText(getApplicationContext(),"Invalid Number",Toast.LENGTH_LONG).show();

        }

        //tool Bar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(username);
        getSupportActionBar().setSubtitle(Number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActivityCompat.requestPermissions(inboxActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);

        //Finding views by Id
        fab=findViewById(R.id.sendbtn);
        editText=findViewById(R.id.typeText);
        imageView=findViewById(R.id.image);
        //imageView.setImageResource(pic);

             SentpendingIntent=PendingIntent.getBroadcast(inboxActivity.this,1,new Intent(SENT),0);
             DeliverypendingIntent=PendingIntent.getBroadcast(inboxActivity.this,2,new Intent(DELIVERED),0);


        //setting  Action to the button
        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 myMessage=editText.getText().toString();
                 //If edit text is Empty don't send the message
                 if (TextUtils.isEmpty(myMessage)){
                     editText.setError("Can't send empty Text");

                  return;
                 }
                 sendMessage();
             }
         });


    }

    public void sendMessage(){

        smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(Number,null,myMessage,SentpendingIntent,DeliverypendingIntent);
        Toast.makeText(inboxActivity.this,"Message Sent \n",Toast.LENGTH_SHORT).show();
        editText.getText().clear();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();

        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(),"Pending delivery",Toast.LENGTH_LONG).show();

        }
    }
}


