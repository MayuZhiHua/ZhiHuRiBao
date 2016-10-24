package yzh.com.zhihuribao.Task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yzh.com.zhihuribao.info.MeunItemData;
import yzh.com.zhihuribao.listener.IMeunItemListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**
 * Created by HP on 2016/10/9.
 */
public class MenuItemTask extends AsyncTask<Void,Void,String> {
    private String url;
    private IMeunItemListener iMeunItemListener;
    private List<MeunItemData> datas;

    public MenuItemTask(String url, IMeunItemListener iMeunItemListener) {
        this.url = url;
        this.iMeunItemListener = iMeunItemListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonStr= UrlToString.urlToString(url);
        return jsonStr;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("others");
            datas=new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                MeunItemData meunItemData=new MeunItemData();
                JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                String name =jsonObject1.getString("name");
                String imageUrl = jsonObject1.getString("thumbnail");
                String title =jsonObject1.getString("description");
                int id =jsonObject1.getInt("id");
                meunItemData.setName(name);
                meunItemData.setTitle(title);
                meunItemData.setImageUrl(imageUrl);
                meunItemData.setId(id);
                datas.add(meunItemData);
            }
            iMeunItemListener.success(datas);
        }catch (Exception e){

            //Log.e("Tag","嘿嘿嘿");
            Log.e("Tag","出错了");
            e.printStackTrace();
        }
    }
}
