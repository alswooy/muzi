package com.Toy2.product.picture;

import com.Toy2.product.picture.dao.PictureUploadDao;
import com.Toy2.product.picture.dto.PictureUploadDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class PictureUploadTest {
    @Autowired
    PictureUploadDao pictureUploadDao;
    @Autowired
    PictureUploadService pictureUploadService;
    @Test
    public void test() {
        PictureUploadDto pictureUploadDto = pictureUploadDao.selectById(1);
        System.out.println("pictureUploadDto = " + pictureUploadDto);
    }

    @Test
    public void selectTest() {
        String bytes = pictureUploadService.selectById(3);
        System.out.println("s = " + bytes);
    }
}
