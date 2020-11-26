package com.hoptb.library_management.ui.borrow_book.reader;

import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.model.Reader;

public interface ItemReaderListener extends BaseAdapter.BaseItemListener {
    void onItemClick(Reader reader);

    void onDeleteClick(Reader reader);
}
