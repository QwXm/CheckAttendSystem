package com.spring.study.util;

import com.spring.study.dao.StudentDao;
import com.spring.study.entity.Course;
import com.spring.study.entity.Student;
import com.spring.study.entity.Teacher;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/7.
 */
public class StudentUtil {
    /**
     * 教师删除自己的课程时，同步删除上该教师的该课程的学生与该课程的关联关系
     * @param course
     * @param studentDao
     * @param teacher
     */
    public static void updateStudent(Course course, StudentDao studentDao,
        Teacher teacher){
        Set<Student> students = teacher.getStudents();
        for (Student student : students) {
            student.getCourses().remove(course);
            studentDao.saveAndFlush(student);
        }
    }
}
