package com.hoptb.library_management.ui.category.add_new_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

public class AddBookViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Long> insertedRow = new MutableLiveData<>();
    public MutableLiveData<Integer> updateStatus = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Book> book = new MutableLiveData<>();


    public AddBookViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void onClickSave(Boolean isEditMode, Integer bookId, String bookName, String amount, String bookType, String author
            , String publisher, String desc, String position) {
        if (isEditMode == null) {
            errorMessage.setValue("Có lỗi xảy ra!");
            return;
        }

        if (bookName.isEmpty() || amount.isEmpty() || bookType.isEmpty() || author.isEmpty() || publisher.isEmpty() || position.isEmpty()) {
            errorMessage.setValue("Vui lòng nhập đầy đủ các trường!");
            return;
        }
        Book book = new Book(Integer.parseInt(amount), bookType, author, publisher, bookName, "", desc);
        book.setPosition(position);

        if (isEditMode && bookId != null) {
            book.setBookId(bookId);
            updateStatus.postValue(libraryManagementOpenHelper.updateBook(book));
        } else {
            insertedRow.postValue(libraryManagementOpenHelper.insertBook(book));
        }

    }

    public void getBook(int id) {
        book.postValue(libraryManagementOpenHelper.selectBook(id));
    }

}
