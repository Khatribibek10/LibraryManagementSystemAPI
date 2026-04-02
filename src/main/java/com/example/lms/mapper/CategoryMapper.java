package com.example.lms.mapper;

import com.example.lms.dto.response.CategoryResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CategoryMapper {

    @Select("""
        SELECT
            id,
            name,
            description
        FROM tbl_category
    """)
    List<CategoryResponse> getAll();

    @Select("""
            SELECT 
                id,
                name,
                description
            FROM tbl_category WHERE id = #{id}
            """)
    Optional<CategoryResponse> findById(Long id);

    @Select("SELECT id, name, description FROM tbl_category WHERE id = #{id}")
    CategoryResponse getCategoryById(Long id);

//    @Insert("INSERT INTO tbl_category(name, description) VALUES(#{name}, #{description})")
//    @Options(useGeneratedKeys = true)
//    void insert(CategoryRequest categoryRequest);

//    @Insert("INSERT INTO tbl_category(name, description) VALUES(#{name}, #{description})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    void insert(Category category);


}