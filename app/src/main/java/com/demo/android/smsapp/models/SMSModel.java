package com.demo.android.smsapp.models;

public class SMSModel {
    private String id;
    private String address;
    private String body;
    private String type;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static SMSModel getSMSModelObject(String id,String address,String body, String type, String date){
        SMSModel smsModel = new SMSModel();
        smsModel.setId(id);
        smsModel.setAddress(address);
        smsModel.setBody(body);
        smsModel.setType(type);
        smsModel.setDate(date);
        return smsModel;
    }
}
