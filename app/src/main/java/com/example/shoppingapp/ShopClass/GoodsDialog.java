package com.example.shoppingapp.ShopClass;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.Goods;


public class GoodsDialog extends Dialog implements View.OnClickListener {


    private Goods goods;


    public GoodsDialog(@NonNull Context context,Goods goods) {
        super(context);
        this.goods = goods;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_details);
        ImageView imageView = findViewById(R.id.store_iv);
        TextView title = findViewById(R.id.store_title);
        TextView sales = findViewById(R.id.store_describe);
        TextView price = findViewById(R.id.goods_price);
        ImageButton closebnt = findViewById(R.id.store_closebutton);
        if(goods !=null){
            imageView.setImageResource(goods.geticon());
            title.setText(goods.getTitle());
            sales.setText(goods.getSales());
            price.setText(goods.getPrice());

        }
        closebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
