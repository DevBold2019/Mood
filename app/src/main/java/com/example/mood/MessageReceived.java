package com.example.mood;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class MessageReceived extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        SmsMessage [] messages;
        String mystring="";

        if (bundle !=null){

            Object[]objects=(Object[])bundle.get("pdus");
            messages=new SmsMessage[objects !=null ? objects.length:0];

            for (int i=0; i<messages.length; i++){
                messages [i]=SmsMessage.createFromPdu((byte[])( objects !=null ? objects[i] : null));
                mystring +=messages[i].getOriginatingAddress(); mystring +=":"; mystring +=messages [i] .getMessageBody(); mystring +="\n";


                Notification notification = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notification = new Notification.Builder(context)
                            .setContentText(messages[i].getMessageBody())
                            .setContentTitle(messages[i].getOriginatingAddress())
                            .setSmallIcon(R.drawable.chatt)
                            .setStyle(new Notification.BigTextStyle().bigText(messages[i].getMessageBody()))
                            .build();
                }
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(1, notification);




              // Toast.makeText(context,"Message \t :"+messages[i].getMessageBody()+"\tFrom\t"+messages[i].getOriginatingAddress(),Toast.LENGTH_SHORT).show();

            }

            Intent intent1=new Intent();
            intent.setAction("SMS_RECEIVED_ACTION");
            intent1.putExtra("message",mystring);
            context.sendBroadcast(intent1);


          //  inboxActivity inst=inboxActivity.instance();
//            inst. updatemyInbox(mystring);

        }






    }
}
