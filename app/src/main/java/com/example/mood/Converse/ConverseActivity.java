package com.example.mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.mood.profile.profileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE;
import static android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE;
import static android.telephony.SmsManager.RESULT_ERROR_NULL_PDU;
import static android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF;

public class ConverseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    List<converseModel> listModel;

    public String getAdd;

    String  connect;
    String contactName;
    String getNo;

    EditText e1;
    SmsManager smsManager;
    FloatingActionButton floatingActionButton;
    converseAdapter converse;

    Cursor cursor, c,curse;
    ContentResolver cr,cre;

    public String namba;
    long time;
    String finalDate;
    public String  ContactName,contactId;
    converseModel conv2,conv1,conv;

    //for inbox
    String body,date,address;

    //for sent messages

    String Date, Body;

    InputStream input;
    int pic;
    Bitmap photo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        getAdd = bundle.getString("Address");



        toolbar = findViewById(R.id.tll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connect = getAdd;


        floatingActionButton = findViewById(R.id.sending);
        listView = findViewById(R.id.chatListView);
        e1 = findViewById(R.id.myEDit);


       /* e1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;
                  *//*  final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;*//*

                if (event.getRawX() <= (e1.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    // your action here
                    Toast.makeText(ConverseActivity.this, "You want an emoji?", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }

        });

*/




        listModel = new ArrayList<>();
        converse = new converseAdapter(ConverseActivity.this,listModel);

        ///////////////getting the sent messages///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        Uri uri=Uri.parse("content://sms/inbox");

        cr=getApplicationContext().getContentResolver();
        cursor=cr.query(uri,null,"thread_id="+connect,null,"date asc");

        if (cursor !=null){

            cursor.moveToFirst();

        }

        for (int i=0; i<cursor.getCount(); i++){

            body=cursor.getString(cursor.getColumnIndexOrThrow("body"));
            address=cursor.getString(cursor.getColumnIndexOrThrow("address"));
            date=cursor.getString(cursor.getColumnIndexOrThrow("date"));



            converseModel cmd=new converseModel();

            cmd.setReceived_msg(body);
            cmd.setLeft(true);
            cmd.setTime_receivd(date);
            cmd.setName_sender(address);

            listModel.add(cmd);

            getSupportActionBar().setSubtitle(address);
            getSupportActionBar().setTitle(address);


            cursor.moveToNext();

        }
        if (cursor==null){

            Toast.makeText(getApplicationContext(),"No reply from this contact",Toast.LENGTH_LONG).show();
            cursor.close();

        }


        cre=getApplicationContext().getContentResolver();
        Uri uri1=Uri.parse("content://sms/sent");

        c=cre.query(uri1,null,"thread_id="+connect,null,"date asc");

        if (c !=null){

            c.moveToFirst();
        }

        for (int m=0; m<c.getCount(); m++){

            Body=c.getString(c.getColumnIndexOrThrow("body"));
            Date=c.getString(c.getColumnIndexOrThrow("date"));



            converseModel cmd1=new converseModel();

            cmd1.setSent_msg(Body);
            cmd1.setLeft(false);
            cmd1.setTime_sent(Date);


            listModel.add(cmd1);

            c.moveToNext();
        }
        if (c==null){

            Toast.makeText(getApplicationContext(),"No reply from this contact",Toast.LENGTH_LONG).show();
            c.close();

        }


        // encode the phone number and build the filter URI
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));

        ContentResolver ctrs=getApplicationContext().getContentResolver();

        curse = ctrs.query(contactUri, null, null, null,null);

        if (curse !=null) {

            curse.moveToFirst();

        }

        for (int d=0; d<curse.getCount(); d++){


            // Get values from contacts database:
            ContactName = curse.getString(curse.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            contactId=curse.getString(curse.getColumnIndex(ContactsContract.PhoneLookup._ID));

            if (ContactName==null){



            }else {

                getSupportActionBar().setTitle(ContactName);


            }
            curse.moveToNext();

        }
        if (curse==null){

            Toast.makeText(getApplicationContext(),"Contact doesn't exist",Toast.LENGTH_LONG).show();

            curse.close();
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SENT = "Message Sent";
                String DELIVERED = "Message Delivered";

                PendingIntent SentpendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(SENT), 0);
                PendingIntent DelpendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(DELIVERED), 0);

                String myMessage = e1.getText().toString();

                //If edit text is Empty don't send the message
                if (TextUtils.isEmpty(myMessage)) {

                    e1.setError("Can't send empty Text");
                    Toast.makeText(ConverseActivity.this, "Can't send a blank sms", Toast.LENGTH_SHORT).show();

                    return;

                    //but if its typed you can send a message
                } else {

                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(address, null, myMessage, SentpendingIntent, DelpendingIntent);

                    Toast.makeText(ConverseActivity.this, "Message Sent \n", Toast.LENGTH_SHORT).show();

                    //getting the current time
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    //now adding sent messages
                    conv2 = new converseModel();
                    conv2.setLeft(false);
                    conv2.setSent_msg(myMessage);
                    conv2.setTime_sent(currentTime);
                    listModel.add(conv2);


                    //when we send the sms the edit text to clear
                    e1.getText().clear();


                }
            }
        });



        converse.notifyDataSetChanged();
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setSelection(converse.getCount() - 1);
        listView.setAdapter(converse);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.inbox_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.call:

                Toast.makeText(getApplicationContext(),"Calling"+address,Toast.LENGTH_LONG).show();
                onRestart();

                break;

            case R.id.profile:

                Intent intent=new Intent(getApplicationContext(), profileActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("Number",address);
                bundle.putString("Name",ContactName);


                startActivity(intent);
                finish();

                break;





        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onRestart() {

        Intent intent=new Intent(ConverseActivity.this,ConverseActivity.class);
        startActivity(intent);
        finish();

        super.onRestart();
    }
}









