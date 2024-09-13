package com.Toy2.product.option.db.dto.request;

public class OptionUpdateRequestDto {
    private final int productNumber;
    private final int optionNumber;
    private final String optionName;
    private final String optionDetails;
    private final boolean status;

    public OptionUpdateRequestDto(int productNumber, int optionNumber, String optionName, String optionDetails, boolean status) {
        this.productNumber = productNumber;
        this.optionNumber = optionNumber;
        this.optionName = optionName;
        this.optionDetails = optionDetails;
        this.status = status;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public String getOptionName() {
        return optionName;
    }

    public String getOptionDetails() {
        return optionDetails;
    }

    public boolean isStatus() {
        return status;
    }
}
