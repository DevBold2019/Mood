package com.example.mood.Adapter_Classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Activities.ConverseActivity;
import com.example.mood.Model_Classes.ConversationModel;
import com.example.mood.R;

import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> implements Filterable {

    Context context;
    List<ConversationModel> conversationModelList;
    List<ConversationModel>myList;


    public ConversationAdapter(Context context, List<ConversationModel> conversationModelList) {

        this.context = context;
        this.conversationModelList = conversationModelList;
        myList=new ArrayList<>(conversationModelList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,t4;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1=itemView.findViewById(R.id.jina);
            t2=itemView.findViewById(R.id.mesoo);
            t3=itemView.findViewById(R.id.mesooCount);
            t4=itemView.findViewById(R.id.picha);
            relativeLayout=itemView.findViewById(R.id.myrelative);


        }
    }

    @NonNull
    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.smslayout,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationAdapter.ViewHolder holder, int position) {

        final ConversationModel conversationModel = conversationModelList.get(position);



       holder.t1.setText(conversationModel.get_address());
       holder.t2.setText(conversationModel.get_msg());
        holder.t3.setText(conversationModel.get_date());
        holder.t4.setText(conversationModel.get_senderName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ConverseActivity.class);

                Bundle bundle=new Bundle();
                bundle.putString("Address", conversationModel.get_id());
                bundle.putString("Thread", conversationModel.getThread());

                intent.putExtras(bundle);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return conversationModelList.size();
    }

    @Override
    public Filter getFilter() {
        return getFilteredData;
    }

    Filter getFilteredData=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ConversationModel>myFilteredList=new ArrayList<>();

            if (charSequence==null || charSequence.length()==0){

                myFilteredList.addAll(myList);

            }
            else{

                String getSearchedText=charSequence.toString().toLowerCase().trim();

                for (ConversationModel model : myList ){

                    if (model.get_address().contains(getSearchedText)){

                        myFilteredList.add(model);
                        Toast.makeText(context,"Match Found",Toast.LENGTH_LONG).show();
                    }else{

                        Toast.makeText(context,"No Match Found",Toast.LENGTH_LONG).show();

                    }


                }


            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=myFilteredList;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            conversationModelList.clear();
            conversationModelList.addAll((List) filterResults.values);
            notifyDataSetChanged();


        }
    };



}
