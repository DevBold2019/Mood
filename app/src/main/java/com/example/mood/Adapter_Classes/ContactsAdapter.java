package com.example.mood.Adapter_Classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Model_Classes.ContactsModel;
import com.example.mood.R;
import com.example.mood.Activities.inboxActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    List<ContactsModel> contactsModels;
    Context context;

    public ContactsAdapter(List<ContactsModel> contactsModels, Context context) {
        this.contactsModels = contactsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_lay,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {

        final ContactsModel contactsModel = contactsModels.get(position);

        holder.textView.setText(contactsModel.getName());
        holder.textView1.setText(contactsModel.getNumber());
        holder.circleImageView.setImageResource(contactsModel.getPic());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(context, inboxActivity.class);

                Bundle extras = new Bundle();
                extras.putString("Number", contactsModel.getNumber());
                extras.putString("Name", contactsModel.getName());
                extras.putInt("pic", contactsModel.getPic());
                intent.putExtras(extras);
                context.startActivity(intent);




            }
        });



    }

    @Override
    public int getItemCount() {

        return contactsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,textView1;
        RelativeLayout relativeLayout;
        CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textView=(TextView) itemView.findViewById(R.id.contName);
            textView1=(TextView) itemView.findViewById(R.id.contNumber);
            circleImageView=itemView.findViewById(R.id.contPic);
            relativeLayout =(RelativeLayout) itemView.findViewById(R.id.relative);

        }
    }
}
