package com.example.pos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConfirmOrderActivityAdapter extends RecyclerView.Adapter<ConfirmOrderActivityAdapter.ConfirmOrderViewHolder> {


    ArrayList<String> name;
    ArrayList<String> price;
    ArrayList<String> qty;
    ArrayList<String> total;



    Activity activity;

    public ConfirmOrderActivityAdapter(Activity activity, ArrayList<String>name,ArrayList<String>price,ArrayList<String>qty, ArrayList<String>total ){

        this.activity=activity;
        this.name=name;
        this.price=price;
        this.qty=qty;
        this.total=total;
    }

    public class ConfirmOrderViewHolder extends RecyclerView.ViewHolder {
        TextView cName, cPrice, unit, total;
        EditText qty;

        public ConfirmOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            cName=itemView.findViewById(R.id.cName);
            cPrice=itemView.findViewById(R.id.cPrice);
            qty=itemView.findViewById(R.id.qty);
            unit=itemView.findViewById(R.id.unit);
            total=itemView.findViewById(R.id.total);

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

        holder.cName.setText(name.get(position));
        holder.cPrice.setText(price.get(position));
        holder.qty.setText(qty.get(position));
        holder.unit.setText(qty.get(position));
        holder.total.setText(total.get(position));

        //System.out.println("bmw"+name);


    }

    @Override
    public int getItemCount() {
        return name.size();
    }


}
