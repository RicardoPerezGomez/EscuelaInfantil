package com.example.lionkids.ui.evaluacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EvaluacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EvaluacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is evaluacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
