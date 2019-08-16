package com.example.mood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class inboxActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0;
    Toolbar toolbar;
  RecyclerView recyclerView;
  EditText editText;
  List<inboxModel>inboxmodelList;
    inboxAdapter inboxAdapter;
    String username, Number,myMessage ;

    FloatingActionButton fab;
    String textRcvd;
    IntentFilter intentFilter;




    private BroadcastReceiver intentReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

      textRcvd=intent.getExtras().getString("message");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        username = extras.getString("Name");
        Number = extras.getString("Number");


        intentFilter=new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(username);
        getSupportActionBar().setSubtitle(Number);
       // getSupportActionBar().setLogo(R.drawable.iceberg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityCompat.requestPermissions(inboxActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);




        fab=findViewById(R.id.sendbtn);
        editText=findViewById(R.id.typeText);
        recyclerView=findViewById(R.id.inboxRcy);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);




        inboxmodelList=new ArrayList<>();



        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String SENT="Message Sent";
                 String DELIVERED="Message Delivered";

                 PendingIntent SentpendingIntent=PendingIntent.getBroadcast(inboxActivity.this,0,new Intent(SENT),0);
                 PendingIntent DelpendingIntent=PendingIntent.getBroadcast(inboxActivity.this,0,new Intent(DELIVERED),0);

                 SmsManager smsManager=SmsManager.getDefault();

                 if (editText !=null){

                     myMessage=editText.getText().toString();
                     smsManager.sendTextMessage(Number,null,myMessage,SentpendingIntent,DelpendingIntent);
                     Toast.makeText(inboxActivity.this,"Message Sent \n"+myMessage,Toast.LENGTH_SHORT).show();




                    // inboxAdapter.notifyDataSetChanged();


                     editText.getText().clear();


                 }

                 else{

                     Toast.makeText(inboxActivity.this,"Invalid",Toast.LENGTH_SHORT).show();


                 }


             }
         });

        inboxmodelList.add(new inboxModel( "Hello","Hey there"));
       // inboxAdapter.notifyDataSetChanged();
        inboxAdapter=new inboxAdapter(inboxActivity.this,inboxmodelList);
        recyclerView.setAdapter(inboxAdapter);







    }

    @Override
    protected void onPostResume() {
        registerReceiver(intentReceiver,intentFilter);
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }
}


