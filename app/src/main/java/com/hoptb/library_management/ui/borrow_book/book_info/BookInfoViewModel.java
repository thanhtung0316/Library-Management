package com.hoptb.library_management.ui.borrow_book.book_info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;

import java.util.List;

public class BookInfoViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<List<Book>> listBook = new MutableLiveData<>();
    public MutableLiveData<List<Book>> searchingList = new MutableLiveData<>();
    public MutableLiveData<List<BorrowingModel>> brList = new MutableLiveData<>();
    public MutableLiveData<Book> bookInfo = new MutableLiveData<>();
    public MutableLiveData<Integer> updateBookStatus = new MutableLiveData<>();


    public BookInfoViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void getAllBooks() {
        listBook.postValue(libraryManagementOpenHelper.getAllBooks());
    }

    public void search(String key) {
        searchingList.postValue(libraryManagementOpenHelper.search(key));
    }

    public void getBookInfo(int bookId) {
        bookInfo.postValue(libraryManagementOpenHelper.selectBook(bookId));
    }

    public void getListBr() {
        brList.postValue(libraryManagementOpenHelper.getAllBrRecords());
    }

    public void updateBook(Book book) {
        updateBookStatus.postValue(libraryManagementOpenHelper.updateBook(book));
    }
}
