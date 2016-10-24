package yzh.com.zhihuribao.Task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.listener.IMenuItemDataListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**
 * 主题日报中RecyclerView下载数据源的异步任务
 * Created by HP on 2016/10/10.
 */
public class MenuItemRecyclerTask extends AsyncTask<Void,Void,String>{

    private String url;
    private IMenuItemDataListener iMenuItemDataListener;
    private List<Data> datas;

    public MenuItemRecyclerTask(String url, IMenuItemDataListener iMenuItemDataListener) {
        this.url = url;
        this.iMenuItemDataListener = iMenuItemDataListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        String s = UrlToString.urlToString(url);
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("stories");
            datas=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                //Log.e("Tag",String.valueOf(jsonArray.length()));
                Data data=new Data();
                JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                String title = (String) jsonObject1.get("title");
                //Log.e("Tag","title"+title);
                int id = (int) jsonObject1.get("id");
                //Log.e("Tag","Id "+String.valueOf(id));
                boolean imagesFlag = jsonObject1.isNull("images");
                //Log.e("Tag","imagesFlag----"+String.valueOf(imagesFlag));
                if(!imagesFlag){
                    JSONArray jsonArray1= jsonObject1.getJSONArray("images");
                    String images=  jsonArray1.getString(0);
                    data.setImageUrl(images);
                    //Log.e("Tag","images url地址"+images);
                }else {
                    String images= MyConstant.IMAGES_DEFAULT;
                    data.setImageUrl(images);
                    //Log.e("Tag","images url地址"+images);
                }
                data.setId(id);
                data.setTitle(title);
                datas.add(data);
            }
            //Log.e("Tag","执行了接口回掉");
            //触发接口回掉数据
            iMenuItemDataListener.success(datas);
        }catch (Exception e){
            e.printStackTrace();
            //Log.e("Tag","出错了");
        }
    }
}
