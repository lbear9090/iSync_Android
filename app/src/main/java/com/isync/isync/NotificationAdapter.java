package com.isync.isync;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final Context mContext;
    LayoutInflater mInflater;
    List<String> mList;
    public NotificationAdapter(Context context, List<String> list){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mList = list;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notification_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.txtNotification.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNotification, txtTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotification = itemView.findViewById(R.id.txtNotification);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
