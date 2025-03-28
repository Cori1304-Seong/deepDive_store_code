package com.ohgiraffers.springdatajpa.menu.model.repository;

import com.ohgiraffers.springdatajpa.menu.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    /* JPQL 사용 */
    @Query(value = "SELECT category_code, category_name, ref_category_code FROM tbl_category",
    nativeQuery = true)
    List<Category> findAllCategory();
}
