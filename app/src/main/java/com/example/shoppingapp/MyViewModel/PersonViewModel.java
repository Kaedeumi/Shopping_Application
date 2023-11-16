package com.example.shoppingapp.MyViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapp.ShopClass.Goods;
import com.example.shoppingapp.ShopClass.User;
import com.example.shoppingapp.UserPage.UserFragment;

import java.util.ArrayList;

public class PersonViewModel extends ViewModel {
    private MutableLiveData<User> personMutableLiveData;
    private MutableLiveData<ArrayList<Goods>> buyingListMutableLiveData;

    /**
     * Initializes the personMutableLiveData and buyingListMutableLiveData and set the values
     */
    public PersonViewModel() {
        personMutableLiveData = new MutableLiveData<>();
        buyingListMutableLiveData = new MutableLiveData<>();
        buyingListMutableLiveData.setValue(new ArrayList<>());
        personMutableLiveData.setValue(UserFragment.user);    // Recovering the data from MainActivity
    }

    public void setPerson(User person){
        personMutableLiveData.setValue(person);
        UserFragment.user = person;   // To prevent the data from being cleaned when distroying Fragment

    }

    public LiveData<User> getPerson() {
        return personMutableLiveData;
    }

    public void setBuyingList(ArrayList<Goods> list){
        buyingListMutableLiveData.setValue(list);
    }

    public LiveData<ArrayList<Goods>> getBuyingList() {
        return buyingListMutableLiveData;
    }

}
