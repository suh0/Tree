package com.example.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder>{

    private ArrayList<ItemLog> itemList;
    private Context context;

    public LogAdapter(Context context, ArrayList<ItemLog> itemList){
        this.context=context;
        this.itemList=itemList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_log, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        ItemLog item=itemList.get(position);
        holder.setItem(item, position);
    }

    public int getItemCount(){
        return itemList.size();
    }

    public void addItem(ItemLog item){
        itemList.add(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_number, txt_date, txt_time, txt_sf;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txt_date=itemView.findViewById(R.id.txt_date);
            txt_number=itemView.findViewById(R.id.txt_number);
            txt_time=itemView.findViewById(R.id.txt_time);
            txt_sf=itemView.findViewById(R.id.txt_sf);
        }
        public void setItem(ItemLog item, int position){
            String number=""+(position+1);
            txt_number.setText(number);
            txt_date.setText(item.getDate());
            txt_time.setText(item.getTime()+"m");

            if(item.getSf().equals("Success")){
                txt_sf.setText("Success");
            }else{
                txt_sf.setText("Fail");
            }
        }
    }
}
