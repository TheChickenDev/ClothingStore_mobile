package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClothesModel implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")

    private String name;
    @SerializedName("img")

    private String img;
    @SerializedName("price")

    private String price;

    public ClothesModel() {
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getImg() {return img;}

    public void setImg(String img) {this.img = img;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}
}
