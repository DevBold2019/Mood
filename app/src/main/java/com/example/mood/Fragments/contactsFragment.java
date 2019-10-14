package com.example.mood.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Contacts.ContactsAdapter;
import com.example.mood.Contacts.ContactsModal;
import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class contactsFragment extends Fragment {

    RecyclerView recyclerView;
    // RecyclerView.Adapter adapter;

    List<ContactsModal> contactsModalList;
    Toolbar toolbar;
    Cursor cursor;
    Context context;
    String name, number;
    int photo;
    //int i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = LayoutInflater.from(getContext()).inflate(R.layout.contacts_fragment, container, false);

            // getSupportActionBar().hide();
          /*  setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Contacts");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

            recyclerView = view.findViewById(R.id.recycle);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            cursor =getActivity(). getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
            contactsModalList = new ArrayList<>();

            while (cursor.moveToNext()) {


                name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                photo= cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                if (photo == 0) {

                    photo = R.drawable.group;

                } else if (photo != 0){

                    photo=photo;


                }


                ContactsModal contactsModal = new ContactsModal();

                contactsModal.setName(name);
                contactsModal.setNumber(number);
                contactsModal.setPic(photo);
                contactsModalList.add(contactsModal);

            }
            if (cursor != null) {
                cursor.close();

            }


            ContactsAdapter contactsAdapter = new ContactsAdapter(contactsModalList,getActivity());
            contactsAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(contactsAdapter);



        return view;
    }


 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contacts_menu, menu);

        MenuItem search_item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) search_item.getActionView();
        // searchView.setFocusable(false);
        // searchView.setQueryHint("Search");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search:

                break;

        }
        return super.onOptionsItemSelected(item);
    }*/


}
