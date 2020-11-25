package com.hoptb.library_management.ui.borrow_book.borrow;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookBorrowingBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;
import com.hoptb.library_management.ui.borrow_book.book_info.BookInfoFragment;

public class BookBorrowingFragment extends BaseFragment<FragmentBookBorrowingBinding, BookBorrowingViewModel> implements View.OnClickListener {

    public static final String TAG = "BookBorrowingFragment";
    private Integer bookId;


    public static BookBorrowingFragment newInstance(int bookId) {
        Bundle args = new Bundle();
        args.putInt("PARAM_BOOK_ID", bookId);
        BookBorrowingFragment fragment = new BookBorrowingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected Class<BookBorrowingViewModel> getViewModelClass() {
        return BookBorrowingViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.imBack.setOnClickListener(this);
        binding.cvAdd.setOnClickListener(this);
        if (getArguments() != null) {
            bookId = getArguments().getInt("PARAM_BOOK_ID");
        }
        if (bookId == null || bookId == 0) {
            return;
        }
        viewModel.getBookInfo(bookId);
        viewModel.bookInfo.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                binding.tvBookName.setText(book.getBookName());
                binding.tvTitleBookCode.setText(getString(R.string.title_book_id, String.valueOf(book.getBookId())));
                binding.tvAmount.setText(getString(R.string.title_amount, String.valueOf(book.getAmount())));
                binding.tvAuthor.setText(book.getAuthor());
                binding.tvBookType.setText(book.getBookType());
                binding.tvPublisher.setText(book.getPublisher());
            }
        });
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
                closeFragment(false);
                break;
            case R.id.cvAdd:
                BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
                if (fragments != null) {
                    fragments.addFragment(BookBorrowingFragment.newInstance(bookId));
                }

                break;

        }
    }

    private void closeFragment(boolean reloadData) {
        BorrowingContainerFragment containerFragment = (BorrowingContainerFragment) getParentFragment();
        if (containerFragment != null) {
            containerFragment.removeFragment(this);
            containerFragment.showFragment(BookInfoFragment.newInstance(), reloadData);
        }
    }
}
