package com.hoptb.library_management.ui.category.add_new_book;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.databinding.FragmentAddNewBookBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class AddBookFragment extends BaseFragment<FragmentAddNewBookBinding, AddBookViewModel> implements View.OnClickListener {
    private Boolean isEditMode;
    private Integer bookId;
    private static int RESULT_LOAD_IMG = 1;
    public final int REQUEST_CODE_FOR_PERMISSIONS = 654;
    private String picturePath;
    private Bitmap bm;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOR_PERMISSIONS);
            }
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
                            binding.imBook.setImageBitmap(book.getBitmap());
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
        binding.imBook.setOnClickListener(this);
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
                        binding.edtDesc.getText().toString(), binding.edPosition.getText().toString(), bm);
                break;
            case R.id.imBook:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMG);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && data != null) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            bm = BitmapFactory.decodeFile(picturePath);
            binding.imBook.setImageBitmap(bm);
        }
    }

    private void getImage(Bitmap bm) {

    }
}
