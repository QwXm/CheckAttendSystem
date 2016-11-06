package com.spring.study.dao;


import com.spring.study.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public interface TeacherDao extends JpaRepository<Teacher, Integer> {
    public Teacher findByUser(String username);
    @Query("select count(*) from Teacher teacher where teacher.work_num = ?")
    Integer findCount(String work_num);
    @Query("select teacher from Teacher teacher where teacher.work_num = ? or teacher.user = ?")
    List<Teacher> findByALL(String work_num, String user);
}
