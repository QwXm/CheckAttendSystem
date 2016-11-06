package com.spring.study.controller;

import com.spring.study.dao.CourseDao;
import com.spring.study.dao.StudentDao;
import com.spring.study.dao.TeacherDao;
import com.spring.study.entity.Course;
import com.spring.study.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by AdministratorDao on 2016/11/4.
 */
@Controller
@RequestMapping(value = "StuManager")
public class StudentController {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private CourseDao courseDao;

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
    public String addStuFroClass(Student student, @RequestParam("courseName") String courseName){
        System.out.println(student.getName());
        System.out.println("this is add student to class");
        Course course = courseDao.findByName(courseName);
        course.getStudents().add(student);
        courseDao.save(course);
        /*Set<Course> courses = student.getCourses();
        student.getCourses().add(course);
        student.setCourses(courses);*/

        return "teacherManager";
    }
}
