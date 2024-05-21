package models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductModel implements Serializable {
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

    @SerializedName("size")
    private ArrayList<String> sizes;

    @SerializedName("thumbnail")
    private List<Thumbnail> thumbnails;

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public void rearrangeThumbnails() {
        if (thumbnails.size() >= 2) {
            Thumbnail mainThumbnail = new Thumbnail();
            mainThumbnail.set_id("0");
            mainThumbnail.setUrl(getImg());

            Thumbnail secondThumbnail = thumbnails.get(0);
            thumbnails.set(0, mainThumbnail);
            thumbnails.add(1, secondThumbnail);
        }
    }

    public static class Thumbnail {
        private String _id;
        private String url;
        private String path;

        public Thumbnail(String _id, String url, String path) {
            this._id = _id;
            this.url = url;
            this.path = path;
        }

        public Thumbnail() {
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

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public ProductModel(String id, String sold, String name, String desc, String price, String img, List<Thumbnail> thumbnails, ArrayList<String> sizes) {
        this.id = id;
        this.sold = sold;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.img = img;
        this.thumbnails = thumbnails;
        this.sizes = sizes;
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

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }
}

