package com.example.medtracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    DatabaseHelper db;
    String msg,time,tim;
    String med = "Error", dos = "Error";
    String min;
    String path = "";
    static int id = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int mins = c.get(Calendar.MINUTE);
        tim = hours + ":" + mins;
        //int count = 0;
        while(med == "Error") {
            if (mins < 10) {
                min = "0" + mins;
            } else {
                min = String.valueOf(mins);
            }
            time = hours + ":" + min;
            db = new DatabaseHelper(context);
            Cursor res = db.find_medname_dosge(time);
            if (res.getCount() != 0) {
                if (res.moveToNext()) {
                    med = res.getString(0);
                    dos = res.getString(1);
                }
                break;
            }
            mins--;
        }
        msg = " You need to take "+med+ " with a dosage of "+dos+" ";
        NotificationHelper notificationHelper = new NotificationHelper(context,id);
        Intent i = new Intent(context,Reminder.class);
        i.putExtra("medicine_name",med);
        i.putExtra("dosage",dos);
        PendingIntent pi = PendingIntent.getActivity(context,id,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(tim+msg,pi);
        notificationHelper.getManager().notify(id, nb.build());
        if (med != "Error") {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + med + ".3gp";
            AudioPlay.playAudio(path);
        }
        id++;
    }
}
