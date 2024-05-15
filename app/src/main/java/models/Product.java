package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Array;
import java.util.List;

public class Product implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("sold")
    private String sold;
    @SerializedName("name")
    private String name;
    @SerializedName("desc")
    private String desc;
    @SerializedName("price")
    private String price;
    @SerializedName("img")
    private String img;
    private List<Thumbnail> thumbnail;
    private class Thumbnail{
        private String _id;
        private String url;
        private String path;

        public Thumbnail(String _id, String url, String path) {
            this._id = _id;
            this.url = url;
            this.path = path;
        }

        public String get_id() {
            return _id;
        }

        public String getUrl() {
            return url;
        }

        public String getPath() {
            return path;
        }
    }


    public Product(String id, String sold, String name, String desc, String price, String img, Array thumbnail) {
        this.id = id;
        this.sold = sold;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
