package com.bilgehankalkan.abra.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilgehankalkan.abra.R;
import com.bilgehankalkan.abra.service.models.Order;

import java.util.List;

/**
 * Created by Bilgehan on 18.02.2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> listOrders;

    public OrderAdapter(Context context, List<Order> listOrders) {
        this.context = context;
        this.listOrders = listOrders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = listOrders.get(position);

        holder.textOrigin.setText(order.getOrigin().getName());
        holder.textDestination.setText(order.getDestination().getName());
        holder.textDate.setText(order.getOrigin().getDate());
        holder.textTime.setText(order.getDestination().getDate());
        holder.textUsername.setText(order.getOwner().getName() + " " + order.getOwner().getSurname());
        holder.textPrice.setText(order.getPrice() + "");
        holder.textRatingAverage.setText(String.valueOf(order.getRating()));

        if (order.getRating() > 4)
            holder.textRatingAverage.setBackground(ContextCompat.getDrawable(context, R.drawable.star_green));
        else if (order.getRating() > 3)
            holder.textRatingAverage.setBackground(ContextCompat.getDrawable(context, R.drawable.star_orange));
        else if (order.getRating() < 1.5)
            holder.textRatingAverage.setBackground(ContextCompat.getDrawable(context, R.drawable.star_red));
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textDate, textTime, textOrigin, textDestination,
                textPrice, textUsername, textAdditional, textRatingAverage;
        ImageView imageAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_background_item_order);
            textDate = itemView.findViewById(R.id.text_view_date_item_order);
            textTime = itemView.findViewById(R.id.text_view_time_item_order);
            textOrigin = itemView.findViewById(R.id.text_view_origin_item_order);
            textDestination = itemView.findViewById(R.id.text_view_destination_item_order);
            textPrice = itemView.findViewById(R.id.text_view_price_item_order);
            textUsername = itemView.findViewById(R.id.text_view_username_item_order);
            textAdditional = itemView.findViewById(R.id.text_view_additional_info_item_order);
            textRatingAverage = itemView.findViewById(R.id.text_view_rating_average_item_order);
            imageAvatar = itemView.findViewById(R.id.image_view_avatar_item_order);
        }
    }
}
