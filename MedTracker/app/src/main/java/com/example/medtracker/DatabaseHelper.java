package com.example.medtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Prescription.db";
    public static final String TABLE_NAME = "PrescriptionData";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Medicine_name";
    public static final String COL_3 = "Morning_time";
    public static final String COL_4 = "Morning_dosage";
    public static final String COL_5 = "Afternoon_time";
    public static final String COL_6 = "Afternoon_dosage";
    public static final String COL_7 = "Night_time";
    public static final String COL_8 = "Night_dosage";
    public static final String COL_9 = "Quanity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,Medicine_name TEXT, Morning_time TEXT ,Morning_dosage INTEGER, Afternoon_time TEXT ,Afternoon_dosage INTEGER, Night_time TEXT, Night_dosage INTEGER, Quanity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String medname,String mortime,String morndosage, String afttime,String aftdosage, String nittime,String nitdosage, String quanity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,medname);
        contentValues.put(COL_3,mortime);
        contentValues.put(COL_4,morndosage);
        contentValues.put(COL_5,afttime);
        contentValues.put(COL_6,aftdosage);
        contentValues.put(COL_7,nittime);
        contentValues.put(COL_8,nitdosage);
        contentValues.put(COL_9,quanity);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String Medicine_name,String quanity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_9,quanity);
        db.update(TABLE_NAME, contentValues, "Medicine_name = ?",new String[] { Medicine_name });
        return true;
    }

    public Integer deleteData (String Medicine_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Medicine_name = ?",new String[] { Medicine_name });
    }

    public Cursor getMedname_count() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Medicine_name,Quanity from "+TABLE_NAME,null);
        return res;
    }

    public int getCount_Medicine(String medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Quanity from PrescriptionData where Medicine_name = '"+medicine+"'",null);
        if(res.getCount() == 0){
            return -1;
        }
        if(res.moveToNext()){
            String count = res.getString(0);
            return Integer.parseInt(count);
        }
        return -1;
    }

    public void reduceMedcount(String medicine,String dos){
        int c = getCount_Medicine(medicine);
        if(c != -1 || c != 0){
            updateData(medicine,String.valueOf(c - Integer.parseInt(dos)));
        }
    }
    public int getId(String medname){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID from PrescriptionData where Medicine_name = '"+medname+"'",null);
        if(res.getCount() == 0){
            return -1;
        }
        if(res.moveToNext()){
            String id = res.getString(0);
            return Integer.parseInt(id);
        }
        return -1;
    }

    public Cursor find_medname_dosge(String time){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res1 = db.rawQuery("select Medicine_name,Morning_dosage from PrescriptionData where Morning_time = '"+time+"'",null);
        if(res1.getCount() != 0){
            return res1;
        }
        Cursor res2 = db.rawQuery("select Medicine_name,Afternoon_dosage from  PrescriptionData where Afternoon_time = '"+time+"'",null);
        if(res2.getCount() != 0){
            return res2;
        }
        Cursor res = db.rawQuery("select Medicine_name,Night_dosage from PrescriptionData where Night_time = '"+time+"'",null);
        return res;


    }





}
