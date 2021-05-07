package com.example.pos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConfirmOrderActivityAdapter extends RecyclerView.Adapter<ConfirmOrderActivityAdapter.ConfirmOrderViewHolder> {


    ArrayList<MyAdapter> items;

    MyAdapter mAdapter= new MyAdapter(null, null);


    ConfirmOrderActivity confirmOrderActivity=new ConfirmOrderActivity();


    Activity activity;

    public ConfirmOrderActivityAdapter(Activity activity){

        this.activity=activity;
    }

    public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
        TextView cName, cPrice;

        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            cName=itemView.findViewById(R.id.cName);
            cPrice=itemView.findViewById(R.id.cPrice);

        }
    }

    @NonNull
    @Override
    public ConfirmOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        ConfirmOrderViewHolder confirmOrderViewHolder=new ConfirmOrderViewHolder(view);
        return confirmOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderViewHolder holder, int position) {

        holder.cName.setText("Coke");
        holder.cPrice.setText("20");

        //System.out.println("Test9: "+ mAdapter.iName);


    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
