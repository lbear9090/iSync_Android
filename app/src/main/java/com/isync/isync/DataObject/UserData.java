package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserData implements Serializable {
    @Expose public int success;
    @Expose public int status;
    @Expose public String message;
    @Expose public User user;
}
