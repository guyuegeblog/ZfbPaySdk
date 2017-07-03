package com.zfbpaysdk.pay.starzfbsdk.zfbpay;

/**
 * Created by ASUS on 2017/6/22.
 */
public class ErrorInfo {
    public static final int CODE_REQ_NOINIT = 0;
    public static final String INFO_REQ_NOINIT = "Payment SDK not initialized, no inheritance InitActivity.";
    public static final int CODE_LOAD_SUCCESS = 1;
    public static final String INFO_LOAD_SUCCESS = "Payment SDK initialization successful.";
    public static final int CODE_ERR_NOTINIT = 2;
    public static final String INFO_ERR_NOTINIT = "didn\'t initialize hotfix sdk or initialize fail.";
    public static final int CODE_ERR_NOTMAIN = 3;
    public static final String INFO_ERR_NOTMAIN = "only allow query in main process.";
    public static final int CODE_ERR_INBLACKLIST = 4;
    public static final String INFO_ERR_INBLACKLIST = "current device does\'t support hotfix.";
    public static final int CODE_REQ_ERR = 5;
    public static final String INFO_REQ_ERR = "pull patch info detail fail, please check log.";
    public static final int CODE_REQ_NOUPDATE = 6;
    public static final String INFO_REQ_NOUPDATE = "there is not update.";
    public static final int CODE_REQ_NOTNEWEST = 7;
    public static final String INFO_REQ_NOTNEWEST = "the query patchversion equals or less than current patchversion, stop download.";
    public static final int CODE_DOWNLOAD_FAIL = 8;
    public static final int CODE_DOWNLOAD_SUCCESS = 9;
    public static final String INFO_DOWNLOAD_SUCCESS = "patch download success.";
    public static final int CODE_DOWNLOAD_BROKEN = 10;
    public static final String INFO_DOWNLOAD_BROKEN = "patch file is broken.";
    public static final int CODE_UNZIP_FAIL = 11;
    public static final String INFO_UNZIP_FAIL = "unzip patch file error, please check value of AndroidMenifest.xml RSASECRET or initialize param aesKey.";
    public static final int CODE_LOAD_RELAUNCH = 12;
    public static final String INFO_LOAD_RELAUNCH = "please relaunch app to load new patch.";
    public static final int CODE_LOAD_FAIL = 13;
    public static final String INFO_LOAD_FAIL = "load patch fail, please check stack trace of an exception: ";
    public static final int CODE_LOAD_NOPATCH = 14;
    public static final String INFO_LOAD_NOPATCH = "not found any patch file to load.";
    public static final int CODE_REQ_APPIDERR = 15;
    public static final String INFO_REQ_APPIDERR = "appid is not found.";
    public static final int CODE_REQ_SIGNERR = 16;
    public static final String INFO_REQ_SIGNERR = "token is invaild, please check APPSECRET.";
    public static final int CODE_REQ_UNAVAIABLE = 17;
    public static final String INFO_REQ_UNAVAIABLE = "req is unavailable as has already been in arrearage.";
    public static final int CODE_REQ_CLEARPATCH = 18;
    public static final String INFO_REQ_CLEARPATCH = "clean client patch as server publish clear cmd.";
    public static final int CODE_REQ_TOOFAST = 19;
    public static final String INFO_REQ_TOOFAST = "two consecutive request should not short then 3s.";
    public static final int CODE_PATCH_INVAILD = 20;
    public static final String INFO_PATCH_INVAILD = "patch invaild, as patch not exist or is dir or not a jar compress file.";
    public static final String REPORT_DOWNLOAD_SUCCESS = "100";
    public static final String REPORT_DOWNLOAD_ERROR = "101";
    public static final String REPORT_LOAD_SUCCESS = "200";
    public static final String REPORT_LOAD_ERROR = "201";

}
