package com.spring.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 2016/11/6.
 */
@Controller
public class TimeController {
    @GetMapping("/timePage")
    public String timePage(){
        return "timeList";
    }
}
