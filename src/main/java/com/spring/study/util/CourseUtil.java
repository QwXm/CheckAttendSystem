package com.spring.study.util;

import com.spring.study.entity.Course;
import com.spring.study.entity.Teacher;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/11/6.
 */
public class CourseUtil {

    /**
     * 修改session中教师的课程集合
     * @param session
     * @param course
     */
    public static void updateSessionTeacher(HttpSession session, Course course){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator = teacher.getCourses().iterator();
        Course oldCourse = null;
        while (iterator.hasNext()){
            Course temp = iterator.next();
            if(temp.getId() == course.getId()){
                oldCourse = temp;
            }
        }
        /* 在迭代中不能执行对集合的更改 */
        teacher.getCourses().remove(oldCourse);
        teacher.getCourses().add(course);
    }

    public static void deleteCourseFormSession(HttpSession session, Course course){
        Teacher teacher = (Teacher) session.getAttribute("cur_teacher");
        Iterator<Course> iterator = teacher.getCourses().iterator();
        Course oldCourse = null;
        while (iterator.hasNext()){
            Course temp = iterator.next();
            if(temp.getId() == course.getId()){
                oldCourse = temp;
            }
        }
        /* 在迭代中不能执行对集合的更改 */
        teacher.getCourses().remove(oldCourse);
    }
}
