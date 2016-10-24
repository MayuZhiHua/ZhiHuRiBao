package yzh.com.zhihuribao.Task;
import android.os.AsyncTask;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import yzh.com.zhihuribao.adapter.RecyclerAdapter;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.listener.IRerfershListener;
import yzh.com.zhihuribao.tool.UrlToString;

/**
 *
 * Created by HP on 2016/10/9.
 */
public class RefreshTask extends AsyncTask<Void,Void,String>{
    private String urlStr;
    private List<Data> datas;
    private IRerfershListener iRerfershListener;

    public RefreshTask(String urlStr, List<Data> datas, IRerfershListener iRerfershListener) {
        this.urlStr = urlStr;
        this.datas = datas;
        this.iRerfershListener=iRerfershListener;

    }


    @Override
    protected String doInBackground(Void... params) {
        String jsonString = UrlToString.urlToString(urlStr);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //将Json字符串解析成数据源
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("stories");
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
            iRerfershListener.refersh();
            //recyclerAdapter.notifyDataSetChanged();
            //recyclerView.loadMoreComplete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
