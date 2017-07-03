package com.zfbpaysdk.pay.starzfbsdk.model;

/**
 * Created by ASUS on 2017/6/9.
 */
public class PayInfo {
    private String area;
    private String area_notify_url;
    private String area_out_trade_no;
    private String subject;
    private String total_fee;
    private String body;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_notify_url() {
        return area_notify_url;
    }

    public void setArea_notify_url(String area_notify_url) {
        this.area_notify_url = area_notify_url;
    }

    public String getArea_out_trade_no() {
        return area_out_trade_no;
    }

    public void setArea_out_trade_no(String area_out_trade_no) {
        this.area_out_trade_no = area_out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
