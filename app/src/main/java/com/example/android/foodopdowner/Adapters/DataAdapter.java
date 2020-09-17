package com.example.android.foodopdowner.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Food_Items;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
   // private List<Food_Items>commentdata;
    Context context;
    private ArrayList<Food_Items.Datum>datumList;
    private static final int CAMERA_REQUEST = 1888;
    Intent data;


    public DataAdapter(Context context, ArrayList<Food_Items.Datum> datumList) {

        this.context =context;
        this.datumList=datumList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, final int position) {

        holder.tv_name.setText(datumList.get(position).getItem_name());
        holder.tv_price.setText(datumList.get(position).getOriginal_price());

        Glide.with(context)
                .load("https://hudsonagileventures.com/foodopd/uploads/" + datumList.get(position).getItem_image())
                .into(holder.tv_image);
//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Log.d(TAG,"onClick: Clicked on:" + tv_name.get(position));
//                Intent intent = new Intent(v.getContext(), MenuDetails.class);
//                intent.putExtra("tv_names", (Serializable) datumList.get(position));
//
//                v.getContext().startActivity(intent);
//            }
//        });
        holder.tv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext(),R.style.CustomDialogTheme);
                View view =LayoutInflater.from(context).inflate(R.layout.activity_menu_details,null);
                EditText et_name = (view.findViewById(R.id.edit_itemname));
                ImageView imageitem1 = (view.findViewById(R.id.imageitem1));
                EditText et_price = (view.findViewById(R.id.edit_price));
                final CheckBox check_veg = (view.findViewById(R.id.check_veg));
                final CheckBox check_non_veg = (view.findViewById(R.id.check_non_veg));
                TextView tv_save = (view.findViewById(R.id.saveitem));


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


                check_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                       if(check_veg.isChecked())
                       {
                           Toast.makeText(context, "veg", Toast.LENGTH_SHORT).show();
                           check_non_veg.setChecked(false);
                       }
                       else
                        {
                            Toast.makeText(context, "non veg", Toast.LENGTH_SHORT).show();
                            check_non_veg.setChecked(true);
                        }
                    }
                });

                builder.setView(view);
                final AlertDialog alertDialog=builder.create();
                holder.parentLayout.removeView(view);
                alertDialog.show();

            }
        });

    }






    @Override
    public int getItemCount() {
        return datumList.size();
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
