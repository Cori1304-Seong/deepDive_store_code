package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.entity.Category;
import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    public MenuDTO findMenuByCode(int menuCode) {

        /*
        * Sercvice 레이어에서는 Controller 계층에서 전달 받은
        * DTO 타입의 객체를 Entity 타입으로 변환을 할 것이다.
        * modelmapper 라이브러리 사용으로 편하게 변환
        * */

        // 기대값은 Menu -> 실제 값은 Optional<Menu>
        // 따라서 findById 메소드는 에러 핸들링을 반드시 구현하게 만들어두었다.
        Menu foundMenu = repository.findById(menuCode)
                                   .orElseThrow(IllegalAccessError::new);


        // 엔티티 타입을 조회해왔는데 실제 리턴 타입은 DTO 타입이다.
        // 따라서 이제 필요한 것이 Entity 타입을 DTO 로 변환해야 하는 과정이 필요
        // 1 번째 인자 -> 변환 대상  // 2 번째 인자 -> 대상 타입
        return modelMapper.map(foundMenu, MenuDTO.class);
    }

    public List<MenuDTO> findAllMenu() {

        List<Menu> menuList = repository.findAll();

        // 이전 findById 는 하나의 행

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    public List<MenuDTO> findByMenuPrice(int menuPrice) {

        /*
        * JpaRepository 에서 구현 된 querymethod 만 사용하는 것이 아닌
        * 우리가 커스터마이징을 해보자!!!!
        * */
        List<Menu> menuList = repository.findByMenuPriceGreaterThan(menuPrice);

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void modify(MenuDTO modifyMenu) {

        // 수정 하기 위해 찾아오기
        Menu foundMenu = repository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        // builder 패턴
        // 메뉴 이름만 수정 후 인스턴스 다시 생성 -> 다른 값들은 그대로
        foundMenu = foundMenu.toBuilder().menuName(modifyMenu.getMenuName()).build();

        // 빌드한 메뉴 객체 다시 저장
        repository.save(foundMenu);

    }
}
