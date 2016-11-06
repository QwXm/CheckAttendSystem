package com.spring.study.controller;

import com.spring.study.dao.AdministratorDao;
import com.spring.study.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AdministratorController {

    @Autowired
    private AdministratorDao administratorDao;

    @RequestMapping(value = "admin_login", method = RequestMethod.POST)
    @ResponseBody
    public String admin_login(@RequestParam("user_name") String username,
                              @RequestParam("password") String password) {
        String status = null;
        Administrator admin = administratorDao.findByUserName(username);
        System.out.println(admin);
        if(admin!=null){
            if(admin.getPassword().equals(password)){

                status = "1";
            }else {

                status = "0";
            }
        }else{

            status = "-1";
        }
        return status;
    }
}
