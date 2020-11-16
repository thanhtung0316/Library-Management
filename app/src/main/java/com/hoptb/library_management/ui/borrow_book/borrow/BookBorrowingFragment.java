package com.hoptb.library_management.ui.borrow_book.borrow;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookBorrowingBinding;

public class BookBorrowingFragment extends BaseFragment<FragmentBookBorrowingBinding, BookBorrowingViewModel> {
    @Override
    protected Class<BookBorrowingViewModel> getViewModelClass() {
        return BookBorrowingViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_borrowing;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
