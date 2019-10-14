package com.example.mood.Contacts;

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

import com.example.mood.R;
import com.example.mood.inboxActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    List<ContactsModal>contactsModals;
    Context context;

    public ContactsAdapter(List<ContactsModal> contactsModals, Context context) {
        this.contactsModals = contactsModals;
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

        final ContactsModal contactsModal=contactsModals.get(position);

        holder.textView.setText(contactsModal.getName());
        holder.textView1.setText(contactsModal.getNumber());
        holder.circleImageView.setImageResource(contactsModal.getPic());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(context, inboxActivity.class);

                Bundle extras = new Bundle();
                extras.putString("Number",contactsModal.getNumber());
                extras.putString("Name",contactsModal.getName());
                extras.putInt("pic",contactsModal.getPic());
                intent.putExtras(extras);
                context.startActivity(intent);




            }
        });



    }

    @Override
    public int getItemCount() {

        return contactsModals.size();
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
