package com.example.sanitizerseller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanitizerseller.R;
import com.example.sanitizerseller.modules.Item;

import java.util.ArrayList;


public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemsAdapterViewHolder>
{
    Context context;
    ArrayList<Item>al;

    public OrderItemsAdapter(Context context, ArrayList<Item> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public OrderItemsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_items_list_card,parent,false);
        return new OrderItemsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsAdapterViewHolder holder, int position) {

        Item i=al.get(position);
        if(position==al.size()-1)
            holder.itemSpecialLine.setVisibility(View.INVISIBLE);
        holder.itemQuantity.setText("Item Quantity : "+i.getItemQuantity());
        holder.itemId.setText("Item ID : "+i.getId());
        holder.itemPrice.setText("Item Price : \u20B9 "+i.getItemMRP()+"/-");
        holder.itemName.setText("Item Name :"+i.getItemName());
        holder.itemWeight.setText("Item Weight : "+i.getOrderId());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class OrderItemsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemPrice,itemId,itemQuantity,itemSpecialLine,itemWeight;
        public OrderItemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemCardName);
            itemPrice=itemView.findViewById(R.id.itemCardPrice);
            itemId=itemView.findViewById(R.id.itemCardId);
            itemQuantity=itemView.findViewById(R.id.itemCardQuantity);
            itemSpecialLine=itemView.findViewById(R.id.itemCardSpecialLine);
            itemWeight=itemView.findViewById(R.id.itemCardWeight);
        }
    }
}
