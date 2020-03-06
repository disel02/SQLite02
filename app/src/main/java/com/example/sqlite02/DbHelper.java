package com.example.sqlite02;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite02.ListAdapter.subjects;

import java.util.ArrayList;

import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="names";
    private static final int DB_VER=1;
    public static final String DB_TABLE="names";
    public static final String DB_COLUMN="rFname";
    public static final String DB_COLUMN2="rLname";
    Context mcontext;

    public DbHelper(@Nullable Context context   ) {
        super(context, DB_NAME, null, DB_VER);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL,%s TEXT NOT NULL);",DB_TABLE,DB_COLUMN,DB_COLUMN2);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query= String.format("DELETE TABLE IF EXISTS %S",DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewTask(String Fname, String Lname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,Fname);
        values.put(DB_COLUMN2,Lname);
        db.insertWithOnConflict(DB_TABLE,null,values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deletetask(String Fname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+" = ?",new String[]{Fname});
        db.close();
    }

    public void updatetask(String Fname, String Lname, String Fname02, String Lname02)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,Fname);
        values.put(DB_COLUMN2,Lname);
        db.update(DB_TABLE,values,DB_COLUMN+" = ? AND "+DB_COLUMN2+" = ?",new String[]{Fname02,Lname02});
        db.close();
    }

    public List<subjects> getTodos(){

        List<subjects> subjectsList;
        subjects subjects = null;
        subjectsList = new ArrayList<subjects>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT rFname,rLname FROM names",null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            subjects = new subjects();

                String fname= cursor.getString(0);
                subjects.sFname=fname;
                String lname=cursor.getString(1);
                subjects.sLname=lname;
                subjectsList.add(subjects);

            cursor.moveToNext();
        }

        return subjectsList;
    }

}