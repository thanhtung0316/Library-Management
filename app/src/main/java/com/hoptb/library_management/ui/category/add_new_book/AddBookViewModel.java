package com.hoptb.library_management.ui.category.add_new_book;

import android.app.Application;

import androidx.annotation.NonNull;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.repositories.AppRepository;

public class AddBookViewModel extends BaseViewModel {

    public AddBookViewModel(@NonNull Application application) {
        super(application);
    }

    public void onClickSave(String bookName, int amount, String bookType, String author
            , String publisher, String desc) {
        Book book = new Book(bookType, author, publisher, bookName, "", desc);
        AppRepository.getInstance().addBook(book);
    }


}
