package com.example.sanitizerseller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.example.sanitizerseller.R;
import com.example.sanitizerseller.modules.Item;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>
{
    Context context;
    ArrayList<Item>al;

    public ItemListAdapter(Context context, ArrayList<Item> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_item_card,parent,false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {

        Item i=al.get(position);
        holder.itemCardName.setText("Name : "+i.getItemName());
        holder.itemCardWeight.setText("Weight : "+i.getItemWeight());
        holder.itemCardOutPrice.setText("Out Price : \u20B9 "+i.getItemOutPrice());
        holder.itemCardMRP.setText("MRP : \u20B9 "+i.getItemMRP());
        holder.itemCardId.setText("ID :"+i.getId());
        Picasso.with(context).load(i.getItemImage()).resize(400,400).centerCrop().into(holder.itemCardImg);
        //Picasso.with(context).load(i.getItemImage()).into(holder.itemCardImg);
        holder.itemCardCategory.setText("Category : "+i.getItemCategory());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {
        ImageView itemCardImg;
        TextView itemCardName,itemCardId,itemCardMRP,itemCardOutPrice,itemCardWeight,itemCardCategory;
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCardImg=itemView.findViewById(R.id.itemCardImg);
            itemCardId=itemView.findViewById(R.id.itemCardId);
            itemCardName=itemView.findViewById(R.id.itemCardName);
            itemCardMRP=itemView.findViewById(R.id.itemCardMRP);
            itemCardOutPrice=itemView.findViewById(R.id.itemCardOutPrice);
            itemCardWeight=itemView.findViewById(R.id.itemCardItemWeight);
            itemCardCategory=itemView.findViewById(R.id.itemCardCategory);
        }
    }
}
