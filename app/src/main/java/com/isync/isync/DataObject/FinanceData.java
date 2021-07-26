package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class FinanceData implements Serializable {
    @Expose public double sale_revenue;
    @Expose public double refund_revenue;
    @Expose public double net_revenue;
    @Expose public double payment;
}
