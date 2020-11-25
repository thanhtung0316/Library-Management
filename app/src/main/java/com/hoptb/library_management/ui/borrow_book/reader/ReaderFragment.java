package com.hoptb.library_management.ui.borrow_book.reader;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentListBookBinding;

public class ReaderFragment extends BaseFragment<FragmentListBookBinding, ReaderViewModel> {
    @Override
    protected Class<ReaderViewModel> getViewModelClass() {
        return ReaderViewModel.class;
    }

    @Override
    protected void onCreateView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_reader;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
