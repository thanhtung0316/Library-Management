package com.hoptb.library_management.ui.return_book;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReturnViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Reader> readerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<BorrowingModel>> brList = new MutableLiveData<>();
    public MutableLiveData<Integer> updateStatus = new MutableLiveData<>();
    public MutableLiveData<Integer> updateBookStatus = new MutableLiveData<>();
    public MutableLiveData<Book> book = new MutableLiveData<>();

    public ReturnViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);
    }

    public void searchReader(String code) {
        readerMutableLiveData.postValue(libraryManagementOpenHelper.selectReader(code));
    }

    public void getBrList(int readerId) {
        brList.postValue(libraryManagementOpenHelper.getBrListThroughCode(readerId));
    }

    public void updateRecord(BorrowingModel model, int amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        int a = model.getAmount() - amount;
        updateStatus.postValue(libraryManagementOpenHelper.updateRtRecord(model.getBrId(), a, date));
    }

    public void updateBook(int bookId, int amount) {
        updateBookStatus.postValue(libraryManagementOpenHelper.updateBook(bookId, amount));
    }

    public void getBook(int id) {
        book.postValue(libraryManagementOpenHelper.selectBook(id));
    }
}
