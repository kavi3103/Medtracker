package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Reminder extends AppCompatActivity {

    TextView text;
    Button yes,no;
    DatabaseHelper myDb;
    String med,dos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        text = (TextView) findViewById(R.id.text);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);

        myDb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            med = extras.getString("medicine_name");
            dos = extras.getString("dosage");
            if(med != "Error" && dos != "Error") {
                text.setText("Have you taken " + med + " with a dosage of " + dos);
                yes.setEnabled(true);
                no.setEnabled(true);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AudioPlay.stopAudio();
                        myDb.reduceMedcount(med,dos);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AudioPlay.stopAudio();
                    }
                });
            }
            else{
                yes.setEnabled(false);
                no.setEnabled(false);
            }
        }
        else{
            text.setText("extras is null!!!");
        }
    }
}
