package com.spring.study.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @ElementCollection(targetClass = Rules.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "rules_info",
    joinColumns = @JoinColumn(name = "rule_id",nullable = false))
    @OrderColumn(name = "list_order")
    private List<Rules> rules = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Rules> getRules() {
        return rules;
    }

    public void setRules(List<Rules> rules) {
        this.rules = rules;
    }
}
