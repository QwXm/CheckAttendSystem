package com.spring.study.dao;

import com.spring.study.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/11/6.
 */
@Repository
public interface TeacherDao extends JpaRepository<Teacher, Integer> {

    @Query("select count(*) from Teacher teacher where teacher.work_num = ?")
    Integer findCount(String work_num);

    void insertTeacher(Teacher teacher);
}
