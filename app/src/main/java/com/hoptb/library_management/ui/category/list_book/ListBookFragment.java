package com.hoptb.library_management.ui.category.list_book;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentListBookBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.CategoryViewModel;
import com.hoptb.library_management.ui.category.add_new_book.AddBookFragment;
import com.hoptb.library_management.ui.category.book_detail.BookDetailFragment;

import java.util.List;

public class ListBookFragment extends BaseFragment<FragmentListBookBinding, ListBookViewModel> implements
        View.OnClickListener, BookItemListener {
    public static final String TAG = "ListBookFragment";
    private static ListBookFragment INSTANCE;
    private CategoryViewModel categoryViewModel;

    public static ListBookFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ListBookFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<ListBookViewModel> getViewModelClass() {
        return ListBookViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.fab.setOnClickListener(this);
        final BaseAdapter<Book> adapter = new BaseAdapter<>(getContext(), R.layout.item_book);
        binding.rcBook.setAdapter(adapter);
        adapter.setListener(this);

        if (getParentFragment() != null) {
            categoryViewModel = new ViewModelProvider(getParentFragment()).get(CategoryViewModel.class);
        }
        categoryViewModel.needReloadListBook.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.getAllBooks();
                }
            }
        });
        viewModel.listBook.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setData(books);
                binding.swipeRf.setRefreshing(false);
                if (books.size() == 0) {
                    binding.tvBookEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding.tvBookEmpty.setVisibility(View.GONE);
                }
            }
        });
        viewModel.deleteStatus.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    viewModel.getAllBooks();
                    Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.swipeRf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getAllBooks();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_book;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                CategoryFragment fragments = (CategoryFragment) getParentFragment();
                if (fragments != null) {
                    fragments.addFragment(AddBookFragment.addNewBook());
                }

                break;
        }
    }

    @Override
    public void onBookClick(Book book) {
        CategoryFragment fragments = (CategoryFragment) getParentFragment();
        if (fragments != null) {
            fragments.addFragment(BookDetailFragment.newInstance(book.getBookId()));
        }
    }

    @Override
    public void onOptionClick(final Book book, View view) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.inflate(R.menu.menu_actions);
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemEdit:
                        CategoryFragment fragments = (CategoryFragment) getParentFragment();
                        if (fragments != null) {
                            fragments.addFragment(AddBookFragment.editMode(book.getBookId()));
                        }
                        break;
                    case R.id.itemDelete:
                        viewModel.deleteBook(book);
                        break;
                }
                return false;
            }
        });

    }


}
