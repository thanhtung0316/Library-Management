package com.hoptb.library_management.ui.borrow_book.borrow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

public class BookBorrowingViewModel extends BaseViewModel {

    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Book> bookInfo = new MutableLiveData<>();

    public BookBorrowingViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void getBookInfo(int bookId) {
        bookInfo.postValue(libraryManagementOpenHelper.selectBook(bookId));
    }


}
