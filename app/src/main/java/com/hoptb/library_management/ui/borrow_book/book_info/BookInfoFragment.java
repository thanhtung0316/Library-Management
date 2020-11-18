package com.hoptb.library_management.ui.borrow_book.book_info;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.MainActivity;
import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;
import com.hoptb.library_management.ui.borrow_book.borrow.BookBorrowingFragment;
import com.hoptb.library_management.ui.category.list_book.BookItemListener;

import java.util.List;

public class BookInfoFragment extends BaseFragment<FragmentBookInfoBinding, BookInfoViewModel>
        implements View.OnClickListener, BookItemListener {

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
        final BaseAdapter<Book> adapter = new BaseAdapter<>(getContext(), R.layout.item_book_hide_option);
        adapter.setListener(this);
        binding.rcBook.setAdapter(adapter);
        viewModel.getAllBooks();
        viewModel.listBook.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setData(books);
                if (books.size()!=0){
                    binding.tvNotFound.setVisibility(View.GONE);
                }else {
                    binding.tvNotFound.setText("Chưa có cuốn sách nào trong thư viện!");
                    binding.tvNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.searchingList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setData(books);
                if (books.size()!=0){
                    binding.tvNotFound.setVisibility(View.GONE);
                }else {
                    binding.tvNotFound.setText(getString(R.string.no_search_result,binding.edSearch.getText().toString()));
                    binding.tvNotFound.setVisibility(View.VISIBLE);
                }
            }
        });

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


        binding.imSearch.setOnClickListener(this);
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.search(s.toString());
            }
        });
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
                    binding.tvTitle.setText("Quản lý mượn sách");
                    binding.grInfo.setVisibility(View.VISIBLE);

                } else {
                    binding.edSearch.setVisibility(View.VISIBLE);
                    binding.tvTitle.setText("");
                    binding.imSearch.setImageResource(R.drawable.ic_baseline_cancel_24);
                    binding.grInfo.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onBookClick(Book book) {
        binding.grInfo.setVisibility(View.VISIBLE);
        binding.rcBook.setVisibility(View.GONE);
        viewModel.getBookInfo(book.getBookId());
    }

    @Override
    public void onOptionClick(Book book, View view) {

    }
}
