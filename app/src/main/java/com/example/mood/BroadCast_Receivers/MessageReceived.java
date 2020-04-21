package com.example.mood.BroadCast_Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mood.Activities.ConverseActivity;
import com.example.mood.Activities.MainUi;
import com.example.mood.R;

public class MessageReceived extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"New Sms",Toast.LENGTH_LONG).show();

        Bundle bundle = intent.getExtras();
        SmsMessage SMS;
        String message = "";
        String sendersNumber = "";

        if (bundle != null) {

            Object[] objects = (Object[]) bundle.get("pdus");

            for (int i = 0; i < objects.length; i++) {

                SMS = SmsMessage.createFromPdu((byte[]) objects[i]);

                sendersNumber = SMS.getOriginatingAddress();
                message = SMS.getMessageBody();


            }
            Toast.makeText(context, "Message \t :" + message + "\tFrom\t" + sendersNumber, Toast.LENGTH_LONG).show();


            NotificationManager mNotificationManager;

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");

            Intent ii = new Intent(context, MainUi.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message);
            bigText.setBigContentTitle(sendersNumber);
            bigText.setSummaryText(message);

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setContentTitle(sendersNumber);
            mBuilder.setContentText(message);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigText);

            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // === Removed some obsoletes
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String channelId = "Your_channel_id";
                NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(channel);
                mBuilder.setChannelId(channelId);
            }

            mNotificationManager.notify(0, mBuilder.build());


        }


    }








}

