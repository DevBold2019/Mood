package com.example.mood.Fragments;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mood.Adapter_Classes.testingAdapter;
import com.example.mood.Model_Classes.testingModel;
import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;


public class TestingFrag extends Fragment {

    List<testingModel>list;
    testingAdapter adapter;
    RecyclerView recyclerView;
    testingModel model;
    String thread="1867";
    String body;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view= inflater.inflate(R.layout.fragment_testing, container, false);

        recyclerView=view.findViewById(R.id.testingRecy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<>();


     /*   Uri uri= Uri.parse("content://smsModel/conversation");
        ContentResolver crs=getActivity().getContentResolver();

        Cursor cursor=crs.query(uri,null,"thread_id="+thread,null,"date DESC");

        if (cursor != null){

            cursor.moveToNext();
            body=cursor.getString(cursor.getColumnIndexOrThrow("body"));
            Toast.makeText(getContext(),"No message from this contact",Toast.LENGTH_SHORT).show();

        }
            cursor.close();*/

        Uri uri=Uri.parse("content://smsModel/");

        ContentResolver crs=getActivity().getContentResolver();
        Cursor cursor=crs.query(uri,null,"thread_id="+thread,null,"date ASC");

        if (cursor !=null){

            cursor.moveToFirst();

        }

        for (int i=0; i<cursor.getCount(); i++){

            body=cursor.getString(cursor.getColumnIndexOrThrow("body"));
            model=new testingModel(body);
            list.add(model);

            cursor.moveToNext();

        }
        if (cursor==null){

            Toast.makeText(getContext(),"No reply from this contact",Toast.LENGTH_LONG).show();
            cursor.close();

        }


        adapter=new testingAdapter(getContext(),list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        return view;
    }

}
