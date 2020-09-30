package com.example.android.foodopdowner.Adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.foodopdowner.Activities.FoodMenu;
import com.example.android.foodopdowner.Helper.ImageChooser;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.FoodItem;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<FoodItem> foodItems;
    Context context;
    ImageChooser imageChooser;


    public DataAdapter(Context context, ArrayList<FoodItem> foodItems, ImageChooser imageChooser) {

        this.context = context;
        this.foodItems = foodItems;
        this.imageChooser = imageChooser;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, final int position) {

        holder.tv_name.setText(foodItems.get(position).getItem_name());
        holder.tv_price.setText(foodItems.get(position).getOriginal_price());
        holder.tv_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imageChooser.showAlertDialog(position);
            }
        });
        if(foodItems.get(position).getItem_image() != null)
        {
            holder.tv_image.setImageURI(foodItems.get(position).getItem_image());
        }
    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }
    public void setData(ArrayList<Food_Items.Datum>datumList){
        Log.v("Food_Items","Values 111=="+ datumList.get(0).getItem_name());

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private ImageView tv_image;
        private TextView tv_price;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_image = (ImageView) itemView.findViewById(R.id.item1);
            tv_price=(TextView)itemView.findViewById(R.id.tv_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
