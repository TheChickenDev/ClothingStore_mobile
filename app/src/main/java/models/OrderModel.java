package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    @SerializedName("_id")
    String id;
    String customerId;
    String orderDate;
    String deliveryDate;
    boolean isCompleted;
    List<ClothCartModel> products;
    String price;
    String shippingFee;
    String totalAmount;
    String note;
    String paymentMethod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<ClothCartModel> getProducts() {
        return products;
    }

    public void setProducts(List<ClothCartModel> products) {
        this.products = products;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderModel(String id, String customerId, String orderDate, String deliveryDate, boolean isCompleted, List<ClothCartModel> products, String price, String shippingFee, String totalAmount, String note, String paymentMethod) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.isCompleted = isCompleted;
        this.products = products;
        this.price = price;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.note = note;
        this.paymentMethod = paymentMethod;
    }
}
