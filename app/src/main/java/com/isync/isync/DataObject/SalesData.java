package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SalesData implements Serializable {
    @Expose public String daily_sales;
    @Expose public String last_weekly_sales;
    @Expose public String monthly_sales;
    @Expose public String year_to_date_sales;
}
