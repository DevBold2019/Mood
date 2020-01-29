package com.example.mood.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Adapter_Classes.ContactsAdapter;
import com.example.mood.Model_Classes.ContactsModel;
import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;

public class contactsFragment extends Fragment {

    RecyclerView recyclerView;
    List<ContactsModel> contactsModelList;
    ContactsAdapter contactsAdapter;
    Cursor cursor;
    String name, number;
    ConstraintLayout layout;
    int photo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        view = LayoutInflater.from(getContext()).inflate(R.layout.contacts_fragment, container, false);

        layout = view.findViewById(R.id.permissionLayout);
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        checkPermission();


        return view;
    }

    private void loadContacts(){

        cursor =getActivity(). getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        contactsModelList = new ArrayList<>();

        while (cursor.moveToNext()) {


            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            photo= cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            if (photo == 0) {

                photo = R.drawable.group;

            } else if (photo != 0){

                photo=photo;


            }


            ContactsModel contactsModel = new ContactsModel();

            contactsModel.setName(name);
            contactsModel.setNumber(number);
            contactsModel.setPic(photo);
            contactsModelList.add(contactsModel);

        }
        if (cursor != null) {
            cursor.close();

        }


        contactsAdapter = new ContactsAdapter(contactsModelList,getActivity());
        contactsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contactsAdapter);




    }

    private void checkPermission(){

        String[]allow ={Manifest.permission.READ_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS};

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), String.valueOf(allow)) == PackageManager.PERMISSION_GRANTED){

            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            loadContacts();

        }else {

            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);


        }


    }
}
