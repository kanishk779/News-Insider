package com.example.android.news_insider.Receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.news_insider.MainActivity;
import com.example.android.news_insider.R;
import com.example.android.news_insider.TopicsSaveInListActivity;

import java.util.ArrayList;

/**
 * Created by hp on 19-07-2018.
 */

public class Notification_Receiver extends BroadcastReceiver {
    private String CHANNEL_ID ="default";
    private ArrayList<String> list;
    @Override
    public void onReceive(Context context, Intent intent) {
        int i = TopicsSaveInListActivity.CompleteDataInfo.size();
        //createNotificationChannel();
        list = TopicsSaveInListActivity.listTopic;
        //SETTING THE NOTIFICATION INTENT
        Intent repeating_intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeating_intent, 0);

        if(TopicsSaveInListActivity.CompleteDataInfo!=null||TopicsSaveInListActivity.CompleteDataInfo.size()!=0)
        {
            //NOTIFICATION SETTING UP CODE
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.searchbar)
                    .setContentTitle("News notification")
                    .setContentText("Hey! Remember you wanted news about "+TopicsSaveInListActivity.CompleteDataInfo.get(i-1)+" Check it now !!")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Hey! Remember you wanted news about "+TopicsSaveInListActivity.CompleteDataInfo.get(i-1)+" Check it now !!"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1000, mBuilder.build());
        }
        else{
            //NOTIFICATION SETTING UP CODE
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.searchbar)
                    .setContentTitle("News notification")
                    .setContentText("Check the latest news to be Updated")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Check the latest news to be Updated"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1000, mBuilder.build());
        }
    }

    /*private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name"*//*getString(R.string.channel_name)*//*;
            String description = "notification"*//*getString(R.string.channel_description)*//*;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
