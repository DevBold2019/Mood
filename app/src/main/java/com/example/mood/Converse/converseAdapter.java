package com.example.mood.Converse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;

public class converseAdapter extends ArrayAdapter<converseModel> {

    TextView t1,t2,t3,t4,t5,t6,t7;

    Context context;
    List<converseModel>list=new ArrayList<>();

    public converseAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(converseModel object) {
        list.add(object);
        super.add(object);
    }


    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        View view1=convertview;
        converseModel conModel=list.get(i);


        if (conModel.left){

            view1=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_layout,viewGroup,false);

            t1=view1.findViewById(R.id.SendersName);
            t2=view1.findViewById(R.id.SendersMessage);
            t3=view1.findViewById(R.id.SendersTime);


            t1.setText(conModel.getName_sender());
            t2.setText(conModel.getReceived_msg());
            t3.setText(conModel.getTime_receivd());

        }else{

            view1=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sent_layout,viewGroup,false);

            t4=view1.findViewById(R.id.mySentText);
            t5=view1.findViewById(R.id.mySendingTime);

            t4.setText(conModel.getSent_msg());
            t5.setText(conModel.getTime_sent());

        }




        return view1;
    }
}
