package com.spring.study.entity;

import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Administrator on 2016/11/6.
 */
@Embeddable
public class Rules {
    @Column(name = "time")
    private Integer time;
    @Column(name = "value")
    private Integer values;
    @Parent
    private Rule rule;
    public Integer getTime() {
        return time;
    }
    public Rules(){}
    public Rules(Integer time,Integer values){
        this.time = time;
        this.values = values;
    }
    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getValues() {
        return values;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setValues(Integer values) {
        this.values = values;
    }
}
