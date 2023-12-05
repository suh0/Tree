package com.example.tree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ProductTree> itemList;
    private static final String TAG="TreeAdapter";
    private SelectListener2 listener;

    public TreeAdapter(Context context, ArrayList<ProductTree> itemList, SelectListener2 listener){
        this.context=context;
        this.itemList=itemList;
        this.listener=listener;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.item_tree, parent, false);
        return new ViewHolder(itemView);
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){ // << 빨간 줄 뜨는데, 실행은 됨.
        ProductTree item=itemList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v){
               Log.d(TAG, "On click: "+ itemList.get(position).price);
               listener.onItemClicked(itemList.get(position), holder.parentLayout, holder.txt_price);
           }
        });
    }
    public void setPurchased(RecyclerView recyclerView, int position) {
        TreeAdapter.ViewHolder viewHolder = (TreeAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if(viewHolder != null) {
            TextView textView = viewHolder.txt_price;
            LinearLayout layout = viewHolder.parentLayout;
            textView.setText("In Stock");
            layout.setBackgroundResource(R.drawable.area_shop_tree_selected);
            notifyItemChanged(position);
        }
    }
    public int getItemCount(){
        return itemList.size();
    }
    public void addItem(ProductTree item){
        itemList.add(item);
    }
    public void clearAllItems() {
        itemList.clear();
        this.notifyDataSetChanged();
        Log.d(TAG, "clearAllItems: current recyclerview size: " + this.getItemCount());
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_price;
        ImageView img_tree;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txt_price=itemView.findViewById(R.id.txt_price);
            img_tree=itemView.findViewById(R.id.img_tree);
            parentLayout=itemView.findViewById(R.id.layout_tree);
        }
        public void setItem(ProductTree item){
            txt_price.setText("$"+item.getPrice());
            img_tree.setImageResource(item.getResId());
        }
    }

    public String getItemName(int index) {
        return itemList.get(index).name;
    }
}
