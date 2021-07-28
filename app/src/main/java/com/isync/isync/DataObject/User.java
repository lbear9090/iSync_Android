package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class User implements Serializable {
    @Expose public String id;
    @Expose public String name;
    @Expose public String email;
}
