package com.Toy2.product.db.dto.response;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.option.OptionType;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;

import java.util.List;
import java.util.Map;

public class ProductResponseDto {
    private final ProductDto productDto;
    private final Map<String, List<ProductOptionDto>> options;
    private final Map<Integer, List<ProductPictureDto>> pictures;
    private static final OptionType[] optionTypes = OptionType.values();


    public ProductResponseDto(ProductDto productDto, Map<String, List<ProductOptionDto>> options, Map<Integer, List<ProductPictureDto>> pictures) {
        this.productDto = productDto;
        this.options = options;
        this.pictures = pictures;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public Map<String, List<ProductOptionDto>> getOptions() {
        return options;
    }

    public Map<Integer, List<ProductPictureDto>> getPictures() {
        return pictures;
    }

    public OptionType[] getOptionTypes() {
        return optionTypes;
    }
}
