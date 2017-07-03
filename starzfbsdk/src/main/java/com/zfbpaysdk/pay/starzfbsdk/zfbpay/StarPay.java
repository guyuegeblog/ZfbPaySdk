package com.zfbpaysdk.pay.starzfbsdk.zfbpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.starpay.unifum.aiwx.wxapi.WXPublicSignalPayActivity;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.switfpass.pay.utils.MD5;
import com.zfbpaysdk.pay.starzfbsdk.R;
import com.zfbpaysdk.pay.starzfbsdk.model.PayInfo;
import com.zfbpaysdk.pay.starzfbsdk.model.PayStatus;
import com.zfbpaysdk.pay.starzfbsdk.model.SerializableMap;
import com.zfbpaysdk.pay.starzfbsdk.model.WapPayInfo;
import com.zfbpaysdk.pay.starzfbsdk.ok.HtmlUtil;
import com.zfbpaysdk.pay.starzfbsdk.ok.NetTool;
import com.zfbpaysdk.pay.starzfbsdk.ok.Ok;
import com.zfbpaysdk.pay.starzfbsdk.ok.PayUrl;
import com.zfbpaysdk.pay.starzfbsdk.ok.WxInfo;
import com.zfbpaysdk.pay.starzfbsdk.ok.XmlUtils;
import com.zfbpaysdk.pay.starzfbsdk.push.InitActivity;
import com.zfbpaysdk.pay.starzfbsdk.view.RqCode;
import com.zfbpaysdk.pay.starzfbsdk.view.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by ASUS on 2017/6/7.
 */
public class StarPay {

    private static Activity mCxt;
    public static StarPayResult starPayResult;
    //支付宝
    // 商户PID
    private String PARTNER = "";
    // 商户收款账号
    private String SELLER = "";
    // 商户私钥，pkcs8格式
    private String RSA_PRIVATE = "";
    // 支付宝公钥
    private String rsa_public = "";
    //支付宝订单号
    private final int SDK_PAY_FLAG = 1;
    private static StarPay starPay;

    private String body;
    private String payPrice;
    private String notifyUrl;
    private String zfb_out_trade_no;
    private String subject;
    private String area;
    private String aliKey;

    //支付宝wap
    private String wapBody;
    private String wapPayPrice;
    private String wapNotifyUrl;
    private String zfbWap_out_trade_no;
    private String wapSubject;
    private String wapArea_return_url;
    private String wapArea;
    private String aliWapKey;
    private final String app_pay = "Y";
    private String wapReturn_Url;

    //微信
    private final String service = "unified.trade.pay";
    private final String version = "2.0";
    private final String charset = "UTF-8";
    private final String sign_type = "MD5";
    private String wxArea;
    private String wxArea_Out_Trade_No;
    private String device_info;
    private String wxBody;
    private String attach;
    private String total_fee;
    private String mch_create_ip;
    private String wxArea_Notify_Url;
    private String time_start;
    private String time_expire;
    private String nonce_str;
    private String sign;
    private String limit_credit_pay = "0";
    private String goods_tag;
    private String wxKey;

    //微信h5
    private String wxPublicSignalArea;
    private String wxPublicSignalOut;
    private String wxPublicSignalBody;
    private String wxPublicSignalPrice;
    private String wxPublicSignalNotifyUrl;
    private String wxPublicSignalKey;
    private String wxPublicAttach;

    //微信扫码
    private String wxCodeArea;
    private String wxCodeOut;
    private String wxCodeBody;
    private String wxCodePrice;
    private String wxCodeNotifyUrl;
    private String wxCodeKey;
    private String wxCodeAttach;


    public void starWxCodePay(String by, String pe, String ntUrl, String oto, String channel, String key, String atta) {
        try {
            this.wxCodeArea = channel;
            this.wxCodeKey = key;
            this.wxCodeBody = by;
            this.wxCodePrice = pe;
            this.wxCodeNotifyUrl = ntUrl;
            this.wxCodeOut = oto;
            this.wxCodeAttach = atta;
            if (TextUtils.isEmpty(wxCodeArea) || TextUtils.isEmpty(wxCodeKey) || TextUtils.isEmpty(wxCodeBody) || TextUtils.isEmpty(wxCodePrice)
                    || TextUtils.isEmpty(wxCodeNotifyUrl) || TextUtils.isEmpty(wxCodeOut)) {
                starPayResult.wxCodePayResultCallBack(AlipayStatus.Pay_Fail);
            } else {
                starWxCodePay();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void starAliWapPay(String by, String pe, String ntUrl, String oto, String sub, String channel, String key, String showUrl) {
        try {
            this.wapReturn_Url = showUrl;
            this.aliWapKey = key;
            this.wapBody = by;
            this.wapPayPrice = pe;
            this.wapNotifyUrl = ntUrl;
            this.zfbWap_out_trade_no = oto;
            this.wapSubject = sub;
            this.wapArea = channel;
            if (TextUtils.isEmpty(aliWapKey) || TextUtils.isEmpty(wapBody) || TextUtils.isEmpty(wapPayPrice) || TextUtils.isEmpty(wapNotifyUrl)
                    || TextUtils.isEmpty(zfbWap_out_trade_no) || TextUtils.isEmpty(wapSubject) || TextUtils.isEmpty(wapArea) || TextUtils.isEmpty(wapReturn_Url)) {
                starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
            } else {
                requeAliWapPayInfo(area, notifyUrl, zfb_out_trade_no, subject, payPrice, body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void starWxPublicSignalPay(String area, String wxH5Ot, String by, String money, String notifyUrl, String atta, String key) {
        this.wxPublicAttach = atta;
        this.wxPublicSignalArea = area;
        this.wxPublicSignalOut = wxH5Ot;
        this.wxPublicSignalBody = by;
        this.wxPublicSignalPrice = money;
        this.wxPublicSignalNotifyUrl = notifyUrl;
        this.wxPublicSignalKey = key;
        if (TextUtils.isEmpty(wxPublicSignalArea) || TextUtils.isEmpty(wxPublicSignalOut) || TextUtils.isEmpty(wxPublicSignalBody) || TextUtils.isEmpty(wxPublicSignalPrice)
                || TextUtils.isEmpty(wxPublicSignalNotifyUrl) || TextUtils.isEmpty(wxPublicSignalKey) || TextUtils.isEmpty(wxPublicAttach)) {
            starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
        } else {
            requetWxPubilcSignalPay();
        }
    }


    public void starWxPay(String area, String wxOt, String by, String money, String notifyUrl, String attach, Activity mcontext, String key) {
        try {
            this.wxKey = key;
            this.wxArea = area;
            this.wxArea_Out_Trade_No = wxOt;
            this.wxBody = by;
            this.attach = attach;
            this.total_fee = money;
            this.wxArea_Notify_Url = notifyUrl;
            if (TextUtils.isEmpty(wxArea) || TextUtils.isEmpty(wxArea_Out_Trade_No) || TextUtils.isEmpty(wxBody) || TextUtils.isEmpty(this.attach)
                    || TextUtils.isEmpty(total_fee) || TextUtils.isEmpty(wxArea_Notify_Url)) {
                starPayResult.wxPayResultCallBack(AlipayStatus.Pay_Fail);
            } else {
                //////////////////////
                starRequestWft();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void starAliPay(String by, String pe, String ntUrl, String oto, String sub, String channel, String key) {
        try {
            this.aliKey = key;
            this.body = by;
            this.payPrice = pe;
            this.notifyUrl = ntUrl;
            this.zfb_out_trade_no = oto;
            this.subject = sub;
            this.area = channel;
            if (TextUtils.isEmpty(body) || TextUtils.isEmpty(payPrice) || TextUtils.isEmpty(notifyUrl) || TextUtils.isEmpty(zfb_out_trade_no)
                    || TextUtils.isEmpty(subject) || TextUtils.isEmpty(area) || TextUtils.isEmpty(aliKey)) {
                starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
            } else {
                requeryPayInfo(area, notifyUrl, zfb_out_trade_no, subject, payPrice, body);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler startZFBPayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                //支付参数确定
                if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                    new AlertDialog.Builder(mCxt).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.dismiss();
                                }
                            }).show();
                    return;
                }
                String bodyTitle = body;
                String payMoney = payPrice;//
                String orderInfo = getOrderInfo(URLEncoder.encode(bodyTitle), URLEncoder.encode(bodyTitle), payMoney, notifyUrl);

                /**
                 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
                 */
                String sign = sign(orderInfo);
                try {
                    /**
                     * 仅需对sign 做URL编码
                     */
                    sign = URLEncoder.encode(sign, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                /**
                 * 完整的符合支付宝参数规范的订单信息
                 */
                final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
                Log.i("payinfostr", payInfo + " ===sign" + sign.length());
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(mCxt);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        zfbHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mCxt);
        String version = payTask.getVersion();
        Toast.makeText(mCxt, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price, String servernotifyurl) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + zfb_out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + URLDecoder.decode(subject) + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + URLDecoder.decode(body) + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + servernotifyurl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    //支付宝后台验证
    @SuppressLint("HandlerLeak")
    private Handler zfbHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    final String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //后台验证支付宝支付是否成功
                        //支付宝后台验证
                        starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Succes);
                    } else {
                        //支付宝支付失败
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void requeryPayInfo(String area, String area_notify_url, String area_out_trade_no, String subject, String total_fee, String body) {
        showLoading();
        if (!NetTool.isConnected(mCxt)) {
            T.showTextToast(mCxt, "您的网络没有连接，请检查您的网络");
            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
            dismisLoading();
            return;
        }
        if (TextUtils.isEmpty(area) || TextUtils.isEmpty(area_notify_url) || TextUtils.isEmpty(area_out_trade_no)
                || TextUtils.isEmpty(subject) || TextUtils.isEmpty(total_fee) || TextUtils.isEmpty(body)) {
            T.showTextToast(mCxt, "请传入有效的支付参数");
            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
            dismisLoading();
            return;
        }
        PayInfo payInfo = new PayInfo();
        payInfo.setArea(area);
        payInfo.setArea_notify_url(area_notify_url);
        payInfo.setArea_out_trade_no(area_out_trade_no);
        payInfo.setSubject(subject);
        payInfo.setTotal_fee(total_fee);
        payInfo.setBody(body);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("area", payInfo.getArea());
            jsonObject.put("area_notify_url", payInfo.getArea_notify_url());
            jsonObject.put("area_out_trade_no", payInfo.getArea_out_trade_no());
            jsonObject.put("subject", payInfo.getSubject());
            jsonObject.put("total_fee", payInfo.getTotal_fee());
            jsonObject.put("body", payInfo.getBody());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            final Map<String, String> stringMap = new HashMap<>();
            stringMap.put("area", payInfo.getArea());
            stringMap.put("area_notify_url", payInfo.getArea_notify_url());
            stringMap.put("area_out_trade_no", payInfo.getArea_out_trade_no());
            stringMap.put("subject", payInfo.getSubject());
            stringMap.put("total_fee", payInfo.getTotal_fee());
            stringMap.put("body", payInfo.getBody());
            //sign
            Map<String, String> signMap = new HashMap<>();
            signMap.put("area", payInfo.getArea());
            signMap.put("area_out_trade_no", payInfo.getArea_out_trade_no());
            signMap.put("subject", payInfo.getSubject());
            signMap.put("total_fee", payInfo.getTotal_fee());
            Collection<String> keyset = signMap.keySet();
            List<String> signlist = new ArrayList<String>(keyset);
            //对key键值按字典升序排序
            Collections.sort(signlist);
            String preStr = "";
            for (int i = 0; i < signlist.size(); i++) {
                preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
            }
            preStr += "key=" + aliKey;
            String sign = MD5.md5s(preStr).toUpperCase();
            stringMap.put("sign", sign);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HtmlUtil.getHtmlUtils().postForHTML(PayUrl.PAY_OUT_TRADE, stringMap, mCxt, starPayResult);
                        org.json.JSONObject jsonObjects = new org.json.JSONObject(result);
                        PARTNER = jsonObjects.getString("partner");
                        SELLER = jsonObjects.getString("seller_id");
                        RSA_PRIVATE = jsonObjects.getString("rsa_private");
                        zfb_out_trade_no = jsonObjects.getString("out_trade_no");
                        notifyUrl = jsonObjects.getString("notify_url");
                        dismisLoading();
                        mCxt.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Alipay alipayInfo = new Alipay();
                                alipayInfo.partner = PARTNER;
                                alipayInfo.seller = SELLER;
                                alipayInfo.rsa_private = RSA_PRIVATE;
                                alipayInfo.notify_url = notifyUrl;
                                //RSA_PUBLIC = alipayInfo.getRsa_public();
                                startZFBPayHandler.sendEmptyMessage(10);
                            }
                        });
                    } catch (Exception e) {
                        dismisLoading();
                        starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
                    }
                }
            }).start();
        } catch (Exception e) {
            dismisLoading();
            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
        }
    }

    public synchronized static StarPay getInstance(Activity mContext) {
        mCxt = mContext;
        starPayResult = (StarPayResult) mContext;
        if (InitActivity.initSdk) {
            starPayResult.payErrorCallBack(ErrorInfo.CODE_LOAD_SUCCESS);
        } else {
            starPayResult.payErrorCallBack(ErrorInfo.CODE_REQ_NOINIT);
        }
        return starPay == null ? (InitActivity.initSdk == true ? starPay = new StarPay() : null) : starPay;
    }

    public void starAliQueryTrade(String outTradeNo) {
        if (!NetTool.isConnected(mCxt)) {
            T.showTextToast(mCxt, "您的网络没有连接，请检查您的网络");
            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        if (TextUtils.isEmpty(outTradeNo)) {
            T.showTextToast(mCxt, "请传入有效的支付订单号");
            starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        try {
            final Map<String, String> stringMap = new HashMap<>();
            stringMap.put("area_out_trade_no", outTradeNo);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HtmlUtil.getHtmlUtils().postForHTML(PayUrl.PAY_QUERY, stringMap, mCxt, starPayResult);
                    try {
                        org.json.JSONObject jsonObjects = new org.json.JSONObject(result);
                        String resultCode = jsonObjects.getString("code");
//                      0.支付失败或者无此订单
//                      1.支付成功
                        PayStatus payStatus = new PayStatus();
                        if (resultCode.equals("1")) {
                            payStatus.setCode(AlipayStatus.Pay_Succes);
                        } else {
                            payStatus.setCode(AlipayStatus.Pay_Fail);
                        }
                        payStatus.setCodeInfo(jsonObjects.getString("code_info"));
                        starPayResult.aliPayQueryCallBack(payStatus);
                    } catch (Exception e) {
                        PayStatus payStatus = new PayStatus();
                        payStatus.setCode("0");
                        payStatus.setCodeInfo("payfail");
                        starPayResult.aliPayQueryCallBack(payStatus);
                    }
                }
            }).start();
        } catch (Exception e) {
            PayStatus payStatus = new PayStatus();
            payStatus.setCode("0");
            payStatus.setCodeInfo("payfail");
            starPayResult.aliPayQueryCallBack(payStatus);
        }
    }


    private void starRequestWft() {
        showLoading();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("body", wxBody); // 商品名称
        params.put("service", service); // 支付类型
        params.put("charset", charset); //
        params.put("sign_type", sign_type); //
        params.put("version", version); //
        params.put("area_notify_url", wxArea_Notify_Url); // 后台通知url
        params.put("area_out_trade_no", wxArea_Out_Trade_No); //订单号
        params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
        params.put("total_fee", total_fee); // 总金额
        params.put("area", wxArea);
        params.put("attach", this.attach);
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("body", wxBody); // 商品名称
        signMap.put("area_out_trade_no", wxArea_Out_Trade_No); //订单号
        signMap.put("total_fee", total_fee); // 总金额
        signMap.put("area", wxArea);
        Collection<String> keyset = signMap.keySet();
        List<String> signlist = new ArrayList<String>(keyset);
        //对key键值按字典升序排序
        Collections.sort(signlist);
        String preStr = "";
        for (int i = 0; i < signlist.size(); i++) {
            preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
        }
        preStr += "key=" + wxKey;
        String sign = MD5.md5s(preStr).toUpperCase();
        params.put("sign", sign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String resultXml = HtmlUtil.getHtmlUtils().postForWft(PayUrl.WX_OUT_TRADE, params, mCxt, starPayResult);
                    final Map<String, String> result = XmlUtils.doXMLParse(resultXml);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.get("status").equalsIgnoreCase("0")) // 成功
                            {
                                //Toast.makeText(mcontxt, R.string.get_prepayid_succ, Toast.LENGTH_LONG).show();
                                RequestMsg msg = new RequestMsg();
                                msg.setTokenId(result.get("token_id"));
                                //微信
                                msg.setTradeType(MainApplication.WX_APP_TYPE);
                                msg.setAppId(result.get("appid"));
                                PayPlugin.unifiedAppPay(mCxt, msg);
                            } else {
                                starPayResult.wxPayResultCallBack(AlipayStatus.Pay_Fail);
                                Toast.makeText(mCxt, mCxt.getResources().getString(R.string.get_prepayid_fail), Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxPayResultCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();
    }


    public void starWxPayQuery(final Activity mcontxt, String wx_out_trade_no) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("area_out_trade_no", wx_out_trade_no); //
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String resultJson = HtmlUtil.getHtmlUtils().postForWft(PayUrl.WX_PAY_QUERY_ORDER_BYUSER_ORDER, params, mcontxt, starPayResult);
                    mcontxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(resultJson);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) // 成功
                                {
                                    starPayResult.wxPayQueryCallBack(AlipayStatus.Pay_Succes);
                                } else {
                                    starPayResult.wxPayQueryCallBack(AlipayStatus.Pay_Fail);
                                }
                            } catch (JSONException e) {
                                starPayResult.wxPayQueryCallBack(AlipayStatus.Pay_Fail);
                            }
                        }
                    });
                } catch (Exception e) {
                    mcontxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mcontxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxPayQueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();

    }


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssddd");

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public String createSign(String signKey, Map<String, String> params) {
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        com.switfpass.pay.utils.SignUtils.buildPayParams(buf, params, false);
        buf.append("&key=").append(signKey);
        String preStr = buf.toString();
        String sign = "";
        // 获得签名验证结果
        try {
            sign = MD5.md5s(preStr).toUpperCase();
        } catch (Exception e) {
            sign = MD5.md5s(preStr).toUpperCase();
        }
        return sign;
    }

    private ProgressDialog progressBar;

    private ProgressDialog getProgressBar(Activity mCxt) {
        return showProgressDialog(mCxt);
    }

    private ProgressDialog showProgressDialog(Activity mCxt) {
        if (progressBar == null) {
            progressBar = new ProgressDialog(mCxt);
            progressBar.setMessage("正在请求支付信息...");
            progressBar.setCancelable(true);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        return progressBar;
    }

    private void showLoading() {
        mCxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProgressDialog dialog = getProgressBar(mCxt);
                    if (!mCxt.isFinishing() && !dialog.isShowing()) {
                        dialog.show();
                    }
                } catch (Exception e) {
                    progressBar = null;
                    showLoading();
                }
            }
        });
    }

    private void dismisLoading() {
        mCxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getProgressBar(mCxt).dismiss();
            }
        });
    }


    private void requetWxPubilcSignalPay() {
        showLoading();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("area", wxPublicSignalArea); // 商品名称
        params.put("area_notify_url", wxPublicSignalNotifyUrl);
        params.put("area_out_trade_no", wxPublicSignalOut);
        params.put("body", wxPublicSignalBody);
        params.put("total_fee", wxPublicSignalPrice);
        params.put("attach", wxPublicAttach);
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("area", wxPublicSignalArea); // 商品名称
        signMap.put("area_out_trade_no", wxPublicSignalOut);
        signMap.put("body", wxPublicSignalBody);
        signMap.put("total_fee", wxPublicSignalPrice);
        Collection<String> keyset = signMap.keySet();
        List<String> signlist = new ArrayList<String>(keyset);
        //对key键值按字典升序排序
        Collections.sort(signlist);
        String preStr = "";
        for (int i = 0; i < signlist.size(); i++) {
            preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
        }
        preStr += "key=" + wxPublicSignalKey;
        final String sign = MD5.md5s(preStr).toUpperCase();
        params.put("sign", sign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //post方式
//                    final String result = HtmlUtil.getHtmlUtils().postForPublicSignalWx(PayUrl.WX_PUBLIC_SIGNAL_OUT_TRADE, params, mCxt, starPayResult);
                    //get方式
                    String url = PayUrl.WX_PUBLIC_SIGNAL_OUT_TRADE + "area=" + wxPublicSignalArea + "&area_notify_url=" + wxPublicSignalNotifyUrl +
                            "&area_out_trade_no=" + wxPublicSignalOut + "&body=" + wxPublicSignalBody + "&total_fee=" + wxPublicSignalPrice + "&attach=" + wxPublicAttach
                            + "&sign=" + sign;
                    final String result = HtmlUtil.getHtmlUtils().getForHTML(url);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mCxt.startActivity(new Intent(mCxt, WXPublicSignalPayActivity.class).putExtra("sbappend", result).
//                                    putExtra("sbbody", wxPublicSignalBody).putExtra("sbprice", wxPublicSignalPrice).putExtra("out",wxPublicSignalOut));
                            showLoading();
                            String sbAppend = result.replace("<script>location.href=", "").replace("</script>", "").replace("\"", "");
                            if (!sbAppend.equals("")) {
                                if (sbAppend.startsWith("weixin://")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sbAppend));
                                    mCxt.startActivity(intent);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mCxt.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    T.showTextCenterToast(mCxt, "请打开微信后再尝试支付");
                                                    dismisLoading();
                                                }
                                            });
                                        }
                                    }, 10000);
                                } else {
                                    mCxt.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            T.showTextCenterToast(mCxt, "跳转失败,请重新尝试支付");
                                            dismisLoading();
                                        }
                                    });
                                }
                            } else {
                                mCxt.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        T.showTextCenterToast(mCxt, "跳转失败,请重新尝试支付");
                                        dismisLoading();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();
    }


    public void starWxPublicSiganlPayQueryTrade(String signalOutTrade) {
        if (TextUtils.isEmpty(signalOutTrade)) {
            starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        showLoading();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("area_out_trade_no", signalOutTrade); // 商品名称
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = HtmlUtil.getHtmlUtils().postForPublicSignalWx(PayUrl.WX_PUBLIC_SIGNAL_QUERY_TRADE, params, mCxt, starPayResult);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) {
                                    starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Succes);
                                } else {
                                    starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();
    }

    public void showToast(boolean ishow) {
        if (ishow) {
            T.isShow = true;
        } else {
            T.isShow = false;
        }
    }


    private void requeAliWapPayInfo(String area, String area_notify_url, String area_out_trade_no, String subject, String total_fee, String body) {
        showLoading();
        if (!NetTool.isConnected(mCxt)) {
            T.showTextToast(mCxt, "您的网络没有连接，请检查您的网络");
            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
            dismisLoading();
            return;
        }
        if (TextUtils.isEmpty(aliWapKey) || TextUtils.isEmpty(wapBody) || TextUtils.isEmpty(wapPayPrice) || TextUtils.isEmpty(wapNotifyUrl)
                || TextUtils.isEmpty(zfbWap_out_trade_no) || TextUtils.isEmpty(wapSubject) || TextUtils.isEmpty(wapArea)) {
            T.showTextToast(mCxt, "请传入有效的支付参数");
            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
            dismisLoading();
            return;
        }
        WapPayInfo payInfo = new WapPayInfo();
        payInfo.setArea(wapArea);
        payInfo.setArea_notify_url(wapNotifyUrl);
        payInfo.setArea_return_url(wapReturn_Url);
        payInfo.setArea_out_trade_no(zfbWap_out_trade_no);
        payInfo.setSubject(wapSubject);
        payInfo.setTotal_fee(wapPayPrice);
        payInfo.setBody(wapBody);
        payInfo.setApp_pay(app_pay);
        try {
            final Map<String, String> stringMap = new HashMap<>();
            stringMap.put("area", payInfo.getArea());
            stringMap.put("area_notify_url", payInfo.getArea_notify_url());
            stringMap.put("area_out_trade_no", payInfo.getArea_out_trade_no());
            stringMap.put("subject", payInfo.getSubject());
            stringMap.put("total_fee", payInfo.getTotal_fee());
            stringMap.put("body", payInfo.getBody());
            stringMap.put("app_pay", payInfo.getApp_pay());
            stringMap.put("area_return_url", wapReturn_Url);
            //sign
            Map<String, String> signMap = new HashMap<>();
            signMap.put("area", payInfo.getArea());
            signMap.put("area_out_trade_no", payInfo.getArea_out_trade_no());
            signMap.put("subject", payInfo.getSubject());
            signMap.put("total_fee", payInfo.getTotal_fee());
            Collection<String> keyset = signMap.keySet();
            List<String> signlist = new ArrayList<String>(keyset);
            //对key键值按字典升序排序
            Collections.sort(signlist);
            String preStr = "";
            for (int i = 0; i < signlist.size(); i++) {
                preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
            }
            preStr += "key=" + aliWapKey;
            String sign = MD5.md5s(preStr).toUpperCase();
            stringMap.put("sign", sign);
            payInfo.setSign(sign);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bundlemap", payInfo);
            mCxt.startActivity(new Intent(mCxt, WXPublicSignalPayActivity.class).putExtras(bundle));
            dismisLoading();
        } catch (Exception e) {
            dismisLoading();
            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
        }
    }


    public void starAliWapPayQueryTrade(String wapOutTradeNo, String area, String aliWapKey) {
        if (!NetTool.isConnected(mCxt)) {
            T.showTextCenterToast(mCxt, "网络没有连接");
            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        if (TextUtils.isEmpty(wapOutTradeNo) || TextUtils.isEmpty(area) || TextUtils.isEmpty(aliWapKey)) {
            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        showLoading();
        Map<String, String> signMap = new HashMap<>();
        signMap.put("area", area);
        signMap.put("area_out_trade_no", wapOutTradeNo);
        Collection<String> keyset = signMap.keySet();
        List<String> signlist = new ArrayList<String>(keyset);
        //对key键值按字典升序排序
        Collections.sort(signlist);
        String preStr = "";
        for (int i = 0; i < signlist.size(); i++) {
            preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
        }
        preStr += "key=" + aliWapKey;
        String sign = MD5.md5s(preStr).toUpperCase();
        final Map<String, String> stringMap = new HashMap<>();
        stringMap.put("area", area);
        stringMap.put("area_out_trade_no", wapOutTradeNo);
        stringMap.put("sign", sign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = HtmlUtil.getHtmlUtils().postForAliWap(PayUrl.ALI_WAPPAY_QUERY_TRADE, stringMap, mCxt, starPayResult);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) {
                                    starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Succes);
                                } else {
                                    starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
                                }
                            } catch (JSONException e) {
                                starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();
    }


    private void starWxCodePay() {
        showLoading();
        final Map<String, String> stringMap = new HashMap<>();
        stringMap.put("area", wxCodeArea);
        stringMap.put("area_notify_url", wxCodeNotifyUrl);
        stringMap.put("area_out_trade_no", wxCodeOut);
        stringMap.put("total_fee", wxCodePrice);
        stringMap.put("body", wxCodeBody);
        stringMap.put("attach", wxCodeAttach);
        Map<String, String> signMap = new HashMap<>();
        signMap.put("area", wxCodeArea);
        signMap.put("area_out_trade_no", wxCodeOut);
        signMap.put("total_fee", wxCodePrice);
        signMap.put("body", wxCodeBody);
        Collection<String> keyset = signMap.keySet();
        List<String> signlist = new ArrayList<String>(keyset);
        //对key键值按字典升序排序
        Collections.sort(signlist);
        String preStr = "";
        for (int i = 0; i < signlist.size(); i++) {
            preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
        }
        preStr += "key=" + wxCodeKey;
        String sign = MD5.md5s(preStr).toUpperCase();
        stringMap.put("sign", sign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = HtmlUtil.getHtmlUtils().postWxCode(PayUrl.WX_CODE_TRADE_TRADE, stringMap, mCxt, starPayResult);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) {
                                    String imageUrl = jsonObject.getString("img_url");
                                    starPayResult.wxCodePayResultCallBack(imageUrl);
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    starPayResult.wxCodePayResultCallBack(msg);
                                }
                            } catch (JSONException e) {
                                starPayResult.wxCodePayResultCallBack(AlipayStatus.Pay_Fail);
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxCodePayResultCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();
    }


    public void starWxCodePayQueryTrade(String wxCodePayOut) {
        if (!NetTool.isConnected(mCxt)) {
            T.showTextCenterToast(mCxt, "网络没有连接");
            starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        if (TextUtils.isEmpty(wxCodePayOut)) {
            starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        showLoading();
        final Map<String, String> stringMap = new HashMap<>();
        stringMap.put("area_out_trade_no", wxCodePayOut);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = HtmlUtil.getHtmlUtils().postWxCodeQuery(PayUrl.WX_CODE_QUERY_TRADE, stringMap, mCxt, starPayResult);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) {
                                    starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Succes);
                                } else {
                                    starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
                                }
                            } catch (JSONException e) {
                                starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();

    }

    public void starWxCodePay2(String area, String wxH5Ot, String by, String money, String notifyUrl, String atta, String key) {
        this.wxPublicAttach = atta;
        this.wxPublicSignalArea = area;
        this.wxPublicSignalOut = wxH5Ot;
        this.wxPublicSignalBody = by;
        this.wxPublicSignalPrice = money;
        this.wxPublicSignalNotifyUrl = notifyUrl;
        this.wxPublicSignalKey = key;
        if (TextUtils.isEmpty(wxPublicSignalArea) || TextUtils.isEmpty(wxPublicSignalOut) || TextUtils.isEmpty(wxPublicSignalBody) || TextUtils.isEmpty(wxPublicSignalPrice)
                || TextUtils.isEmpty(wxPublicSignalNotifyUrl) || TextUtils.isEmpty(wxPublicSignalKey) || TextUtils.isEmpty(wxPublicAttach)) {
            starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
        } else {
            requetWxPay2();
        }
    }


    private void requetWxPay2() {
        showLoading();
        final Map<String, String> params = new HashMap<String, String>();
        params.put("area", wxPublicSignalArea); // 商品名称
        params.put("area_notify_url", wxPublicSignalNotifyUrl);
        params.put("area_out_trade_no", wxPublicSignalOut);
        params.put("body", wxPublicSignalBody);
        params.put("total_fee", wxPublicSignalPrice);
        params.put("attach", wxPublicAttach);
        Map<String, String> signMap = new HashMap<String, String>();
        signMap.put("area", wxPublicSignalArea); // 商品名称
        signMap.put("area_out_trade_no", wxPublicSignalOut);
        signMap.put("body", wxPublicSignalBody);
        signMap.put("total_fee", wxPublicSignalPrice);
        Collection<String> keyset = signMap.keySet();
        List<String> signlist = new ArrayList<String>(keyset);
        //对key键值按字典升序排序
        Collections.sort(signlist);
        String preStr = "";
        for (int i = 0; i < signlist.size(); i++) {
            preStr += signlist.get(i) + "=" + signMap.get(signlist.get(i)) + "&";
        }
        preStr += "key=" + wxPublicSignalKey;
        final String sign = MD5.md5s(preStr).toUpperCase();
        params.put("sign", sign);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //post方式
//                    final String result = HtmlUtil.getHtmlUtils().postForPublicSignalWx(PayUrl.WX_PUBLIC_SIGNAL_OUT_TRADE, params, mCxt, starPayResult);
                    //get方式
                    final String url = PayUrl.WX_PUBLIC_SIGNAL_OUT_TRADE + "area=" + wxPublicSignalArea + "&area_notify_url=" + wxPublicSignalNotifyUrl +
                            "&area_out_trade_no=" + wxPublicSignalOut + "&body=" + wxPublicSignalBody + "&total_fee=" + wxPublicSignalPrice + "&attach=" + wxPublicAttach
                            + "&sign=" + sign;
//                    final String result = HtmlUtil.getHtmlUtils().getForHTML(url);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!url.equals("")) {
                                if (!url.equals("")) {
                                    Bitmap qrBitmap = RqCode.generateBitmap(url, 500, 500);
                                    starPayResult.wxCodePay2ResultCallBack(qrBitmap);
                                } else {
                                    mCxt.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            T.showTextCenterToast(mCxt, "支付失败,请重新尝试支付");
                                            starPayResult.wxCodePay2ResultCallBack(null);
                                        }
                                    });
                                }
                            } else {
                                mCxt.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        T.showTextCenterToast(mCxt, "支付失败,请重新尝试支付");
                                        starPayResult.wxCodePay2ResultCallBack(null);
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxCodePay2ResultCallBack(null);
                        }
                    });
                }
            }
        }).start();
    }

    public void starWxCodePay2QueryTrade(String wxCodePayOut) {
        if (!NetTool.isConnected(mCxt)) {
            T.showTextCenterToast(mCxt, "网络没有连接");
            starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        if (TextUtils.isEmpty(wxCodePayOut)) {
            starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
            return;
        }
        showLoading();
        final Map<String, String> stringMap = new HashMap<>();
        stringMap.put("area_out_trade_no", wxCodePayOut);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = HtmlUtil.getHtmlUtils().postWxCodeQuery2(PayUrl.WX_PUBLIC_SIGNAL_QUERY_TRADE, stringMap, mCxt, starPayResult);
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String code = jsonObject.getString("code");
                                if (code.equals("1")) {
                                    starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Succes);
                                } else {
                                    starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
                                }
                            } catch (JSONException e) {
                                starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    dismisLoading();
                    mCxt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            T.showTextToast(mCxt, "网络繁忙，请稍后再试!!");
                            starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
                        }
                    });
                }
            }
        }).start();

    }

}