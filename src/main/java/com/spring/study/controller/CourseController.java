package com.spring.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/11/5.
 */
@Controller
@RequestMapping(value = "/CourseManager")
public class CourseController {

    @RequestMapping("/teacherManager")
    public String teacherManager(){
        return "teacherManager";
    }

    @RequestMapping("/classList")
    public String classList(){
        return "classList";
    }

    @RequestMapping("/editClass")
    public String editClass(){
        return "editClass";
    }

    @RequestMapping("/deleteClass")
    public String deleteClass(){
        return "deleteClass";
    }

    /* 查看当前课程学生签到情况 */
    @RequestMapping("attendStuList")
    public String attendStuList(){
        return "attendStuList";
    }

    /* -------------业务操作---------------- */


}
