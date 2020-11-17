package com.hoptb.library_management.ui.category.list_book;

import android.view.View;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentListBookBinding;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.add_new_book.AddBookFragment;

public class ListBookFragment extends BaseFragment<FragmentListBookBinding, ListBookViewModel> implements View.OnClickListener {
    public static final String TAG = "ListBookFragment";
    private static ListBookFragment INSTANCE;

    public static ListBookFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ListBookFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<ListBookViewModel> getViewModelClass() {
        return ListBookViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.fab.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_book;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                CategoryFragment fragments = (CategoryFragment) getParentFragment();
                if (fragments != null) {
                    fragments.addFragment(AddBookFragment.newInstance());
                }

                break;
        }
    }
}
