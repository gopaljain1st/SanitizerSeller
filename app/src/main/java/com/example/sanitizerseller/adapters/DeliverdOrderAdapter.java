package com.example.sanitizerseller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.sanitizerseller.R;
import com.example.sanitizerseller.modules.Order;

public class DeliverdOrderAdapter extends RecyclerView.Adapter<DeliverdOrderAdapter.orderAdapterViewHolder>
{
    Context context;
    ArrayList<Order> al;

    public DeliverdOrderAdapter(Context context, ArrayList<Order> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public DeliverdOrderAdapter.orderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.delivered_fragment_card,parent,false);
        return new DeliverdOrderAdapter.orderAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapterViewHolder holder, int position) {
        final Order o=al.get(position);
        holder.orderDate.setText(o.getDateOfOrder());
        holder.address.setText(o.getAddress());
        holder.name.setText(o.getName());
        holder.itemCount.setText(o.getItemCount());
        holder.mobile.setText(o.getMobile());
        holder.totalPrice.setText(o.getTotalAmount());
        holder.orderId.setText(o.getOrderId());
        holder.orderStatus.setText(o.getOrderStatus());
        holder.orderTime.setText(o.getTimeOfOrder());
        holder.deliveryPlace.setText(o.getDeliveryTime());
    }
    @Override
    public int getItemCount() {
        return al.size();
    }


    public class orderAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView orderDate,orderTime,orderId,orderStatus,name,address,mobile,itemCount,totalPrice,deliveryPlace;
        public orderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate=itemView.findViewById(R.id.order_date);
            orderTime=itemView.findViewById(R.id.order_time);
            orderId=itemView.findViewById(R.id.orderId);
            orderStatus=itemView.findViewById(R.id.status);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            mobile=itemView.findViewById(R.id.mobile);
            itemCount=itemView.findViewById(R.id.total_savings);
            totalPrice=itemView.findViewById(R.id.payableAmount);
            deliveryPlace=itemView.findViewById(R.id.itemWeightData);
           }
    }
}

