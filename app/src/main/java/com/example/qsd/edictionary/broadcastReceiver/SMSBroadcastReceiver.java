package com.example.qsd.edictionary.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by QSD on 2016/12/19.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static MessageListener mMessageListener;
    public SMSBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object [] pdus= (Object[]) intent.getExtras().get("pdus");
        for(Object pdu:pdus){
            SmsMessage smsMessage=SmsMessage.createFromPdu((byte [])pdu);
            String sender=smsMessage.getDisplayOriginatingAddress();
            String content=smsMessage.getMessageBody();
            long date=smsMessage.getTimestampMillis();
            Date timeDate=new Date(date);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=simpleDateFormat.format(timeDate);

            System.out.println("短信来自:"+sender);
            System.out.println("短信内容:"+content);
            System.out.println("短信时间:"+time);

            mMessageListener.OnReceived(content);

            //如果短信来自5556,不再往下传递,一般此号码可以作为短信平台的号码。
            if("5556".equals(sender)){
                System.out.println(" abort ");
                abortBroadcast();
            }

        }
    }

    // 回调接口
    public interface MessageListener {
        public void OnReceived(String message);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.mMessageListener=messageListener;
    }
}
