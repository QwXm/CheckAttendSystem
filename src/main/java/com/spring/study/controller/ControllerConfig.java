package com.spring.study.controller;


import com.spring.study.dao.*;
import com.spring.study.entity.*;
import com.spring.study.util.DateUtil;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by AdministratorDao on 2016/9/27.
 */
@Controller
@Configuration
@ComponentScan("com.spring.study.dao")
public class ControllerConfig {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TimeManagerDao timeManagerDao;
    @Autowired
    private RuleDao ruleDao;
    @Autowired
    private SignDao signDao;
    @Autowired
    private CourseDao courseDao;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public  String showHomePage(@CookieValue(value = "user",defaultValue = "hello")String user
                                ,Model model)
    {
        if(!user.equals("hello"))
        {
            model.addAttribute("user",user);
        }
        return "index";
    }
    @RequestMapping(value = "/student_sign",method = RequestMethod.POST)
    @ResponseBody
    public String studentSign(@RequestParam("studentNumber") String number, HttpServletResponse response)
    {
        String status = null;
        Student student = studentDao.findByStudentNumber(number);
        TimeManager timeManager = timeManagerDao.findLastResult().get(0);
        //获取当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(timeManager.getSchool_time());
        int first_week = date.get(Calendar.WEEK_OF_YEAR);//一年中开学当天是第几周
        date.setTime(new Date());
        int current_week = date.get(Calendar.WEEK_OF_YEAR);//一年中今天为第几周
        current_week = current_week - first_week + 1;//现在是开学第几周

        int current_of_week = date.get(Calendar.DAY_OF_WEEK);//获取当前周几
        //判断当前时间是否在某个上课时间段
        boolean flog = false;
        int i;
        List<Time> times = timeManager.getTimes();
        System.out.println(times);
        for (i=0;i<times.size();i++) {//测试为第几节课
            Time time = times.get(i);

            if(DateUtil.compareTime(new Date(),time.getStart_time())&&!DateUtil.compareTime(new Date(),time.getEnd_time()))
            {
                flog = true;

                break;
            }
        }
        if(flog)//如果处于上课时间段里，查看该学生此节课是否有课
        {
            Set<Course> courses = student.getCourses();
            Course current_course = null;
            for (Course course:courses ) {
                boolean IsCurrentDay = false;//如果值为true那么表示这门今天有课
                String[] weeks = course.getDay_for_week().split(",");
                for (String s:weeks) {
                    if(s.equals(current_of_week+""))
                        IsCurrentDay = true;
                }
                System.out.println("测试+"+IsCurrentDay);
                System.out.println("测试+"+course.getStart_week());
                System.out.println("测试+"+current_week);
                System.out.println("测试+"+course.getEnd_week());
                System.out.println("测试+"+course.getSection());
                System.out.println("测试+"+i);
                //如果在当前周当前节次类有匹配的课程，说明该学生此时有课
                if (IsCurrentDay&&course.getStart_week()<=current_week&&course.getEnd_week()>=current_week&&course.getSection()==(i+1))
                {
                    //匹配扣分情况。。记录后将所有有关信息保存到签到表中
                    current_course = course;
                    Rule rule = ruleDao.findLastResult().get(0);
                    List<Rules> rules = rule.getRules();//获取所有规则


                    //时间差
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    long between_time = 0;
                    try {
                        Date now_time = dateFormat.parse(dateFormat.format(new Date()));
                        Date need_time = dateFormat.parse(times.get(i).getStart_time().toString());
                        between_time = now_time.getTime() - need_time.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int betweenTime = (int)between_time/(60*1000);
                    int deduction = 0;
                    String des_deduction = "";
                    if(betweenTime>rules.get(0).getTime()&&betweenTime<rules.get(1).getTime())//迟到时间大于某个值小于某个值
                    {
                        deduction = -rules.get(0).getValues();
                        des_deduction = "迟到";
                        status = "迟到";
                    }
                    if(betweenTime>rules.get(1).getTime())//严重迟到
                    {
                        deduction = -rules.get(1).getValues();
                        des_deduction = "严重迟到";
                        status = "严重迟到";
                    }
                    //查找当前上课的老师
                    Teacher current_teacher = null;
                    Set<Teacher> teachers = student.getTeachers();
                    for (Teacher teacher:teachers) {
                       for(Course cour:teacher.getCourses())
                       {
                           if(cour.equals(current_course))
                           {
                               current_teacher = teacher;
                           }
                       }
                    }
                    //判断当前学生是否在尝试重复签到
                    Sign si = signDao.findWithMyStyle(current_teacher.getId(),current_course.getId(),student.getId(),new Date());
                    if(si!=null)
                    {
                        status="2";
                    }
                    else {
                        //添加Cookie
                        Cookie c1 = new Cookie("user",student.getStudentNumber());
                        Cookie c2 = new Cookie("teacherId",current_teacher.getId()+"");
                        Cookie c3 = new Cookie("courseId",current_course.getId()+"");
                        c1.setMaxAge(3000);
                        c2.setMaxAge(3000);
                        c3.setMaxAge(3000);
                        response.addCookie(c1);
                        response.addCookie(c2);
                        response.addCookie(c3);
                        //记录数据库
                        Sign sign = new Sign();
                        sign.setCourse(current_course);
                        sign.setDate(new Date());
                        sign.setDeduction(deduction);
                        sign.setDes_deduction(des_deduction);
                        sign.setStudent(student);
                        sign.setTeacher(current_teacher);
                        signDao.save(sign);
                        status = "1";
                    }

                }
                else//没有课程，不能签到
                {
                    System.out.println("没有课程不能签到");
                    status = "0";
                }
            }
        }
        else
        {
            status = "-1";
        }
        return status;
    }
    @ResponseBody
    @PostMapping("/student_login_out")
    public String studentLoginOut(@RequestParam("user") String user, @CookieValue("courseId")String courseId,
                                  @CookieValue("teacherId")String teacherId, HttpServletRequest request,HttpServletResponse response)
    {
        Student student = studentDao.findByStudentNumber(user);
        Course course = courseDao.findById(Integer.parseInt(courseId));
        Sign sign = signDao.findWithMyStyle(Integer.parseInt(teacherId),Integer.parseInt(courseId),student.getId(),new Date());
        Rule rule = ruleDao.findLastResult().get(0);
        List<Rules> rules = rule.getRules();//获取所有规则

        TimeManager timeManager = timeManagerDao.findLastResult().get(0);
        List<Time> times = timeManager.getTimes();
        Integer i = course.getSection();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        long between_time = 0;
        try {
            Date now_time = dateFormat.parse(dateFormat.format(new Date()));
            Date need_time = dateFormat.parse(times.get(i).getEnd_time().toString());
            between_time = need_time.getTime() - now_time.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int betweenTime = (int)between_time/(60*1000);
        if(betweenTime>0)//说明该学生还没有下课就跑了
        {
            if(betweenTime>rules.get(2).getTime()&&betweenTime<rules.get(3).getTime())
            {
                sign.setDeduction(-rules.get(2).getValues()+sign.getDeduction());
                sign.setDes_deduction(sign.getDes_deduction()+"+早退");
            }
            if(betweenTime>rules.get(3).getTime())
            {
                sign.setDeduction(-rules.get(3).getValues()+sign.getDeduction());
                sign.setDes_deduction(sign.getDes_deduction()+"+严重早退");
            }
        }
        else{//该学生操作正常
            sign.setDes_deduction(sign.getDes_deduction()+"+正常签退");
        }
        //清除Cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie c:cookies) {
            Cookie cc = new Cookie(c.getName(),null);
            System.out.println(cc.getName());
            cc.setMaxAge(0);
            cc.setPath(request.getContextPath());
            response.addCookie(cc);
        }
        signDao.saveAndFlush(sign);
        return "1";
    }
}
