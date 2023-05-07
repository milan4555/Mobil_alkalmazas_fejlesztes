package com.example.stronger;

import android.app.NotificationManager;
import android.content.Context;

public class NotificationHandler {
    private Context mContext;
    private NotificationManager mManager;

    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
