package com.hoptb.library_management.ui.return_book;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentReturnBinding;

public class ReturnFragment extends BaseFragment<FragmentReturnBinding, ReturnViewModel> {

    private static ReturnFragment INSTANCE;

    public static ReturnFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReturnFragment();
        }
        return INSTANCE;
    }


    @Override
    protected Class<ReturnViewModel> getViewModelClass() {
        return ReturnViewModel.class;
    }

    @Override
    protected void onCreateView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_return;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
