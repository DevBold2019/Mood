package com.example.mood.BroadCast_Receivers;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.example.mood.R;

public class MessageReceived extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        SmsMessage  smsmessages;
        String message="";
        String sendersNumber="";

        if (bundle !=null){

            Object [] objects=(Object[])bundle.get("pdus");

            for (int i=0; i<objects.length; i++){

                smsmessages =SmsMessage.createFromPdu((byte[]) objects[i] );

                sendersNumber +=smsmessages.getOriginatingAddress();
                message =smsmessages.getMessageBody();



            }
            Toast.makeText(context,"Message \t :"+message+"\tFrom\t"+sendersNumber,Toast.LENGTH_SHORT).show();


            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(context)
                        .setContentText(message)
                        .setContentTitle(sendersNumber)
                        .setSmallIcon(R.drawable.sms)
                        .setStyle(new Notification.BigTextStyle().bigText(message))
                        .build();
            }
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationManagerCompat.notify(1000, notification);





            Intent intent1=new Intent();
            intent1.setAction("SMS_RECEIVED_ACTION");
            intent1.putExtra("message",message);
            intent1.putExtra("number",sendersNumber);
            context.sendBroadcast(intent1);

        }






    }
}
