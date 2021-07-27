package com.isync.isync.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isync.isync.DataObject.Snapshot;
import com.isync.isync.DataObject.SnapshotData;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<SnapshotData> mSnapshot;
    private MutableLiveData<String> mSales;
    public DashboardViewModel() {
        mSnapshot = new MutableLiveData<>();
        mSales = new MutableLiveData<>();
    }

    public void setSnapshot(SnapshotData snapshot){mSnapshot.postValue(snapshot);}
    public LiveData<SnapshotData> getSnapshot(){return mSnapshot;}

    public void setSales(String sales){mSales.postValue(sales);}
    public LiveData<String> getSales(){return mSales;}
}