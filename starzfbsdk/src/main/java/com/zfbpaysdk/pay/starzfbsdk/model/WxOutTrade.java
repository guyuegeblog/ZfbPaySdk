package com.zfbpaysdk.pay.starzfbsdk.model;

/**
 * Created by ASUS on 2017/6/13.
 */
public class WxOutTrade {
    private String service;
    private String version;
    private String charset;
    private String sign_type;
    private String area;
    private String area_out_trade_no;
    private String device_info;
    private String body;
    private String attach;
    private int total_fee;
    private String mch_create_ip;
    private String area_notify_url;
    private String time_start;
    private String time_expire;
    private String nonce_str;
    private String sign;
    private String limit_credit_pay;
    private String goods_tag;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_out_trade_no() {
        return area_out_trade_no;
    }

    public void setArea_out_trade_no(String area_out_trade_no) {
        this.area_out_trade_no = area_out_trade_no;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getMch_create_ip() {
        return mch_create_ip;
    }

    public void setMch_create_ip(String mch_create_ip) {
        this.mch_create_ip = mch_create_ip;
    }

    public String getArea_notify_url() {
        return area_notify_url;
    }

    public void setArea_notify_url(String area_notify_url) {
        this.area_notify_url = area_notify_url;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLimit_credit_pay() {
        return limit_credit_pay;
    }

    public void setLimit_credit_pay(String limit_credit_pay) {
        this.limit_credit_pay = limit_credit_pay;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }
}
