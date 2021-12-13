package com.example.debuggingtest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.debuggingtest.DBmain.TABLENAME;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ModelViewHolder> {
    Context context;
    ArrayList<Model>modelArrayList = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase;
    //generate constructor

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.singledata,null);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ModelViewHolder holder, int position) {
        final Model model=modelArrayList.get(position);
        holder.txtfname.setText(model.getFirstname());
        holder.txtlname.setText(model.getLastname());

        //click on button go to main activity
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("id",model.getId());
                bundle.putString("fname",model.getFirstname());
                bundle.putString("lname",model.getLastname());
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("userdata",bundle);
                context.startActivity(intent);
            }
        });

        //delete row
        holder.delete.setOnClickListener(new View.OnClickListener() {
            DBmain dBmain=new DBmain(context);
            @Override
            public void onClick(View v) {
                sqLiteDatabase=dBmain.getReadableDatabase();
                long delele=sqLiteDatabase.delete(TABLENAME,"id="+model.getId(),null);
                if (delele!=-1){
                    Toast.makeText(context, "deleted data successfully", Toast.LENGTH_SHORT).show();
                    modelArrayList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView txtfname,txtlname;
        Button edit,delete;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            txtfname=(TextView)itemView.findViewById(R.id.txtfname);
            txtlname=(TextView)itemView.findViewById(R.id.txtlname);
            edit=(Button)itemView.findViewById(R.id.txt_btn_edit);
           delete=(Button)itemView.findViewById(R.id.txt_btn_delete);
        }
    }
}
