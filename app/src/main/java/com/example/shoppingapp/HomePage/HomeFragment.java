package com.example.shoppingapp.HomePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.ShopClass.GoodsDialog;
import com.example.shoppingapp.MyViewModel.GoodsListViewModel;
import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.User;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents an Android Fragment used within an Android application
 * for displaying a homepage or landing page for a shopping app.
 */
public class HomeFragment extends Fragment {

    // An ArrayList of infoBeans used for displaying banner images on the homepage
    private final ArrayList<infoBean> banners;

    // TextView and ImageView objects used for displaying product information
    TextView price;
    TextView title;
    TextView sale;
    ImageView icon;

    // A ListView object widget used to display a list of products on the homepage
    ListView myList = null;

    // A boolean variable used to control delays when adding products to a shopping list
    private boolean delay = false;

    // An instance of a ViewModel class related to the management of goods
    private GoodsListViewModel goodsListViewModel;

    // the current app user
    private User user;

    /**
     * Initializes the banners ArrayList with several infoBean objects for banner images
     */
    public HomeFragment() {
        banners = new ArrayList<>();
        banners.add(new infoBean(R.drawable.ad1));
        banners.add(new infoBean(R.drawable.ad2));
        banners.add(new infoBean(R.drawable.ad3));
        banners.add(new infoBean(R.drawable.ad4));
    }


    /**
     * This method is called when the fragment is created and it's responsible for
     * inflating the fragment's layout and setting up the various widgets.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return inflate
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.home_layout, container, false);
        // Initializes the myList ListView and sets an Adapter to populate it with product data
        myList = inflate.findViewById(R.id.home_listview);
        // Sets up a banner (for displaying advertisements) using the Banner widget
        Banner banner = inflate.findViewById(R.id.banner);

        MyAdapter myAdapter = new MyAdapter(getContext());
        myList.setAdapter(myAdapter);

        // Add the Lifecycle
        banner.addBannerLifecycleObserver(this).setAdapter(new BannerViewAdapter(banners, this))
                // Add the indicator
                .setIndicator(new CircleIndicator(getContext()));

        return inflate;
    }

    /**
     * A customer adapter class used to populate the myList ListView with product data.
     */
    private class MyAdapter extends BaseAdapter {
        private Context context;

        /**
         * Constructor of MyAdapter
         * @param context the Context object
         */
        public MyAdapter(Context context){
            this.context = context;
        }

        /**
         *
         * @return the amount of item
         */
        @Override
        public int getCount() {
            return titles.length;
        }

        /**
         * i represents the index of the current item, starting from 0.
         *
         * @param i Position of the item whose data we want within the adapter's
         * data set.
         * @return the ith item
         */
        @Override
        public Object getItem(int i) {
            return titles[i];
        }

        /**
         * returns the index of the item being selected
         *
         * @param i The position of the item within the adapter's data set whose row id we want.
         * @return the index of the item being selected
         */
        @Override
        public long getItemId(int i) {
            return i;
        }


        /**
         *
         * Called by the ListView to obtain a View for each item in the data source
         * (an array of products in this case) and configure the content to be loaded by the listView,
         * including figures and data
         *
         * @param i The position of the item within the adapter's data set of the item whose view
         *        we want.
         * @param view The old view to reuse, if possible. Note: You should check that this view
         *        is non-null and of an appropriate type before using. If it is not possible to convert
         *        this view to display the correct data, this method can create a new view.
         *        Heterogeneous lists can specify their number of view types, so that this View is
         *        always of the right type (see {@link #getViewTypeCount()} and
         *        {@link #getItemViewType(int)}).
         * @param viewGroup The parent that this view will eventually be attached to
         * @return the new View
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // A new View is always generated whenever being called, which will
            // generate multiple Views upon a larger data, causing waste of source
            // so this if statement reduces memory consumption.
            if(view ==null){
                // if a View is null, a new view is created by inflating a layout resource
                LayoutInflater inflater= LayoutInflater.from(context);
                view = inflater.inflate(R.layout.list_item,null);
            }

            // Initializing the various widgets
            title = view.findViewById(R.id.title);
            price = view.findViewById(R.id.price);
            sale= view.findViewById(R.id.sales);
            icon = view.findViewById(R.id.iv);
            ImageButton add = view.findViewById(R.id.add_toshoppinglist);

            // Data from arrays at the specific position i is used to set the text and image resources
            // of the initialized views. This data populates the current item in the ListView.
            title.setText(titles[i]);
            price.setText(prices[i]);
            sale.setText(sales[i]);
            icon.setImageResource(icons[i]);
            Goods goods = new Goods(titles[i],prices[i],sales[i],icons[i]);

            // Set the onClickListener on the add button.
            add.setOnClickListener(new View.OnClickListener() {

                /**
                 * Defines the actions to be performed when being clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    // if delay is false, if so,
                    if (!delay){
                        // Uses getActivity() to obtain the MainActivity instance
                        MainActivity activity = (MainActivity) getActivity();
                        // Retrieves the goodsListView from the activity
                        goodsListViewModel = activity.getGoodsListViewModel();
                        // Calls the addtoListGoods method and passing in a Goods object created from this item's data.
                        goodsListViewModel.addtoListGoods(goods);

                        // Sets the delay to true to prevent fast clicking.
                        delay = true;
                        // Changes the button's image source to indicate a successful addition.
                        add.setImageResource(R.drawable.add_successedbutton);

                        // A new instance of a Handler class is created as an anonymous inner class
                        // The Handler is used to manage and post messages and run code on a specific thread.
                        @SuppressLint("HandlerLeak")
                        Handler mHandler = new Handler() {

                            /**
                             * Defines what should happen when a message is received by the Handler.
                             *
                             * @param msg
                             */
                            @Override
                            public void handleMessage(Message msg) {
                                // When a message is received, sets the delay variable to false and
                                // changes the image resource of an add object to a drawable resource
                                delay=false;
                                add.setImageResource(R.drawable.add_button);
                            }
                        };

                        // A new instance of a TimerTask is created as an anonymous inner class
                        // A TimerTask is a task that can be scheduled to run at a specified time or with delay.
                        TimerTask task = new TimerTask(){
                            /**
                             * Defines what will happen when a TimerTask is run.
                             */
                            public void run() {
                                // Creates a new Message object and sends it to the Handler.
                                Message message = new Message();
                                mHandler.sendMessage(message);
                            }
                        };

                        // A Timer instance is created which is used to schedule and execute tasks at specified intervals
                        Timer timer = new Timer();
                        // After a goods has been selected, a tick will change back to a + in 1 second.
                        timer.schedule(task, 1000);

                    }
                }
            });

            // When the item in the ListView is clicked, it creates and displays a GoodsDialog
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsDialog dialog = new GoodsDialog(context,goods);
                    dialog.show();
                }
            });
            return view;
        }


    }


    /**
     * An adapter class for a banner or image slider component within an Android Application
     * which populates the banner with data and handles the creation and recycling of views.
     */
    public class BannerViewAdapter extends BannerAdapter<infoBean, BannerViewAdapter.BannerViewHodler> {
        private Fragment fragment;

        /**
         * The constructor takes a list of infoBean objects and a Fragment as parameters.
         * It initializes the adapter with the provided banner data and stores the Fragment in class member variables.
         *
         * @param banners The list of infoBean
         * @param fragment a Fragment object
         */
        public BannerViewAdapter(List<infoBean> banners, Fragment fragment) {
            super(banners);
            this.fragment = fragment;
        }

        /**
         * This method is called to create a new banner item view holder.
         *
         * @param parent ViewGroup object
         * @param viewType int
         * @return A BannerViewHolder that manages the banner item's View.
         */
        @Override
        public BannerViewAdapter.BannerViewHodler onCreateHolder(ViewGroup parent, int viewType) {
            // An ImageView to display the banner image
            ImageView imageView = new ImageView(parent.getContext());
            // Configure the ImageView such that it matches the parent's width and height
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            // The scale type is set to the center (the image will be centered within the View)
            // setScaleType processes the image in terms of size, CENTER_CROP scales the original image after centering
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            return new BannerViewHodler(imageView);

        }

        /**
         * This method is called to bind data to a banner item's View
         *
         * @param holder   XViewHolder
         * @param data     the infoBean data
         * @param position the current position
         * @param size     the total size
         */
        @Override
        public void onBindView(BannerViewAdapter.BannerViewHodler holder, infoBean data, int position, int size) {
            // The imageView inside the holder is set to display the image specified by the data object's picture attribute
            // Associates the image with the banner item View
            holder.imageView.setImageResource(data.picture);
        }

        // Holds the View for each banner item.
        public class BannerViewHodler extends RecyclerView.ViewHolder {
            ImageView imageView;

            public BannerViewHodler(@NonNull ImageView itemView) {
                super(itemView);
                this.imageView = itemView;
            }

        }
    }
    private String[] titles={"Men's lightweight casual suit jacket for a Sophisticated look",
            "ZiTY Men's Outdoor Tactical Jacket Waterproof Hooded Windproof Windbreaker",
            "★LOCAL STOCK★Men's Polyester Round Neck Short Sleeve T - Shirt",
            "ZiTY Men Army Waterproof Outdoor Cargo Jacket Shark Skin Soft Shell Clothes Military Tactical Windproof Jacket Windbreaker",
            "Levi's® Men's Original Housemark T-Shirt 56605-0017"};
    private String[] prices={"66$", "158$", "109$", "158$", "5$"};
    private int[] icons={R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};
    private String[] sales ={ "400+ sold",
            "3k+ sold",
            "3k+ sold",
            "3k+ sold",
            "20k+ sold"};

}
