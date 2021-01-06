package com.example.pos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<InventObj> data1;
    //String data1[];
    public MyAdapter(Context ct, ArrayList<InventObj> s1){
        this.context=ct;
        this.data1=s1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       // LayoutInflater inflater=LayoutInflater.from(context);
       // View view = inflater.inflate(R.layout.my_row, parent,false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.item.setText(data1.get(i).getItemName());
        holder.price.setText(String.valueOf(data1.get(i).getSellPrice()));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText item,price,qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            price=itemView.findViewById(R.id.price);
            qty=itemView.findViewById(R.id.qty);

        }
    }
}
