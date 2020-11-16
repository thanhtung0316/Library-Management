package com.hoptb.library_management.ui.borrow_book.book_info;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;

public class BookInfo extends BaseFragment<FragmentBookInfoBinding, BookInfoViewModel> {
    @Override
    protected Class<BookInfoViewModel> getViewModelClass() {
        return BookInfoViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_info;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
