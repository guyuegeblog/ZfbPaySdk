package com.zfbpaysdk.pay.starzfbsdk.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/6/9.
 */
public class SecretInfo {
    private String partner;
    private String seller_id;
    private String notify_url;
    private String code;
    private String code_info;
    private String rsa_private;
    private String out_trade_no;

//    public static SecretInfo objectFromData(String str) {
//
//        return new com.google.gson.Gson().fromJson(str, SecretInfo.class);
//    }
//
//    public static SecretInfo objectFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//
//            return new com.google.gson.Gson().fromJson(jsonObject.getString(str), SecretInfo.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static List<SecretInfo> arraySecretInfoFromData(String str) {
//
//        Type listType = new com.google.gson.reflect.TypeToken<ArrayList<SecretInfo>>() {
//        }.getType();
//
//        return new com.google.gson.Gson().fromJson(str, listType);
//    }
//
//    public static List<SecretInfo> arraySecretInfoFromData(String str, String key) {
//
//        try {
//            JSONObject jsonObject = new JSONObject(str);
//            Type listType = new com.google.gson.reflect.TypeToken<ArrayList<SecretInfo>>() {
//            }.getType();
//
//            return new com.google.gson.Gson().fromJson(jsonObject.getString(str), listType);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList();
//
//
//    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_info() {
        return code_info;
    }

    public void setCode_info(String code_info) {
        this.code_info = code_info;
    }

    public String getRsa_private() {
        return rsa_private;
    }

    public void setRsa_private(String rsa_private) {
        this.rsa_private = rsa_private;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
