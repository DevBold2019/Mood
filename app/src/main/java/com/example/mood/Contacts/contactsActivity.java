package com.example.mood;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class contactsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
   // RecyclerView.Adapter adapter;

    List<ContactsModal>contactsModalList;
    Toolbar toolbar;
    Cursor cursor;
    Context context;
    String name,number;
    //int i;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context=getApplication();

       // getSupportActionBar().hide();

        toolbar=findViewById(R.id.tool);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.Contacts.DISPLAY_NAME);
        contactsModalList = new ArrayList<>();

        while(cursor.moveToNext()){


            name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
             number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactsModal contactsModal = new ContactsModal(""+name,""+number,R.drawable.group);
            contactsModalList.add(contactsModal);

        }
                if (cursor != null){
                cursor.close();

                }





        ContactsAdapter contactsAdapter=new ContactsAdapter(contactsModalList,this);
        contactsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contactsAdapter);



    }





    }

