package com.zfbpaysdk.pay.starzfbsdk.ok;

/**
 * Created by ASUS on 2017/6/9.
 */
public class PayUrl {
    public static final String PAY_OUT_TRADE = "http://api.baosteelhb.com:8080/alipay/AliAPPOrderServlet?";
    public static final String PAY_QUERY = "http://api.baosteelhb.com:8080/alipay/AliAPPOrderQueryServlet?";

    public static final String WX_OUT_TRADE = "http://114.215.17.116:8080/wxpay/APPOrderServlet";
    public static final String WX_AREA_OPENCLOSE = "http://114.215.17.116:8080/wxpay/AdInfoServlet?";
    public static final String WX_PAY_QUERY_ORDER_BYSERVER_ORDER = "http://114.215.17.116:8080/wxpay/APPOrderQueryServlet?";
    public static final String WX_PAY_QUERY_ORDER_BYUSER_ORDER = "http://114.215.17.116:8080/wxpay/APPAreaOrderQueryServlet?";

    public static final String WX_PUBLIC_SIGNAL_OUT_TRADE = "http://pay.sdcxbj.com/yxxpay/Pay?";
    public static final String WX_PUBLIC_SIGNAL_QUERY_TRADE = "http://pay.wdjcbj.com:8080/wxpay/GZHAreaOrderQueryServlet?";

    public static final String ALI_WAPPAY_TRADE_TRADE = "http://api.baosteelhb.com:8080/alipay/AliMD5OrderServlet?";
    public static final String ALI_WAPPAY_QUERY_TRADE = "http://api.baosteelhb.com:8080/alipay/AliMD5OrderQueryServlet?";

    public static final String WX_CODE_TRADE_TRADE = "http://pay.wdjcbj.com:8080/wxpay/SaoMaOrderServlet?";
    public static final String WX_CODE_QUERY_TRADE = "http://pay.wdjcbj.com:8080/wxpay/GZHAreaOrderQueryServlet?";

}
