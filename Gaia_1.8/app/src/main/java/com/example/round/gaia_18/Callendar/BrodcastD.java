package com.example.round.gaia_18.Callendar;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.round.gaia_18.MainActivity;
import com.example.round.gaia_18.R;

import static com.example.round.gaia_18.Callendar.CallendarMainActivity.AlarmLogMemo;
import static com.example.round.gaia_18.Callendar.CallendarMainActivity.AlarmLogTitle;
import static com.example.round.gaia_18.OverlayService.dataList;

/**
 * Created by 리제 on 2017-10-23.
 */

public class BrodcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    static MediaPlayer mediaPlayer;
    RemoteViews contentView;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함

        mediaPlayer = MediaPlayer.create(context, R.raw.alarmbgm);
        mediaPlayer.start();

        Intent intent4 = new Intent(context, setBGM.class);

        if(dataList.getOverlayPlants().size() == 0) {
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, setBGM.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.reward2)
                    .setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("" + AlarmLogTitle)
                    .setContentText("" + AlarmLogMemo)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText("" + AlarmLogMemo));

            notificationmanager.notify(1, builder.build());
        }else{
            dataList.getOverlayPlants().get(0).setAlarm(AlarmLogTitle,AlarmLogMemo);
        }

    }

    class setBGM{
        setBGM(){
            Log.i("test","test");
            mediaPlayer.stop();
        }
    }

}