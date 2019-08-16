package com.example.mood.Chats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mood.R;

public class frag1 extends Fragment {

    ListView listView;
Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vie=inflater.inflate(R.layout.f1,container,false);

        listView= vie.findViewById(R.id.listV);
        button=vie.findViewById(R.id.buttonSms);


        return vie;
    }

}
