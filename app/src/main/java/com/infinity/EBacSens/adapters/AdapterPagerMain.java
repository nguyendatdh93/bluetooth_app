package com.infinity.EBacSens.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.infinity.EBacSens.fragments.Fragment1;
import com.infinity.EBacSens.fragments.Fragment2;
import com.infinity.EBacSens.fragments.Fragment3;
import com.infinity.EBacSens.fragments.Fragment4;

public class AdapterPagerMain extends FragmentStatePagerAdapter {

    int tabCount;

    public AdapterPagerMain(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return new Fragment1();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}