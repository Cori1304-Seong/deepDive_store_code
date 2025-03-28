package com.ohgiraffers.springdatajpa.menu.model.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

}
