package com.hoptb.library_management.ui.category;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentContainerBinding;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class CategoryFragment extends BaseFragment<FragmentContainerBinding, CategoryViewModel> {

    private static CategoryFragment INSTANCE;

    public static CategoryFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<CategoryViewModel> getViewModelClass() {
        return CategoryViewModel.class;
    }

    @Override
    protected void onCreateView() {
        addFragment(ListBookFragment.newInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public String getTitle() {
        return null;
    }


    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.brContainer, fragment);
        for (Fragment fm : getChildFragmentManager().getFragments()) {
            transaction.hide(fm);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fm, boolean reloadData) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (Fragment f : getChildFragmentManager().getFragments()) {
            transaction.hide(f);
        }
        transaction.show(fm);
        viewModel.needReloadListBook.setValue(reloadData);
        transaction.commit();
    }

    public void hideFragment(Fragment fm) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(fm);
        transaction.commit();
    }
}
