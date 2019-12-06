package com.example.mood.Converse;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.mood.Fragments.testingAdapter;
import com.example.mood.Fragments.testingModel;
import com.example.mood.R;
import com.example.mood.profile.profileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConverseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    List<converseModel> listModel;

    public String getAdd,getThread;

    String  connect;
    String contactName;
    String getNo;

    EditText e1;
    SmsManager smsManager;
    FloatingActionButton floatingActionButton;
    converseAdapter converse;


    Cursor cursor,curse;
    ContentResolver cr;

    public String namba;
    public String  ContactName,contactId;
    converseModel conv2;

    testingModel test;
    List<testingModel>testList;
    testingAdapter adapter1;
    converseAdapter adapter;

    //for inbox
    String body,address;

    //for sent messages

    String  Body;
    Long Date;
    Long date;

    InputStream input;
    int pic;
    Bitmap photo;

    String timeGot;
    String timeSend;
    String name;
    String tarehe, tarehe1;

    public  String add;




    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converse);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        getAdd = bundle.getString("Address");
        getThread = bundle.getString("Thread");



        toolbar = findViewById(R.id.tll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connect = getAdd;



        floatingActionButton = findViewById(R.id.sending);

        listView = findViewById(R.id.chatListView);

        e1 = findViewById(R.id.myEDit);


        e1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;

                if (event.getRawX() <= (e1.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    // your action here
                    Toast.makeText(ConverseActivity.this, "You want an emoji?", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }

        });


        adapter=new converseAdapter(getApplication(),R.layout.inbox_layout);


        loadSms();



       /* cre=getApplicationContext().getContentResolver();
        Uri uri1=Uri.parse("content://sms/sent");

        c=cre.query(uri1,null,"thread_id="+connect,null,"date asc");

        if (c !=null){

            c.moveToFirst();
        }

        for (int m=0; m<c.getCount(); m++){

            Body=c.getString(c.getColumnIndexOrThrow("body"));
            Date=c.getLong(c.getColumnIndexOrThrow("date"));

            Date date1=new Date(Date); // accept long value.
            tarehe1 = date1.toString();
            System.out.println(date1);


            converseModel cmd1=new converseModel();

            cmd1.setSent_msg(Body);
            cmd1.setLeft(false);
            cmd1.setTime_sent(tarehe1);

            timeSend=cmd1.getTime_sent();


            listModel.add(cmd1);

            c.moveToNext();
        }
        if (c==null){

            Toast.makeText(getApplicationContext(),"No reply from this contact",Toast.LENGTH_LONG).show();
            c.close();

        }*/




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadSms();
                String SENT = "Message Sent";
                String DELIVERED = "Message Delivered";

                PendingIntent SentpendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(SENT), 0);
                PendingIntent DelpendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(DELIVERED), 0);

                String myMessage = e1.getText().toString();

                //If edit text is Empty don't send the message

                if (e1.getText().toString().trim().isEmpty()) {

                    floatingActionButton.setVisibility(View.INVISIBLE);
                    e1.setError("Can't send empty Text");
                    Toast.makeText(ConverseActivity.this, "Can't send a blank sms", Toast.LENGTH_SHORT).show();


                    return;

                }
                    floatingActionButton.setVisibility(View.VISIBLE);

                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(address, null, myMessage, SentpendingIntent, DelpendingIntent);

                    Toast.makeText(ConverseActivity.this, "Message Sent \n", Toast.LENGTH_SHORT).show();
                    //getting the current time
                  //  String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                     loadSms();
                    //when we send the sms the edit text to clear
                    e1.getText().clear();

            }
        });

    }
    public void loadSms(){

        testList=new ArrayList<>();

        Uri uri=Uri.parse("contentms/inbox://s");

        cr=getApplicationContext().getContentResolver();
        cursor=cr.query(uri,null,"thread_id="+connect,null,"date asc");

        if (cursor !=null){

            cursor.moveToFirst();

        }

        for (int i=0; i<cursor.getCount(); i++){

            body=cursor.getString(cursor.getColumnIndexOrThrow("body"));
            address=cursor.getString(cursor.getColumnIndexOrThrow("address"));
            date=cursor.getLong(cursor.getColumnIndexOrThrow("date"));

            add=address;

            Date date1=new Date(date); // accept long value.
            tarehe = date1.toString();


            converseModel cmd1=new converseModel();

            cmd1.setReceived_msg(body);
            cmd1.setName_sender(address);
            cmd1.setLeft(true);
            cmd1.setTime_receivd(tarehe);
            rcvMessage(cmd1);




            cursor.moveToNext();

        }
        if (cursor==null){

            Toast.makeText(getApplicationContext(),"No reply from this contact",Toast.LENGTH_LONG).show();
            cursor.close();

        }

        getSupportActionBar().setTitle(add);



        //gettting the sent messages
        Cursor c;
        ContentResolver cre;

        cre=getApplicationContext().getContentResolver();
        Uri uri1=Uri.parse("content://sms/sent");

        c=cre.query(uri1,null,"thread_id="+connect,null,"date asc");

        if (c !=null){

            c.moveToFirst();
        }

        for (int m=0; m<c.getCount(); m++){

            Body=c.getString(c.getColumnIndexOrThrow("body"));
            Date=c.getLong(c.getColumnIndexOrThrow("date"));



            converseModel cmd1=new converseModel();

            cmd1.setSent_msg(Body);
            cmd1.setLeft(false);
            cmd1.setTime_sent(tarehe1);
            cmd1.setTime_sent(Date.toString());
            sendMessage(cmd1);

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

            name=contactName;

            if (ContactName==null){



            }else {

                getSupportActionBar().setTitle(ContactName);
                getSupportActionBar().setSubtitle(address);


            }
            curse.moveToNext();

        }
        if (curse==null){

            Toast.makeText(getApplicationContext(),"Contact doesn't exist",Toast.LENGTH_LONG).show();

            curse.close();
        }


        listView.setAdapter(adapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setSelection(adapter.getCount() - 1);


    }

    private void sendMessage(converseModel message) {

        // chatArrayAdapter=new ChatArrayAdapter(inboxActivity.this,R.drawable.sent_bg);
        adapter.add(message);
        adapter.notifyDataSetChanged();

    }

    private void rcvMessage(converseModel message) {

        adapter.add(message);
        adapter.notifyDataSetChanged();

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

                Toast.makeText(getApplicationContext(),"Calling\t"+add,Toast.LENGTH_LONG).show();

                break;


            case  R.id.delete:


                Toast.makeText(getApplicationContext(),"Deleting"+getThread,Toast.LENGTH_LONG).show();


                Uri thread = Uri.parse("content://sms/inbox");

                getApplicationContext(). getContentResolver().delete(thread,"thread_id="+getThread, null);

                break;

            case R.id.profile:

                Intent intent=new Intent(getApplicationContext(), profileActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("Number",add);
                bundle.putString("Name",name);


                intent.putExtras(bundle);
                startActivity(intent);
                finish();

                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        loadSms();
        super.onStart();

    }

    @Override
    protected void onPause() {
        loadSms();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        loadSms();
        super.onRestart();
    }
}










