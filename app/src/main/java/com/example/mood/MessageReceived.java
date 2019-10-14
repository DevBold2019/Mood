package com.example.mood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

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
