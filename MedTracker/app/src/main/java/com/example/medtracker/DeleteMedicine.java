package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteMedicine extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText medname;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_medicine);
        myDb = new DatabaseHelper(this);
        medname = (EditText)findViewById(R.id.med);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_med();
            }
        });
    }
    public void delete_med(){
        Integer deletedRows = myDb.deleteData(medname.getText().toString().toLowerCase());
        if(deletedRows > 0)
            Toast.makeText(DeleteMedicine.this,"Medicine Deleted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(DeleteMedicine.this,"Medicine not Deleted",Toast.LENGTH_LONG).show();
    }
}
