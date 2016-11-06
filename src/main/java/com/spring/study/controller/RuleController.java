package com.spring.study.controller;

import com.spring.study.dao.RuleDao;
import com.spring.study.entity.Rule;
import com.spring.study.entity.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6.
 */
@Controller
public class RuleController {
    @Autowired
    private RuleDao ruleDao;
    @RequestMapping("/setRule")
    public String setRule()
    {
        return "ruleConfig";
    }
    @RequestMapping("addRule")
    public String addRule(@RequestParam("arg1")Integer agr1,
                          @RequestParam("arg2")Integer agr2,
                          @RequestParam("arg3")Integer agr3,
                          @RequestParam("arg4")Integer agr4,
                          @RequestParam("arg5")Integer agr5,
                          @RequestParam("arg6")Integer agr6,
                          @RequestParam("arg7")Integer agr7,
                          @RequestParam("arg8")Integer agr8)
    {
        Rule rule = new Rule();
        List<Rules> list = rule.getRules();
        Rules rules1 = new Rules(agr1,agr2);
        Rules rules2 = new Rules(agr3,agr4);
        Rules rules3 = new Rules(agr5,agr6);
        Rules rules4 = new Rules(agr7,agr8);
        list.add(rules1);
        list.add(rules2);
        list.add(rules3);
        list.add(rules4);
        ruleDao.save(rule);
        return "ruleConfig";
    }
}
