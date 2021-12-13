package com.example.debuggingtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public static final String DBNAME="student.db";
    public static final String TABLENAME="course";
    public static final int VER=1;
    String query;
    public DBmain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query="create table "+TABLENAME+"(id integer primary key, fname text, lname text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        query="drop table if exists "+TABLENAME+"";
        db.execSQL(query);
        onCreate(db);
    }
}
