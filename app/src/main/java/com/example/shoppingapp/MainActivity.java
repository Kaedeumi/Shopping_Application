package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shoppingapp.CartPage.CartFragment;
import com.example.shoppingapp.DatabasePack.Database;
import com.example.shoppingapp.DatabasePack.MySQLiteHelper;
import com.example.shoppingapp.HomePage.HomeFragment;
import com.example.shoppingapp.MyViewModel.GoodsListViewModel;
import com.example.shoppingapp.MyViewModel.PersonViewModel;
import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.ShopClass.User;
import com.example.shoppingapp.SortPage.SortFragment;
import com.example.shoppingapp.UserPage.UserFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    // Instance variables to be used throughout the class
    private ViewPager2 viewPager2;
    public static ArrayList<Goods> shoppingList;

    // A UI widget in Android that groups multiple radio buttons together
    RadioGroup rg;
    RadioButton rb_home,rb_cart,rb_user,rb_sort;
    RgAdapter rgAdapter;
    private PersonViewModel personViewModel;
    private GoodsListViewModel goodsListViewModel;
    User user;

    /**
     *  View Initialization
     *  Responsible for initializing and configuring the views, setting up the adapter for ViewPager2
     *  creating and adding fragments to the adapter, and managing the interaction between the Viewpager2 and RadioGroups.
     *  The ViewPager2 is in the activity_main.xml
     * @param bundle
     */
    private void initView(Bundle bundle){
        // Find the views and assign them into variables
        viewPager2 =findViewById(R.id.ViewPager2);
        // rg: RadioGroups
        rg = findViewById(R.id.rg_main);
        rb_home = findViewById(R.id.rb_home);
        rb_cart = findViewById(R.id.rb_cart);
        rb_user = findViewById(R.id.rb_user);
        rb_sort = findViewById(R.id.rb_sort);

        // Handling changes on the RadioGroup rg and specifies that the current class
        // should handle changes in radio button selection
        rg.setOnCheckedChangeListener(this);
        // Manage the fragments displayed within ViewPager2
        rgAdapter = new RgAdapter(this);
        // Sets the rgAdapter as the adapter for the ViewPager2
        viewPager2.setAdapter(rgAdapter);

        // Create fragments to be displayed within the ViewPager2
        // It's necessary to populate the ViewPager2 with the fragments that users can swipe through
        UserFragment userFragment = new UserFragment();
        HomeFragment homehomeFragment = new HomeFragment();
        SortFragment sortFragment = new SortFragment();
        CartFragment cartFragment = new CartFragment();

        // Add the created fragments into the rgAdapter
        rgAdapter.addFragment(homehomeFragment);
        rgAdapter.addFragment(sortFragment);
        rgAdapter.addFragment(cartFragment);
        rgAdapter.addFragment(userFragment);

        // Create Drawable objects from resources using getResources().getDrawable()
        Drawable rg1 = getResources().getDrawable(R.drawable.home_button_selector);
        Drawable rg2 = getResources().getDrawable(R.drawable.sort_button_selector);
        Drawable rg3 = getResources().getDrawable(R.drawable.cart_button_selector);
        Drawable rg4 = getResources().getDrawable(R.drawable.personcenter_button_selector);

        // Set their bounds (dimensions), the four parameters are
        // 1. distance between left/right sides, 2. distance between upper/lower sides,
        // 3. length 4. width
        rg1.setBounds(0, 0, 170, 170);
        rg2.setBounds(0, 0, 150, 150);
        rg3.setBounds(0, 0, 150, 150);
        rg4.setBounds(0, 0, 150, 150);

        // Set the drawable images (drawables) as compound drawables for the radio buttons
        rb_home.setCompoundDrawables(null, rg1, null, null);
        rb_sort.setCompoundDrawables(null, rg2, null, null);
        rb_cart.setCompoundDrawables(null, rg3, null, null);
        rb_user.setCompoundDrawables(null, rg4, null, null);

        // Sets the currently displayed item in the ViewPager2 to be the first item (index 0)
        viewPager2.setCurrentItem(0);

        // Registers an OnPageChangeCallback for the ViewPager2, which is invoked when the
        // user swipes between pages in the 'ViewPager2', and updates the selected radio button accordingly.
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            // Displays the corresponding pages when the user clicks on one of them.
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        rb_home.setChecked(true);
                        break;
                    case 1:
                        rb_sort.setChecked(true);
                        break;
                    case 2:
                        rb_cart.setChecked(true);
                        break;
                    case 3:
                        rb_user.setChecked(true);
                        break;
                }
            }
        });


    }

    /**
     * Responds to changes in the selected radio button within the RadioGroup and perform actions accordingly.
     * This method is called whenever a radio button within the associated RadioGroup is checked/unchecked.
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // This switch statement checks checkedId to determine which radio button is being clicked.
        switch (checkedId){
            case R.id.rb_home:
                viewPager2.setCurrentItem(0); // switch to the home page
                System.out.println("Home");
                break;
            case R.id.rb_sort:
                viewPager2.setCurrentItem(1); // same here and below
                System.out.println("Sort");
                break;
            case R.id.rb_cart:
                viewPager2.setCurrentItem(2);
                System.out.println("Cart");
                break;
            case R.id.rb_user:
                viewPager2.setCurrentItem(3);
                System.out.println("User");
                break;
        }
    }

    /**
     * Getter methods for the PersonViewModel and getGoodsListViewModel
     * @return personViewModel / goodsListViewModel
     */
    public PersonViewModel getPersonViewModel() {
        return personViewModel;
    }
    public GoodsListViewModel getGoodsListViewModel() {return goodsListViewModel;}

    // Driver code

    /**
     *  This code utilizes the class Database for Database object construction.
     *  The relevant methods are also predefined in the Database class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the layout as predefined
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieves the intent that started this activity and extracts the additional data
        // passed with the intent as extras and stores it in bundle.
        Bundle bundle = getIntent().getExtras();
        // if the bundle contains something

        if(bundle!= null) {
            // Retrieve 3 string values (name, password, id) and store them in variables
            String name = bundle.getString("username");
            String password = bundle.getString("password");
            String Id = bundle.getString("id");

            // Initializes a SQLite database and passes the current context (the activity) as a parameter
            Database database = new Database(new MySQLiteHelper(this));
            // retrieve the user object as described by the provided attributes and assign it to the variable
            user = database.findPersonFromSQLite(Id,name,password);
        }

        // Creates a ViewModel instance for handling data associated with the Person class
        personViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // Sets the user object obtained earlier as data within the personViewModel
        personViewModel.setPerson(user);
        // Creates a ViewModel instance for managing data
        goodsListViewModel = new ViewModelProvider(this).get(GoodsListViewModel.class);

        Database database = new Database(new MySQLiteHelper(this));

        // retrieves a shopping list based on the user object and assigns the result to shoppingList variable
        shoppingList = database.findGoodsListFromSQLite(user);

        // Sets an empty ArrayList of Goods as data within the goodsListViewModel
        goodsListViewModel.setListGoods(new ArrayList<Goods>());
        personViewModel.setBuyingList(shoppingList);
        initView(savedInstanceState);

    }
}