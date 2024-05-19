package models;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {
    String _id;
    String name;
    String email;
    String password;
    String address;
    String phone;
    String avatar;
    List<ClothCartModel> cart;

    public UserModel(String _id, String name, String email, String password, String address, String phone, String avatar, List<ClothCartModel> cart) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.cart = cart;
    }

    public String getTotalPay() {
        if (cart.isEmpty())
            return "0";
        double totalPrice = 0.0;
        for (ClothCartModel item : cart) {
            double price = Double.parseDouble(item.getPrice());
            int quantity = Integer.parseInt(item.getQuantity());
            totalPrice += price * quantity;
        }
        return String.valueOf(totalPrice);
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ClothCartModel> getCart() {
        return cart;
    }

    public void setCart(List<ClothCartModel> cart) {
        this.cart = cart;
    }
}
