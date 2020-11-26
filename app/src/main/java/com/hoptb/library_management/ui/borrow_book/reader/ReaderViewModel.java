package com.hoptb.library_management.ui.borrow_book.reader;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;
import com.hoptb.library_management.database.LibraryManagementOpenHelper;
import com.hoptb.library_management.model.Reader;

import java.util.List;

public class ReaderViewModel extends BaseViewModel {
    private LibraryManagementOpenHelper libraryManagementOpenHelper;
    public MutableLiveData<Long> insertStatus = new MutableLiveData<>();
    public MutableLiveData<Integer> deleteStatus = new MutableLiveData<>();

    public MutableLiveData<List<Reader>> readerList = new MutableLiveData<>();
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public ReaderViewModel(@NonNull Application application) {
        super(application);
        libraryManagementOpenHelper = new LibraryManagementOpenHelper(application);

    }

    public void addReader(Reader reader) {
        if (libraryManagementOpenHelper.selectReader(reader.getStudentCode()) != null) {
            errorMsg.postValue("Độc giả đã tồn tại, vui lòng kiểm tra trong danh sách");
        } else {
            insertStatus.postValue(libraryManagementOpenHelper.insertReader(reader));
        }

    }

    public void getAllReader() {
        readerList.postValue(libraryManagementOpenHelper.getAllReaders());
    }

    public void deleteReader(Reader reader) {
        deleteStatus.postValue(libraryManagementOpenHelper.deleteReader(reader.getReaderId()));
    }
}
