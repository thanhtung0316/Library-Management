package com.hoptb.library_management;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentManagement {
    private static final String TAG = "MainActivityFragmentMan";

    private static FragmentManagement INSTANCE;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static FragmentManagement getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FragmentManagement();
        }
        return INSTANCE;
    }
    public void addFragment(Fragment fragment) {
        if (getFragmentPosition(fragment) != -1)
            mFragments.remove(getFragmentPosition(fragment));
        mFragments.add(fragment);
    }

    public void removeFragment(Fragment fragment) {
        mFragments.remove(fragment);
    }

    public void removeFragment(int position) {
        mFragments.remove(position);
    }

    public ArrayList<Fragment> getFragments() {
        return mFragments;
    }

    public void removeAllFragments() {
        mFragments.clear();
    }

    private int getFragmentPosition(Fragment fragment) {
        for (Fragment f : mFragments) {
            if (f != null && f.getClass().toString().equals(fragment.getClass().toString())) {
                return mFragments.indexOf(f);
            }
        }
        return -1;
    }

    public boolean isFragmentExists(Fragment fragment) {
        for (Fragment f : mFragments) {
            if (f != null && f.getClass().toString().equals(fragment.getClass().toString())) {
                return true;
            }
        }
        return false;
    }
}
