package com.example.medtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper_Basic extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BasicInformation.db";
    public static final String TABLE_NAME = "BasicInfo";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Patient_name";
    public static final String COL_3 = "Address";
    public static final String COL_4 = "MobileNo";
    public static final String COL_5 = "PharmacyNo";
    public static final String COL_6 = "NeighborNo";
    public static final String COL_7 = "RelativeNo";
    public static final String COL_8 = "HospitalNo";


    public DataBaseHelper_Basic(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID TEXT PRIMARY KEY ,Patient_name TEXT, Address TEXT ,MobileNo TEXT,PharmacyNo TEXT,NeighborNo TEXT,RelativeNo TEXT,HospitalNo TEXT)");
        //insertData(null,null,null,null,null,null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String id,String patname,String address,String mobileno, String pharmacy,String neighbor, String relative,String hospital) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,patname);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,mobileno);
        contentValues.put(COL_5,pharmacy);
        contentValues.put(COL_6,neighbor);
        contentValues.put(COL_7,relative);
        contentValues.put(COL_8,hospital);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData(String patname,String address,String mobileno, String pharmacy,String neighbor, String relative,String hospital) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        String id = "1";
        contentValues.put(COL_2,patname);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,mobileno);
        contentValues.put(COL_5,pharmacy);
        contentValues.put(COL_6,neighbor);
        contentValues.put(COL_7,relative);
        contentValues.put(COL_8,hospital);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;




        //db.execSQL("Update BasicInfo Set Patient_name = '"+patname+"', Address = '"+ address+"', MobileNo = '"+ mobileno+"', PharmacyNo = '"+ pharmacy+"', NeighborNo = '"+ neighbor+"', RelativeNo = '"+ relative+"', HospitalNo = '"+ hospital+"' where ID = 1");
        //return true;
    }



    public String getName() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select Patient_name from BasicInfo where ID ="+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String Name = res.getString(0);
            return Name;
        }
        return "Error";
    }

    public String getAddress() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select Address from BasicInfo where ID = "+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String address = res.getString(0);
            return address;
        }
        return "Error";
    }

    public String getMobileno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select MobileNo from BasicInfo where ID ="+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String mobileno = res.getString(0);
            return mobileno;
        }
        return "Error";
    }

    public String getPharmacyno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select PharmacyNo from BasicInfo where ID ="+id ,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String pharmacyno = res.getString(0);
            return pharmacyno;
        }
        return "Error";
    }

    public String getRelativeno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select RelativeNo from BasicInfo where ID = "+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String relativeno = res.getString(0);
            return relativeno;
        }
        return "Error";
    }

    public String getNeighborno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select NeighborNo from BasicInfo where ID = "+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String neighborno = res.getString(0);
            return neighborno;
        }
        return "Error";
    }

    public String getHospitalno() {
        SQLiteDatabase db = this.getWritableDatabase();
        String id = "1";
        Cursor res = db.rawQuery("select HospitalNo from BasicInfo where ID = "+id,null);
        if(res.getCount() == 0){
            return "Error";
        }
        if(res.moveToNext()){
            String hospitalno = res.getString(0);
            return hospitalno;
        }
        return "Error";
    }


}
