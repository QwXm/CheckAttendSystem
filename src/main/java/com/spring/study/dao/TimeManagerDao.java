package com.spring.study.dao;

import com.spring.study.entity.TimeManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
@Repository
public interface TimeManagerDao extends JpaRepository<TimeManager,Integer> {
    @Query("select t from TimeManager t order by t.id")
    List<TimeManager> findLastResult();
}
