package com.Toy2.product.db.dao;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.request.ProductRequestDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductDaoImpl implements ProductDao {
    private static final String nameSpace = "ProductMapper.";

    @Autowired
    SqlSession sqlSession;

    /**
     *
     * @return Table의 모든 행의 갯수
     */
    @Override
    public int count() {
        return sqlSession.selectOne(nameSpace + "countProduct");
    }

    /**
     * @param productDto
     * @return 변경된 행의 갯수
     * @apiNote 하나의 행을 저장
     * @see #update(ProductUpdateRequestDto)
     * @see #delete(int)
     * */
    @Override
    public boolean insert(ProductDto productDto) {
        int insert;
        try {
            insert = sqlSession.insert(nameSpace + "insertProduct", productDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("데이터 입력 실패", e);
        }
        return insert != 0;
    }

    @Override
    public boolean insert(ProductRequestDto productRequestDto) {
        int insert;
        try {
            insert = sqlSession.insert(nameSpace + "insertProduct2", productRequestDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("데이터 입력 실패", e);
        }
        return insert != 0;
    }

    /**
     * @param productNumber
     * @return 파라미터로 조회된 값
     * @throws NullPointerException 존재하지 않는 값을 조회 하려고 하는경우
     * @apiNote 하나의 행을 조회
     * */
    @Override
    public ProductDto select(int productNumber) {
        return sqlSession.selectOne(nameSpace + "selectProduct", productNumber);
    }


    @Override
    public List<ProductDto> selectPage(ProductPageRequestDto pageRequestDto) {
        return sqlSession.selectList(nameSpace + "selectProductPage", pageRequestDto);
    }


    /**
     * {@link #insert(ProductDto)} 와 유사함 <br>
     * @param productUpdateRequestDto
     * @return 변경된 행의 갯수
     * @apiNote 하나의 행을 수정
     */
    @Override
    public boolean update(ProductUpdateRequestDto productUpdateRequestDto) {
        int update = sqlSession.update(nameSpace + "updateProduct", productUpdateRequestDto);
        return update != 0;
    }

    /**
     * @param productNumber
     * @return 변경된 행의 갯수
     * @apiNote 하나의 행을 삭제
     */
    @Override
    public boolean delete(int productNumber) {
        int delete = sqlSession.delete(nameSpace + "deleteProduct", productNumber);
        return delete != 0;
    }

    /**
     * @param productNumber
     * @return 수정된 행의 갯수
     * @apiNote 데이터를 삭제하지 않고 비활성화
     * */
    @Override
    public boolean disable(int productNumber) {
        int disable = sqlSession.update(nameSpace + "disableProduct", productNumber);
        return disable != 0;
    }


}
