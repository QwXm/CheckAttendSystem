package com.spring.study.dao;

import com.spring.study.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by Administrator on 2016/10/24.
 */
@Repository
public interface  SignDao extends JpaRepository<Sign,Integer>{

    List<Sign> findAllByCourse_id(Integer id);

    List<Sign> findAllByCourse_idAndTeacher_id(Integer courseId, Integer teacherId);
    @Query("select sign from Sign sign where sign.teacher.id=? and sign.course.id=? and sign.student.id=? and sign.date=?")
    Sign findWithMyStyle(Integer teacher_id, Integer course_id, Integer student_id, Date date);
}
