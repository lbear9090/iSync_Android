package com.isync.isync.helper;

import com.isync.isync.DataObject.DailyPerformance;
import com.isync.isync.DataObject.DashboardData;
import com.isync.isync.DataObject.EmailTemplate;
import com.isync.isync.DataObject.Snapshot;
import com.isync.isync.DataObject.User;
import com.isync.isync.DataObject.UserData;

public class Global {
    public static String baseURL = "https://isync.com/VA/api/";
    public static String g_token = "";
    public static User user = null;
    public static DashboardData dashboardData = null;
    public static Snapshot snapshot = null;
    public static DailyPerformance dailyPerformance = null;
    public static EmailTemplate emailTemplate = null;
}
