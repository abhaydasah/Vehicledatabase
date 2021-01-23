package com.example.vehicledatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper  extends SQLiteOpenHelper {

      public static final String DATABASE_NAME ="vehicle.db";
      public static final String TABLE_NAME ="preset_data_table";
      public static final String COL_1 ="ID";
      public static final String COL_2 ="FANSPEED";
      public static final String COL_3 ="ACSTATE";
      public static final String COL_4 ="TEMPERATURE";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,FANSPEED INTEGER,ACSTATE TEXT,TEMPERATURE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String FANSPEED,String AC,String TEMPER){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,FANSPEED);
        contentValues.put(COL_3,AC);
        contentValues.put(COL_4,TEMPER);



        long Inserted=db.insert(TABLE_NAME,null,contentValues);


        if (Inserted==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from preset_data_table", null );
        return res;
    }

}
