package com.spring.study.controller;

import com.spring.study.dao.TimeManagerDao;
import com.spring.study.entity.Time;
import com.spring.study.entity.TimeManager;
import com.spring.study.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/6.
 */
@Controller
public class TimeController {

    /*自动注入dao*/
    @Autowired
    private TimeManagerDao timeManagerDao;
    @GetMapping("/timePage")
    public String timePage(Model model){
        model.addAttribute("lesson",1);
        return "timeList";
    }
    @PostMapping("/timeSet")
    public String timeSet(@RequestParam("lesson") String lesson,
                          @RequestParam("start_time")String start_time,
                          @RequestParam("end_time") String end_time,
                          @RequestParam(value = "school_time",required = false) String school_time,
                          HttpSession  session, Model model) throws ParseException {

        if(session.getAttribute("timeManager")==null){
            lesson = (Integer.parseInt(lesson)+1)+"";
            //创建时间对象
            TimeManager timeManager = new TimeManager();
            //获取开始时间并从字符创转变为时间
            Date starTime = DateUtil.changeDate(start_time);
            //获取结束时间并从字符串转变为时间
            Date endTime = DateUtil.changeDate(end_time);
            //获取开课时间从字符串转变为时间

            /*封装为时间类*/
            Time time = new Time();
            time.setStart_time(starTime);
            time.setEnd_time(endTime);


            //时间段封装到TimeMamager对象
            timeManager.getTimes().add(time);
            session.setAttribute("timeManager",timeManager);

        }else{
            TimeManager timeManager = (TimeManager) session.getAttribute("timeManager");

                lesson = String.valueOf(Integer.parseInt(lesson)+1);
                //获取开始时间并从字符创转变为时间
                Date starTime = DateUtil.changeDate(start_time);
                //获取结束时间并从字符串转变为时间
                Date endTime = DateUtil.changeDate(end_time);
                //获取开课时间从字符串转变为时间

                /*封装为时间类*/
                Time time = new Time();
                time.setStart_time(starTime);
                time.setEnd_time(endTime);

                timeManager.getTimes().add(time);
                model.addAttribute("lesson",lesson);
                if(timeManager.getTimes().size()>=8){
                    Date schoolTime = DateUtil.changeTime(school_time);
                    timeManager.setSchool_time(schoolTime);
                    timeManagerDao.save(timeManager);
                    return "index";
                }
                else
                    return "timeList";

        }

        model.addAttribute("lesson",lesson);
        return "timeList";
    }
}
