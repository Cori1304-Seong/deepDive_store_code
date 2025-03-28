package com.ohgiraffers.springdatajpa.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /*
    * view 를 리턴하는 방식
    * 1. String
    * 2. void
    * 3. ModelView
    * */

    @GetMapping(value = {"/" , "/main"})
    public String main() {

        return "main/main";

    }

}
