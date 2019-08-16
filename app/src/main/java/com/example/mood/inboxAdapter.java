package com.example.mood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class inboxAdapter extends RecyclerView.Adapter<inboxAdapter.ViewHolder> {


    Context context;
    List<inboxModel>inboxModelList;

    public inboxAdapter(Context context, List<inboxModel> inboxModelList) {
        this.context = context;
        this.inboxModelList = inboxModelList;
    }

    @NonNull
    @Override
    public inboxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_lay,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull inboxAdapter.ViewHolder holder, int position) {

    inboxModel inboxModel= inboxModelList.get(position);

    holder.t1.setText(inboxModel.getMessage());
    holder.t3.setText(inboxModel.getReply());


   /* holder.t2.setText(inboxModel.getTimeReceived());
    holder.t4.setText(inboxModel.getTimeSent());*/



    }

    @Override
    public int getItemCount() {
        return inboxModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,t4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.sentText);
            t2=itemView.findViewById(R.id.sentTime);



            t3=itemView.findViewById(R.id.receivedText);
            t4=itemView.findViewById(R.id.receivedTime);



        }
    }
}
