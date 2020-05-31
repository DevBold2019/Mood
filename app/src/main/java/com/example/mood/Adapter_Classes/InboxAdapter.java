package com.example.mood.Adapter_Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mood.Model_Classes.InboxModel;
import com.example.mood.R;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter {


    public static final int VIEW_TYPE_MESSAGE_SENT=1;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED=2;

    Context context;
    List<InboxModel> list;

    public InboxAdapter(Context context, List<InboxModel> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        InboxModel message =  list.get(position);


        if (message.getType().equals("1")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;

        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_layout, parent, false);

            return new SentMessageHolder(view);

        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_layout, parent, false);

            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InboxModel message =  list.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;

        }
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageText, timeText, nameText;


        SentMessageHolder(View itemView) {
            super(itemView);

            messageText =itemView.findViewById(R.id.SendersMessage);
            timeText = itemView.findViewById(R.id.SendersTime);


        }

        void bind(InboxModel message) {

            timeText.setText(message.getTime_sent());
            messageText.setText(message.getSent_msg());


        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.mySentText);
            timeText = itemView.findViewById(R.id.mySendingTime);



        }

        void bind(InboxModel message) {

            messageText.setText(message.getSent_msg());
            timeText.setText(message.getTime_sent());



        }
    }


}










