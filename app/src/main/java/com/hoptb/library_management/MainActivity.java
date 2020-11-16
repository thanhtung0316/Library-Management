package com.hoptb.library_management;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hoptb.library_management.base.BaseActivity;
import com.hoptb.library_management.databinding.ActivityMainBinding;
import com.hoptb.library_management.ui.borrow_book.BorrowFragment;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.personal.PersonalFragment;
import com.hoptb.library_management.ui.return_book.ReturnFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {
    private final int BORROW_POS = 0;
    private final int RETURN_POS = 1;
    private final int CATEGORY_POS = 2;
    private final int PERSONAL_POS = 3;
    private int selectedPos = -1;
    private Fragment selectedFragment = null;

    @Override
    protected void initAct(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            binding.navigationView.setSelectedItemId(R.id.item_borrow);
            showFragment(BorrowFragment.newInstance());
        }
        binding.navigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItemId = savedInstanceState.getInt("SelectedItemId");
        binding.navigationView.setSelectedItemId(selectedItemId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("SelectedItemId", binding.navigationView.getSelectedItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.item_borrow:
                selectedPos = BORROW_POS;
                selectedFragment = BorrowFragment.newInstance();
                break;
            case R.id.item_return:
                selectedPos = RETURN_POS;
                selectedFragment = ReturnFragment.newInstance();
                break;
            case R.id.item_category:
                selectedPos = CATEGORY_POS;
                selectedFragment = CategoryFragment.newInstance();
                break;
            case R.id.item_personal:
                selectedPos = PERSONAL_POS;
                selectedFragment = PersonalFragment.newInstance();
                break;
        }
        showFragment(selectedFragment);
        return true;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!FragmentManagement.getInstance().isFragmentExists(fragment)) {
            //fragment does not exist, add fragment
            transaction.add(R.id.container, fragment, fragment.getClass().toString());
            transaction.addToBackStack(fragment.getClass().toString());
            transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        FragmentManagement.getInstance().addFragment(fragment);


        for (Fragment f : FragmentManagement.getInstance().getFragments()) {
            if (f != null && !f.getClass().toString().equals(fragment.getClass().toString())) {
                //hide fragment not selected
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                Fragment fragToHide = getSupportFragmentManager().findFragmentByTag(f.getClass().toString());
                if (fragToHide != null)
                    t.hide(fragToHide).commit();
            } else {
                //show selected fragment
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                Fragment fragToShow = getSupportFragmentManager().findFragmentByTag(f.getClass().toString());

                if (fragToShow != null)
                    t.show(fragToShow).commit();
            }
        }
    }
}