package com.spring.study.controller;

import com.spring.study.dao.CourseDao;
import com.spring.study.dao.SignDao;
import com.spring.study.dao.TimeManagerDao;
import com.spring.study.entity.*;
import com.spring.study.util.CalculateRecord;

import com.spring.study.util.CourseUtil;
import com.spring.study.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Autowired
    private TimeManagerDao timeManagerDao;

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
    public String deleteClass(Model model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
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
    public String attendStuList(Model model, HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        /* 获取当前时间是星期几，第几节课，从教师的课程数据中获取相应的课程 */
        Calendar calendar = Calendar.getInstance();
        TimeManager timeManager = timeManagerDao.findLastResult().get(0);
        Integer curWeekday = calendar.get(Calendar.DAY_OF_WEEK);  //获取星期几
        boolean flog = false;
        int i;
        List<Time> times = timeManager.getTimes();
        for (i=0;i<times.size();i++) {//测试为第几节课
            Time time = times.get(i);
            if(DateUtil.compareTime(new Date(),time.getStart_time())&&!DateUtil.compareTime(new Date(),time.getEnd_time()))
            {
                flog = true;
                break;
                /* 测试到匹配即终止循环，此时的即当前匹配到的节次 */
            }
        }
        if(flog){
            //根据周几，节次获取课程
            Course curCourse = null;
            Iterator<Course> iterator = teacher.getCourses().iterator();
            while(iterator.hasNext()){
                Course course = iterator.next();
                if (course.getDay_for_week().lastIndexOf(curWeekday+"")>=0 &&
                        course.getSection() == i){
                    System.out.println(course);
                    curCourse = course;
                }
            }
            if(curCourse != null){
                //根据课程，教师，日期获取Sign，当签到日期等于今天日期
                System.out.println(curCourse.getId());
                System.out.println(teacher.getId());
                List<Sign> signs = signDao.findAllByCourse_idAndTeacher_id(curCourse.getId(), teacher.getId());
                System.out.println(signs);
                List<Student> students = new ArrayList<>();
                Integer signStuNumber = 0;
                for (Sign s : signs) {
                    /* 一天中一节课只上一次，所以只签到一次，只需要获取当天该教师该课程的签到情况 */
                    System.out.println(s.getDate().getDate());
                    System.out.println(calendar.get(Calendar.DATE));
                    if (s.getDate().getDate() == calendar.get(Calendar.DATE)){
                        students.add(s.getStudent());
                        signStuNumber++;
                        System.out.println(signStuNumber);
                    }
                }
                model.addAttribute("courseName", curCourse.getName());
                model.addAttribute("signStuNumber", signStuNumber);
                model.addAttribute("signStuList", students);
            } else{
                model.addAttribute("courseName","您当前没有课");
            }
        } else {
            model.addAttribute("courseName","现在是下课时间");
        }
        /* 将课程名称注入到Model中，名称为：courseName */
        return "attendStuList";
    }

    /* -------------业务操作---------------- */

    @RequestMapping("/addOrUpdateCourse")
    @ResponseBody
    public String addOrUpdateCourse(Course course, Model model, @RequestParam("weekDate") String weekDate){
        System.out.println("添加课程"+course.getName());
        System.out.println(weekDate);
        course.setDay_for_week(weekDate);
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
    public String deleteCourse(@RequestParam("courseIds") String courseIds, HttpSession session){
        /* 删除id属于list的所有Course */
        List<Course> list = new ArrayList<>();
        Iterator<Course> iterator = ((Teacher)session.getAttribute("cur_teacher")).getCourses().iterator();
        while(iterator.hasNext()){
            Course course = iterator.next();
            if (courseIds.indexOf(course.getId().toString()) >= 0){
                list.add(course);
            }
        }
        System.out.println(list);
        /* 级联删除的问题，如何保证不删除关联的对象 */
        courseDao.deleteInBatch(list);
        for (Course course : list) {
            CourseUtil.deleteCourseFormSession(session, course);
        }
        return "redirect:/CourseManager/classList";
    }

    @RequestMapping("/updateCourse")
    public String updateCourse(Model model, Course course){

        /* 保存更新 */
        /* 没有更新操作，应该需要自定义 */
        courseDao.saveAndFlush(course);
        return "redirect:/CourseManager/classList";
    }
}
