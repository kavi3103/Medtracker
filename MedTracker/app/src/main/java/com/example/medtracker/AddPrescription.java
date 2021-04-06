package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class AddPrescription extends AppCompatActivity implements View.OnClickListener  {

    EditText medname,morTime,morDosage,aftTime,aftDosage,nigTime,nigDosage,quant;
    Button submit,clear,record,stop,updatequant,delmed;
    ImageButton selectmorn,selectaft,selectnigt,viewAll;

    private int  mHour, mMinute;

    DatabaseHelper myDb;

    String path = "";
    MediaRecorder mediaRecorder;

    final int REQUEST_PERMISSION_CODE = 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

        if(!checkPermissionFromDevice())
            requestPermissions();

        myDb = new DatabaseHelper(this);

        medname = (EditText)findViewById(R.id.medname);
        morTime = (EditText)findViewById(R.id.mornTime);
        morDosage = (EditText)findViewById(R.id.mornDos);
        aftTime = (EditText)findViewById(R.id.aftTime);
        aftDosage = (EditText)findViewById(R.id.aftDos);
        nigTime = (EditText)findViewById(R.id.nigTime);
        nigDosage = (EditText)findViewById(R.id.nigDos);
        quant = (EditText)findViewById(R.id.quani);

        selectmorn = (ImageButton) findViewById(R.id.selectmor);
        selectaft = (ImageButton) findViewById(R.id.selectaft);
        selectnigt = (ImageButton) findViewById(R.id.selectnig);

        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);
        viewAll = (ImageButton) findViewById(R.id.viewall);
        updatequant = (Button) findViewById(R.id.updatequant);
        delmed = (Button) findViewById(R.id.deletemedicine);
        record = (Button) findViewById(R.id.record);
        stop = (Button) findViewById(R.id.stop);

        selectmorn.setOnClickListener(this);
        selectnigt.setOnClickListener(this);
        selectaft.setOnClickListener(this);
        submit.setOnClickListener(this);
        clear.setOnClickListener(this);
        viewAll.setOnClickListener(this);
        updatequant.setOnClickListener(this);
        delmed.setOnClickListener(this);
        record.setOnClickListener(this);
        stop.setOnClickListener(this);
        stop.setEnabled(false);



    }

    public void onClick(View view)
    {
        if(view == selectmorn){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String min;
                            if(minute < 10){
                               min = "0"+minute;
                            }
                            else{
                                min = String.valueOf(minute);
                            }

                            morTime.setText(hourOfDay + ":" + min);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(view == selectaft){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String min;
                            if(minute < 10){
                                min = "0"+minute;
                            }
                            else{
                                min = String.valueOf(minute);
                            }

                            aftTime.setText(hourOfDay + ":" + min);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(view == selectnigt){
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String min;
                            if(minute < 10){
                                min = "0"+minute;
                            }
                            else{
                                min = String.valueOf(minute);
                            }

                            nigTime.setText(hourOfDay + ":" + min);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if(view == record){

            String medicine = medname.getText().toString().toLowerCase();

            if(checkPermissionFromDevice()){
                    //Toast.makeText(AddPrescription.this,medicine,Toast.LENGTH_SHORT).show();
                    //showMessage(medicine,medicine);
                if(medicine.trim().length()  != 0) {
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + medicine + ".3gp";
                    setUPMediaRecorder();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    stop.setEnabled(true);
                    record.setEnabled(false);

                    Toast.makeText(AddPrescription.this, "Recording.... to" + path, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddPrescription.this,"Give medicine name",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                requestPermissions();
            }
        }
        if(view == stop){
            mediaRecorder.stop();
            stop.setEnabled(false);
            record.setEnabled(true);
        }
        if(view == clear){
            clearText();
        }
        if(view == submit){
            boolean isInserted = myDb.insertData(medname.getText().toString().toLowerCase(),morTime.getText().toString(),morDosage.getText().toString(),aftTime.getText().toString(),aftDosage.getText().toString(),nigTime.getText().toString(),nigDosage.getText().toString(),quant.getText().toString());
            if(isInserted == true)
                Toast.makeText(AddPrescription.this,"Medicine added",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(AddPrescription.this,"Medicine not added",Toast.LENGTH_LONG).show();
            String time1 = morTime.getText().toString();
            String time2 = aftTime.getText().toString();
            String time3 = nigTime.getText().toString();
            int id = myDb.getId(medname.getText().toString().toLowerCase());
            if(time1.length() != 0) {
                startAlarm(time1,3*id+1);
            }
            if(time2.length() != 0) {
                startAlarm(time2,3*id+2);
            }
            if(time3.length() != 0) {
                startAlarm(time3,3*id+3);
            }

            clearText();

        }
        if(view == viewAll){
            Cursor res = myDb.getAllData();
            if(res.getCount() == 0) {
                // show message
                showMessage("Error","Nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append("Id :"+ res.getString(0)+"\n");
                buffer.append("Medicine Name :"+ res.getString(1)+"\n");
                buffer.append("Morning :  "+ res.getString(2)+" Dos: "+res.getString(3) + "\n");
                buffer.append("Afternoon :  "+ res.getString(4)+" Dos: "+res.getString(5) + "\n");
                buffer.append("Night :  "+ res.getString(6)+" Dos: "+res.getString(7) + "\n");
                buffer.append("Quanity :"+ res.getString(8)+"\n\n");
            }

            // Show all data
            showMessage("Prescription",buffer.toString());
        }
        if(view == updatequant){
            updatequanity();
        }
        if(view == delmed){
            delete_medicine();
        }

    }

    private void startAlarm(String time,int pi) {
        String timesplit[] = time.split(":");
        int hour = Integer.parseInt(timesplit[0]);
        int min = Integer.parseInt(timesplit[1]);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        //Toast.makeText(AddPrescription.this,c.getTime().toString(),Toast.LENGTH_LONG).show();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pi, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }


    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        medname.setText("");
        morTime.setText("");
        morDosage.setText("");
        aftDosage.setText("");
        aftTime.setText("");
        nigDosage.setText("");
        nigTime.setText("");
        quant.setText("");
        medname.requestFocus();
    }

    protected void onStop() {
        // TODO Auto-generated method stub
        myDb.close();
        super.onStop();
    }

    public void updatequanity(){
        Intent upd = new Intent(this,  UpdateQuanity.class);
        startActivity(upd);
    }

    public void delete_medicine(){
        Intent del = new Intent(this,  DeleteMedicine.class);
        startActivity(del);
    }


    private void setUPMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted" , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    private boolean checkPermissionFromDevice() {
        int write_external = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return write_external == PackageManager.PERMISSION_GRANTED && record_audio == PackageManager.PERMISSION_GRANTED;
    }
}


