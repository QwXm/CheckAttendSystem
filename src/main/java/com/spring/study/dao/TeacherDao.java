package com.spring.study.dao;

import com.spring.study.entity.Course;
import com.spring.study.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by Administrator on 2016/11/5.
 */
public interface TeacherDao extends JpaRepository<Teacher, Integer> {
    public Set<Course> findCoursesById(Integer id);
}
