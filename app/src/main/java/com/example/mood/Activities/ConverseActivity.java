package com.example.mood.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.mood.Adapter_Classes.InboxAdapter;
import com.example.mood.Model_Classes.InboxModel;
import com.example.mood.Model_Classes.testingModel;
import com.example.mood.R;
import com.example.mood.updateConvos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ConverseActivity extends AppCompatActivity {

    Toolbar toolbar;
    List<InboxModel> listModel;
    public String getAdd, getThread;
    String connect;
    EditText e1;
    SmsManager smsManager;
    FloatingActionButton floatingActionButton,sim1,sim2;
    RecyclerView recyclerView;
     MediaPlayer mp;
     ImageButton imageButton;

    Notification notification ;

    Cursor cursor, curse;
    ContentResolver cr;
    public String ContactName, contactId;
    List<testingModel> testList;
    InboxAdapter adapter;
    //for inbox
    String body, address;
    //for sent messages
    String type, getType;
    Long date;
    String name;
    String tarehe;
    public String add;
    CardView cardView;
    SubscriptionInfo simInfo1;
    SubscriptionInfo simInfo2;
    PendingIntent SentpendingIntent,DeliverypendingIntent;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_converse);
        ConverseActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //startService(new Intent(this, updateConvos.class));


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        getAdd = bundle.getString("Address");
        getThread = bundle.getString("Thread");

        toolbar = findViewById(R.id.tll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listModel = new ArrayList<>();

        mp = MediaPlayer.create(this, R.raw.eventually);


        connect = getAdd;

        floatingActionButton = findViewById(R.id.sending);

        sim1 = findViewById(R.id.sim1_button);
        sim2 = findViewById(R.id.sim2_button);


       // imageButton.setVisibility(View.INVISIBLE);

        e1 = findViewById(R.id.myEDit);
        recyclerView = findViewById(R.id.converseecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);


        cardView=findViewById(R.id.chooseSimcard);

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

        loadSms();



        // checking when the keyboard is open
        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                   recyclerView.scrollToPosition(0);
                   loadSms();

                } else {
                   loadSms();
                }
            }
        });

        //Toast.makeText(getApplicationContext(),""+listModel.size(),Toast.LENGTH_LONG).show();


        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                checkText();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                floatingActionButton.setImageResource(R.drawable.ic_send_black_24dp);
                checkText();


            }

            @Override
            public void afterTextChanged(Editable s) {
                floatingActionButton.setImageResource(R.drawable.ic_send_black_24dp);

                checkText();


            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If edit text is Empty don't send the message
                if (e1.getText().toString().trim().isEmpty()) {

                    e1.setError("Empty !!");
                    return;

                }

                sendSms();


              // notifyme();

            }
        });

        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Choose a SimCard to send Sms",Toast.LENGTH_LONG).show();
                cardView.setVisibility(View.VISIBLE);


                return false;
            }
        });



    }

    @SuppressLint("NewApi")
    public void loadSms() {

       listModel.clear();


        Uri uri = Uri.parse("content://sms/");

        cr = getApplicationContext().getContentResolver();
        cursor = cr.query(uri, null, "thread_id=" + connect, null, "date asc");

        System.out.println(Arrays.toString(new String[]{"" + Arrays.toString(cursor.getColumnNames())}));
      // Toast.makeText(getApplicationContext(),""+Arrays.toString(new String[]{"" + Arrays.toString(cursor.getColumnNames())}),Toast.LENGTH_LONG).show();

        if (cursor.moveToFirst()) {

            do {

                body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

                add = address;

                getContacts(address);

                switch (type) {

                    case "1":
                        getType = "1";
                        break;
                    case "2":
                        getType = "2";
                        break;

                }
                Date date1 = new Date(date); // accept long value.
                tarehe = date1.toString();

                java.util.Date date = new java.util.Date(date1.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                System.out.println(sdf.format(date));
                tarehe = sdf.format(date);


                InboxModel cmd1 = new InboxModel();
                cmd1.setReceived_msg(body);
                cmd1.setSent_msg(body);
                cmd1.setName_sender(add);
                cmd1.setType(getType);
                cmd1.setTime_receivd(tarehe);
                cmd1.setTime_sent(tarehe);

                listModel.add(cmd1);


            } while (cursor.moveToNext());


        }
        if (cursor == null) {

            cursor.close();

        }

        adapter = new InboxAdapter(getApplicationContext(), listModel);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(listModel.size() - 1);
        adapter.notifyDataSetChanged();


    }

    //Getting Contact of The person the current inbox I'm in
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getContacts(String address) {

        // encode the phone number and build the filter URI
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));

        ContentResolver ctrs = getApplicationContext().getContentResolver();
        Objects.requireNonNull(getSupportActionBar()).setTitle(add);

        curse = ctrs.query(contactUri, null, null, null, null);

        if (curse != null) {

            curse.moveToFirst();

        }
        if (curse.moveToFirst()) {

            do {

                // Get values from contacts database:
                ContactName = curse.getString(curse.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                contactId = curse.getString(curse.getColumnIndex(ContactsContract.PhoneLookup._ID));

                getSupportActionBar().setTitle(ContactName);
                getSupportActionBar().setSubtitle(this.address);

                curse.moveToNext();


                if (curse == null) {
                    curse.close();
                }


            } while (curse.moveToNext());


        }


    }


    public void sendSms() {

        String SENT = "Message Sent";
        String DELIVERED = "Message Delivered";
        final String myMessage = e1.getText().toString();


        SentpendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(SENT), 0);
        DeliverypendingIntent = PendingIntent.getBroadcast(ConverseActivity.this, 0, new Intent(DELIVERED), 0);

        smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(address, null, myMessage, SentpendingIntent, DeliverypendingIntent);

        e1.getText().clear();

        listModel.clear();
        loadSms();



        // SEND BroadcastReceiver checks if there's network for sending
        BroadcastReceiver sendSMS = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "Message Sent", Toast.LENGTH_LONG).show();
                        loadSms();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Recharge and Send Later", Toast.LENGTH_LONG).show();
                        loadSms();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service Try again", Toast.LENGTH_LONG).show();
                        loadSms();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();
                        loadSms();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Can't Send Sms in flight mode", Toast.LENGTH_LONG).show();
                        loadSms();
                        break;


                }
            }
        };

        // DELIVERY BroadcastReceiver checks delivery Status of the message
        BroadcastReceiver deliverSMS = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:

                        Toast.makeText(getBaseContext(), "Message Delivered", Toast.LENGTH_LONG).show();
                        mp.start();
                        adapter.notifyDataSetChanged();

                        break;
                    case Activity.RESULT_CANCELED:

                        Toast.makeText(getBaseContext(), "Not Delivered", Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };

        registerReceiver(sendSMS, new IntentFilter(SENT));
        registerReceiver(deliverSMS, new IntentFilter(DELIVERED));



        //Sim Card Selection For Sending Sms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager localSubscriptionManager = SubscriptionManager.from(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {

                List localList = localSubscriptionManager.getActiveSubscriptionInfoList();

               simInfo1 = (SubscriptionInfo) localList.get(0);
               simInfo2 = (SubscriptionInfo) localList.get(1);


                //SendSMS From SIM One
                sim1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cardView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Sim card 1 chosen",Toast.LENGTH_LONG).show();
                        SmsManager.getSmsManagerForSubscriptionId(simInfo1.getSubscriptionId()).sendTextMessage(address, null, myMessage, SentpendingIntent, DeliverypendingIntent);

                    }
                });


                //SendSMS From SIM Two
                sim2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cardView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Sim card 2 chosen",Toast.LENGTH_LONG).show();

                        SmsManager.getSmsManagerForSubscriptionId(simInfo2.getSubscriptionId()).sendTextMessage(address, null, myMessage, SentpendingIntent, DeliverypendingIntent);




                    }
                });





            }


        }


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
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:"+add));
                startActivity(intent1);
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

    public void checkText(){


        if (e1.getText().toString().trim().isEmpty()){

            floatingActionButton.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
        }



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



    public  void  notifyme() {


         NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ConverseActivity.this, "notify_001");

        Intent ii = new Intent(ConverseActivity.this, ConverseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ConverseActivity.this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(" SATURDAY");
        bigText.setBigContentTitle("Today is a good day");
        bigText.setSummaryText("Level up");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager = (NotificationManager) ConverseActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        // === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());



}





















}










