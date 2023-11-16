package com.example.shoppingapp.SortPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.GoodsDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MySortAdapter extends RecyclerView.Adapter<MySortAdapter.ViewHolder> {
    private List<Goods> products;
    private Context context;

    private static boolean delay = false;


    public MySortAdapter(Context context, List<Goods> products) {
        this.context = context;
        this.products = products;

    }
    public interface OnListItemClickListener {
        void onListItemClick(String title, String price,int icon);
    }

    private OnListItemClickListener mListener;

    public void setOnListItemClickListener(OnListItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goods product = products.get(position);
        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText(product.getPrice());
        holder.salesTextView.setText(product.getSales());
        holder.iconView.setImageResource(product.geticon());

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListItemClick(product.getTitle(),product.getPrice(), product.geticon());
                if (!delay){
                    delay = true;
                    holder.add.setImageResource(R.drawable.add_successedbutton);

                    @SuppressLint("HandlerLeak") Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            delay=false;
                            holder.add.setImageResource(R.drawable.add_button);
                        }
                    };
                    TimerTask task = new TimerTask(){
                        public void run() {
                            Message message = new Message();
                            mHandler.sendMessage(message);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000);

                }
            }

        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDialog dialog = new GoodsDialog(context,product);
                dialog.show();
            }


        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Goods> products) {
        this.products = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView priceTextView;
        public TextView salesTextView;
        public ImageView iconView;
        public ImageButton add;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            priceTextView = itemView.findViewById(R.id.price);
            salesTextView = itemView.findViewById(R.id.sales);
            iconView = itemView.findViewById(R.id.iv);
            add = itemView.findViewById(R.id.add_toshoppinglist);

        }
    }
}

