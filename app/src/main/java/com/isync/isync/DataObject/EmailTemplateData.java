package com.isync.isync.DataObject;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class EmailTemplateData implements Serializable {
    @Expose public String id;
    @Expose public String email_subject;
    @Expose public String email_body;
}