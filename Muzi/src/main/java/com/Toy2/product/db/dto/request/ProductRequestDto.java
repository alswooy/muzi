package com.Toy2.product.db.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRequestDto {
    @JsonProperty
    private Map<String, List<String>> productOptionData = new HashMap<>();
    private int productNumber;
    private int productPrice;
    private String productName;
    private boolean newItem;
    private boolean discountable;
    private String notice;
    private String productCode;
    private int deliveryFee;
    private boolean privateProduct;

    // Getters and Setters
    public Map<String, List<String>> getProductOptionData() {
        return productOptionData;
    }
    @JsonAnySetter
    public void setMapData(String key, List<String> value) {
        productOptionData.put(key, value);
    }



    public ProductRequestDto(int productPrice, String productName, boolean newItem,
                             boolean discountable, String notice,
                             String productCode, int deliveryFee, boolean privateProduct,
                             Map<String, List<String>> productData) {
        this.productPrice = productPrice;
        this.productName = productName;
        this.newItem = newItem;
        this.discountable = discountable;
        this.notice = notice;
        this.productCode = productCode;
        this.deliveryFee = deliveryFee;
        this.privateProduct = privateProduct;
        this.productOptionData = productData;
    }

    public ProductRequestDto(Map<String, List<String>> productData, int productNumber,
                             int productPrice, String productName, boolean newItem,
                             boolean discountable, String notice,
                             String productCode, int deliveryFee, boolean privateProduct) {
        this.productOptionData = productData;
        this.productNumber = productNumber;
        this.productPrice = productPrice;
        this.productName = productName;
        this.newItem = newItem;
        this.discountable = discountable;
        this.notice = notice;
        this.productCode = productCode;
        this.deliveryFee = deliveryFee;
        this.privateProduct = privateProduct;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public ProductRequestDto() {
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isNewItem() {
        return newItem;
    }

    public boolean isDiscountable() {
        return discountable;
    }

    public String getNotice() {
        return notice;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public boolean isPrivateProduct() {
        return privateProduct;
    }


    @Override
    public String toString() {
        return "ProductRequestDto{" +
                "productOptionData=" + productOptionData +
                ", productNumber=" + productNumber +
                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", newItem=" + newItem +
                ", discountable=" + discountable +
                ", notice='" + notice + '\'' +
                ", productCode='" + productCode + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", privateProduct=" + privateProduct +
                '}';
    }
}
