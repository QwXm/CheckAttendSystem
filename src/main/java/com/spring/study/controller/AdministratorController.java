package com.spring.study.controller;

import com.spring.study.dao.AdministratorDao;
import com.spring.study.entity.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministratorController {

    @Autowired
    private AdministratorDao administratorDao;

    @RequestMapping(value = "admin_login", method = RequestMethod.POST)
    public String admin_login(@RequestParam("user_name") String user_name, @RequestParam("password") String password) {
        String status = null;
        Administrator admin = administratorDao.findAdministratorByUserName(user_name);


        return status;
    }
}
