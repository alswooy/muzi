package com.Toy2.product.picture.dao;

import com.Toy2.product.picture.dto.PictureUploadDto;

public interface PictureUploadDao {

    boolean insertPicture(PictureUploadDto pictureUploadDto);

    PictureUploadDto selectById(int id);
}
