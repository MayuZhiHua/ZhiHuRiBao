package yzh.com.zhihuribao.Task;

import android.os.AsyncTask;

import org.json.JSONObject;

import yzh.com.zhihuribao.info.SplashData;
import yzh.com.zhihuribao.info.TimeAndString;
import yzh.com.zhihuribao.listener.ISplashListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**闪屏画面下载数据源的异步任务
 * Created by HP on 2016/10/13.
 */
public class SplashDownTask extends AsyncTask<Void,Void,TimeAndString>{

    private String splashImageUrl;
    private ISplashListener iSplashListener;


    public SplashDownTask( String splashImageUrl,ISplashListener iSplashListener) {
        this.iSplashListener = iSplashListener;
        this.splashImageUrl = splashImageUrl;
    }

    @Override
    protected TimeAndString doInBackground(Void... params) {
        //获得起始时间
        long startTime = System.currentTimeMillis();
        String s = UrlToString.urlToString(splashImageUrl);
        //获得结束时间
        long endTime = System.currentTimeMillis();
        //获得加载数据的时间
        long loadTime = endTime - startTime;
        TimeAndString timeAndString= new TimeAndString();
        timeAndString.setTime(loadTime);
        timeAndString.setS(s);
        return timeAndString;
    }

    @Override
    protected void onPostExecute(TimeAndString timeAndString) {
        super.onPostExecute(timeAndString);
        //根据返回值s，通过Json解析数据结构
        try{
            JSONObject jsonObject=new JSONObject(timeAndString.getS());
            String text = jsonObject.getString("text");
            String img = jsonObject.getString("img");
            SplashData splashData =new SplashData();
            splashData.setText(text);
            splashData.setImg(img);
            //下载数据完成 通过接口回调数据
            iSplashListener.sucess(splashData,timeAndString.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


