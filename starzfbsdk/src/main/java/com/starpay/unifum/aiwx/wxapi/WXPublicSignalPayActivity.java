package com.starpay.unifum.aiwx.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zfbpaysdk.pay.starzfbsdk.R;
import com.zfbpaysdk.pay.starzfbsdk.model.WapPayInfo;
import com.zfbpaysdk.pay.starzfbsdk.ok.PayUrl;

public class WXPublicSignalPayActivity extends Activity {

    private WebView webView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        // 禁止屏幕休眠
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_wxh5_pay);
        initWebView();
    }

    //private String sbAppend, sbBody, sbPrice, out;
    private WapPayInfo serializableMap;

    private void initWebView() {
        //初始化webview
        //启用支持javascript
        try {
            showLoading(this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismisLoading(WXPublicSignalPayActivity.this);
                }
            }, 2000);
//            out = getIntent().getStringExtra("out");
//            sbAppend = getIntent().getStringExtra("sbappend");
//            sbPrice = getIntent().getStringExtra("sbprice");
//            sbBody = getIntent().getStringExtra("sbbody");
//            sbAppend = sbAppend.replace("<script>location.href=", "").replace("</script>", "").replace("\"", "");
            serializableMap= (WapPayInfo) getIntent().getExtras().get("bundlemap");
            webView = (WebView) findViewById(R.id.webview);
            WebSettings webSetting = webView.getSettings();
            webSetting.setJavaScriptEnabled(true);//支持javaScript
            webSetting.setDefaultTextEncodingName("utf-8");//设置网页默认编码
            webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
            webSetting.setAllowFileAccess(true);
            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSetting.setSupportZoom(true);
            webSetting.setBuiltInZoomControls(true);
            webSetting.setUseWideViewPort(true);
            webSetting.setSupportMultipleWindows(false);
            webSetting.setLoadWithOverviewMode(true);
            webSetting.setAppCacheEnabled(true);
            webSetting.setDatabaseEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setJavaScriptEnabled(true);
            webSetting.setGeolocationEnabled(true);
            webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
            webSetting.setAppCacheEnabled(true);
            webSetting.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
            webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
            webSetting.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
            webSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
            // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
            webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
            webView.addJavascriptInterface(this, "publicsignalpay");//对应js中的test.xxx
//            webView.loadUrl("file:///android_asset/index.html");
//            String sbPr = (Double.parseDouble(sbPrice) / 100) + "";
//            // 传递参数调用
//            final String sb = sbAppend + "&" + sbBody + "&" + sbPr;
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    webView.loadUrl("javascript:javacalljswith(" + "'" + sb + "'" + ")");
//                }
//            },500);
            //webView.loadData(locationHrefUrl(sbAppend), "text/html; charset=UTF-8", null);
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webView.loadData(createWebForm(PayUrl.ALI_WAPPAY_TRADE_TRADE, serializableMap), "text/html; charset=UTF-8", null);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    Log.i("aliurl",url);
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }

                @Override
                public void onPageStarted(WebView view, String url,
                                          Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }


                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    //handler.cancel(); 默认的处理方式，WebView变成空白页
                    //handler.process();
                    //handleMessage(Message msg); 其他处理
                }
            });
            webView.setWebChromeClient(new WebChromeClient());
        } catch (Exception e) {
            WXPublicSignalPayActivity.this.finish();
        }
    }

    @JavascriptInterface
    public void signalpay(String msg) {//对应js中xxx.hello("")
        showExit();
    }

    @JavascriptInterface
    public void paytoast(String msg) {//对应js中xxx.hello("")
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void starPay() {
//        showLoading(this);
//        String sbPr = (Double.parseDouble(sbPrice) / 100) + "";
//        final String sb = sbAppend + "&" + sbBody + "&" + sbPr;
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl("javascript:javacalljswith(" + "'" + sb + "'" + ")");
//            }
//        },500);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismisLoading(WXPublicSignalPayActivity.this);
//            }
//        }, 10000);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sbAppend));
//        startActivity(intent);
    }

    private ProgressDialog progressBar;

    private ProgressDialog getProgressBar(Activity mCxt) {
        return showProgressDialog(mCxt);
    }

    private synchronized ProgressDialog showProgressDialog(Activity mCxt) {
        if (progressBar == null) {
            progressBar = new ProgressDialog(mCxt);
            progressBar.setMessage("正在处理支付信息...");
            progressBar.setCancelable(true);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        return progressBar;
    }

    private void showLoading(final Activity mCxt) {
        mCxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getProgressBar(mCxt).show();
            }
        });
    }

    private void dismisLoading(final Activity mCxt) {
        mCxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getProgressBar(mCxt).dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        showExit();
    }

    private void showExit() {
        WXPublicSignalPayActivity.this.finish();
//        new AlertDialog.Builder(this).setTitle("结束充值? 您还未确认支付状态!")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setCancelable(false)
//                .setPositiveButton("不充值了", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击“确认”后的操作
//                        dialog.dismiss();
//                        WXPublicSignalPayActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("我已支付", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击“返回”后的操作,这里不设置没有任何操作
//                        dialog.dismiss();
//                        WXPublicSignalPayActivity.this.finish();
//                    }
//                }).show();
    }

//    @Override
//    public void aliPayResultCallBack(String result) {
//
//    }
//
//    @Override
//    public void aliPayQueryCallBack(PayStatus serverResult) {
//
//    }
//
//    @Override
//    public void wxPayResultCallBack(String result) {
//
//    }
//
//    @Override
//    public void wxPayQueryCallBack(String serverResult) {
//
//    }
//
//    @Override
//    public void wxPublicSignalPayQueryCallBack(String serverResult) {
//        Log.i("payresult", "后端微信公众号查询sdk服务器结果=" + serverResult);
//        if (serverResult.equals(AlipayStatus.Pay_Succes)) {
//            webView.loadUrl("javascript:queryresult(" + "'" + AlipayStatus.Pay_Succes + "'" + ")");
//        } else {
//            webView.loadUrl("javascript:queryresult(" + "'" + AlipayStatus.Pay_Fail + "'" + ")");
//        }//支付已过期(请重新支付)或支付完毕
//    }

    @Override
    protected void onResume() {
        super.onResume();
        //StarPay.getStarPay(this).wxPublicSiganlPayQueryTrade(out);
    }


    private String createWebForm(String url, WapPayInfo pay_request) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html>").append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
        sb.append("<title>").append("pay").append("</title>");
//        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/wxzf.css\">  \n" +
//                "<script src=\"file:///android_asset/js/jquery.js\"></script> ");
        sb.append("</head><script language=\"javascript\">");
        sb.append("function checkform(){");
        sb.append("document.payForm.submit();");
        sb.append("}");

        sb.append("function checkpay(){");
        sb.append("androidpay.clickOnAndroid();");
        sb.append("}");

        sb.append("</script>");
        sb.append("<body onLoad=checkform()  style=\"text-align:center\">");
        sb.append("<form method=\"post\" name=\"payForm\"  action=\"" + url + "\">");
        sb.append("<table style=\"text-align:center\">");

        sb.append("<input type=\"hidden\" name=\"area\"  value=\"" + pay_request.getArea() + "\">");

        sb.append("<input type=\"hidden\" name=\"area_notify_url\"  value=\"" + pay_request.getArea_notify_url() + "\">");

        sb.append("<input type=\"hidden\" name=\"area_return_url\"  value=\"" + pay_request.getArea_return_url() + "\">");

        sb.append("<input type=\"hidden\" name=\"area_out_trade_no\"  value=\"" + pay_request.getArea_out_trade_no() + "\">");

        sb.append("<input type=\"hidden\" name=\"subject\"  value=\"" + pay_request.getSubject() + "\">");

        sb.append("<input type=\"hidden\" name=\"total_fee\"  value=\"" + pay_request.getTotal_fee() + "\">");

        sb.append("<input type=\"hidden\" name=\"app_pay\"  value=\"" + pay_request.getApp_pay() + "\">");

        sb.append("<input type=\"hidden\" name=\"body\"  value=\"" + pay_request.getBody() + "\">");

        sb.append("<input type=\"hidden\" name=\"sign\"  value=\"" + pay_request.getSign() + "\">");

//        sb.append("<input type=\"button\" value=\"第1步:微信支付\" style=\"width:" + horiental + "; height:" + vetircal + ";text-align:center; background-color:#00cc33;font-size:50px;margin-top:" + ScreenTool.getHeight(this) / 2 + ";color:#FFFFFF;\" onclick=\"checkform();\">");
//
//        sb.append("<input type=\"button\" value=\"第2步:支付完成\" style=\"width:" + horiental + "; height:" + vetircal + "; background-color:#00cc33;font-size:50px;margin-top:50px;color:#FFFFFF;\" onclick=\"checkpay()\">");

        sb.append("</table>");
        sb.append("</form>");//支付完成(确认支付状态)
        sb.append("</body>");
        sb.append("</html>");

        String string = sb.toString();
        return string;
    }
}
