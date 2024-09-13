package com.Toy2.product.db.dto.request;

//사용자가 수정할 수 있는 데이터

import com.Toy2.product.option.db.dto.ProductOptionDto;
import java.util.List;

/**
 * <br> 상품 이름
 * <br> 가격
 * <br> 옵션
 * <br> 내용
 * <br> 신상품 여부
 * <br> 할인 가능 여부
 * <br> 재고
 * <br> 참고 사항
 * <br> 상품 코드
 */
public class ProductUpdateRequestDto {
    public int productNumber;
    private int productPrice;
    private String productName;
    private boolean newItem;
    private boolean discountable;
    private String notice;
    private String productCode;
    private int deliveryFee;
    private boolean privateProduct;
    private List<ProductOptionDto> productOptions;

    public List<ProductOptionDto> getProductOptions() {
        return productOptions;
    }

    public ProductUpdateRequestDto() {
    }



    public ProductUpdateRequestDto(Builder builder) {
        this.productNumber = builder.productNumber;
        this.productPrice = builder.productPrice;
        this.productName = builder.productName;
        this.newItem = builder.newItem;
        this.discountable = builder.discountable;
        this.notice = builder.notice;
        this.productCode = builder.productCode;
        this.deliveryFee = builder.deliveryFee;
        this.privateProduct = builder.privateProduct;
        this.productOptions = builder.productOptions;
    }


    public int getProductNumber() {
        return productNumber;
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

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNewItem(boolean newItem) {
        this.newItem = newItem;
    }

    public void setDiscountable(boolean discountable) {
        this.discountable = discountable;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setPrivateProduct(boolean privateProduct) {
        this.privateProduct = privateProduct;
    }

    public void setProductOptions(List<ProductOptionDto> productOptions) {
        this.productOptions = productOptions;
    }

    public static class Builder {
        private int productNumber;
        private int productPrice;
        private String productName;
        private boolean newItem;
        private boolean discountable;
        private String notice;
        private String productCode;
        private int deliveryFee;
        private boolean privateProduct;
        private List<ProductOptionDto> productOptions;

        public Builder productNumber(int productNumber) {
            this.productNumber = productNumber;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productPrice(int productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public Builder newItem(boolean newItem) {
            this.newItem = newItem;
            return this;
        }

        public Builder discountable(boolean discountable) {
            this.discountable = discountable;
            return this;
        }

        public Builder notice(String notice) {
            this.notice = notice;
            return this;
        }

        public Builder productCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public Builder deliveryFee(int deliveryFee) {
            this.deliveryFee = deliveryFee;
            return this;
        }

        public Builder privateProduct(boolean privateProduct) {
            this.privateProduct = privateProduct;
            return this;
        }

        public Builder productOptions(List<ProductOptionDto> productOptions) {
            this.productOptions = productOptions;
            return this;
        }

        public ProductUpdateRequestDto build() {
            return new ProductUpdateRequestDto(this);
        }

    }

    @Override
    public String toString() {
        return "ProductUpdateRequestDto{" +
                "productNumber=" + productNumber +
                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", newItem=" + newItem +
                ", discountable=" + discountable +
                ", notice='" + notice + '\'' +
                ", productCode='" + productCode + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", privateProduct=" + privateProduct +
                ", productOptions=" + productOptions +
                '}';
    }
}
