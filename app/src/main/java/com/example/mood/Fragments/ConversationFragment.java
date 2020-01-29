package com.example.mood.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Model_Classes.ConversationModel;
import com.example.mood.Adapter_Classes.ConversationAdapter;
import com.example.mood.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ConversationFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    String mer;
    static List<ConversationModel> conversationModelLists;
    ConversationModel objConversationModel;
    static Context context;
    ConversationAdapter myadapter;
    String contactName;
    String address = "";
    String PataNamba;
    String Snippet;
    ConstraintLayout layout;
    RecyclerView recyclerView;
    int GENERAL_CODE = 1;
    String[] perms;
    SearchView searchView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation, container, false);

        context = getActivity();

        recyclerView = view.findViewById(R.id.recyclerV);
        layout = view.findViewById(R.id.permissionLayout);
        searchView=view.findViewById(R.id.conversationSearchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

               // Toast.makeText(getContext(),"Searching.....\t"+s,Toast.LENGTH_LONG).show();
                myadapter.getFilter().filter(s);

                return false;
            }
        });


        CheckPermissions();


        return view;


    }

    private void getConversations() {

        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");

        Cursor c = getActivity().getContentResolver().query(SMS_INBOX, null, null, null, "date desc");


        String count[] = new String[c.getCount()];
        String snippet[] = new String[c.getCount()];
        String thread_id[] = new String[c.getCount()];
        String date[] = new String[c.getCount()];


        conversationModelLists = new ArrayList<>();


        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {

            count[i] = c.getString(c.getColumnIndexOrThrow("msg_count"));
            thread_id[i] = c.getString(c.getColumnIndexOrThrow("thread_id"));
            snippet[i] = c.getString(c.getColumnIndexOrThrow("snippet"));
            android.util.Log.i("COLUMNS", Arrays.toString(c.getColumnNames()));
            // date[i] = c.getString(c.getColumnIndexOrThrow("date"));

            mer = thread_id[i];
            Snippet = snippet[i];

            System.out.println(mer);


            Cursor curse = getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), null, "thread_id=" + mer, null, null);


            if (curse != null) {

                curse.moveToFirst();

            }

            for (int x = 0; x < curse.getCount(); x++) {

                address = curse.getString(curse.getColumnIndexOrThrow("address"));

                curse.moveToNext();

            }


            if (curse != null) {

                curse.close();
            }


            ContentResolver cr = context.getContentResolver();

            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));

            Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);


            PataNamba = address;

            if (cursor != null) {

                cursor.moveToFirst();

            }

            for (int l = 0; l < cursor.getCount(); l++) {

                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));

                if (contactName == null) {

                    PataNamba = address;

                    return;
                }
                PataNamba = contactName;


                cursor.moveToNext();

            }
            if (cursor == null) {


                cursor.close();
            }

            c.moveToNext();


            ConversationModel objSms = new ConversationModel();

            objSms.set_msg(snippet[i]);
            objSms.set_address(PataNamba);
            objSms.set_date(count[i]);
            objSms.set_id(mer);
            objSms.setThread(mer);
            objSms.set_senderName(String.valueOf(PataNamba.toString().charAt(0) + "" + PataNamba.toString().charAt(1)));


            conversationModelLists.add(objSms);


        }

        if (c != null) {

            c.close();

        }

        myadapter = new ConversationAdapter(getActivity(), conversationModelLists);
        myadapter.notifyDataSetChanged();
        recyclerView.setAdapter(myadapter);

    }


    @AfterPermissionGranted(123)
    public void CheckPermissions() {


       perms = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {


            layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

           getConversations();

        } else {

            layout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            EasyPermissions.requestPermissions(getActivity(), "We need permissions to Read Sms", 123, perms);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {

        new ConversationFragment();
        getConversations();
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {

      if (EasyPermissions.somePermissionPermanentlyDenied(getActivity(), Arrays.asList(perms))){

          new AppSettingsDialog.Builder(getActivity()).build().show();

      }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){


         getConversations();

        }
    }



    @Override
    public void onResume() {
        new ConversationFragment();
        super.onResume();
    }


}



