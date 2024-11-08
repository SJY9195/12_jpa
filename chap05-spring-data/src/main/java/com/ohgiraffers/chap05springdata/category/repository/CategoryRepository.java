package com.ohgiraffers.chap05springdata.category.repository;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> { // <카테고리 엔티티를 관리 , id(Integer)를 기준으로 작업>

    Category findByCategoryCode(int categoryCode); //findBy 다음에 올 이름이 WHERE문에 찾아서 jpa가 추가해준다!

    Category findByCategoryName(String categoryName);
}
