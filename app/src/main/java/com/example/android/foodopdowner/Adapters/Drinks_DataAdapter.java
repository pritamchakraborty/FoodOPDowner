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
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Drinks_Item;

import java.util.ArrayList;

public class Drinks_DataAdapter extends RecyclerView.Adapter<Drinks_DataAdapter.ViewHolder> {

    Context context;
    private  ArrayList<Drinks_Item.DDatum> ddatumList;


   /* Drinks_DataAdapter(Context context,ArrayList<Drinks_Item.DDatum>ddatumList){
        this.context=context;
        this.ddatumList=ddatumList;
    }*/
    public Drinks_DataAdapter(Context context, ArrayList<Drinks_Item.DDatum> ddatumList) {
        this.context=context;
        this.ddatumList=ddatumList;

    }



    @NonNull
    @Override
    public Drinks_DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drinks_card_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Drinks_DataAdapter.ViewHolder holder, final int position) {
       holder.drink_name.setText(ddatumList.get(position).getItem_name());
       holder.drink_price.setText(ddatumList.get(position).original_price);


        Glide.with(context)
                .load("https://hudsonagileventures.com/foodopd/uploads/" + ddatumList.get(position).getItem_image())
                .into(holder.drink_image);
       holder.drink_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext(),R.style.CustomDialogTheme);
               View view1 =LayoutInflater.from(context).inflate(R.layout.activity_drink_menu_details,null);
               EditText et_name = (view1.findViewById(R.id.edit_itemname));
               EditText et_price = (view1.findViewById(R.id.edit_price));
               TextView tv_save = (view1.findViewById(R.id.saveitem));

               et_name.getText().toString();
               et_price.getText().toString();

               builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });

               tv_save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(context, "Item saved", Toast.LENGTH_SHORT).show();
                   }
               });

               builder.setView(view1);
               final AlertDialog alertDialog=builder.create();
               holder.parentLayout.removeView(view1);
               alertDialog.show();

           }
       });

    }

    @Override
    public int getItemCount() {
        return ddatumList.size();
    }
    public void setData(ArrayList<Drinks_Item.DDatum>ddatumList){
        Log.v("Drinks_Item", "Values 111 == " + ddatumList.get(0).getItem_name());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView drink_name;
        private ImageView drink_image;
        private TextView drink_price;

        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            drink_name = (TextView) itemView.findViewById(R.id.drink_name);
            drink_image=(ImageView)itemView.findViewById(R.id.drink_image);
            drink_price=(TextView)itemView.findViewById(R.id.drink_price);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
