package com.example.pos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private OnItemClickListener mListerer;
    ArrayList<DataObj> dataObj=new ArrayList<>();
    ArrayList<String> iName=new ArrayList<>();
    ArrayList<Double> iPrice=new ArrayList<>();
    ArrayList<Double> iQty=new ArrayList<>();
    ArrayList<Double> iBill=new ArrayList<>();
    Double tBill=0.0;

    public interface OnItemClickListener{
        void onItemClicked(int position);
        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListerer=listener;
    }

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

       //LayoutInflater inflater=LayoutInflater.from(context);
       // View view = inflater.inflate(R.layout.my_row, parent,false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_rv_item_layout, parent, false);
        return new MyViewHolder(view, mListerer);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.item.setText(data1.get(i).getItemName());
        holder.price.setText(String.valueOf(data1.get(i).getSellPrice()));
        holder.qty.setText("0");


    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        EditText item,price,qty;
        ImageView imgCart;
        TextView  cName, cPrice;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            item=itemView.findViewById(R.id.item);
            price=itemView.findViewById(R.id.price);
            qty=itemView.findViewById(R.id.qty);
            imgCart=itemView.findViewById(R.id.cart);

            cName=itemView.findViewById(R.id.cName);
            cPrice=itemView.findViewById(R.id.cPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener != null){

                        int position= getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }

                }
            });


            imgCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener != null){

                        int position= getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){


                                    iName.add(item.getText().toString());
                            iPrice.add( Double.valueOf(price.getText().toString()));
                            iQty.add(Double.valueOf(qty.getText().toString()));
                            iBill.add(Double.valueOf(price.getText().toString()) *
                                    Double.valueOf(qty.getText().toString()));
                            tBill=tBill + (Double.valueOf(price.getText().toString()) *
                                    Double.valueOf(qty.getText().toString()));

                            dataObj.add(new DataObj(iName,iPrice,iQty,iBill,tBill));

                            System.out.println("Check: "+iName);



                            //Log.e("Test",dataObj.get(0).getItem().toString());
                            //System.out.println(dataObj.get(0).item);
                            //salesActivity=new SalesActivity();
                            listener.OnDeleteClick(position);
                        }
                    }

                }
            });




        }
    }
}
