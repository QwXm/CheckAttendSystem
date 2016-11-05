package com.spring.study.dao;

import com.spring.study.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {
    Course findByName(String courseName);

    void deleteCourseIdIn(List<Integer> list);

    Course findById(Integer courseId);
}
