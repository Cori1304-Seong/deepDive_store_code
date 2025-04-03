package com.ohgiraffers.springdatajpa.menu.controller;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionControllerAdvice {
//        @ExceptionHandler(ResponseStatusException()) // 모든 예외 처리
//        public ModelAndView handleException(Exception ex) {
//            ModelAndView mav = new ModelAndView("exception/default_exception"); // error.html 템플릿 사용
//            mav.addObject("errorMessage", ex.getMessage()); // 에러 메시지 전달
//            return mav;
//        }


    // 404 Not Found 에러 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex) {

        return "/exception/default_exception";

    }

}
