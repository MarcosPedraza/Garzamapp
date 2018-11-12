package com.uaeh.garza.garzamapp.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver  extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";


    public interface Listener {
        void onTextReceived(String emisor,String text);
    }

    public Listener listener;


    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        {
            String msmDe = "";
            String contenido = "";

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    msmDe = smsMessage.getDisplayOriginatingAddress();
                    contenido += smsMessage.getMessageBody();
                }
            }

            if(listener != null)
            {
                listener.onTextReceived(msmDe, contenido);
            }

        }

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
