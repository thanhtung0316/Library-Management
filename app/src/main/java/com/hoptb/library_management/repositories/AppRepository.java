package com.hoptb.library_management.repositories;

import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;

public class AppRepository {
    private static AppRepository INSTANCE;
    private LibraryManagementOpenHelper libraryManagementOpenHelper;

    public static AppRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppRepository();
        }
        return INSTANCE;
    }

    public void addBook(Book book) {

    }

}
