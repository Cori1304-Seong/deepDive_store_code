package com.ohgiraffers.springdatajpa.menu.model.repository;

import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
*  JpaRepository 는 <대상 엔티티 , 대상 엔티티의 식별자 타입>
* */

/*
* JpaRepository 란?
* EntityManager , Factory , Transaction 을 구현한 클래스이다.
* 따라서 우리는 이제 명시적으로 구현할 필요 없이 구현 클래스를 상속 받아
* 사용할 수 있다.
* */

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /* 우리가 커스터마이징 한 쿼리메소드는 JpaRepository 가 가지고 있지 않기
    * 때문에 추가가 되어야 한다.
    *  */

    List<Menu> findByMenuPriceGreaterThan(int menuPrice);

}
