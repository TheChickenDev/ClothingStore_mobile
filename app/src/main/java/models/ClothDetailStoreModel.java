package models;

import com.google.gson.annotations.SerializedName;

public class ClothDetailStoreModel {
    @SerializedName("_id")
    private String id;
    private String img;
    private String name;
    private double price;
    private String size;
    private int quantity;

    public ClothDetailStoreModel(String id, String img, String name, double price, String size, int quantity) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}