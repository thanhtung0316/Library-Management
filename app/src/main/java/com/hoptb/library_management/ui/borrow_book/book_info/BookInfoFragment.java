package com.hoptb.library_management.ui.borrow_book.book_info;

import android.view.View;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.MainActivity;
import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;
import com.hoptb.library_management.ui.borrow_book.borrow.BookBorrowingFragment;

import java.util.List;

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
        viewModel.getAllBooks();
        viewModel.listBook.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

            }
        });

        binding.imSearch.setOnClickListener(this);


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

            case R.id.imSearch:
                if (binding.edSearch.getVisibility() == View.VISIBLE) {
                    binding.edSearch.setVisibility(View.INVISIBLE);
                    binding.imSearch.setImageResource(R.drawable.ic_baseline_search_24);
                    binding.edSearch.setText("");
                    MainActivity.hideKeyboard(getActivity());
                    binding.tvTitle.setText("Thông tin");

                } else {
                    binding.edSearch.setVisibility(View.VISIBLE);
                    binding.tvTitle.setText("");
                    binding.imSearch.setImageResource(R.drawable.ic_baseline_cancel_24);
                }
                break;
        }
    }
}
