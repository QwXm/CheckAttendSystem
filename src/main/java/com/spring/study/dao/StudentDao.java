package com.spring.study.dao;

import com.spring.study.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/10/23.
 */
@Repository
public interface StudentDao extends JpaRepository<Student,Integer>{
    Student findByStudentNumber(String number);
}
