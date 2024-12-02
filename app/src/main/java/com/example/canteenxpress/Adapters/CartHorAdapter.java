package com.example.canteenxpress.Adapters;

import static com.example.canteenxpress.Myapp.db;
import static com.example.canteenxpress.Myapp.dbHelper;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenxpress.Models.CartHorModel;
import com.example.canteenxpress.R;

import java.util.List;

public class CartHorAdapter extends RecyclerView.Adapter<CartHorAdapter.ViewHolder> {
    Context context;
    List<CartHorModel> list;

    public CartHorAdapter(Context context, List<CartHorModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CartHorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CartHorAdapter.ViewHolder holder, int position) {
        CartHorModel item = list.get(position);
        holder.name.setText(list.get(position).getName());
        holder.image.setImageResource(list.get(position).getImage());
        holder.price.setText("" + list.get(position).getPrice());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = item.getId(); // Assuming getId() returns the unique identifier (ID) of the item
                dbHelper.deleteUserCart(db, id);
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, remove;
        TextView name, price;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hor_name);
            image = itemView.findViewById(R.id.hor_image);
            price = itemView.findViewById(R.id.hor_price);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}
