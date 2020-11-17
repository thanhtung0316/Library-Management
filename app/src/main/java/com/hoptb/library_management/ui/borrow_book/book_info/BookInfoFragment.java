package com.hoptb.library_management.ui.borrow_book.book_info;

import android.view.View;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;
import com.hoptb.library_management.ui.borrow_book.borrow.BookBorrowingFragment;

public class BookInfoFragment extends BaseFragment<FragmentBookInfoBinding, BookInfoViewModel> implements View.OnClickListener {

    private static BookInfoFragment INSTANCE;
    public static final String TAG = "BookInfoFragment";

    public static BookInfoFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookInfoFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<BookInfoViewModel> getViewModelClass() {
        return BookInfoViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.lnBorrow.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_info;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnBorrow:
                BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
                if (fragments != null) {
                    fragments.addFragment(BookBorrowingFragment.newInstance());

                }


                break;
        }
    }
}
