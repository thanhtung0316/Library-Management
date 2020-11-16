package com.hoptb.library_management.ui.category;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentCategoryBinding;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding, CategoryViewModel> {

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
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
