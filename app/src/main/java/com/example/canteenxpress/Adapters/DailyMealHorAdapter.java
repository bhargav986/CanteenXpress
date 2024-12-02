package com.example.canteenxpress.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenxpress.AddToCartActivity;
import com.example.canteenxpress.Models.CartHorModel;
import com.example.canteenxpress.Models.DailyMealHorModel;
import com.example.canteenxpress.Models.HomeHorModel;
import com.example.canteenxpress.R;

import java.util.List;

public class DailyMealHorAdapter extends RecyclerView.Adapter<DailyMealHorAdapter.ViewHolder> {
    Context context;
    List<DailyMealHorModel> list;

    public DailyMealHorAdapter(Context context, List<DailyMealHorModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DailyMealHorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal_hor_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DailyMealHorAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.hor_img);
            name = itemView.findViewById(R.id.hor_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        DailyMealHorModel clickedItem = list.get(position);
                        Intent intent = new Intent(context, AddToCartActivity.class);
                        intent.putExtra("itemName", clickedItem.getName());
                        intent.putExtra("itemImage", clickedItem.getImage());
                        intent.putExtra("itemPrice", clickedItem.getPrice());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
