package com.hoptb.library_management.ui.return_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;

import java.util.List;

public class ReturnViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Reader> readerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BorrowingModel>> brList = new MutableLiveData<>();

    public ReturnViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void searchReader(String code) {
        readerMutableLiveData.postValue(libraryManagementOpenHelper.selectReader(code));
    }

    public void getBrList(String readerId){
       brList.postValue(libraryManagementOpenHelper.getBrListThroughCode(readerId));
    }
}
