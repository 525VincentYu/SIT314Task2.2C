package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Db2ModelClass>db2ModelClassesArrayList = new ArrayList<Db2ModelClass>();

    public MyAdapter(ArrayList<Db2ModelClass> db2ModelClassesArrayList) {
        this.db2ModelClassesArrayList = db2ModelClassesArrayList;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.itemtruck1, null);
        MyAdapter.MyViewHolder myViewHoder = new MyAdapter.MyViewHolder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
       Db2ModelClass db2ModelClass = db2ModelClassesArrayList.get(position);
       holder.imageButton.setImageBitmap(db2ModelClass.getImage());
       holder.name.setText(db2ModelClass.getName());
       holder.discription.setText(db2ModelClass.getDiscription());

       //share button item click

       holder.imageButton2.setOnClickListener((view)-> {
            {
               String title1 = db2ModelClass.getName();
               String context1 = db2ModelClass.getDiscription();

               Intent share1 = new Intent(Intent.ACTION_SEND);

               String extraText="This car type is"+title1+", besides its Model is"+context1;

               share1.putExtra(Intent.EXTRA_TEXT, extraText);
               share1.setType("text/plain");
               context.startActivity(Intent.createChooser(share1,"SHARE"));
           }

       });

    }

    @Override
    public int getItemCount() {
        return db2ModelClassesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageButton imageButton,imageButton2;
        TextView name;
        TextView discription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.image);
            imageButton2 = itemView.findViewById(R.id.share);
            name = itemView.findViewById(R.id.trukname);
            discription = itemView.findViewById(R.id.discription);
        }
    }
}
