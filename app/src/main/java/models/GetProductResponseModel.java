package models;

import java.util.List;

public class GetProductResponseModel {
    private List<ProductModel> products;
    private String currentPage;
    private  String totalPage;
    private  String totalProduct;

    public GetProductResponseModel(List<ProductModel> products, String currentPage, String totalPage, String totalProduct) {
        this.products = products;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalProduct = totalProduct;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
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
