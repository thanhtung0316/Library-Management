package com.hoptb.library_management.ui.category.list_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

import java.util.List;

public class ListBookViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<List<Book>> listBook = new MutableLiveData<>();
    public MutableLiveData<Integer> deleteStatus = new MutableLiveData<>();

    public ListBookViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void getAllBooks() {
        listBook.postValue(libraryManagementOpenHelper.getAllBooks());
    }

    public void deleteBook(Book book) {
        deleteStatus.postValue(libraryManagementOpenHelper.deleteBook(book.getBookId()));
    }
}
