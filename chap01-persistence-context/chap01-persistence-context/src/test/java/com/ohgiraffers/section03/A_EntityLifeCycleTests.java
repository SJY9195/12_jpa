package com.ohgiraffers.section03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class A_EntityLifeCycleTests {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    /*
    * 영속성 컨텍스트는 엔티티 매니저가 엔티티 객체를 저장하는 공간으로,
    * 엔티티 객체를 보관하고 정리한다.
    * 엔티티 매니저가 생성될 때 하나의 영속성 컨텍스트가 만들어진다.
    *
    * 엔티티의 생명주기
    * 비영속, 영속, 준영속
    * */

    @Test
    void 비영속_테스트(){

        Menu foundMenu = entityManager.find(Menu.class, 11);

        // 객체만 생성하면, 영속성 컨텍스트나 db와 관련없는 비영속 상태
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());

        System.out.println(newMenu);
        Assertions.assertFalse(foundMenu == newMenu);

    }

    @Test
    void 영속성_연속_조회_테스트(){
        /*
        * 엔티티 매니저가 영속성 컨텍스트에 엔티티 객체를 저장(persist) 하면
        * 영속성 컨텍스트가 엔티티 객체를 관리하게 되고 이를 영속 상태 라고 한다.
        * find(), jpql 을 사용한 조회도 영속 상태가 된다.
        * */
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 11);  // 영속화 되서 실행시키면 쿼리문 1개 밖에 안뜬다. 1개에서 찾은걸 영속화 시켰기 때문!!

        Assertions.assertTrue(foundMenu1 == foundMenu2);  // 새로운 객체가 아니라 영속성 되어서 같은 값이기 때문에 이게 true가 나온다!
        System.out.println(foundMenu1);
        System.out.println(foundMenu2);
    }

    @Test
    void 영속성_객체_추가_테스트(){

        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");

        entityManager.persist(menuToRegist); //영속화되서 DB엔 안날렸지만 commit 하면 DB에 반영될 수 있다! (cf) commit을 해줘야 DB에 추가가 된다! 이 코드는 메모리에만 저장 된 상태이다! db에 반영은 x
        Menu foundMenu = entityManager.find(Menu.class, 500);  //영속화되서 쿼리문을 날렸기 때문에 실행시키면 쿼리문을 다시 안날려진다!
        Assertions.assertTrue(menuToRegist == foundMenu);

    }

    @Test
    void 준영속_detach_테스트(){

        Menu foundMenu = entityManager.find(Menu.class, 11);
        Menu foundMenu1 = entityManager.find(Menu.class, 12);

        /*
        * 영속성 컨텍스트가 관리하던 엔티티 객체가 더 이상 관리되지 않는 상태
        * 로 전환되면, 해당 객체는 준영속 상태로 바뀐다.
        * 이는 JPA 객체의 변경 사항을 데이터베이스에 자동 반영되지 않는 상태
        *
        * Detach 메소드를 사용하면 특정 엔티티를 준영속 상태로 만들 수 있다.
        * 즉, 원하는 객체만 선택적으로 영속성 컨텍스트에서 분리할 수 있다.  ex) 주문서 : 장바구니에 담고 담아있는 상태가 준영속, 주문 확정눌렀을 때 주문들어가게 된 데이터가 영속!
        * */                                                               //잠깐 영속 전에 빼놓는 상태이다!

        entityManager.detach(foundMenu1); // 준영속화

        foundMenu.setMenuPrice(5000);
        foundMenu1.setMenuPrice(5000);

        Assertions.assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());
        entityManager.merge(foundMenu1);
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());   //11번은 이미 영속화 상태기 때문에 되나, 12번은 준영속화니까 오류가 난다(변형이x)!
    }

    @Test
    void close_테스트(){

        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        entityManager.close();  //이렇게 close 하면 오류가 뜨므로, close를 위치에 맞게 잘 써야한다!

        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);

        Assertions.assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());
        Assertions.assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());
    }

    @Test
    void 삭제_remove_테스트(){

        /*
        * remove : 엔티티를 영속성 컨텍스트에서 삭제 한다.
        * 트랜잭션을 커밋하는 순간 데이터베이스에 반영된다.
        * */
        Menu foundMenu = entityManager.find(Menu.class, 2);

        entityManager.remove(foundMenu);

        Menu refoundMenu = entityManager.find(Menu.class, 2); // 이미 영속성 상태인 것을 지우면 또 꺼내오려면 오류가 뜬다!

        Assertions.assertEquals(2, foundMenu.getMenuCode());
        Assertions.assertEquals(null, refoundMenu);

    }

    @Test
    void 병합_merge_수정_테스트(){

        Menu menuToDetach = entityManager.find(Menu.class, 3);
        entityManager.detach(menuToDetach);

        menuToDetach.setMenuName("수박죽");

        Menu refoundMenu = entityManager.find(Menu.class, 3);

        System.out.println(menuToDetach.hashCode());
        System.out.println(refoundMenu.hashCode());

        entityManager.merge(menuToDetach); //primary 키가아닌건 merge가 된다!!!   //merge 메소드 : 준영속 상태의 객체를 다시 영속 상태로 만들어, 이 객체의 변경 사항이 DB에 적용 될 수 있도록 한다!!

        Menu mergedMenu = entityManager.find(Menu.class, 3);
        Assertions.assertEquals("수박죽", mergedMenu.getMenuName());
    }

    @Test
    void 병합_merge_수정_테스트2(){

        Menu menuToDetach = entityManager.find(Menu.class, 3);
        entityManager.detach(menuToDetach);

        menuToDetach.setMenuCode(999);  //DB에 Autoincrement걸어두면 그것이 우선순위이다!!
        menuToDetach.setMenuName("수박죽");

        entityManager.merge(menuToDetach);   //merge해야 하는데 식별코드를 못찾으므로 오류가 난다! //primary키를 건들면 merge가 안된다!!

        Menu mergedMenu = entityManager.find(Menu.class, 999);
        Assertions.assertEquals("수박죽", mergedMenu.getMenuName());
    }

    @Test
    void 병합_merge_삽입_테스트(){
        Menu menuToDetach = entityManager.find(Menu.class, 3);
        entityManager.detach(menuToDetach);

        menuToDetach.setMenuCode(999);
        menuToDetach.setMenuName("수박죽");

        EntityTransaction entityTransaction = entityManager.getTransaction(); //트랜잭션하면 자동으로 식별코드(primary key) 999를 찾아준다!
        entityTransaction.begin();
        entityManager.merge(menuToDetach);
        entityTransaction.commit();

        Menu mergedMenu = entityManager.find(Menu.class, 999);
        Assertions.assertEquals("수박죽", mergedMenu.getMenuName());
    }

}
