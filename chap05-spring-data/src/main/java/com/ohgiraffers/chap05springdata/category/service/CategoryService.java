package com.ohgiraffers.chap05springdata.category.service;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> selectAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            return null;
        }

        return categoryList;
    }


    public Category selectCategoryByCode(Integer categoryCode){
        Category category = categoryRepository.findByCategoryCode(categoryCode);

        if(Objects.isNull(category)){
            return null;
        }
        return category;
    }


    public Category insertCategory(String categoryName) {

        // 이름 중복 체크
        Category foundCategory = categoryRepository.findByCategoryName(categoryName);

        if(!Objects.isNull(foundCategory)){
            return null;
        }

        Category insertCategory = new Category();
        insertCategory.setCategoryName(categoryName);

        Category result = categoryRepository.save(insertCategory);
        /*
        * save() 는 jpa 에서 EntityManager 를 통해 데이터를 저장하는 메소드
        * 기본적으로, jpa 는 트랜잭션 내에서 자동으로 커밋을 처리한다.
        * 새로운 엔티티의 경우 : db에 저장하고 저장한 객체 반환
        * 기존에 존재하는 엔티티의 경우 : 해당 엔티티의 정보를 업데이트하고 업데이트한 객체 반환
        * */
        return result;

    }

    public Category updateCategory(Integer categoryCode, String categoryName) {
        Category foundCategory = categoryRepository.findByCategoryCode(categoryCode);
        if(Objects.isNull(foundCategory)){
            return null;
        }

        foundCategory.setCategoryName(categoryName);
        Category result = categoryRepository.save(foundCategory);
        return result;
    }

    public boolean deleteCategory(Integer categoryCode) {

        Category category = categoryRepository.findById(categoryCode).orElse(null); // 없을 경우를 생각해서 .orElse를 넣어줘야 한다!

        if(category == null){  //카테고리 값이 없으면 리턴을 안해준다 (void이므로)
            return false;
        }

        categoryRepository.delete(category);
        return true;
    }


    public Integer findByCategory(int code) {

        Category category = categoryRepository.findByCategoryCode(code);

        if(Objects.isNull(category)){
            return null;
        }
        return category.getCategoryCode();

    }
}
