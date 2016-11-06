package com.spring.study.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AdministratorDao on 2016/10/23.
 */
@Entity
@Table
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(targetClass = Integer.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "rules_info",
    joinColumns = @JoinColumn(name = "rule_id",nullable = false))
    @MapKeyColumn(name = "late_time")
    @MapKeyClass(Integer.class)
    @Column(name = "value")
    private Map<Integer,Integer> rules = new HashMap<Integer,Integer>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Integer, Integer> getRules() {
        return rules;
    }

    public void setRules(Map<Integer, Integer> rules) {
        this.rules = rules;
    }
}
