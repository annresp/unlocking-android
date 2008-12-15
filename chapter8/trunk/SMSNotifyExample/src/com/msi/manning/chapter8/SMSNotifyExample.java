package com.msi.manning.chapter8;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.app.PendingIntent;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

public class SMSNotifyExample extends BroadcastReceiver {
     /** TAG used for Debug-Logging */
     private static final String LOG_TAG = "SMSReceiver";
     public static final int NOTIFICATION_ID_RECEIVED = 0x1221;
     static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
     private  CharSequence from = null;
     private CharSequence tickerMessage = null;

	@SuppressWarnings("deprecation")
	public void onReceiveIntent(Context context, Intent intent) {
   
          NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
          if (intent.getAction().equals(ACTION)) {
             
               StringBuilder sb = new StringBuilder();
               
          
               Bundle bundle = intent.getExtras();
               if (bundle != null) {
            	   Object[] pdusObj = (Object[]) bundle.get("pdus"); 
                  SmsMessage[] messages =  new SmsMessage[pdusObj.length]; 
                     //    Telephony.Sms.Intents.getMessagesFromIntent(intent);
            
                    for (SmsMessage currentMessage : messages){
                         sb.append("Received compressed SMS\nFrom: ");
                         sb.append(currentMessage.getDisplayOriginatingAddress());
                         from = currentMessage.getDisplayOriginatingAddress();
                         sb.append("\n----Message----\n");
                         sb.append(currentMessage.getDisplayMessageBody());
                     }
               }
             
               Log.i(LOG_TAG, "[SMSApp] onReceiveIntent: " + sb);
               this.abortBroadcast();
             
               Intent i = new Intent(context, SMSNotifyActivity.class);
               context.startActivity(i);

               CharSequence appName = "SMSNotifyExample"; 
               tickerMessage = sb.toString(); 
               Long theWhen = System.currentTimeMillis();
               
              PendingIntent.getBroadcast((Context) appName, 0, i, 0);
			Notification notif = new Notification(
                       R.drawable.incoming,
                       tickerMessage,   
                       theWhen);	
              
		//	notif.vibrate = new long[] { 100, 250, 100, 500};
            nm.notify(R.string.alert_message, notif);
          }
     }
	@Override
	public void onReceive(Context context, Intent intent) {
		
	}
}