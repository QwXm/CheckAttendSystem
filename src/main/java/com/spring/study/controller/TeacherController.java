package com.spring.study.controller;

import com.spring.study.dao.TeacherDao;
import com.spring.study.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/11/5.
 */
@Controller
public class TeacherController {
    @Autowired
    private TeacherDao teacherDao;

    @ResponseBody
    @RequestMapping(value = "/teacher_login",method = RequestMethod.POST)
    public String teacher_Login(@RequestParam("username") String username,
    @RequestParam("password") String password, HttpSession session)
    {
        String status = null;
        Teacher teacher = teacherDao.findByUser(username);
        if(teacher!=null)
        {
            if(teacher.getPassword().equals(password))
            {
                status = "1";
                session.setAttribute("cur_teacher", teacher);
            }
            else
            {
                session.setAttribute("cur_teacher", null);
                status = "0";
            }
        }
        else
        {
            session.setAttribute("cur_teacher", null);
            status = "-1";
        }
        return status;
    }
    @GetMapping(value = "/teacherAddPage")
    public String TeacherAddPage(){
        return "addteacher";
    }
    @PostMapping("/add")
    @ResponseBody
    public String add(Teacher teacher){
        String status = null;
        Teacher teach = teacherDao.findByUser(teacher.)

        return status;
    }
}
