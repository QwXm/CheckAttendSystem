package com.spring.study.controller;

import com.spring.study.dao.CourseDao;
import com.spring.study.dao.SignDao;
import com.spring.study.entity.Course;
import com.spring.study.entity.Sign;
import com.spring.study.entity.Student;
import com.spring.study.entity.Teacher;
import com.spring.study.util.CalculateRecord;
import net.sf.json.JSONArray;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/5.
 */
@Controller
@RequestMapping(value = "/CourseManager")
public class CourseController {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private SignDao signDao;


    @RequestMapping("/teacherManager")
    public String teacherManager(){
        //添加学生
        return "teacherManager";
    }

    @RequestMapping("/classList")
    public String classList(Model model, HttpSession session){
        //显示所有课程及显示某一条课程的所有学生
        List<Course> courses = new ArrayList<>();  //获取当前登录教师的所有课程
        List<Student> students = new ArrayList<>();//初始化状态下显示该教师的第一门课程中的所有学生
        List<Sign> signs = new ArrayList<>();      //获取所有的扣分记录

        /* 获取当前登录的教师 */
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator1 = null;
        if(teacher != null){
            iterator1 = teacher.getCourses().iterator();
        }
        while(iterator1.hasNext()){
            courses.add(iterator1.next());
        }
        /* 为方便存取，在此从数据库中获取一次后，添加到session中，留作后用 */
        session.setAttribute("courses", courses);
        if (courses.get(0) != null){
            signs = signDao.findAllByCourse_id(courses.get(0).getId());
            Iterator<Student> iterator = courses.get(0).getStudents().iterator();
            while(iterator.hasNext()){
                Student student = iterator.next();
                /* 为学生设置平时成绩，待优化...时间复杂度高 */
                student.setRecord(CalculateRecord.calculateRecord(signs, student.getId()));
                students.add(student);
            }
        }

        model.addAttribute("courses", courses);
        model.addAttribute("students",students);
        return "classList";
    }

    @RequestMapping("/editClass")
    public String editClass(){
        return "editClass";
    }

    @RequestMapping("/deleteClass")
    public String deleteClass(Model model){
        Teacher teacher = new Teacher();
        Iterator<Course> iterator = teacher.getCourses().iterator();
        List<Course> courses = new ArrayList<>();
        while (iterator.hasNext()){
            courses.add(iterator.next());
        }
        model.addAttribute("courses", courses);
        return "deleteClass";
    }

    /* 查看当前课程学生签到情况 */
    @RequestMapping("attendStuList")
    public String attendStuList(){
        return "attendStuList";
    }

    /* -------------业务操作---------------- */

    @RequestMapping("/addOrUpdateCourse")
    @ResponseBody
    public String addOrUpdateCourse(Course course, Model model){
        System.out.println("添加课程"+course.getName());
        if(courseDao.save(course) != null){
            return "添加课程成功！！！";
        } else {
            return "添加课程失败，请重新添加...";
        }
    }

    @RequestMapping("/showStuListByClass")
    public String showStuListByCourse(@RequestParam("courseId") Integer courseId, Model model,
         HttpSession session){

        //显示所有课程及显示某一条课程的所有学生
        List<Student> students = new ArrayList<>();//初始化状态下显示该教师的第一门课程中的所有学生
        List<Sign> signs = new ArrayList<>();      //获取所有的扣分记录
        List<Course> courses = (List<Course>) session.getAttribute("courses");

        if (courseId != null){
            signs = signDao.findAllByCourse_id(courseId);
            /* 从session中获取当前登录 教师的课程集合，在classList方法中封装的session */
            Course cur_course = new Course();
            for (Course course : courses) {
                if (course.getId() == courseId){
                    cur_course = course;
                    break;
                }
            }
            Iterator<Student> iterator = cur_course.getStudents().iterator();
            while(iterator.hasNext()){
                Student student = iterator.next();
                /* 为学生设置平时成绩，待优化...时间复杂度高 */
                student.setRecord(CalculateRecord.calculateRecord(signs, student.getId()));
                students.add(student);
            }
        }
        /* 向前台界面设计值 */
        model.addAttribute("courses", courses);
        model.addAttribute("students",students);
        return "classList";
    }

    @RequestMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseIds") List<Integer> list){
        /* 删除id属于list的所有Course */
        courseDao.deleteCourseIdIn(list);
        return "forword:/CourseManager/deleteClass";
    }

    @RequestMapping("/updateCourse")
    public String updateCourse(Model model, Course course){

        /* 保存更新 */
        /* 没有更新操作，应该需要自定义 */
        courseDao.save(course);
        return "forword:/CourseManager/classList";
    }

    @RequestMapping("/editCourseById")
    @ResponseBody
    public String editCourseById(@RequestParam("courseId") Integer courseId,
                                 HttpSession session){

        List<Course> courses = (List<Course>) session.getAttribute("courses");
        Course cur_course = new Course();
        for (Course course : courses) {
            if (course.getId() == courseId){
                cur_course = course;
                break;
            }
        }
        List<Course> list = new ArrayList<>();
        list.add(cur_course);
        JSONArray jsonArray = JSONArray.fromObject(list);    //将List集合转化为JSON对象
        return "jsonArray";
    }
}
