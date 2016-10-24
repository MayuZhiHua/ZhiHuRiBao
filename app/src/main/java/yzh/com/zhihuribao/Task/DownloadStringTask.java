package yzh.com.zhihuribao.Task;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.listener.IDownloadListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**
 * Created by HP on 2016/10/8.
 */
public class DownloadStringTask extends AsyncTask<Void,Void,String> {
    private IDownloadListener iDownloadListener;
    private String urlStr;
    private List<Data> datas;
    //JSON的类型，是ViewPager的还是RecyclerView
    private int type;
    public DownloadStringTask(String urlStr, List<Data> datas,int type,IDownloadListener iDownloadListener) {
        this.urlStr = urlStr;
        this.datas = datas;
        this.type=type;
        this.iDownloadListener=iDownloadListener;

    }

    @Override
    protected String doInBackground(Void... params) {
        //获得JSON 字符串
        String jsonString = UrlToString.urlToString(urlStr);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //将Json字符串解析成数据源
        try{
            switch (type){
                //ViewPager的JSON解析
                case  MyConstant.PAGER_VIEW_TYPE:
                    //datas=new ArrayList<>();
                    for(int i=0;i<5;i++){
                        Data data=new Data();
                        JSONObject jsonObject=new JSONObject(s);
                        JSONArray jsonArray=jsonObject.getJSONArray("top_stories");
                        //JSONArray 中的json对象
                        JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                        String ImageUrl = (String) jsonObject1.get("image");
                        String title = (String) jsonObject1.get("title");
                        int id= (int) jsonObject1.get("id");
                        data.setId(id);
                        data.setTitle(title);
                        data.setImageUrl(ImageUrl);
                        datas.add(data);
                    }
                    iDownloadListener.success(datas,MyConstant.PAGER_VIEW_TYPE);
                    break;
                //RecyclerView的JSON解析
                case MyConstant.RECYCLER_VIEW_TYPE:
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.getJSONArray("stories");
                    //Log.e("Tag","JSONARRAY的数量"+jsonArray.length());
                    for(int i=0;i<jsonArray.length();i++){
                        Data data=new Data();
                        JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                        String title = (String) jsonObject1.get("title");
                        //Log.e("Tag","title"+title);
                        int id = (int) jsonObject1.get("id");
                        //Log.e("Tag","Id "+String.valueOf(id));
                        JSONArray jsonArray1= jsonObject1.getJSONArray("images");
                        String images=  jsonArray1.getString(0);
                        //Log.e("Tag","images url地址"+images);
                        data.setId(id);
                        data.setTitle(title);
                        data.setImageUrl(images);
                        datas.add(data);
                    }
                    //Log.e("Tag",""+datas.size());
                    iDownloadListener.success(datas,MyConstant.RECYCLER_VIEW_TYPE);
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Tag","出错了");
        }
    }

}
