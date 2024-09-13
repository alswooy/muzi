package com.Toy2.product.picture;

import com.Toy2.product.picture.dao.PictureUploadDao;
import com.Toy2.product.picture.dto.PictureUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureUploadService {
    @Autowired
    private PictureUploadDao pictureUploadDao;

    public void insert(PictureUploadDto pictureUploadDto) {
        pictureUploadDao.insertPicture(pictureUploadDto);
    }

    public String selectById(int id) {
        PictureUploadDto pictureUploadDto = pictureUploadDao.selectById(id);
        System.out.println("pictureUploadDto = " + pictureUploadDto);
        String fileData = pictureUploadDto.getFileData();
        return fileData;
    }
}
