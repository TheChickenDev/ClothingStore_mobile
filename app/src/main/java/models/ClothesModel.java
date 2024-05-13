package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClothesModel implements Serializable {
    String _id;
    String name;
    String img;
    String price;

    public ClothesModel(String _id, String name, String img, String price) {
        this._id = _id;
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
