package com.spring.study.controller;

import com.spring.study.dao.StudentDao;
import com.spring.study.dao.TeacherDao;
import com.spring.study.entity.Course;
import com.spring.study.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/4.
 */
@Controller
@RequestMapping(value = "StuManager")
public class StudentController {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;

    @RequestMapping("/addStuClass")
    public String addStuClass(Model model){

        List<String> courseNames = new ArrayList<>();
        Set<Course> courses = teacherDao.findCoursesById(1);
        if (courses != null) {
            Iterator<Course> iterator = courses.iterator();
            while(iterator.hasNext()){
                courseNames.add(iterator.next().getName());
            }
        }
        model.addAttribute("courseNames", courseNames);
        return "addStuClass";
    }

    @RequestMapping("addStuToClass")
    public String addStuFroClass(Student student){
        System.out.println(student.getName());
        System.out.println("this is add student to class");
        return "teacherManager";
    }
}
