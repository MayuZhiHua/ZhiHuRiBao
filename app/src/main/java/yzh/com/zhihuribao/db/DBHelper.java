package yzh.com.zhihuribao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库的辅助类
 * Created by HP on 2016/10/13.
 */
public class DBHelper extends SQLiteOpenHelper{
    //数据库的名字
    private static final String DB_NAME = "shoucang.db"; //必须带上.db后缀名

    //数据库的版本
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //当数据库第一次创建的时候被触发，只能被触发1次
    //一般在该方法中创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.e("Tag","前前前前");
        db.execSQL("CREATE TABLE collect (_id INTEGER PRIMARY KEY , title TEXT NOT NULL, imgUrl TEXT NOT NULL)");
        //Log.e("Tag","后后后后后后后");
    }
    //当数据库升级的时候触发
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
