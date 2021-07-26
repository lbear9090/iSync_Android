package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DailyPerformanceByPartner implements Serializable {
    @Expose public DailyPerformance[] data;
    @Expose public int total_clicks;
    @Expose public int total_netsales;
    @Expose public double total_partner_revenue;
    @Expose public double total_your_revenue;
}
