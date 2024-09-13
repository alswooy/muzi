package com.Toy2.product.service;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductRequestDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import com.Toy2.product.domain.service.ProductService;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.option.db.dto.request.OptionRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
@Transactional
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void productServiceSelectTest() {
        ProductDto productDto = productService.selectProduct(1);
        System.out.println("productDto = " + productDto);
        assertThat(productDto.getProductNumber()).isGreaterThan(0);
    }

    @Test
    public void productServiceSelectPageTest() {
        List<ProductDto> productDtos = productService.selectProductPage(new ProductPageRequestDto(2, 10));
        System.out.println("productDtos = " + productDtos);
    }

    @Test
    @Transactional
    public void productServiceInsertTest() {
        ProductDto build = new ProductDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .deliveryFee(3000)
                .privateProduct(false)
                .productCode("")
                .notice("없음").build();
        boolean b = productService.insertProductAndOption(build);
        assertThat(b).isTrue();
    }

    @Test
    @Transactional
    public void productServiceUpdateTest() {
        productService.insertProductAndOption(new ProductDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .privateProduct(false)
                .deliveryFee(3000)
                .productCode("")
                .notice("없음").build());

        ProductUpdateRequestDto build = new ProductUpdateRequestDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productPrice(999999)
                .productName("테스트 수정 상품")
                .newItem(false)
                .discountable(true)
                .notice("없음")
                .productCode("qqqqqq")
                .deliveryFee(50000)
                .privateProduct(true)
                .build();

        boolean update = productService.updateProduct(build);
        ProductDto productDto = productService.selectProduct(Integer.MAX_VALUE);
        assertThat(productDto.getProductName()).isEqualTo("테스트 수정 상품");
        assertThat(update).isTrue();
    }

    @Test
    @Transactional
    public void productServiceManyInsertTest() {
        List<ProductDto> productDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductDto build = new ProductDto.Builder()
                    .productNumber(i + 10000)
                    .productName("테스트 상품")
                    .productPrice(10000)
                    .postingStatus(true)
                    .amount(100)
                    .discountable(true)
                    .newItem(true)
                    .viewCount(0)
                    .privateProduct(false)
                    .deliveryFee(100000)
                    .productCode("")
                    .notice("없음").build();
            productDtos.add(build);
        }
        productService.insertProductAndOption(productDtos);
        for (int i = 0; i < 10; i++) {
            ProductDto productDto = productService.selectProduct(10000 + i);
            assertThat(productDto).isNotNull();
        }
    }

    @Test
    @Transactional
    public void productServiceDeleteTest() {
        boolean delete = productService.deleteService(1);
        assertThat(delete).isTrue();
    }

    @Test
    public void productOptionSelectTest() {
        Map<String, List<ProductOptionDto>> stringProductOptionDtoMap = productService.selectProductOption(1);
        System.out.println("stringProductOptionDtoMap = " + stringProductOptionDtoMap);
    }

    @Test
    @Commit
    public void productOptionInsert() {
        productService.insertOption(new OptionRequestDto(201 , "옵션 1", List.of("옵션 세부 1", "옵션 세부 2"), true));
    }

    @Test
    @Transactional
    public void 페이지_옵션_삽입() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("옵션", List.of("1", "2", "3"));
        ProductRequestDto dto = new ProductRequestDto(
                10000, "상품 1",  true,
                true, "상품 설명", "1234", 12345, false, map);
        System.out.println("dto = " + dto);
        productService.insertProductAndOption(dto);
    }
}
