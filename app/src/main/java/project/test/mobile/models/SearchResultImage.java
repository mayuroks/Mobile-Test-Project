package project.test.mobile.models;

import java.util.ArrayList;

/**
 * Created by Mayur on 18-10-2017.
 */

public class SearchResultImage {
    String id;
    String title;
    String type;
    int width;
    int height;
    String link;
    ArrayList<SearchResultImage> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<SearchResultImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<SearchResultImage> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
