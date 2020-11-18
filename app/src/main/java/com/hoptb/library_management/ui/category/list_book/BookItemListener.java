package com.hoptb.library_management.ui.category.list_book;

import android.view.View;

import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.model.Book;

public interface BookItemListener extends BaseAdapter.BaseItemListener {
    void onBookClick(Book book);

    void onOptionClick(Book book, View view);
}
