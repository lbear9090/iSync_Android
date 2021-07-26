package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DailyPerformance implements Serializable {
    @Expose public int success;
    @Expose public int status;
    @Expose public String message;
    @Expose public DailyPerformanceByPartner daily_performance_by_partner;
}
