package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class DashboardData implements Serializable {
    @Expose public int success;
    @Expose public int status;
    @Expose public String message;
    @Expose public SalesData sales;
    @Expose public FinanceData finance_data;
    @Expose public int total_proposal_sent;
}
