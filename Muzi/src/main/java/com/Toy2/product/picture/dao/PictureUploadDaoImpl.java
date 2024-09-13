package com.Toy2.product.picture.dao;

import com.Toy2.product.picture.dto.PictureUploadDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PictureUploadDaoImpl implements PictureUploadDao{

    private static final String nameSpace = "PictureUploadMapper.";
    @Autowired
    private SqlSession sqlSession;


    @Override
    public boolean insertPicture(PictureUploadDto pictureUploadDto) {
        return sqlSession.insert(nameSpace + "insertPicture", pictureUploadDto) != 0;
    }

    @Override
    public PictureUploadDto selectById(int id) {
        return sqlSession.selectOne(nameSpace + "selectById", id);
    }
}
