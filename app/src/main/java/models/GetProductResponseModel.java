package models;

import java.util.List;

public class GetProductResponseModel {
    private List<ClothModel> products;
    private String currentPage;
    private  String totalPage;
    private  String totalProduct;

    public GetProductResponseModel(List<ClothModel> products, String currentPage, String totalPage, String totalProduct) {
        this.products = products;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalProduct = totalProduct;
    }

    public List<ClothModel> getProducts() {
        return products;
    }

    public void setProducts(List<ClothModel> products) {
        this.products = products;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(String totalProduct) {
        this.totalProduct = totalProduct;
    }
}
