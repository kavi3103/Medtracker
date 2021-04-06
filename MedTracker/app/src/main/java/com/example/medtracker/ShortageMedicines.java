package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShortageMedicines extends AppCompatActivity {
    DatabaseHelper myDb;
    DataBaseHelper_Basic db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortage_medicines);

        myDb = new DatabaseHelper(this);
        db = new DataBaseHelper_Basic(this);

        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<String> med = new ArrayList<String>();
        ArrayList<Integer> count = new ArrayList<Integer>();

        Cursor res = myDb.getMedname_count();
        if(res.getCount() != 0){
            while (res.moveToNext()){
                String m_name = res.getString(0);
                int c = Integer.parseInt(res.getString(1));
                if(c < 4){
                    med.add(m_name);
                    count.add(c);
                }
            }
        }
        if(med.size() == 0){
            TextView textView = new TextView(this);
            textView.setText("You have sufficient medicines");
            textView.setTextSize((float) 20);
            textView.setPadding(20, 50, 20, 50);
            linearLayout.addView(textView);

        }
        ArrayList<String> textArray = new ArrayList<String>();
        for(int i=0;i<med.size();i++){
            textArray.add("Medicine " + med.get(i) + " is having less in number " + count.get(i) + ".\n ");
        }
        for( int i = 0; i < textArray.size(); i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(textArray.get(i));
            textView.setTextSize((float) 20);
            textView.setPadding(20, 50, 20, 50);
            linearLayout.addView(textView);
        }
        String me = "";
        for(int i=0;i<med.size();i++){
            me += med.get(i);
            if(i != med.size()-1){
                me += ", ";
            }

        }
        if(med.size() != 0){
            Button btnTag = new Button(this);
            btnTag.setText("Order medicines");
            final String pharamcy = db.getPharmacyno();
            String Address = db.getAddress();
            final String msg = "Please deliver the following medicines:" + me+" to this Address : "+Address;
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        }
                        else {
                            requestPermissions( new String[]{
                                    Manifest.permission.SEND_SMS
                            },1);
                        }
                    }
                    //Toast.makeText(ShortageMedicines.this,"clicked",Toast.LENGTH_SHORT).show();
                    if(pharamcy == "Error"){
                      Toast.makeText(ShortageMedicines.this,"Save your personal info",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SendSms(pharamcy, msg);
                    }
                }
            });
            linearLayout.addView(btnTag);

        }
    }
    private void SendSms(String phone,String msg){
        //String phone = "8681085144";
        //String msg = "Text Message";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, msg, null, null);
            Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"Message not sent",Toast.LENGTH_SHORT).show();
        }


    }
}
