package com.zfbpaysdk.pay.starzfbsdk.model;

import java.io.Serializable;

/**
 * Created by ASUS on 2017/6/9.
 */
public class WapPayInfo implements Serializable {
    private String area;
    private String area_notify_url;
    private String area_return_url;
    private String area_out_trade_no;
    private String subject;
    private String total_fee;
    private String body;
    private String app_pay;
    private String sign;

    public String getArea_return_url() {
        return area_return_url;
    }

    public void setArea_return_url(String area_return_url) {
        this.area_return_url = area_return_url;
    }

    public String getApp_pay() {
        return app_pay;
    }

    public void setApp_pay(String app_pay) {
        this.app_pay = app_pay;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

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
