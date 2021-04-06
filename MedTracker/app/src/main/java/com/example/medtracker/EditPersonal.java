package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPersonal extends AppCompatActivity implements View.OnClickListener {

    EditText name,add,mob,phar,hosp,rel,neigh;
    Button save;

    DataBaseHelper_Basic db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal);
        name = (EditText) findViewById(R.id.Pat_name);
        add = (EditText) findViewById(R.id.Pat_Address);
        mob = (EditText) findViewById(R.id.Mobile) ;
        phar = (EditText) findViewById(R.id.Pharmacy);
        hosp = (EditText) findViewById(R.id.Hospital);
        rel = (EditText) findViewById(R.id.Relative);
        neigh = (EditText) findViewById(R.id.Neighbor);
        save = (Button) findViewById(R.id.save);
        db = new DataBaseHelper_Basic(this);
        save.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        if(view == save) {
            boolean isSaved;
            if(db.getName() == "Error") {
                isSaved = db.insertData("1", name.getText().toString(), add.getText().toString(), mob.getText().toString(), phar.getText().toString(), neigh.getText().toString(), rel.getText().toString(), hosp.getText().toString());
            }
            else{
                isSaved = db.updateData(name.getText().toString(), add.getText().toString(), mob.getText().toString(), phar.getText().toString(), neigh.getText().toString(), rel.getText().toString(), hosp.getText().toString());
            }
            if(isSaved == true){
                Toast.makeText(EditPersonal.this,"Saved",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(EditPersonal.this,"Not Saved",Toast.LENGTH_LONG).show();
            }

            GotoPersonal();
        }

    }

    public void GotoPersonal() {
        Intent per = new Intent(this,  PersonalInfo.class);
        startActivity(per);
    }
}
