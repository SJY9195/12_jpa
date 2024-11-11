package com.ohgiraffers.chap05springdata.menu.infra;

import com.ohgiraffers.chap05springdata.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 느슨한 결합관계 구조. 따로 CategoryFind 인터페이스의 구현체인 CategoryFindImpl을 만들어서 결합을 해주는게 좋다!
@Service
public class CategoryFindImpl implements CategoryFind{

    @Autowired
    CategoryService categoryService;

    @Override
    public Integer getCategory(int code) {
        return categoryService.findByCategory(code);
    }
}
