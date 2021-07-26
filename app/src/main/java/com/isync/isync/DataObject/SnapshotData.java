package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SnapshotData implements Serializable {
    @Expose public String[] labels;
    @Expose public int[] data;
}
