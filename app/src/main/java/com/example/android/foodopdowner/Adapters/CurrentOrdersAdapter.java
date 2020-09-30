package com.example.android.foodopdowner.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.foodopdowner.Helper.ImageChooser;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.CurrentOrdersModel;
import com.example.android.foodopdowner.model.FoodItem;
import com.example.android.foodopdowner.model.Food_Items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentOrdersAdapter extends RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder> {
    List<CurrentOrdersModel>currentOrdersData ;
    Context context;


    public CurrentOrdersAdapter(Context context, List<CurrentOrdersModel> currentOrdersData) {

        this.context = context;
        this.currentOrdersData = currentOrdersData;

    }

    @NonNull
    @Override
    public CurrentOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_orders_adapter, parent, false);

        return new CurrentOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrentOrdersAdapter.ViewHolder holder, final int position) {

//        String input = currentOrdersData.get(position).getData().get(position).getCreatedAt();
//        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = null;
//        try {
//            date = inputFormatter.parse(input);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        DateFormat outputFormatter = new SimpleDateFormat("HH:mm:ss");
//        String output = outputFormatter.format(date);
        holder.tv_order_id.setText(currentOrdersData.get(position).getData().get(position).getOrderId());
        //holder.tv_order_time.setText(output);
    }


    @Override
    public int getItemCount() {
        return currentOrdersData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_order_id, tv_first_name, tv_last_name, tv_order_time, tv_pickup_time, pickup, delivery;
        private Spinner spinner_item;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_order_id = (TextView) itemView.findViewById(R.id.tv_order_id);
            tv_first_name = (TextView) itemView.findViewById(R.id.tv_first_name);
            tv_last_name = (TextView) itemView.findViewById(R.id.tv_last_name);
            tv_order_time = (TextView) itemView.findViewById(R.id.tv_order_time);
            tv_pickup_time = (TextView) itemView.findViewById(R.id.tv_pickup_time);
            pickup = (TextView) itemView.findViewById(R.id.pickup);
            delivery = (TextView) itemView.findViewById(R.id.delivery);
            delivery = (TextView) itemView.findViewById(R.id.delivery);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            spinner_item = itemView.findViewById(R.id.spinner_item);

        }
    }
}