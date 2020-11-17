package com.hoptb.library_management.ui.category.add_new_book;

import android.view.View;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentAddNewBookBinding;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class AddBookFragment extends BaseFragment<FragmentAddNewBookBinding, AddBookViewModel> implements View.OnClickListener {

    private static AddBookFragment INSTANCE;

    public static AddBookFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddBookFragment();
        }
        return INSTANCE;
    }


    @Override
    protected Class<AddBookViewModel> getViewModelClass() {
        return AddBookViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.imBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_new_book;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                CategoryFragment containerFragment = (CategoryFragment) getParentFragment();
                if (containerFragment != null) {
                    containerFragment.removeFragment(this);
                    containerFragment.showFragment(ListBookFragment.newInstance());
                }
                break;
            case R.id.btnSave:
                viewModel.onClickSave(binding.edBookName.getText().toString(), Integer.parseInt(
                        binding.edAmount.getText().toString()), binding.edBookType.getText().toString(),
                        binding.edAuthor.getText().toString(), binding.edPublisher.getText().toString(),
                        binding.edtDesc.getText().toString());
                break;
        }
    }
}
