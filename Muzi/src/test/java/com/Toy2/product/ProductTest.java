package com.Toy2.product;

import com.Toy2.product.db.dao.ProductDao;
import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductTest {

    @Autowired
    ProductDao productDao;

    @Before
    public void before() {

    }

    @Test
    public void productCountTest() {
        int count = productDao.count();
        System.out.println("count = " + count);
        System.out.println(count/10);
    }

    //assert문이 있어야함
    @Test
    @Transactional
    public void insertDummiesData() {
        int count = 0;

        for (int i = 0; i < 100; i++) {
            try {
                ProductDto build = new ProductDto.Builder()
                        .productNumber(i + count)
                        .productName("테스트 상품" + i)
                        .productPrice(10000)
                        .postingStatus(true)
                        .amount(100)
                        .discountable(true)
                        .newItem(true)
                        .viewCount(0)
                        .deliveryFee(30000)
                        .privateProduct(false)
                        .productCode("")
                        .notice("없음").build();
                productDao.insert(build);

            } catch (IllegalArgumentException e) {
                System.out.println("");
                count++;
                i--;
            }
        }
    }

    //데이터 꺼내서 확인하기
    @Test
    @Transactional
    public void productInsertTest() {
        ProductDto build = new ProductDto.Builder()
//                .productNumber((int) (Math.random()*100000000))
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .deliveryFee(30000)
                .privateProduct(false)
                .productCode("")
                .notice("없음").build();
        assertThat(productDao.insert(build)).isTrue();
    }

    @Test
    @Transactional
    public void 데이터_삽입_실패_테스트() {
        ProductDto build = new ProductDto.Builder()
//                .productNumber((int) (Math.random()*100000000))
                .productNumber(Integer.MAX_VALUE)
                .productName("테스트 상품")
                .productPrice(10000)
                .postingStatus(true)
                .amount(100)
                .discountable(true)
                .newItem(true)
                .viewCount(0)
                .deliveryFee(30000)
                .privateProduct(false)
                .productCode("")
                .notice("없음").build();
        productDao.insert(build);
        assertThatThrownBy(() -> productDao.insert(build))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void productReadTest() {
        ProductDto select = productDao.select(999999998);

        System.out.println("select = " + select);
    }

    @Test
    @Transactional
    public void productUpdateTest() {
        ProductUpdateRequestDto build = new ProductUpdateRequestDto.Builder()
                .productNumber(0)
                .productPrice(999999)
                .productName("테스트 수정 상품")
                .newItem(false)
                .discountable(true)
                .notice("없음")
                .deliveryFee(30000)
                .privateProduct(false)
                .productCode("qqqqqq")
                .build();
        assertThat(productDao.update(build)).isTrue();
    }

    @Test
    @Transactional
    public void 데이터_수정_실패_테스트() {
        ProductUpdateRequestDto build = new ProductUpdateRequestDto.Builder()
                .productNumber(Integer.MAX_VALUE)
                .productPrice(999999)
                .productName("테스트 수정 상품")
                .newItem(false)
                .discountable(true)
                .notice("없음")
                .productCode("qqqqqq")
                .deliveryFee(30000)
                .privateProduct(false)
                .build();
        assertThat(productDao.update(build)).isFalse();
    }

    @Test
    @Transactional
    public void productDeleteTest() {
        boolean delete = productDao.delete(0);
        System.out.println("delete = " + delete);
        assertThat(delete).isTrue();
    }

    @Test
    @Transactional
    public void 데이터_삭제_실패_테스트() {
        boolean delete = productDao.delete(Integer.MAX_VALUE);
        System.out.println("delete = " + delete);
        assertThat(delete).isFalse();
    }

    @Test
    @Transactional
    public void 데이터_비활성화() {
        boolean disable = productDao.disable(0);
        assertThat(disable).isTrue();
    }

    @Test
    public void 페이지_선택_테스트() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("limit", 10);
        hashMap.put("offset", 0);

        List<ProductDto> productDtos = productDao.selectPage(new ProductPageRequestDto(0, 10));
        assertThat(productDtos).isNotNull();
        System.out.println("productDtos = " + productDtos);
    }



}
