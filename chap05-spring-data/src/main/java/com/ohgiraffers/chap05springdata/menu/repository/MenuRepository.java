package com.ohgiraffers.chap05springdata.menu.repository;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Category save(Category insertCategory);

    Menu findByMenuName(String menuName);

    Menu findByMenuCode(int menuCode);

    List<Menu> findAll();
}
