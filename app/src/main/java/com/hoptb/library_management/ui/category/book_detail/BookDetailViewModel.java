package com.hoptb.library_management.ui.category.book_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

public class BookDetailViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Book> bookInfo = new MutableLiveData<>();

    public BookDetailViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void getBookInfo(int bookId) {
        bookInfo.postValue(libraryManagementOpenHelper.selectBook(bookId));
    }
}
