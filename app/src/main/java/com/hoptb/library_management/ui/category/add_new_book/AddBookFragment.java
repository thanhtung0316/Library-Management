package com.hoptb.library_management.ui.category.add_new_book;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentAddNewBookBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class AddBookFragment extends BaseFragment<FragmentAddNewBookBinding, AddBookViewModel> implements View.OnClickListener {
    private Boolean isEditMode;
    private Integer bookId;

    public static AddBookFragment addNewBook() {
        Bundle args = new Bundle();
        args.putBoolean("KEY_IS_EDIT_MODE", false);
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static AddBookFragment editMode(int id) {
        Bundle args = new Bundle();
        args.putInt("BOOK_ID", id);
        args.putBoolean("KEY_IS_EDIT_MODE", true);
        AddBookFragment fragment = new AddBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<AddBookViewModel> getViewModelClass() {
        return AddBookViewModel.class;
    }

    @Override
    protected void onCreateView() {
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean("KEY_IS_EDIT_MODE");
            bookId = getArguments().getInt("BOOK_ID");
        }
        if (isEditMode == null) {
            return;
        }

        if (isEditMode) {
            if (bookId != null) {
                binding.tvTitle.setText("Chỉnh sửa");
                viewModel.getBook(bookId);
                viewModel.book.observe(getViewLifecycleOwner(), new Observer<Book>() {
                    @Override
                    public void onChanged(Book book) {
                        if (book != null) {
                            binding.edBookName.setText(book.getBookName());
                            binding.edAmount.setText(String.valueOf(book.getAmount()));
                            binding.edAuthor.setText(book.getAuthor());
                            binding.edBookType.setText(book.getBookType());
                            binding.edPublisher.setText(book.getPublisher());
                            binding.edtDesc.setText(book.getDescription());
                            binding.edPosition.setText(book.getPosition());
                        }
                    }
                });

                viewModel.updateStatus.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            closeFragment(true);
                            Toast.makeText(getContext(), "Chỉnh sửa thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        } else {
            viewModel.insertedRow.observe(getViewLifecycleOwner(), new Observer<Long>() {
                @Override
                public void onChanged(Long aLong) {
                    if (aLong != null && aLong != -1) {
                        closeFragment(true);
                        Toast.makeText(getContext(), "Thêm mới sách thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm mới sách không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            viewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            });
        }
        binding.imBack.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_new_book;
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
            case R.id.btnSave:
                viewModel.onClickSave(isEditMode, bookId, binding.edBookName.getText().toString(),
                        binding.edAmount.getText().toString(), binding.edBookType.getText().toString(),
                        binding.edAuthor.getText().toString(), binding.edPublisher.getText().toString(),
                        binding.edtDesc.getText().toString(), binding.edPosition.getText().toString());
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
