package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton pers,msg,addpres,shortmed,rem;

    DatabaseHelper myDb;
    NotificationManager NM;
    DataBaseHelper_Basic db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        db = new DataBaseHelper_Basic(this);
        pers = (ImageButton) findViewById(R.id.personal);
        addpres = (ImageButton) findViewById(R.id.prescription);
        msg = (ImageButton) findViewById(R.id.emergency);
        rem = (ImageButton) findViewById(R.id.reminder);

        shortmed = (ImageButton) findViewById(R.id.shortmed);
        pers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalInfo();
            }
        });
        addpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addprescription();
            }
        });
        shortmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortage();
            }
        });

        ArrayList<String> med = new ArrayList<String>();
        ArrayList<Integer> count = new ArrayList<Integer>();

        Cursor res = myDb.getMedname_count();
        if(res.getCount() != 0){
            while (res.moveToNext()){
                String m_name = res.getString(0);
                int c = Integer.parseInt(res.getString(1));
                if(c <= 0){
                    myDb.deleteData(m_name);
                }
                else if(c < 4){
                    med.add(m_name);
                    count.add(c);
                }
            }
        }

        String me = "";
        for(int i=0;i<med.size();i++){
            me += med.get(i);
            if(i != med.size()-1){
                me += ", ";
            }

        }

        NM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        final String CHANNEL_ID ="my_channel_01";

        if(med.size() != 0 ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "my_notification";
                NotificationChannel nc = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
                nc.setDescription("New Notification");
                NM.createNotificationChannel(nc);
            }
            Intent i = new Intent(MainActivity.this,ShortageMedicines.class);
            PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID).setContentTitle("Shortage Alert!!!").setContentText("You have shortage on the following medicines: "+me).setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pi);
            NM.notify(0,builder.build());
        }


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showMessage("Button Clicked","Emergency Button Clicked");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    }
                    else {
                        requestPermissions( new String[]{
                                Manifest.permission.SEND_SMS
                        },1);
                    }
                }

                String permission[]={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
                if(ContextCompat.checkSelfPermission(MainActivity.this,permission[0])!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,permission[1])!=PackageManager.PERMISSION_GRANTED ){
                    ActivityCompat.requestPermissions(MainActivity.this,permission,101);
                }

                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if (l != null) {
                    if (db.getName() != "Error") {
                        String neighbor = db.getNeighborno();
                        String relative = db.getRelativeno();
                        String hospital = db.getHospitalno();
                        String name = db.getName();
                        String msg = "The person " + name + " is in need of emergency\n";
                        double lat = l.getLatitude();
                        double lon = l.getLongitude();
                        String message="http://maps.google.com/maps?q="+lat+","+lon;
                        StringBuffer smsBody = new StringBuffer();
                        smsBody.append(msg);
                        smsBody.append(Uri.parse(message));
                        SendSms(neighbor,smsBody.toString());
                        SendSms(relative,smsBody.toString());
                        SendSms(hospital,smsBody.toString());
                        //SendlongSms(hospital, msg + msg1);
                    } else {
                        Toast.makeText(MainActivity.this, "Save your personal info", Toast.LENGTH_LONG).show();
                    }
                }
                }
        });
    }

    public void personalInfo() {
        Intent per = new Intent(this,  PersonalInfo.class);
        startActivity(per);
    }

    public void addprescription(){
        Intent adpre = new Intent(this,  AddPrescription.class);
        startActivity(adpre);
    }


    public void shortage(){
        Intent sh = new Intent(this,  ShortageMedicines.class);
        startActivity(sh);
    }

    private void SendSms(String phone,String msg){
        android.telephony.SmsManager.getDefault().sendTextMessage(phone, null, msg, null,null);
    }







}
