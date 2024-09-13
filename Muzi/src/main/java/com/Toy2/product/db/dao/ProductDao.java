package com.Toy2.product.db.dao;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductRequestDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;

import java.util.List;

public interface ProductDao {

    int count();

    boolean insert(ProductDto build);
    boolean insert(ProductRequestDto build);

    ProductDto select(int productNumber);
    List<ProductDto> selectPage(ProductPageRequestDto pageRequestDto);

    boolean update(ProductUpdateRequestDto updateRequestDto);
    boolean delete(int productNumber);
    boolean disable(int productNumber);
}
