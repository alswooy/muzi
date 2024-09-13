package com.Toy2.product.option.db.dto.request;

public class OptionDeleteRequestDto {
    private int productNumber;
    private String optionName;

    public OptionDeleteRequestDto() {
    }

    public OptionDeleteRequestDto(int productNumber, String optionName) {
        this.productNumber = productNumber;
        this.optionName = optionName;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public String getOptionName() {
        return optionName;
    }
}
