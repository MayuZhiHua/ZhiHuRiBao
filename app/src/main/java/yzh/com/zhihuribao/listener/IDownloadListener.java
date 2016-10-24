package yzh.com.zhihuribao.listener;

import java.util.List;

import yzh.com.zhihuribao.info.Data;

/**
 * Created by HP on 2016/10/8.
 */
public interface IDownloadListener {
    void success(List<Data> datas,int type);
}
