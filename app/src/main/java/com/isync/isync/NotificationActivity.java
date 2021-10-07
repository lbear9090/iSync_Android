package com.isync.isync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String[] notifications = {
                "notification 1",
                "notification 2",
                "notification 3",
                "notification 4",
                "notification 5",
        };
        RecyclerView listNotification = findViewById(R.id.listNotification);

        NotificationAdapter notifAdapter = new NotificationAdapter(this, Arrays.asList(notifications));
        listNotification.setAdapter(notifAdapter);
    }

    public void onBack(View v){
        finish();
    }
}