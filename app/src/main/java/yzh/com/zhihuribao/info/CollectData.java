package yzh.com.zhihuribao.info;

import java.io.Serializable;

/**
 * Created by HP on 2016/10/13.
 */
public class CollectData implements Serializable{
    private int id ;
    private String title;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
