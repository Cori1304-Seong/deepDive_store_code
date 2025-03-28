package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
// 필드에 final 붙은 객체를 자동으로 생성자 주입을 해준다.
@RequiredArgsConstructor // DI 의존성 주입
@Tag(name = "MenuController <@Tag.name>", description = "Menu와 관련된 view를 반환한다. <@Tag.description>")
public class MenuController {

    private final MenuService menuService;


@Operation(summary = "findMenuByCode(), id로 menu 조회 <@Operation.summary>", description = "int menuCode로 조회한 값을 Model에 집어 넣고 view로 이동 <@Operation.description>" )
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "메뉴 조회 성공", content = @Content(mediaType = "text/html")),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(mediaType = "text/html"))
        })
@GetMapping("/{menuCode}")
    public String findMenuByCode(
//        @Parameter(name = "id", description = "Menu의 id", required = true)
        @Schema(description = "Path Value", example = "1")
        @PathVariable int menuCode,

            Model model) {

        MenuDTO foundMenu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", foundMenu);

        return "menu/detail";
    }

    // 메뉴 전체조회를 실행하는 핸들러 메소드
    @GetMapping("/list")
    public String findMenuList(Model model) {

        List<MenuDTO> menuList = menuService.findAllMenu();

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethodSubPage() {}

    @Operation(summary = "findByMenuPrice(), 가격으로 메뉴 조회 <@Operation.summary>", description = "int menuPrice로 조회된 값을 Model에 집어 넣고 view로 이동 <@Operation.description>" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 조회 성공", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "204", description = "조건에 맞는 메뉴 없음", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(mediaType = "text/html"))
    })
    @GetMapping("/search")
    public String findByMenuPrice(
        @Parameter(name = "price", description = "Menu의 price", required = true)
            @RequestParam int menuPrice,
            Model model) {
        System.out.println("menuPrice = " + menuPrice);

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList" , menuList);
        model.addAttribute("menuPrice", menuPrice);

        return "menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPage() {}

    // 페이지를 return 하는 메소드가 아닌
    // data 만 리턴 하는 메소드로 만들 것이다.
    // Rest-API
    @Operation(summary = "모든 Category 반환 <@Operation.summary>", description = "음식 등록에 사용되는 모든 category 종류가 반환된다. regist.html에서 사용됨" )
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "category 조회 성공", content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {
        return menuService.findAllCategory();
    }

    @GetMapping("/modify")
    public void modifyPage() {}

    @PostMapping("/modify")
    public String modifyMenu(@ModelAttribute MenuDTO modifyMenu) {
        menuService.modify(modifyMenu);

        return "redirect:/menu/" + modifyMenu.getMenuCode();
    }

}
