package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PersonalInfo extends AppCompatActivity {

    Button edit;
    TextView name,address,mobileno,pharmacyno,hospitalno,relativeno,neighborno;
    ImageButton back;

    DataBaseHelper_Basic db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        edit = (Button) findViewById(R.id.edit);
        name = (TextView) findViewById(R.id.Name);
        address = (TextView) findViewById(R.id.Address);
        mobileno = (TextView) findViewById(R.id.Mobileno);
        pharmacyno = (TextView) findViewById(R.id.Pharmacyno);
        relativeno = (TextView) findViewById(R.id.Relativeno);
        hospitalno = (TextView) findViewById(R.id.Hospitalno);
        neighborno = (TextView) findViewById(R.id.Neighborno);

        back = (ImageButton) findViewById(R.id.back);

        db = new DataBaseHelper_Basic(this);

        if(db.getName() != "Error") {

            name.setText(db.getName());
            address.setText(db.getAddress());
            mobileno.setText(db.getMobileno());
            pharmacyno.setText(db.getPharmacyno());
            relativeno.setText(db.getRelativeno());
            hospitalno.setText(db.getHospitalno());
            neighborno.setText(db.getNeighborno());

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToEdit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoMain();
            }


        });
    }

    private void GotoMain() {
        Intent bck = new Intent(this,  MainActivity.class);
        startActivity(bck);
    }

    public void GoToEdit() {
        Intent per = new Intent(this,  EditPersonal.class);
        startActivity(per);
    }

}
