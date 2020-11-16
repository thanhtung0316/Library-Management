package com.hoptb.library_management.ui.borrow_book;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBorrowBinding;

public class BorrowFragment extends BaseFragment<FragmentBorrowBinding, BorrowViewModel> {
    private FragmentBorrowBinding binding;
    private static BorrowFragment INSTANCE;


    public static BorrowFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BorrowFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<BorrowViewModel> getViewModelClass() {
        return BorrowViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_borrow;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
