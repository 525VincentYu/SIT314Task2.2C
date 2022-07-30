package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    ArrayList<Db2ModelClass> db2ModelClassesArrayList = new ArrayList<Db2ModelClass>();
    private RecyclerViewClickListener listener;

    public MyAdapter2(Context context, ArrayList<Db2ModelClass> db2ModelClassesArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.db2ModelClassesArrayList = db2ModelClassesArrayList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public MyAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.itemtruck, null);
        MyViewHolder myViewHoder = new MyViewHolder(view);
        return myViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Db2ModelClass db2ModelClass = db2ModelClassesArrayList.get(position);
        holder.imageButton.setImageBitmap(db2ModelClass.getImage());
        holder.feedback.setEnabled(false);
        holder.name.setText(db2ModelClass.getName());
        holder.discription.setText(db2ModelClass.getDiscription());
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.complete.setBackground(context.getResources().getDrawable(R.drawable.button_border));
                holder.feedback.setEnabled(true);
            }
        });

        //feedback item click

        holder.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Enabled=0;
                if(holder.feedback.isEnabled()==false){
                     Enabled = 1;
                     Intent intent = new Intent(context.getApplicationContext(),MyorderActivity.class);
                    intent.putExtra("key1",Enabled);
                    context.startActivity(intent);
                }


                Intent intent = new Intent(context.getApplicationContext(),FeedbackActivity.class);
                intent.putExtra("key",position);
                context.startActivity(intent);
            }
        });

        //item click and turn to order detail page

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(showitemActivity.this, "Test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(),OrderdetailsActivity.class);
                intent.putExtra("key",position);
                context.startActivity(intent);
            }
        });
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
        if (db2ModelClassesArrayList != null) {
            return db2ModelClassesArrayList.size();
        } else {
            Toast.makeText(context, "nullllll", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public interface RecyclerViewClickListener{
        void  onClick(View v,int position);
   }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton imageButton, imageButton2;
        TextView name;
        TextView discription;
        Button complete, feedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.image);
            imageButton2 = itemView.findViewById(R.id.share);
            complete = itemView.findViewById(R.id.complete);
            feedback = itemView.findViewById(R.id.feedback);

            name = itemView.findViewById(R.id.trukname);
            discription = itemView.findViewById(R.id.discription);
            itemView.setOnClickListener(this);




        }


        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }
}
