package yzh.com.zhihuribao.Task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.DetailsData;
import yzh.com.zhihuribao.listener.IDetailsListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**
 * 详情页面下载Url字符串并解析的异步任务
 * Created by HP on 2016/10/9.
 */
public class DetailsTask extends AsyncTask<Void,Void,String>{
    private String url;
    private DetailsData data;
    private IDetailsListener iDetailsListener;
    private int type ;

    public DetailsTask(String url, DetailsData data, IDetailsListener iDetailsListener,int type) {
        this.url = url;
        this.data = data;
        this.iDetailsListener = iDetailsListener;
        this.type=type;
    }

    @Override
    protected String doInBackground(Void... params) {
        //获得JSON 字符串
        String jsonString = UrlToString.urlToString(url);
        //Log.e("Tag","url"+url);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //JSON解析
        try{
            JSONObject jsonObject=new JSONObject(s);
            switch (type){
                case 0:
                    //判断Json有没有images这项
                    boolean imagesFlag = jsonObject.isNull("images");
                    String title=jsonObject.getString("title");
                    String body = jsonObject.getString("body");
                    JSONArray cssArray =jsonObject.getJSONArray("css");
                    String css = cssArray.getString(0);
                    if(!imagesFlag){
                        String imageUrl =jsonObject.getString("image");
                        data.setImageUrl(imageUrl);
                    }else{
                        String images= MyConstant.IMAGES_DEFAULT;
                        data.setImageUrl(images);
                    }
                    data.setCss(css);
                    data.setTitle(title);
                    data.setBody(body);
                    iDetailsListener.success(data);
                    break;
                case 1:
                    //判断Json有没有images这项
                    boolean imagesFlag1= jsonObject.isNull("images");
                    String title1 = jsonObject.getString("title");
                    String body1 = jsonObject.getString("body");
                    JSONArray cssArray2 =jsonObject.getJSONArray("css");
                    String css2 = cssArray2.getString(0);
                    if(!imagesFlag1){
                        JSONArray jsonArray1= jsonObject.getJSONArray("images");
                        String images=  jsonArray1.getString(0);
                        data.setImageUrl(images);
                    }else{
                        String images= MyConstant.IMAGES_DEFAULT;
                        data.setImageUrl(images);
                    }
                    data.setCss(css2);
                    data.setTitle(title1);
                    data.setBody(body1);
                    iDetailsListener.success(data);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
