package com.app.tunmgmg.sampledatabases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tun Mg Mg on 11/8/2016.
 */

public class Database extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase mydb;


    public Database(Context context) {
        super(context,"student db",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE studentInfo(Id INTEGER PRIMARY KEY AUTOINCREMENT,Name CHAR,Roll CHAR,Class CHAR,Major CHAR)";
              db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String s, String s1, String s2, String s3) {
        mydb=getWritableDatabase();
        String sql = "Insert into studentInfo(Name,Roll,Class,Major)" +
                "values('"+s+"','"+s1+"','"+s2+"','"+s3+"')";
        mydb.execSQL(sql);
        mydb.close();
        Log.d("insert","insert");
    }

    public ArrayList<GetData> getAllData() {
        ArrayList<GetData> arraylist=new ArrayList<>();
        mydb=getReadableDatabase();
        String sql="select * from studentInfo";
        Cursor cursor=mydb.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
              GetData getData=new GetData();
                getData.setId(cursor.getInt(0));
                getData.setName(cursor.getString(1));
                getData.setRoll(cursor.getString(2));
                getData.setStudentClass(cursor.getString(3));
                getData.setMajor(cursor.getString(4));
                arraylist.add(getData);
            }while (cursor.moveToNext());
        }
       return arraylist;
    }


    public void updateData(int count,String s, String s1, String s2, String s3) {
        mydb=getWritableDatabase();

        String sql="Update studentInfo set Name ='"+s+"' where Id="+count+"";
        mydb.execSQL(sql);
        mydb.close();
        Log.d("update","update");
    }

    public void deleteData(int id, String s, String s1, String s2, String s3) {

        mydb=getWritableDatabase();
        String sql="Delete from studentInfo where Id="+id+"";
        mydb.execSQL(sql);
        mydb.close();

    }

    public Cursor searchData( String s) {
        mydb=getReadableDatabase();
        String sql="select * from studentInfo where Name='"+s+"'";

        Cursor cursor=mydb.rawQuery(sql,null);

        Log.d("search","search");
        return cursor;
    }
}
