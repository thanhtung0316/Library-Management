package com.hoptb.library_management.ui.category;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hoptb.library_management.base.BaseViewModel;

public class CategoryViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> needReloadListBook = new MutableLiveData<>(true);

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }
}
