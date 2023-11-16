package com.example.shoppingapp.CartPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.R;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{
    private ArrayList<Goods> products;

    private static ArrayList<Integer> list_select = new ArrayList<>();
    private Context context;


    public MyCartAdapter(Context context, ArrayList<Goods> products) {
        this.context = context;
        this.products = products;

    }
    private OnPriceChangeListener mPriceChangeListener;

    public interface OnPriceChangeListener {
        void onPriceChange(int totalPrice);
    }

    public void setOnPriceChangeListener(OnPriceChangeListener listener) {
        mPriceChangeListener = listener;
    }





    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new MyCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        Goods product = products.get(position);
        holder.GoodsNameBuying.setText(product.getTitle());
        holder.priceView.setText(product.getPrice());
        holder.iconView.setImageResource(product.geticon());

        if (list_select.contains(position)){
            holder.checkBoxSelect.setChecked(true);
        }else {
            holder.checkBoxSelect.setChecked(false);
        }
        holder.checkBoxSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = holder.getAdapterPosition();

                if (holder.checkBoxSelect.isChecked()){
                    list_select.add(p);
                } else {
                    list_select.remove(Integer.valueOf(p));
                }

                int allPrice = 0;

                for (int i=0;i<products.size();i++){
                    if (list_select.contains(i)){
                        allPrice+=products.get(i).getPriceInt()*Integer.valueOf(holder.goodsnum.getText().toString());
                    }
                }
                if (mPriceChangeListener != null) {
                    mPriceChangeListener.onPriceChange(allPrice);
                }

            }
        });
        holder.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (holder.checkBoxSelect.isChecked()) {
                        holder.goodsnum.setText(String.valueOf(Integer.valueOf(holder.goodsnum.getText().toString()) + 1));
                        int allPrice = 0;

                        for (int i = 0; i < products.size(); i++) {
                            if (list_select.contains(i)) {
                                allPrice += products.get(i).getPriceInt() * Integer.valueOf(holder.goodsnum.getText().toString());
                            }
                        }
                        if (mPriceChangeListener != null) {
                            mPriceChangeListener.onPriceChange(allPrice);
                        }
                    } else {
                        holder.goodsnum.setText(String.valueOf(Integer.valueOf(holder.goodsnum.getText().toString()) + 1));
                    }
                }

        });
        holder.subbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(holder.goodsnum.getText().toString())>1){
                    if (holder.checkBoxSelect.isChecked()) {
                        holder.goodsnum.setText(String.valueOf(Integer.valueOf(holder.goodsnum.getText().toString()) - 1));
                        int allPrice = 0;

                        for (int i = 0; i < products.size(); i++) {
                            if (list_select.contains(i)) {
                                allPrice += products.get(i).getPriceInt() * Integer.valueOf(holder.goodsnum.getText().toString());
                            }
                        }
                        if (mPriceChangeListener != null) {
                            mPriceChangeListener.onPriceChange(allPrice);
                        }
                    } else {
                        holder.goodsnum.setText(String.valueOf(Integer.valueOf(holder.goodsnum.getText().toString()) - 1));
                    }
                }
            }
        });

    }


    @Override
    public int getItemCount() {

        if (products == null) {
            return 0;
        }
        else {return products.size();}
    }

    public void setProducts(ArrayList<Goods> products) {
        this.products = products;
    }
    public void setSelectList(ArrayList<Integer> list){
        this.list_select = list;
    }
    public ArrayList<Integer> getSelectList(){
        return this.list_select;
    }
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView GoodsNameBuying;
        public TextView priceView;

        public ImageView iconView;
        public CheckBox checkBoxSelect;
        public ImageButton addbutton;
        public ImageButton subbutton;
        public TextView goodsnum;
        public ViewHolder(View itemView) {
            super(itemView);
            GoodsNameBuying = itemView.findViewById(R.id.GoodsNameBuying);
            priceView = itemView.findViewById(R.id.GoodsPriceBuying);
            iconView = itemView.findViewById(R.id.imageViewBuying);
            checkBoxSelect = itemView.findViewById(R.id.checkBoxSelect);
            addbutton = itemView.findViewById(R.id.addgoods);
            subbutton = itemView.findViewById(R.id.deletegoods);
            goodsnum = itemView.findViewById(R.id.good_num);
        }
    }
}
