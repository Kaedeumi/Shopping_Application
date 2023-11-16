package com.example.shoppingapp.MyViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapp.ShopClass.Goods;

import java.util.ArrayList;

// This model is a class that extends the viewmodel class, managing and providing data to a UI component
public class GoodsListViewModel extends ViewModel {
    // Allows you to change the stored data. It holds a list of a list of goods.
    private final MutableLiveData<ArrayList<Goods>> list_goods;

    public GoodsListViewModel() {
        list_goods = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Goods>> getListGoods() {
        return list_goods;
    }

    public void setListGoods(ArrayList<Goods> list){
        this.list_goods.setValue(list);
    }

    /**
     * Add a single Goods object to the existing list of goods
     * @param goods the additional Goods object to be added
     */
    public void addtoListGoods(Goods goods){
        ArrayList<Goods> list = this.list_goods.getValue();
        list.add(goods);
        this.list_goods.setValue(list);
    }

}
