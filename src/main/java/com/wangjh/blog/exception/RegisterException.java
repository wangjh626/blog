package com.wangjh.blog.exception;

import org.springframework.web.servlet.ModelAndView;

public class RegisterException {
    private String message;
    private String path;

    public RegisterException() {
    }

    public ModelAndView error(String message, String path) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName(path);
        return modelAndView;
    }
}
