package com.hoptb.library_management.ui.borrow_book.borrow;

import android.view.View;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookBorrowingBinding;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;
import com.hoptb.library_management.ui.borrow_book.book_info.BookInfoFragment;

public class BookBorrowingFragment extends BaseFragment<FragmentBookBorrowingBinding, BookBorrowingViewModel> implements View.OnClickListener {
    private static BookBorrowingFragment INSTANCE;
    public static final String TAG = "BookBorrowingFragment";

    public static BookBorrowingFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookBorrowingFragment();
        }
        return INSTANCE;
    }


    @Override
    protected Class<BookBorrowingViewModel> getViewModelClass() {
        return BookBorrowingViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.imBack.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_borrowing;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                BorrowingContainerFragment containerFragment = (BorrowingContainerFragment) getParentFragment();
                if (containerFragment != null) {
                    containerFragment.removeFragment(this);
                    containerFragment.showFragment(BookInfoFragment.newInstance());
                }
                break;
        }
    }
}
