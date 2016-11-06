package com.spring.study.dao;

import com.spring.study.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Administrator on 2016/10/24.
 */
@Repository
public interface  SignDao extends JpaRepository<Sign,Integer>{

    List<Sign> findAllByCourse_id(Integer id);

    List<Sign> findAllByCourse_idAndTeacher_id(Integer courseId, Integer teacherId);
}
