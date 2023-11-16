package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class works with a ViewPager2 to manage fragments that can be swiped between within an Android App.
 * It is used for managing fragments within a ViewPager2 in an Android app.
 */
public class RgAdapter extends FragmentStateAdapter{
    // Create a list of Class to store classes representing fragments
    // Each element of this list corresponds to a fragment class that can be instantiated.
    private List<Class> fragments;

    /**
     * The constructor takes a FragmentActivity as a parameter.
     * Typically used to create an instance of this adapter within an activity
     * @param fragmentActivity
     */
    public RgAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        if (fragments == null) {
            // if not initialized, assign it as an empty ArrayList
            fragments = new ArrayList<>();
        }
    }

    /**
     * Add fragment classes to the fragments list
     * Takes a fragment object as a parameter and adds the class of the fragment to the list
     * Allows you to dynamically add fragments to the adapter.
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (fragments != null) {
            fragments.add(fragment.getClass());
        }
    }

    /**
     * Overridden from the FragmentStateAdapter
     * Called when a new fragment needs to be created for a given position within the ViewPager2
     * Attempts to create a new instance of a fragment class from the fragments list using reflection
     * It assumes that each class in the fragments list has a default (no-argument) constructor.
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            // try to create a new Fragment object and return
            return (Fragment) fragments.get(position).newInstance();
            // otherwise, return null
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the total numbers of fragments managed by this adapter (fragments' size)
     * @return
     */
    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
