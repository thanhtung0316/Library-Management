package com.hoptb.library_management.ui.category.book_detail;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookDetailBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class BookDetailFragment extends BaseFragment<FragmentBookDetailBinding, BookDetailViewModel> implements View.OnClickListener {


    public static BookDetailFragment newInstance(Integer bookId) {
        Bundle args = new Bundle();
        args.putInt("BOOK_ID", bookId);
        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<BookDetailViewModel> getViewModelClass() {
        return BookDetailViewModel.class;
    }

    @Override
    protected void onCreateView() {
        if (getArguments() != null) {
            int bookId = getArguments().getInt("BOOK_ID");
            if (bookId != 0) {
                viewModel.getBookInfo(bookId);
            }
        }
        binding.imBack.setOnClickListener(this);
        viewModel.bookInfo.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                binding.tvBookName.setText(book.getBookName());
                binding.tvTitleBookCode.setText(getString(R.string.title_book_id, String.valueOf(book.getBookId())));
                binding.tvAmount.setText(getString(R.string.title_amount, String.valueOf(book.getAmount())));
                binding.tvAuthor.setText(book.getAuthor());
                binding.tvBookType.setText(book.getBookType());
                binding.tvPublisher.setText(book.getPublisher());
                binding.tvDesc.setText(getString(R.string.title_desc, book.getDescription()));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_detail;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                closeFragment(false);
                break;
        }
    }

    private void closeFragment(boolean reloadData) {
        CategoryFragment containerFragment = (CategoryFragment) getParentFragment();
        if (containerFragment != null) {
            containerFragment.removeFragment(this);
            containerFragment.showFragment(ListBookFragment.newInstance(), reloadData);
        }
    }
}
