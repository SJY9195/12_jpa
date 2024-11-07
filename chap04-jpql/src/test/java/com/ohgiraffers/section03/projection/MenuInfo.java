package com.ohgiraffers.section03.projection;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuInfo {
      //DB설계 할 때 복합키 설정하면 name, price 조합이 중복이 되면 안된다! (DB에 복합키쓰려면 중복되면 안된다!) (unique를 써야 한다!)
    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    public MenuInfo() {
    }

    public MenuInfo(String menuName, int menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                '}';
    }

}
