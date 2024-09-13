package com.Toy2.product.option.db.dto;

import com.Toy2.product.option.OptionType;

import java.beans.ConstructorProperties;

public class ProductOptionDto {
    private int optionNumber;
    private int productNumber;
    private String optionName;
    private String optionDetail;
    private boolean status;
    private boolean required;
    private String optionType;


    @ConstructorProperties({"optionNumber", "productNumber", "optionName", "optionDetail", "status", "required", "optionType"})
    public ProductOptionDto(int optionNumber, int productNumber, String optionName, String optionDetail, boolean status, boolean required, String optionType) {
        this.optionNumber = optionNumber;
        this.productNumber = productNumber;
        this.optionName = optionName;
        this.optionDetail = optionDetail;
        this.status = status;
        this.required = required;
        this.optionType = OptionType.validate(optionType);
    }

    public ProductOptionDto() {
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getOptionDetail() {
        return optionDetail;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isRequired() {
        return required;
    }

    public String getOptionType() {
        return optionType;
    }

    @Override
    public String toString() {
        return "ProductOptionDto{" +
                "optionNumber=" + optionNumber +
                ", productNumber=" + productNumber +
                ", optionName='" + optionName + '\'' +
                ", optionDetail='" + optionDetail + '\'' +
                ", status=" + status +
                ", required=" + required +
                ", optionType=" + optionType +
                '}';
    }
}
