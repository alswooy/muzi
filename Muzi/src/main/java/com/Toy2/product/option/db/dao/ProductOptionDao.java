package com.Toy2.product.option.db.dao;

import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.option.db.dto.request.OptionDeleteRequestDto;
import com.Toy2.product.option.db.dto.request.OptionRequestDto;

import java.util.List;

public interface ProductOptionDao {
    int count();
    List<ProductOptionDto> selectOptions(int productNumber);

    ProductOptionDto selectOption(int optionNumber);
    boolean insert(OptionRequestDto optionRequestDto);

    boolean updateOptions(List<ProductOptionDto> optionUpdateRequestDto);


    boolean deleteOption(OptionDeleteRequestDto optionDeleteRequestDto);

    boolean deleteOptionValue(int optionNumber);
}
