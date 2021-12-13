package com.example.debuggingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.debuggingtest.DBmain.TABLENAME;

public class MainActivity extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    EditText fname, lname;
    Button submit, Display,edit;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBmain = new DBmain(this);
        //create method
        findid();
        insertData();
        editData();
    }

    private void editData() {
        if (getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle=getIntent().getBundleExtra("userdata");
            id=bundle.getInt("id");
            fname.setText(bundle.getString("fname"));
            lname.setText(bundle.getString("lname"));
           edit.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
    }

    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("fname", fname.getText().toString());
                cv.put("lname", lname.getText().toString());

                sqLiteDatabase = dBmain.getWritableDatabase();
                Long recinsert = sqLiteDatabase.insert(TABLENAME, null, cv);
                if (recinsert != null) {
                    Toast.makeText(MainActivity.this, "successfully inserted data", Toast.LENGTH_SHORT).show();
                    //clear when click on submit
                    fname.setText("");
                    lname.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "something wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // when click on display button open display data activity

        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayData.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"User Updated..",Toast.LENGTH_SHORT).show();
            }
        });


       // storing edited data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("fname",fname.getText().toString());
                cv.put("lname",lname.getText().toString());

                sqLiteDatabase=dBmain.getReadableDatabase();
                long recedit=sqLiteDatabase.update(TABLENAME,cv,"id="+id,null);
                if (recedit!=-1){
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    submit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this, "something wrong try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findid()  {
        fname = (EditText) findViewById(R.id.edit_fname);
        lname = (EditText) findViewById(R.id.edit_lname);
        submit = (Button) findViewById(R.id.submit_btn);
         Display = (Button) findViewById(R.id.display_btn);
         edit = (Button) findViewById(R.id.edit_btn_main);
    }
}