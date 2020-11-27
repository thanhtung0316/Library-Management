package com.hoptb.library_management.ui.return_book;

import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.model.BorrowingModel;

public interface ItemRtListener extends BaseAdapter.BaseItemListener {
    void onItemRtClick(BorrowingModel borrowingModel);
}
