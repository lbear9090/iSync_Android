package com.isync.isync.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isync.isync.DataObject.DashboardData;
import com.isync.isync.DataObject.Snapshot;
import com.isync.isync.DataObject.SnapshotData;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<SnapshotData> mSnapshot;
    private MutableLiveData<DashboardData> mDashboard;
    public DashboardViewModel() {
        mSnapshot = new MutableLiveData<>();
        mDashboard = new MutableLiveData<>();
    }

    public void setSnapshot(SnapshotData snapshot){mSnapshot.postValue(snapshot);}
    public LiveData<SnapshotData> getSnapshot(){return mSnapshot;}

    public void setDashboard(DashboardData dashboardData){mDashboard.postValue(dashboardData);}
    public LiveData<DashboardData> getDashboard(){return mDashboard;}
}