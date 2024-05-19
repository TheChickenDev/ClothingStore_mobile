package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClothCartModel implements Serializable {
    @SerializedName("productId")
    private String id;
    private String name;
    private String img;
    private String size;
    private String price;
    private String quantity;

    public ClothCartModel(String id, String name, String img, String size, String price, String quantity) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
