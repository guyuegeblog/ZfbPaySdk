package com.zfbpaysdk.pay.starzfbsdk.zfbpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by ASUS on 2017/6/7.
 */
public class StarPay {

    private Activity mContext;
    private StarPayResult starPayResult;
    //支付宝
    // 商户PID
    private final String PARTNER = "2088621765537800";
    // 商户收款账号
    private final String SELLER = "2450203949@qq.com";
    // 商户私钥，pkcs8格式
    private static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALEt4tVKWrSnQEMo\n" +
            "6gNRDvQQ99YDP48yWEdhNZnOLQxakuEOL06A1dHsHeHxiAxV82uohyZyhdjEPmqt\n" +
            "0HAo8eW1HeMqf0WkQL9Yjv6LQVCW+pOkKh2nW1I9/jUfVxElV4S0pb/ZVOK3MkcX\n" +
            "isZiS1kcRnMkupcZE7sIJ3uLJV5nAgMBAAECgYAYX++s2CXSZI/6rU5EVopwGUV/\n" +
            "M8MyuAJdD4qINk0MPgmm3IS1kBPaZVlXFBtbVxe939l3+JlhUN4lXvQl+fwNGyBv\n" +
            "EflHWvsvO1tUKxyu9ts/dZlLzy3bfmKqqA+VCSouHDE27fE8/usfb6YFnpjLxcdi\n" +
            "4qS7OUBwbw7lwYZSwQJBAOjcZX8K04Ie2qf+ihZ+vr5NroMBjMJcWmtZ3SPRfbBJ\n" +
            "LQAFwq9ZGRUT8+THVBcOaLbbiyT2dYSzg38TJ6db0wUCQQDCyQnBBj1QLA1kKyih\n" +
            "tj6bvGOhBf+7JxkX+bkgQb11iFf6m1t0tjIeXfllCUJ1/NCiqKwzVyittnyals2h\n" +
            "kf97AkB8p3ch4h7FmlS897LeRe1AHH+eG9dJOH0i2JFcGkopc5LGX08P5SdbNDos\n" +
            "r1Phn9IIiVBcch8I8bwj65IfsjUtAkABSvpQmK0BgjlYh9zOLfp+aCK1J+gJovnM\n" +
            "qfCroSQTyqLi2/MgdDEi+Ha2T/hr9hCgc8Qo1xRDCuoK0KNuxhxPAkEA2bNt6hmr\n" +
            "bCv6hZqInPLpz8U66DEVRiBgplsN2nTDzCe1Im4QLeM7MqIn0TwGRozJiLGCrYAh\n" +
            "maPd1FLFwefYIQ==";
    // 支付宝公钥
    private final String rsa_public = "";
    //支付宝订单号
    private String Zfb_out_trade_no = "";
    private final int SDK_PAY_FLAG = 1;

    private String body;
    private String payPrice;
    private String notifyUrl;
    private String zfb_out_trade_no;

    public StarPay(Activity mContext) {
        this.mContext = mContext;
        starPayResult = (StarPayResult) this.mContext;
    }

    public void startZfbPay(String by, String pe, String ntUrl, String oto) {
        try {
            this.body = by;
            this.payPrice = pe;
            this.notifyUrl = ntUrl;
            this.zfb_out_trade_no = oto;
            if (TextUtils.isEmpty(body) || TextUtils.isEmpty(payPrice) || TextUtils.isEmpty(notifyUrl) || TextUtils.isEmpty(zfb_out_trade_no)) {
                starPayResult.payResult("支付取消");
            } else {
                Alipay alipayInfo = new Alipay();
                alipayInfo.partner = PARTNER;
                alipayInfo.seller = SELLER;
                alipayInfo.rsa_private = RSA_PRIVATE;
                alipayInfo.notify_url = "";
                //RSA_PUBLIC = alipayInfo.getRsa_public();
                startZFBPayHandler.sendEmptyMessage(10);
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
                    new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
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

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(mContext);
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
        PayTask payTask = new PayTask(mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
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
        Zfb_out_trade_no = zfb_out_trade_no;
        orderInfo += "&out_trade_no=" + "\"" + Zfb_out_trade_no + "\"";

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
                        starPayResult.payResult(zfbPaySucces.Pay_Succes);
                    } else {
                        //支付宝支付失败
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            starPayResult.payResult(zfbPaySucces.Pay_Fail);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            starPayResult.payResult(zfbPaySucces.Pay_Fail);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
