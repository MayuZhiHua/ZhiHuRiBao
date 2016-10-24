package yzh.com.zhihuribao.tool;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 根据接口获得Url的字符串
 * Created by HP on 2016/10/8.
 */
public class UrlToString {

    //根据Url地址获得Str字符串
    public static String urlToString(String urlStr) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        InputStream is = null;
        StringBuffer sb = new StringBuffer();
        String str=null;
        try {
            URL u = new URL(urlStr);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
                sb.append("\r\n");
            }
            reader.close();
            conn.disconnect();
            str = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Tag", "字符串网络操作出错");
        }
        return str;
    }
}
