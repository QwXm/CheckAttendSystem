package com.spring.study.controller;

import com.spring.study.entity.Time;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/6.
 */
@Controller
public class TimeController {
    @GetMapping("/timePage")
    public String timePage(){
        return "timeList";
    }
    @PostMapping("/timeSet")
    public String timeSet(@RequestParam("lesson") String lesson, @RequestParam("start_time")String start_time, @RequestParam("end_time") String end_time, HttpSession session){
        System.out.println(lesson);
        System.out.println(" :"+"开始："+start_time+"结束："+end_time);
        return "index";
    }
}
