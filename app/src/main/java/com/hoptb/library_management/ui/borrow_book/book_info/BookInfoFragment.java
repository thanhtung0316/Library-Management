package com.hoptb.library_management.ui.borrow_book.book_info;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;

import java.util.ArrayList;
import java.util.List;

public class BookInfoFragment extends BaseFragment<FragmentBookInfoBinding, BookInfoViewModel>
        implements View.OnClickListener {

    private static BookInfoFragment INSTANCE;
    public static final String TAG = "BookInfoFragment";

    public static BookInfoFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookInfoFragment();
        }
        return INSTANCE;
    }

    private int bookId;

    @Override
    protected Class<BookInfoViewModel> getViewModelClass() {
        return BookInfoViewModel.class;
    }

    @Override
    protected void onCreateView() {
        viewModel.getAllBooks();

        viewModel.listBook.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                List<String> bookNameList = new ArrayList<>();
                for (Book book : books) {
                    bookNameList.add(book.getBookName());
                }
                ArrayAdapter<Book> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                        books);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinner.setAdapter(adapter);

            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                Book book = (Book) adapter.getItem(i);
                binding.tvBookName.setText(book.getBookName());
                binding.tvTitleBookCode.setText(getString(R.string.title_book_id, String.valueOf(book.getBookId())));
                binding.tvAmount.setText(getString(R.string.title_amount, String.valueOf(book.getAmount())));
                binding.tvAuthor.setText(book.getAuthor());
                binding.tvBookType.setText(book.getBookType());
                binding.tvPublisher.setText(book.getPublisher());
                bookId = book.getBookId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                    fragments.getBorrowingFragment().setData(bookId);
                    fragments.showFragment(fragments.getBorrowingFragment());
                }
                break;
        }
    }
}
