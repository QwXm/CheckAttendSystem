package com.spring.study.dao;

import com.spring.study.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
@Repository
public interface RuleDao extends JpaRepository<Rule,Integer> {
    @Query(value = "select r from Rule r order by r.id")
    List<Rule> findLastResult();
}
