package com.example.android.foodopdowner.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.foodopdowner.Helper.ImageChooser;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.DrinkItem;
import com.example.android.foodopdowner.model.Drinks_Item;
import com.example.android.foodopdowner.model.FoodItem;

import java.util.ArrayList;

public class Drinks_DataAdapter extends RecyclerView.Adapter<Drinks_DataAdapter.ViewHolder> {

    Context context;
    private ArrayList<DrinkItem> drinkItems;
    ImageChooser imageChooser;


    public Drinks_DataAdapter(Context context, ArrayList<DrinkItem> drinkItems, ImageChooser imageChooser) {
        this.context=context;
        this.drinkItems=drinkItems;
        this.imageChooser = imageChooser;

    }


    @NonNull
    @Override
    public Drinks_DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drinks_card_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Drinks_DataAdapter.ViewHolder holder, final int position) {
        holder.drink_name.setText(drinkItems.get(position).getItem_name());
        holder.drink_price.setText(drinkItems.get(position).getOriginal_price());
        holder.drink_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageChooser.showAlertDialog(position);
            }
        });
        if (drinkItems.get(position).getItem_image() != null) {
            holder.drink_image.setImageURI(drinkItems.get(position).getItem_image());
        }
    }

    @Override
    public int getItemCount(){
        return drinkItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView drink_name;
        private ImageView drink_image;
        private TextView drink_price;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            drink_name = (TextView) itemView.findViewById(R.id.tv_name);
            drink_image=(ImageView)itemView.findViewById(R.id.item1);
            drink_price=(TextView)itemView.findViewById(R.id.tv_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
