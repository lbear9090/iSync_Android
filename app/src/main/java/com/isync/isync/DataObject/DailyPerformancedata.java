package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DailyPerformancedata implements Serializable {
    @Expose public String date;
    @Expose public String affiliate_id;
    @Expose public int clicks;
    @Expose public int units_sold;
    @Expose public double partner_revenue;
    @Expose public Double your_revenue;
}
