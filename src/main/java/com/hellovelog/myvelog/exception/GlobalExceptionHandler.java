package com.hellovelog.myvelog.exception;

import com.hellovelog.myvelog.exception.customException.DuplicateUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    public ModelAndView handleDuplicateUserException(DuplicateUserException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "예기치 않은 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        modelAndView.setViewName("error");
        return modelAndView;
    }
}