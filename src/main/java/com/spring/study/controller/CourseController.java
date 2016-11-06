package com.spring.study.controller;

import com.spring.study.dao.*;
import com.spring.study.entity.*;
import com.spring.study.util.CalculateRecord;

import com.spring.study.util.CourseUtil;
import com.spring.study.util.DateUtil;
import com.spring.study.util.StudentUtil;
import com.sun.javafx.sg.prism.NGShape;
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
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private StudentDao studentDao;

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
    public String editClass(HttpSession session, Model model){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator = teacher.getCourses().iterator();
        List<Course> courses = new ArrayList<>();
        while (iterator.hasNext()){
            Course course = iterator.next();
            courses.add(course);
        }
        if (courses.size()>0){
            model.addAttribute("editCourse", courses.get(0));
        }
        model.addAttribute("courses",courses);
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

    @RequestMapping("/addCourse")
    public String addCourse(Course course, Model model, @RequestParam("weekDate") String weekDate,
                            HttpSession session){
        System.out.println("添加课程"+course.getName());
        System.out.println(weekDate);
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        course.setDay_for_week(weekDate);
        course.setStudents(new HashSet<Student>());
        teacher.getCourses().add(course);
        courseDao.save(course);
        teacherDao.saveAndFlush(teacher);
        return "redirect:/CourseManager/classList";
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
        List<Student> stuList = new ArrayList<>();
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator = teacher.getCourses().iterator();

        while(iterator.hasNext()){
            Course course = iterator.next();
            if (courseIds.indexOf(course.getId().toString()) >= 0){
                list.add(course);
            }
        }
        System.out.println(list);
        for (Course c : list) {
            teacher.getCourses().remove(c);
            StudentUtil.updateStudent(c, studentDao, teacher);
        }
        teacherDao.saveAndFlush(teacher);
        for (Course course : list) {
            CourseUtil.deleteCourseFormSession(session, course);
        }
        return "redirect:/CourseManager/classList";
    }

    @RequestMapping("/updateCourse")
    public String updateCourse(Model model, Course course, HttpSession session){

        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Course oldCourse = new Course();
        Iterator<Course> iterator = teacher.getCourses().iterator();
        while (iterator.hasNext()){
            Course c = iterator.next();
            System.out.println(course.getId()+"asfkha");
            if (c.getId()==course.getId()){
                oldCourse = c;
            }
        }
        course.setStudents(oldCourse.getStudents());
        course.setTeacher(oldCourse.getTeacher());
        teacher.getCourses().remove(oldCourse);
        teacher.getCourses().add(course);
        courseDao.saveAndFlush(course);
        teacherDao.saveAndFlush(teacher);
        return "redirect:/CourseManager/classList";
    }

    @RequestMapping("/editSelectCourse")
    public String editSelectCourse(Model model, @RequestParam("courseId") Integer id,
        HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator = teacher.getCourses().iterator();
        List<Course> courses = new ArrayList<>();
        Course editCourse = new Course();
        while (iterator.hasNext()){
            Course course = iterator.next();
            if (course.getId() == id) {
                System.out.println(id);
                editCourse = course;
            }
            courses.add(course);
        }
        model.addAttribute("editCourse", editCourse);
        model.addAttribute("courses", courses);
        return "editClass";
    }
}
