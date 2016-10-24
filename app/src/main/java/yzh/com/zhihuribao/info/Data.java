package yzh.com.zhihuribao.info;

import java.io.Serializable;

/**
 * Created by HP on 2016/10/8.
 */
public class Data implements Serializable{

    private String imageUrl;
    private String title;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
