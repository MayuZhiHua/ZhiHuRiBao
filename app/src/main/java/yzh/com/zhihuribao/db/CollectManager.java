package yzh.com.zhihuribao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yzh.com.zhihuribao.info.CollectData;

/**
 * 收藏表的管理类
 * Created by HP on 2016/10/13.
 */
public class CollectManager {
    //表的名字和字段的常量
    private static final String TABLE_NAME = "collect";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMG_URL = "imgUrl";

    private DBHelper dbHelper;

    public CollectManager(Context context) {
        //初始化DbHelper
        dbHelper = new DBHelper(context);
    }

    //添加单collect数据
    public void insert(CollectData collectData) throws Exception{
        //打开数据库
        //如果数据库不存在，则创建并打开。否则，直接打开。
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //数据添加的操作
        ContentValues cv = new ContentValues();
        //Log.e("Tag",11111111+"");
        cv.put(KEY_ID,collectData.getId());
        //Log.e("Tag",22222222+"");
        cv.put(KEY_TITLE, collectData.getTitle());
        //Log.e("Tag",33333333+"");
        cv.put(KEY_IMG_URL,collectData.getImgUrl());
        //Log.e("Tag",44444444+"");
        db.insert(TABLE_NAME, null, cv);
        //Log.e("Tag",55555555+"");
        //关闭数据库
        db.close();
    }
    //删除单个collect数据
    public void delete(CollectData collectData) throws Exception{
        //打开数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //删除操作
        db.delete(
                TABLE_NAME,
                KEY_ID + "= ?",
                new String[]{String.valueOf(collectData.getId())}
        );
        db.close();
    }
    //修改联系人
    public void upDate(CollectData collectData) throws Exception{
        //打开数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //修改数据
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, collectData.getTitle());
        cv.put(KEY_IMG_URL,collectData.getImgUrl());
        db.update(TABLE_NAME, cv, KEY_ID + "=?", new String[]{String.valueOf(collectData.getId())});
        //关闭数据库
        db.close();
    }
    //查询所有联系人
    public List<CollectData> getAll() throws Exception{
        List<CollectData> collects = new ArrayList<>();
        //查询数据，放入集合
        //打开数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //执行查询操作
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor是游标（集），存放了你的数据的
        //先根据字段的名称，获得字段的id
        int idIndex = cursor.getColumnIndex(KEY_ID);
        int titleIndex = cursor.getColumnIndex(KEY_TITLE);
        int imgUrlIndex = cursor.getColumnIndex(KEY_IMG_URL);
        while(cursor.moveToNext()) {
            //取指定位置的数据,参数指的是字段的下标，从0开始
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String imgUrl = cursor.getString(imgUrlIndex);
            CollectData collectData=new CollectData();
            collectData.setId(id);
            collectData.setTitle(title);
            collectData.setImgUrl(imgUrl);
            collects.add(collectData);
        }
        //关闭游标
        cursor.close();
        //关闭数据库
        db.close();
        return collects;
    }

}
