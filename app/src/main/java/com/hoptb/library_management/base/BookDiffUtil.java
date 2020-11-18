package com.hoptb.library_management.base;

import androidx.recyclerview.widget.DiffUtil;

import com.hoptb.library_management.model.Book;

import java.util.List;

public class BookDiffUtil extends DiffUtil.Callback {
    private List<Book> oldList;
    private List<Book> newList;

    public BookDiffUtil(List<Book> oldList, List<Book> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
