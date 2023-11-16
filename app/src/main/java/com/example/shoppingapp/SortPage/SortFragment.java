package com.example.shoppingapp.SortPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.MyViewModel.GoodsListViewModel;
import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.GoodsDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 * This class displays a list of goods/products with various categories and sorting options.
 */
public class SortFragment extends Fragment implements MySortAdapter.OnListItemClickListener {

    // An instance of RecyclerView widget to display a list of goods.
    RecyclerView myrecycleview;

    // Buttons that represent the different categories.
    Button manbt;
    Button womenbt;
    Button childbt;
    Button makeupbt;
    Button snackbt;
    Button dailybt;

    // An instance of a ViewModel to store and manage the list of selected goods
    private GoodsListViewModel goodsListViewModel;

    // An adapter used to populate the RecyclerView with goods
    private MySortAdapter adapter;
    private boolean delay = false;


    public SortFragment() {
        // Required empty public constructor
    }

    /**
     * This method is called when the fragment is created and its user interface is initialized.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflates a layout file to create the fragment's UI
        // Initializes the relevant buttons
        View view = inflater.inflate(R.layout.selected_layout, container, false);
        myrecycleview = view.findViewById(R.id.reViewGoods);
        manbt = view.findViewById(R.id.man);
        womenbt = view.findViewById(R.id.women);
        makeupbt = view.findViewById(R.id.makeup);
        dailybt = view.findViewById(R.id.daily);
        childbt = view.findViewById(R.id.child);
        snackbt = view.findViewById(R.id.snack);

        // set up the adapter and its listener
        adapter = new MySortAdapter(getContext(),getProducts1());
        adapter.setOnListItemClickListener(this);
        myrecycleview.setAdapter(adapter);
        myrecycleview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        // Set the onClickListener on all of the category tags
        manbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When a button is clicked, it invokes a callback function that updates the adapter's data by calling setProducts()
                adapter.setProducts(getProducts1());
                // Notify the RecyclerView that the data has changed, triggering a UI update to display the new list of products
                adapter.notifyDataSetChanged();
            }
        });
        womenbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setProducts(getProducts2());
                adapter.notifyDataSetChanged();
            }
        });
        makeupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setProducts(getProducts3());
                adapter.notifyDataSetChanged();
            }
        });
        dailybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setProducts(getProducts4());
                adapter.notifyDataSetChanged();
            }
        });
        childbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setProducts(getProducts5());
                adapter.notifyDataSetChanged();
            }
        });
        snackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setProducts(getProducts6());
                adapter.notifyDataSetChanged();
            }
        });

        return view;




    }


    private List<Goods> getProducts1() {
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("Men's Taiping Bird Jacket 2023 Spring New Loose Fashion Thick Woolen Casual Jacket with Color Collision Embroidery", "1666","400+ People Paid",R.drawable.a1));
        products.add(new Goods("Short-Sleeved Men's T-Shirt Pure Cotton Trendy Round Neck Clothes American Retro Style Men's Half-Sleeve Summer", "126","3000+ People Paid",R.drawable.a2));
        products.add(new Goods("LAZY DAY Original Men's Clothing Set for Spring Outdoor Windproof Jacket with Hood Loose Jacket Trend", "1158","3000+ People Paid",R.drawable.a3));
        products.add(new Goods("Clearance Sale~ Short-Sleeved Men's T-Shirt, Foreign Trade, New Summer Half-Sleeved Top for Men", "1109","20,000+ People Paid",R.drawable.a4));
        products.add(new Goods("Zhenwei's LIFE Short-Sleeved T-Shirt for Men in Summer, Pure Cotton Men's Top Brand Men's T-Shirt for Couples M", "15","20,000+ People Paid",R.drawable.a5));
        products.add(new Goods("Ice Silk Cotton Men's Short-Sleeved T-Shirt White Half-Sleeve Summer Clothes Men's Pure Color Silk Light Body-fitting Base Shirt", "149.9","10,000+ People Paid",R.drawable.a6));
        return products;
    }

    private List<Goods> getProducts2() {
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("Women's Denim Shorts Summer 2023 New High-Waisted Thin Section Shorts Rolled Edge Shorts Summer Broken Hole Casual Hot Pants", "179","100+ People Paid",R.drawable.women1));
        products.add(new Goods("Slim-Fit Women's High-Waisted Summer Thin Section Suit Small Fresh American Straight-Leg Large-Size Fat mm with Martin Boots", "198","100+ People Paid",R.drawable.women2));
        products.add(new Goods("Sand Color Deep Red Cotton Loose Round Neck Short-Sleeved T-Shirt Top", "1118","8 People Paid",R.drawable.women3));
        products.add(new Goods("Short-Sleeved T-Shirt for Women in Summer 2023 New Loose Mid-Length Fashionable European-Style Half-Sleeved Top", "1267","2 People Paid",R.drawable.women4));
        products.add(new Goods("Blue Floral Dress 2023 Spring/Summer New Style", "1122","1 Person Paid",R.drawable.women5));
        products.add(new Goods("2023 New Style Slimming and Age-Reducing French Dress Two-Piece Set", "1118","100+ People Paid",R.drawable.women6));
        return products;
    }

    private List<Goods> getProducts3(){
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("520 Gift 24h Delivery TF Classic Double-Lipstick Gift Box with Gold Foil Black Tube High-Definition Engraving", "1980","1,000+ People Paid",R.drawable.makeup1));
        products.add(new Goods("520 Gift HR Helena Green Bottle Essence Black Bandage Cream Repair Kit Gift Box", "12660","1,000+ People Paid",R.drawable.makeup2));
        products.add(new Goods(";L'Oréal Revitalift Water and Milk Set Wrinkle Resistance and Firming Mom's Skincare", "1399","60,000+ People Paid",R.drawable.makeup3));
        products.add(new Goods("HFP Water and Milk Set Moisturizing and Repairing Oily Skin Acne Skin Shrink Pores Skincare", "1308","2,000+ People Paid",R.drawable.makeup4));
        products.add(new Goods("OLAY Yulan Oil Big Red Bottle Water and Milk Set", "1409","30,000+ People Paid",R.drawable.makeup5));
        products.add(new Goods("L'Oréal Black Essence Water and Milk Set Gift Box Anti-Aging Skincare", "1309","20,000+ People Paid",R.drawable.makeup6));
        return products;
    }

    private List<Goods> getProducts4() {
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("KTV Table Setting Supplies Stainless Steel Boxed Tabletop Ornaments Light Luxury Fruit Plate Ashtray Microphone Stand Paper Towel Holder Fruit Peel Bowl", "118","9 People Paid",R.drawable.daily1));
        products.add(new Goods("Space Aluminum Kitchen Storage Rack Floor-Mounted Multi-Layer Household Appliances Microwave Oven Electric Oven Storage and Storage Cabinet", "1398","56 People Paid",R.drawable.daily2));
        products.add(new Goods("High School Entrance Examination College Entrance Examination Refueling Motivational Gift Golden List Named Gift Box 18-Year-Old Adult Gift for Boys and Girls Ceremony Feeling Gift", "159","1,000+ People Paid",R.drawable.daily3));
        products.add(new Goods("Good Bathing Brush Long-Handled Bathing Back Home", "14","600+ People Paid",R.drawable.daily4));
        products.add(new Goods("10-Piece Nail Clipper Set", "16","54 People Paid",R.drawable.daily5));
        products.add(new Goods("Home Dormitory Bathroom Deodorant", "16","37 People Paid",R.drawable.daily6));
        return products;
    }

    private List<Goods> getProducts5() {
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("Boys' Summer Set 2023 New Children's Short-Sleeved Sports Trendy Cool Boys' Summer Clothes", "179","200+ People Paid",R.drawable.child1));
        products.add(new Goods("Korean AS Brand Genuine Boys' Pure Cotton Short-Sleeved T-Shirt Summer 2023 New Luminous Clothes Children's Night Light Tide", "1148","600+ People Paid",R.drawable.child2));
        products.add(new Goods("Bambet Boys' Summer Set 2023 Summer New British Handsome Short-Sleeved Butterfly Collar Bow Tie Two-Piece Suit Dress", "1269","85 People Paid",R.drawable.child3));
        products.add(new Goods("Dimei Girls' Dress 2023 Summer New Fashionable High-Quality Princess Dress Children's Vest Dress", "1140","1,000+ People Paid",R.drawable.child4));
        products.add(new Goods("Boys' Short-Sleeved T-Shirt Summer Large Boys' Red National Tide Half-Sleeve Top Children's Pure Cotton Summer Clothing", "139","200+ People Paid",R.drawable.child5));
        products.add(new Goods("Boys' Short-Sleeved T-Shirt Summer Children's Pure Cotton Summer Clothing Half-Sleeve Top for Boys", "129","400+ People Paid",R.drawable.child6));
        return products;
    }

    private List<Goods> getProducts6() {
        List<Goods> products = new ArrayList<>();
        products.add(new Goods("Big Gift Box Children's Day Birthday Gift for Girlfriend Snacks Leisure Food Whole Box", "1256","300+ People Paid",R.drawable.snack1));
        products.add(new Goods("Giant 520 Valentine's Day Gift Snacks Children's Day", "1520","49 People Paid",R.drawable.snack2));
        products.add(new Goods("CtrlCal Multi-Card Chicken Breast Instant Fitness Meal 0 Sucrose High Protein Low Fat Office Snack", "189","1,000+ People Paid",R.drawable.snack3));
        products.add(new Goods("Snacks Bread Whole Box Breakfast Office Recommended Leisure Food", "124","10,000+ People Paid",R.drawable.snack4));
        products.add(new Goods("Baicao Wei Pure Milk Cube Bread 480g Hand-Torn Bread Whole Box Stock Breakfast Toast Cake", "123","7,000+ People Paid",R.drawable.snack5));
        products.add(new Goods("Big Gift Box Meat Snacks Midnight Snack Whole Box Spicy Snack Leisure Food Snacks for Girlfriend", "1139","800+ People Paid",R.drawable.snack6));
        return products;
    }



    /**
     * Called when an item in the RecyclerView is clicked.
     * Receives information about the clicked item, creates a Goods object and adds to a GoodsListViewModel
     *
     * @param title the name of the product
     * @param price the product's price
     * @param icon its corresponding icon
     */
    @Override
    public void onListItemClick(String title, String price, int icon) {

            Goods goods = new Goods(title,price,"",icon);
            MainActivity activity = (MainActivity) getActivity();
            goodsListViewModel = activity.getGoodsListViewModel();
            goodsListViewModel.addtoListGoods(goods);


    }
}