package com.Toy2.product.option;

import com.Toy2.product.option.db.dao.ProductOptionDao;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.option.db.dto.request.OptionDeleteRequestDto;
import com.Toy2.product.option.db.dto.request.OptionRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ProductOptionTest {

    @Autowired
    private ProductOptionDao productOptionDao;

    @Test
    public void optionCountTest() {
        int count = productOptionDao.count();
        System.out.println("count = " + count);
    }

    @Test
    public void selectOptionsTest() {
        List<ProductOptionDto> productOptionDtos = productOptionDao.selectOptions(111);
        System.out.println(productOptionDtos);
    }

    @Test
    @Transactional
    public void insertOptionTest() {
        boolean insert = productOptionDao.insert(new OptionRequestDto(1, "옵션1", List.of("옵션 내용1", "옵션 내용2", "옵션 내용3"), true));
        System.out.println("insert = " + insert);
    }

    @Test
    @Transactional
    public void updateOptionTest() {
        List<ProductOptionDto> productOptionDtos = productOptionDao.selectOptions(1);
        ProductOptionDto productOptionDto = new ProductOptionDto(productOptionDtos.get(0).getOptionNumber(), 1, "변경", "변경2", true, true, OptionType.validate("default"));
        productOptionDtos.add(productOptionDto);
        boolean update = productOptionDao.updateOptions(productOptionDtos);
        System.out.println("update = " + update);
    }

    @Test
    @Transactional
    public void deleteOptionTest() {
        boolean b = productOptionDao.deleteOption(new OptionDeleteRequestDto(1, "옵션1"));
        System.out.println("b = " + b);
        System.out.println("productOptionDao = " + productOptionDao.selectOptions(1));
    }
    @Test
    @Transactional
    public void deleteOptionValueTest() {
        boolean b = productOptionDao.deleteOptionValue(8);
        System.out.println("b = " + b);
    }
}
