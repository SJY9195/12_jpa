package com.ohgiraffers.chap05springdata.menu.service;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.category.repository.CategoryRepository;
import com.ohgiraffers.chap05springdata.menu.dto.MenuDTO;
import com.ohgiraffers.chap05springdata.menu.entity.Menu;
import com.ohgiraffers.chap05springdata.menu.infra.CategoryFind;
import com.ohgiraffers.chap05springdata.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired // 느슨한 결합구조를 위해 service 직접 넣지 않는다.
    private CategoryFind categoryFind;

    public List<Menu> selectAllMenu() {

        List<Menu> menuList = menuRepository.findAll();
        if (menuList.isEmpty()) {
            return null;
        }
        return menuList;
    }

    public Object insertMenu(MenuDTO menuDTO){

        // 메뉴 이름 중복 체크
        Menu menu = menuRepository.findByMenuName(menuDTO.getMenuName());

        if(!Objects.isNull(menu)){
            return menuDTO.getMenuName() + "메뉴가 존재함.";
        }

        // 가격 유효성 검사
        if(menuDTO.getMenuPrice() <= 0){
            return menuDTO.getMenuPrice() + " 가격이 잘못 입력됨";
        }

        // 카테고리 코드 검사
        Integer categoryCode = categoryFind.getCategory(menuDTO.getCategoryCode());

        if(Objects.isNull(categoryCode)){
            return menuDTO.getCategoryCode()+"는 존재하지 않습니다.";
        }

        Menu newMenu = new Menu();
        newMenu.setMenuName(menuDTO.getMenuName());
        newMenu.setMenuPrice(menuDTO.getMenuPrice());
        newMenu.setCategoryCode(menuDTO.getCategoryCode());
        newMenu.setOrderableStatus(menuDTO.getOrderableStatus());

        Menu result = menuRepository.save(newMenu);

        return result;
    }
}

//    public Category selectMenuByCode(int menuCode){
//        Menu menu = menuRepository.findByMenuCode(menuCode);
//
//        if(Objects.isNull(menu)){
//            return null;
//        }
//        return menu;
//    }


//    public Category insertMenu(String menuName) {
//
//        // 이름 중복 체크
//        Category foundCategory = menuRepository.findBymenuName(menuName);
//
//        if(!Objects.isNull(foundCategory)){
//            return null;
//        }
//
//        Category insertCategory = new Category();
//        insertCategory.setCategoryName(menuName);
//
//        Category result = menuRepository.save(insertCategory);
//        /*
//         * save() 는 jpa 에서 EntityManager 를 통해 데이터를 저장하는 메소드
//         * 기본적으로, jpa 는 트랜잭션 내에서 자동으로 커밋을 처리한다.
//         * 새로운 엔티티의 경우 : db에 저장하고 저장한 객체 반환
//         * 기존에 존재하는 엔티티의 경우 : 해당 엔티티의 정보를 업데이트하고 업데이트한 객체 반환
//         * */
//        return result;
//
//    }
//
//    public Menu updateMenu(Integer menuCode, String menuName) {
//        Menu foundCategory = menuRepository.findByMenuCode(menuCode);
//        if(Objects.isNull(foundMenu)){
//            return null;
//        }
//
//        foundCategory.setMenuName(menuName);
//        Category result = menuRepository.save(foundMenu);
//        return result;
//    }
//
//    public boolean deleteMenu(Integer menuCode) {
//
//        Menu menu = menuRepository.findById(menuCode).orElse(null); // 없을 경우를 생각해서 .orElse를 넣어줘야 한다!
//
//        if(menu == null){  //카테고리 값이 없으면 리턴을 안해준다 (void이므로)
//            return false;
//        }
//
//        menuRepository.delete(menu);
//        return true;
//    }
//
//}
