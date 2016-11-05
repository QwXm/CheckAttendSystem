package com.spring.study.controller;


import com.spring.study.dao.RuleDao;
import com.spring.study.dao.SignDao;
import com.spring.study.dao.StudentDao;
import com.spring.study.dao.TimeManagerDao;
import com.spring.study.entity.*;
import com.spring.study.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/9/27.
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

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public  String showHomePage()
    {
        return "index";
    }
    @RequestMapping(value = "/student_sign",method = RequestMethod.POST)
    @ResponseBody
    public String studentSign(@RequestParam("studentNumber") String number)
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
                //如果在当前周当前节次类有匹配的课程，说明该学生此时有课
                if (IsCurrentDay&&course.getStart_week()<=current_week&&course.getEnd_week()>=current_week&&course.getSection()==(i))
                {
                    //匹配扣分情况。。记录后将所有有关信息保存到签到表中
                    current_course = course;
                    Rule rule = ruleDao.findLastResult().get(0);
                    Map<Integer,Integer> rules = rule.getRules();//获取所有规则
                    Set key = rules.keySet();
                    int[] keys = new int[4]; int j = 0;
                    Iterator iterator = key.iterator();
                    while(iterator.hasNext())
                    {
                        keys[j] = (int)iterator.next();
                        j++;
                    }
                    System.out.println("测试"+keys[1]);
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
                    if(betweenTime>keys[0]&&betweenTime<keys[1])//迟到时间大于某个值小于某个值
                    {
                        deduction = rules.get(keys[0]);
                        des_deduction = "迟到";
                        status = "迟到";
                    }
                    if(betweenTime>keys[1])//严重迟到
                    {
                        deduction = rules.get(keys[1]);
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
}
