package com.zfbpaysdk.pay.starzfbsdk.ok;

import android.app.Activity;
import android.util.Log;

import com.zfbpaysdk.pay.starzfbsdk.view.T;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.AlipayStatus;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPayResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;


public class HtmlUtil {

    public static HtmlUtil htmlUtil;

    public static synchronized HtmlUtil getHtmlUtils() {
        if (htmlUtil == null) {
            htmlUtil = new HtmlUtil();
        }
        return htmlUtil;
    }

    /**
     * @param url
     * @return
     */
    public  String getForHTML(String url) {
        String html = null;
        HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
        HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
        try {
            HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
            int resStatu = responce.getStatusLine().getStatusCode();// 返回结果
            if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不�?
                // 获得相应实体
                HttpEntity entity = responce.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);// 获得html源代�?
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return html;

    }

    /**
     * 提交请求并得到结果
     *
     * @param URL
     * @param parasMap
     * @return
     */
    public static String postForHTML(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            Log.i("errorstr",e.getMessage());
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.aliPayResultCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }

    /**
     * 提交请求并得到结果字符)
     *
     * @param URL
     * @param
     * @return
     */
    public static String postStrForHTML(String URL, String str) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            byte[] bypes = str.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strre = resultStr.toString();
        return strre;
    }


    /***
     * 微信特殊方法
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postForWft(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.wxPayResultCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }

    /***
     * 微信公众号
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postForPublicSignalWx(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.wxPublicSignalPayQueryCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }


    /***
     * 支付宝wap
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postForAliWap(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.aliWapPayQueryCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }


    /***
     * 微信扫码
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postWxCode(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.wxCodePayResultCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }


    /***
     * 微信扫码查询
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postWxCodeQuery(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.wxCodePayQueryCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }

    /***
     * 微信扫码查询2
     *
     * @param URL
     * @param parasMap
     * @param mContext
     * @param starPayResult
     * @return
     */
    public static String postWxCodeQuery2(String URL, Map<String, String> parasMap, final Activity mContext, final StarPayResult starPayResult) {
        StringBuffer resultStr = new StringBuffer();
        try {
            URL url = new URL(URL);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");// 提交模式
            con.setDoOutput(true);// 是否输入参数
            con.setUseCaches(false);
            con.setFollowRedirects(true);

            StringBuffer params = new StringBuffer();
            // 表单参数与get形式�?��
            Set<String> key = parasMap.keySet();
            for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
                String s = (String) it.next();
                params.append(s).append("=").append(parasMap.get(s)).append("&");
            }
            byte[] bypes = params.toString().getBytes("UTF-8");
            con.getOutputStream().write(bypes);// 输入参数

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    resultStr.append(line);
                }
            }
            in.close();
        } catch (Exception e) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showTextToast(mContext, "网络繁忙，请稍后再试!!");
                    starPayResult.wxCodePay2QueryCallBack(AlipayStatus.Pay_Fail);
                }
            });
        }
        return resultStr.toString();
    }
}
