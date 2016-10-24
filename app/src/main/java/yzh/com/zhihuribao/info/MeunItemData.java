package yzh.com.zhihuribao.info;

import java.io.Serializable;

/**
 * Created by HP on 2016/10/9.
 */
public class MeunItemData implements Serializable{
    private String name;
    private String imageUrl;
    private String title;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
