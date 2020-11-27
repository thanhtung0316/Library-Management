package com.hoptb.library_management.ui.borrow_book.book_info;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookInfoBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;

import java.util.ArrayList;
import java.util.List;

public class BookInfoFragment extends BaseFragment<FragmentBookInfoBinding, BookInfoViewModel>
        implements View.OnClickListener {

    private static BookInfoFragment INSTANCE;
    public static final String TAG = "BookInfoFragment";
    private Book book;

    public static BookInfoFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookInfoFragment();
        }
        return INSTANCE;
    }

    private int bookId;
    private BaseAdapter<BorrowingModel> adapter;

    @Override
    protected Class<BookInfoViewModel> getViewModelClass() {
        return BookInfoViewModel.class;
    }

    @Override
    protected void onCreateView() {
        viewModel.getAllBooks();
        adapter = new BaseAdapter<>(getContext(), R.layout.item_br_record);
        binding.rcBorrow.setAdapter(adapter);

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
        viewModel.updateBookStatus.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.getBookInfo(bookId);
            }
        });
        viewModel.bookInfo.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                setUpView(book);
                viewModel.getListBr(String.valueOf(book.getBookId()));
            }
        });


        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Adapter adapter = adapterView.getAdapter();
                book = (Book) adapter.getItem(i);
                setUpView(book);
                viewModel.getListBr(String.valueOf(book.getBookId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.lnBorrow.setOnClickListener(this);
        viewModel.brList.observe(getViewLifecycleOwner(), new Observer<List<BorrowingModel>>() {
            @Override
            public void onChanged(List<BorrowingModel> borrowingModels) {
                for (int i = 0; i < borrowingModels.size(); i++) {
                    borrowingModels.get(i).setNumericalOrder(i + 1);
                }
                adapter.setData(borrowingModels);
            }
        });
        binding.swipeRf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getAllBooks();
                viewModel.getListBr(String.valueOf(bookId));
                Toast.makeText(getContext(), "Đã làm mới", Toast.LENGTH_SHORT).show();
                binding.swipeRf.setRefreshing(false);
            }
        });
    }

    private void setUpView(Book book) {
        binding.tvBookName.setText(book.getBookName());
        binding.tvTitleBookCode.setText(getString(R.string.title_book_id, String.valueOf(book.getBookId())));
        binding.tvAmount.setText(getString(R.string.title_amount, String.valueOf(book.getAmount())));
        binding.tvAuthor.setText(book.getAuthor());
        binding.tvBookType.setText(book.getBookType());
        binding.tvPublisher.setText(book.getPublisher());
        bookId = book.getBookId();
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
                if (book==null){
                    Toast.makeText(getContext(), "Vui lòng chọn đầu sách.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (book.getAmount() <= 0) {
                    Toast.makeText(getContext(), "Sách này trong kho đã hết, vui lòng chọn cuốn khác!", Toast.LENGTH_SHORT).show();
                    return;
                }

                BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
                if (fragments != null) {
                    fragments.getBorrowingFragment().setData(bookId);
                    fragments.showFragment(fragments.getBorrowingFragment());
                }
                break;
        }
    }

    public void setData(int amount) {
        book.setAmount(book.getAmount() - amount);
        viewModel.updateBook(book);
        viewModel.getListBr(String.valueOf(book.getBookId()));

    }
}
