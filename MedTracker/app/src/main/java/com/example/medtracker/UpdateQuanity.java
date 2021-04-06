package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateQuanity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText medname,quant;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quanity);

        myDb = new DatabaseHelper(this);

        medname = (EditText)findViewById(R.id.med_name);
        quant = (EditText)findViewById(R.id.qua);
        update = (Button) findViewById(R.id.updateq);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updatequan();
            }
        });

    }

    public void Updatequan(){
        boolean isUpdate = myDb.updateData(medname.getText().toString().toLowerCase(),
                quant.getText().toString());
        if(isUpdate == true)
            Toast.makeText(UpdateQuanity.this,"Medicine Updated",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UpdateQuanity.this,"Medicine not Updated",Toast.LENGTH_LONG).show();
    }

}
