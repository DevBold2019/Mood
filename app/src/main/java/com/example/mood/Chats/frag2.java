package com.example.mood.Chats;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mood.contactsActivity;
import com.example.mood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class frag2 extends Fragment {

    FloatingActionButton floatingActionButton;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.f2,container,false);

        floatingActionButton=v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), contactsActivity.class);
                startActivity(intent);

            }
        });



        return v;



    }






}
