package com.hoptb.library_management.ui.borrow_book.book_info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

import java.util.List;

public class BookInfoViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<List<Book>> listBook = new MutableLiveData<>();


    public BookInfoViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void getAllBooks() {
        listBook.postValue(libraryManagementOpenHelper.getAllBooks());
    }
}
