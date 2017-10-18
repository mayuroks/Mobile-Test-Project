package project.test.mobile.models;

import java.util.ArrayList;

/**
 * Created by Mayur on 18-10-2017.
 */

public class ImgurAPIResponse<T> {
    ArrayList<T> data;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
