package com.example.shoppingapp.UserPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.DatabasePack.Database;
import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.LoginActivity;
import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.DatabasePack.MySQLiteHelper;
import com.example.shoppingapp.MyViewModel.PersonViewModel;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.User;

import java.util.ArrayList;

public class UserFragment extends Fragment{

    private PersonViewModel personViewModel;
    private Button charge;
    private Button esc;
    private TextView usermoney;
    private TextView username;
    public static User user;
    public static ArrayList<Goods> userbuyedlist;
    private RecyclerView userrecycleview = null;

    /**
     * This method is called when the fragment is creating its user interface.
     * It initializes and sets up UI elements and adapters for the RecyclerView
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment. It instantiates the XML layout.
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflates a layout resource to create the fragment's View
        View view =  inflater.inflate(R.layout.login_success_layout,container,false);
        // Initialize the UI elements
        usermoney = view.findViewById(R.id.textViewPersonMoney);
        charge = view.findViewById(R.id.buttonCharge);
        username = view.findViewById(R.id.textViewPersonName);
        esc = view.findViewById(R.id.buttonEsc);
        userrecycleview = view.findViewById(R.id.userbuyingview);

        // Retrieves the parent activity associated with the fragment
        // Assuming that the fragment is attached to a MainActivity or its subclass.
        MainActivity activity = (MainActivity) getActivity();
        personViewModel = activity.getPersonViewModel();
        user = personViewModel.getPerson().getValue();
        username.setText(user.getName());
        usermoney.setText(String.valueOf(user.getMoney()));

        // Retrieves a list of goods from the ViewModel's LiveData
        userbuyedlist = personViewModel.getBuyingList().getValue();

        // Creates an adapter (myUserAdapter) to populate a RecyclerView with data from userbuyedlist
        MyUserAdapter myUserAdapter = new MyUserAdapter(this.getContext(),userbuyedlist);
        userrecycleview.setAdapter(myUserAdapter);

        // Sets the layout manager for the RecyclerView to display items in a vertical list
        userrecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Sets up an observer on the personViewModel 's LiveData for the User object
        // When the data inside the LiveData<User> changes, the onChanged() method will be triggered.
        personViewModel.getPerson().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User person) {
                // A safety check that ensures that there is valid data
                if(person != null){
                    // These actions are performed given that the person object is not null.
                    // The user object is updated with the new User data received from the observer.
                    user = person;
                    // Update the username and usermoney TextViews with the name and money attributes of the User object
                    username.setText(user.getName());
                    usermoney.setText(String.valueOf(user.getMoney()));

                    // Creates an instance of the Database class for database operations
                    Database database = new Database(new MySQLiteHelper(getContext()));
                    // Retrieves a list of Goods associated with the User. Fetches goods from a local SQL database.
                    ArrayList<Goods> goodsArrayList = database.findGoodsListFromSQLite(user);
                    // Updates the BuyingList LiveData in the personViewModel with the newly fetched list of goods
                    personViewModel.setBuyingList(goodsArrayList);
                    // Retrieves the current value of the BuyingList LiveData and assigns it to the userBuyedList variable
                    userbuyedlist = personViewModel.getBuyingList().getValue();
                    // Updates the data of the myUserAdapter with the newly fetched list of goods
                    myUserAdapter.setProducts(userbuyedlist);
                    // Sets the adapter for the RecyclerView with the updated data
                    userrecycleview.setAdapter(myUserAdapter);
                    // Sets the layout manager for the userrecycleview again ensuring that the same configuration is being used.
                    userrecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                }

            }

        });

        // Listener for the charging button
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Charged successfully",Toast.LENGTH_SHORT).show();
                user.setMoney(user.getMoney()+1000); // charge 1000 by default
                Database database = new Database(new MySQLiteHelper(getContext()));
                database.updatePersonToSQLite(user); // update the database
                usermoney.setText(String.valueOf(user.getMoney())); // update the amount of userMoney
            }
        });

        // Listener for the esc button
        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Directs back to the login page
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


}
