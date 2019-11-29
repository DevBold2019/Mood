package com.example.mood.Chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Converse.ConverseActivity;
import com.example.mood.R;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.ViewHolder> {

    Context context;
    List<Sms>smsList;


    public SmsAdapter(Context context, List<Sms> smsList) {

        this.context = context;
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public SmsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.smslayout,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsAdapter.ViewHolder holder, int position) {

        final Sms sms=smsList.get(position);



       holder.t1.setText(sms.get_address());
       holder.t2.setText(sms.get_msg());
        holder.t3.setText(sms.get_date());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ConverseActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("Address",sms.get_id());

                intent.putExtras(bundle);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3;
         RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.jina);
            t2=itemView.findViewById(R.id.mesoo);
            t3=itemView.findViewById(R.id.mesooCount);
            relativeLayout=itemView.findViewById(R.id.myrelative);


        }
    }

}
