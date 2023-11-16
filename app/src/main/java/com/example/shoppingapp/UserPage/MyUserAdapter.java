package com.example.shoppingapp.UserPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.R;

import java.util.ArrayList;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.ViewHolder>{
    private ArrayList<Goods> products;
    private Context context;
    public MyUserAdapter(Context context, ArrayList<Goods> products) {
        this.context = context;
        this.products = products;

    }
    public MyUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull MyUserAdapter.ViewHolder holder, int position) {
        Goods product = products.get(position);
        holder.GoodsNameBought.setText(product.getTitle());
        holder.priceView.setText(product.getPrice());
        holder.iconView.setImageResource(product.geticon());
    }
    public void setProducts(ArrayList<Goods> products) {
        this.products = products;
    }
    @Override
    public int getItemCount() {

        if (products == null) {
            return 0;
        }
        else {return products.size();}
    }
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView GoodsNameBought;
        public TextView priceView;

        public ImageView iconView;

        public ViewHolder(View itemView) {
            super(itemView);
            GoodsNameBought = itemView.findViewById(R.id.GoodsNameBought);
            priceView = itemView.findViewById(R.id.GoodsPriceBought);
            iconView = itemView.findViewById(R.id.imageViewBought);

        }
    }


}
