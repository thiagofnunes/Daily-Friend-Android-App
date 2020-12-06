package com.thiagonunes.dailyfriend.model;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int id;


    public MyViewModelFactory(Application application, int id) {
        mApplication = application;
        this.id = id;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecordEditorViewModel(mApplication, id);
    }
}
