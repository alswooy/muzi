package com.Toy2.product.db.dto;

public class ProductDto {
    private final int productNumber;
    private final int productPrice;
    private final String productName;
    private final boolean newItem;
    private final boolean postingStatus;
    private final boolean discountable;
    private final int productAmount;
    private final String notice;
    private final int viewCount;
    private final String productCode;
    private final int deliveryFee;
    private final boolean privateProduct;
    private final Integer mainImage;


    /**
     *
     * @param productNumber
     * @param productPrice
     * @param productName
     * @param newItem
     * @param postingStatus
     * @param discountable
     * @param productAmount
     * @param notice
     * @param viewCount
     * @param productCode
     * <br>
     * MyBatis에서 Builder를 자동으로 찾아서 사용하지 않음<br>
     * resultType를 builder로 바꾸면 될까 싶지만 시도 해보지는 않음
     */

    public ProductDto(int productNumber, int productPrice, String productName, boolean newItem,
                      boolean postingStatus, boolean discountable, int productAmount, String notice,
                      int viewCount, String productCode, int deliveryFee, boolean privateProduct, Integer mainImage) {
        this.productNumber = productNumber;
        this.productPrice = productPrice;
        this.productName = productName;
        this.newItem = newItem;
        this.postingStatus = postingStatus;
        this.discountable = discountable;
        this.productAmount = productAmount;
        this.notice = notice;
        this.viewCount = viewCount;
        this.productCode = productCode;
        this.deliveryFee = deliveryFee;
        this.privateProduct = privateProduct;
        this.mainImage = mainImage;
    }




    public ProductDto(Builder builder) {
        this.productNumber = builder.productNumber;
        this.productName =builder. productName;
        this.productPrice =builder. productPrice;
        this.newItem =builder. newItem;
        this.postingStatus =builder. postingStatus;
        this.discountable =builder. discountable;
        this.productAmount =builder. productAmount;
        this.viewCount = builder.viewCount;
        this.notice = builder.notice;
        this.productCode = builder.productCode;
        this.deliveryFee = builder.deliveryFee;
        this.privateProduct = builder.privateProduct;
        this.mainImage = builder.mainImage;
    }



    public int getProductNumber() {
        return productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public boolean isNewItem() {
        return newItem;
    }

    public boolean isPostingStatus() {
        return postingStatus;
    }

    public boolean isDiscountable() {
        return discountable;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public int getViewCount() {
        return viewCount;
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

    public Integer getMainImage() {
        return mainImage;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", newItem=" + newItem +
                ", postingStatus=" + postingStatus +
                ", discountable=" + discountable +
                ", productAmount=" + productAmount +
                ", notice='" + notice + '\'' +
                ", viewCount=" + viewCount +
                ", productCode='" + productCode + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", privateProduct=" + privateProduct +
                '}';
    }

    public static class Builder {
        private int productNumber;
        private String productName;
        private int productPrice;
        private boolean newItem;
        private boolean postingStatus;
        private boolean discountable;
        private int productAmount;
        private int viewCount;
        private String notice;
        private String productCode;
        private int deliveryFee;
        private boolean privateProduct;
        private  Integer mainImage;

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

        public Builder postingStatus(boolean postingStatus) {
            this.postingStatus = postingStatus;
            return this;
        }

        public Builder discountable(boolean discountable) {
            this.discountable = discountable;
            return this;
        }

        public Builder amount(int productAmount) {
            this.productAmount = productAmount;
            return this;
        }

        public Builder notice(String notice) {
            this.notice = notice;
            return this;
        }

        public Builder viewCount(int viewCount) {
            this.viewCount = viewCount;
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

        public Builder mainImage(Integer mainImage) {
            this.mainImage = mainImage;
            return this;
        }


        public ProductDto build() {
            return new ProductDto(this);
        }

    }

}
