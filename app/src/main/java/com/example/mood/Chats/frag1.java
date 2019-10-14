package com.example.mood.Chats;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Contacts.mygettingModel;
import com.example.mood.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class frag1 extends Fragment {

    public String today;
     String mer;
    public List<Integer> test;


    static List<Sms> smsLists;
    List<Sms> slist;

    Sms objSms;
    int totalSMS;
    Cursor c, cursor1;

    String add, body, state, date, tarehe, folder, thread;
    String sent = "sent";
    String inbox = "inbox";
    String id;
    static Context context;
    Sms sms, sm;
    SmsAdapter smsAdapter, myadapter;
    ContentResolver contrsv;
    String contactName;
    int i;

    ContentResolver ctrs;

    String address="";

    String  PataNamba;
    String Snippet,Count;
    String Availability;



    RecyclerView recyclerView;
    private String[] thread_id;
    int GENERAL_CODE=1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vie = inflater.inflate(R.layout.f1, container, false);

        context = getActivity();

        recyclerView = vie.findViewById(R.id.recyclerV);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);




        String[]allow ={Manifest.permission.READ_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS};
       if (ContextCompat.checkSelfPermission(context.getApplicationContext(), String.valueOf(allow))==PackageManager.PERMISSION_GRANTED){

           Toast.makeText(getContext(),"Permissions are allowed",Toast.LENGTH_LONG).show();

        }else {
           String[] perms = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS};


           ActivityCompat.requestPermissions(getActivity(),perms,GENERAL_CODE);
       }

        getSMSCOnversationlist();


        return vie;

    }


    public void getSMSCOnversationlist() {

        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");

        Cursor c = getActivity().getContentResolver().query(SMS_INBOX, null,null, null, "date desc");


           String count[] = new String[c.getCount()];
           String snippet[] =new String[c.getCount()];
          String  thread_id[]= new String[c.getCount()];


          smsLists=new ArrayList<>();


          c.moveToFirst();

        for ( i = 0; i < c.getCount(); i++) {

           count[i] = c.getString(c.getColumnIndexOrThrow("msg_count")).toString();
            thread_id[i]= c.getString(c.getColumnIndexOrThrow("thread_id")).toString();
            snippet[i] = c.getString(c.getColumnIndexOrThrow("snippet")).toString();

            mer= thread_id[i];
            Snippet=snippet[i];


            Cursor curse=getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), null,"thread_id="+mer, null, null);


            if (curse !=null) {

                curse.moveToFirst();

            }

            for (int x=0; x<curse.getCount(); x++) {

                address = curse.getString(curse.getColumnIndexOrThrow("address"));

                curse.moveToNext();

            }


            if (curse !=null){

                curse.close();
            }




            ContentResolver cr = context.getContentResolver();

            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));

            Cursor cursor = cr.query(uri, new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

            if (cursor != null) {

                cursor.moveToFirst();


            }

            for (int l=0; l<cursor.getCount(); l++) {

                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));

                if (contactName=="0"){

                    PataNamba=address;


                }
                else{

                    PataNamba=contactName;

                }



                cursor.moveToNext();

            }
            if (cursor == null) {


                cursor.close();
            }


            System.out.println("My adddresss is:"+address);
            System.out.println(today);

            c.moveToNext();





            objSms = new Sms();


            objSms.set_address(PataNamba);
            objSms.set_msg(snippet[i]);
            objSms.set_date(count[i]);
            objSms.set_id(mer);

            smsLists.add(objSms);


        }

        if (c !=null) {

            c.close();

        }

        myadapter = new SmsAdapter(getActivity(), smsLists);
        myadapter.notifyDataSetChanged();
        recyclerView.setAdapter(myadapter);

    }



        //today = (DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR));





    @Override
    public void onResume() {

     /*   Intent intent=new Intent(context,getClass());

        startActivity(intent);
*/
        super.onResume();
    }
}



