package com.spring.study.controller;

import com.spring.study.dao.StudentDao;
import com.spring.study.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/4.
 */
@Controller
@RequestMapping(value = "StuManager")
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @RequestMapping("/addStuClass")
    public String addStuClass(Model model){


        return "addStuClass";
    }

    @RequestMapping("addStuToClass")
    public String addStuFroClass(){
        System.out.println("this is add student to class");
        return "";
    }
}
