package com.example.shoppingapp.CartPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.DatabasePack.Database;
import com.example.shoppingapp.DatabasePack.MySQLiteHelper;
import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.MyViewModel.GoodsListViewModel;
import com.example.shoppingapp.MainActivity;
import com.example.shoppingapp.MyViewModel.PersonViewModel;
import com.example.shoppingapp.R;
import com.example.shoppingapp.ShopClass.User;

import java.util.ArrayList;

public class CartFragment extends Fragment implements MyCartAdapter.OnPriceChangeListener {
    private RecyclerView cartrecycle;
    private MyCartAdapter myCartAdapter;
    private Button deletbtn;
    private Button buybtn;
    private CheckBox checkBoxall;
    private EditText addressEditText;
    private Spinner deliverySpinner;

    // The shoppingList is an ArrayList of Goods object
    private static ArrayList<Goods> shoppingList;
    TextView allprice;
    private PersonViewModel personViewModel;
    private GoodsListViewModel goodsListViewModel;
    public CartFragment() {
        // Required empty public constructor

    }

    /**
     * Responsible for creating and configuring the user interface for the fragment
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the view and create the components
        View view = inflater.inflate(R.layout.cart_layout, container, false);
        cartrecycle = view.findViewById(R.id.recycleViewCart);
        deletbtn = view.findViewById(R.id.buttonDelete);
        buybtn = view.findViewById(R.id.buttonBuy);
        checkBoxall = view.findViewById(R.id.checkBoxAll);
        allprice = view.findViewById(R.id.textViewAllPrice);
        addressEditText = view.findViewById(R.id.address_edit_text);
        deliverySpinner = view.findViewById(R.id.delivery_spinner);

        // Set up the options for the delivery spinner
        ArrayAdapter<CharSequence> deliveryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.delivery_options, android.R.layout.simple_spinner_item);
        // Sets the layout for the spinner's dropdown items
        deliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Initializes the Spinner with the delivery options.
        deliverySpinner.setAdapter(deliveryAdapter);

        // Get the MyCart data of the user databases
        MainActivity activity = (MainActivity) getActivity();
        // Obtains the ViewModel Instances from the parent Activity
        personViewModel = activity.getPersonViewModel();

        // Retrieves the current list of goods from the goodsListViewModel and stores it in the shoppingList Variable
        goodsListViewModel = activity.getGoodsListViewModel();
        shoppingList = goodsListViewModel.getListGoods().getValue();

        // Initializes an instance of a custom adapter and provides it with the fragment's context and the shoppingList data
        myCartAdapter = new MyCartAdapter(this.getContext(),shoppingList);
        // Sets a listener for price changes in the myCartAdapter to update the total price
        myCartAdapter.setOnPriceChangeListener(this);

        // Sets the RecyclerView's adapter to the myCartAdapter
        cartrecycle.setAdapter(myCartAdapter);
        // Sets the RecyclerView's layout manager to a vertical LinearLayoutManager
        cartrecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        // Observes changes in the goodsListViewModel.getListGoods() LiveData and updates the adapter
        // and RecyclerView when the data changes
        goodsListViewModel.getListGoods().observe(getViewLifecycleOwner(), new Observer<ArrayList<Goods>>() {
            @Override
            public void onChanged(ArrayList<Goods> goods) {
                shoppingList = goods;
                myCartAdapter.setProducts(goods);

                cartrecycle.setAdapter(myCartAdapter);
                cartrecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }
        });

        // When the delete button is being clicked, drop the item(s) from the ArrayList
        deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the temp ArrayList temporarily stores the modified list of goods.
                ArrayList<Goods> temp = new ArrayList<>();
                // Calls a method to remove items from the (yellow-coloured) list based on the indices stored
                temp = deleteListWithIndex(goodsListViewModel.getListGoods().getValue(),myCartAdapter.getSelectList());
                // Clears the list of selected items in the myCartAdapter. The selected items are deselected after deletion.
                myCartAdapter.getSelectList().clear();

                // Sets the temp to be the new List of goods in the Model, causing the UI to update.
                goodsListViewModel.setListGoods(temp);
                // After deletion, the total price is set to 0.
                allprice.setText("0");
                // Display a toast message showing that the deletion is successful.
                Toast.makeText(CartFragment.this.getContext(),"Deletion Succeeded",Toast.LENGTH_SHORT).show();
            }
        });

        // When the buy button is being clicked, proceed to confirm the payment
        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current user from the personViewModel
                User user = personViewModel.getPerson().getValue();

                // Calculate the total price of selected items in the shopping cart
                int totalPrice = 0;
                for (int i=0;i<goodsListViewModel.getListGoods().getValue().size();++i){
                    if (myCartAdapter.getSelectList().contains(i)){
                        totalPrice+=goodsListViewModel.getListGoods().getValue().get(i).getPriceInt();
                    }
                }

                // Store the total price in a final variable for use in the AlertDialog
                int finalPrice = totalPrice;

                // Define an OnClickListener for the AlertDialog's "Confirm" button
                DialogInterface.OnClickListener buyOkListener =  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        // Decide the following action based on the user's current balance
                        if (user.getMoney() < finalPrice){
                            // Display a Toast if the user doesn't have enough balance for the purchase
                            Toast.makeText(getContext(),"Not enough balance, purchase failed",Toast.LENGTH_SHORT).show();
                        } else {
                            // Otherwise, the user has enough money
                            // Create a new Database instance for handling database operations
                            Database database = new Database(new MySQLiteHelper(getContext()));
                            // Deduct the purchase price from the user's balance
                            user.setMoney(user.getMoney() - finalPrice);
                            // Update the user's information in the database
                            database.updatePersonToSQLite(user);
                            // Update the user's information in the personViewModel
                            personViewModel.setPerson(user);

                            // Iterate over the selected items and add them to the user's buying list in the database
                            for (int i=0;i<goodsListViewModel.getListGoods().getValue().size();++i){
                                if (myCartAdapter.getSelectList().contains(i)){
                                    database.insertGoodsToSQLite(user,goodsListViewModel.getListGoods().getValue().get(i));
                                    personViewModel.getBuyingList().getValue().add(goodsListViewModel.getListGoods().getValue().get(i));
                                }
                            }
                            // Update the user's buying list in the personViewModel
                            personViewModel.setBuyingList(personViewModel.getBuyingList().getValue());

                            // Remove the selected items from the shopping cart
                            ArrayList<Goods> temp = new ArrayList<>();
                            temp = deleteListWithIndex(goodsListViewModel.getListGoods().getValue(),myCartAdapter.getSelectList());
                            myCartAdapter.getSelectList().clear();
                            allprice.setText("0");
                            goodsListViewModel.setListGoods(temp);

                            // Display a Toast confirming the successful purchase
                            Toast.makeText(CartFragment.this.getContext(),"Purchase Successful",Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // Define an OnClickListener for the AlertDialog's "Cancel" button
                DialogInterface.OnClickListener buyCancelListener =  new DialogInterface.OnClickListener() {
                    // Display a Toast indicating that the user canceled the purchase
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(),"Cancel Purchasing",Toast.LENGTH_SHORT).show();
                    }
                };

                // Set up the popping window for the confirmation of the purchase
                String infoMeg = "You have currently $%s \nShopping expense is $%s \nYour address is %s\nDelivery method is %s" +
                        "\nDo you confirm your purchase?";
                infoMeg = String.format(infoMeg,user.getMoney(),totalPrice,addressEditText.getText().toString(),deliverySpinner.getSelectedItem().toString());
                AlertDialog dialogInfo;
                dialogInfo = new AlertDialog.Builder(getContext()).setTitle("Confirm Payment")
                        .setMessage(infoMeg)
                        .setIcon(R.drawable.question)
                        .setPositiveButton("Confirm",buyOkListener)
                        .setNegativeButton("Cancel",buyCancelListener)
                        .create();
                dialogInfo.show();

            }


        });

        // Set the listener for the checkBoxall
        checkBoxall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxall.isChecked()){
                    // If the checkbox is checked, select all items in the shopping cart
                    // Clear the selection list in the adapter
                    myCartAdapter.getSelectList().clear();

                    // Add all the items in the shopping cart to the selection list
                    for(int i=0;i<shoppingList.size();i++){
                        myCartAdapter.getSelectList().add(i);
                    }

                    // Notify the adapter that the data has changed
                    myCartAdapter.notifyDataSetChanged();
                } else {
                    // If the checkbox is unchecked, deselect all items in the shopping cart
                    // Clear the selection list in the adapter
                    myCartAdapter.getSelectList().clear();
                    // Notify the adapter that the data has been changed (deselection)
                    myCartAdapter.notifyDataSetChanged();
                }

                // Iterate through the shoppingList to calculate the total price of selected items
                int allPrice = 0;
                for (int i=0;i<shoppingList.size();++i){
                    // Check if the item at index 'i' is selected
                    if (myCartAdapter.getSelectList().contains(i)){
                        View childview = cartrecycle.getChildAt(i);

                        if(childview!=null){
                            // Get the child view (assumed to be a row in a RecyclerView)
                            TextView goodsnum = childview.findViewById(R.id.good_num);

                            // Get the quantity of the selected item
                            int price = Integer.valueOf(goodsnum.getText().toString());

                            // Calculate the price for the selected item and add it to the total price
                            allPrice += shoppingList.get(i).getPriceInt()*price;
                        }
                    }
                }

                // Find the TextView with ID 'textViewAllPrice' and set its text to the calculated total price
                TextView allpricesTextView = view.findViewById(R.id.textViewAllPrice);
                allpricesTextView.setText(String.valueOf(allPrice));
            }

        });
        return view;
    }


    /**
     * This method is used to update the displayed total price in the UI when the price changes
     *
     * @param totalPrice the price of every selected item in total
     */
    @Override
    public void onPriceChange(int totalPrice) {
        allprice.setText(String.valueOf(totalPrice));
    }

    /**
     * Used for removing selected items from a shopping cart or similar operation
     *
     * @param list the original list
     * @param indexList the list of elements to be deleted
     * @return a new list with the corresponding items deleted
     */
    private ArrayList<Goods> deleteListWithIndex(ArrayList<Goods> list,ArrayList<Integer> indexList){
        // Stores all other Goods remaining after deletion to be returned
        ArrayList<Goods> newlist = new ArrayList<>();

        for (int i=0;i<list.size();i++) {
            // if the index i is not in the indexList,
            if (!indexList.contains(i)) {
                // adds the corresponding Goods object to the newlist
                newlist.add(list.get(i));
            }
        }

        return newlist;


    }



}
