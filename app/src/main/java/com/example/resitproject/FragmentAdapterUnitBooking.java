package com.example.resitproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

public class FragmentAdapterUnitBooking extends FragmentStateAdapter {
    public FragmentAdapterUnitBooking(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1: return new TabFragmentPrices();
        }
        return new TabFragmentBooking();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}