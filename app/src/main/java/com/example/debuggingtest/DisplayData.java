package com.example.debuggingtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

import static com.example.debuggingtest.DBmain.TABLENAME;

public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        dBmain=new DBmain(this);
        findid();
       displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayData() {
        sqLiteDatabase=dBmain.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select *from "+TABLENAME+"",null);
        ArrayList<Model>modelArrayList=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String fname=cursor.getString(1);
            String lname=cursor.getString(2);
            modelArrayList.add(new Model(id, fname, lname));
        }
        cursor.close();
        myAdapter=new MyAdapter(this,R.layout.singledata,modelArrayList,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }



    private void findid() {
        recyclerView=findViewById(R.id.rv);
    }
}