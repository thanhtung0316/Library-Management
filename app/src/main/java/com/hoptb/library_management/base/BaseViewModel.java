package com.hoptb.library_management.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {
    private Application application;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
}
