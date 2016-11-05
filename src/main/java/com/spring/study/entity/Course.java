package com.spring.study.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/21.
 */
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id
    private String name;//课程名
    @ManyToMany(targetEntity = Teacher.class)
    @JoinTable(name = "teacher_course",
                joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
                ,inverseJoinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "id"))
    private Set<Teacher> teacher = new HashSet<Teacher>();//任课教师
    @ManyToMany(targetEntity = Teacher.class, fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    @Cascade(CascadeType.ALL)
    private Set<Student> students;//上这门课的学生
    private Integer term;//学期
    private Integer start_week;//开始周次
    private Integer end_week;//结束周次
    private Integer weekDate;   //具体周几上课
    private Integer section;//节次

    public Integer getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(Integer weekDate) {
        this.weekDate = weekDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(Set<Teacher> teacher) {
        this.teacher = teacher;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getStart_week() {
        return start_week;
    }

    public void setStart_week(Integer start_week) {
        this.start_week = start_week;
    }

    public Integer getEnd_week() {
        return end_week;
    }

    public void setEnd_week(Integer end_week) {
        this.end_week = end_week;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (start_week != null ? !start_week.equals(course.start_week) : course.start_week != null) return false;
        if (end_week != null ? !end_week.equals(course.end_week) : course.end_week != null) return false;
        return section != null ? section.equals(course.section) : course.section == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (start_week != null ? start_week.hashCode() : 0);
        result = 31 * result + (end_week != null ? end_week.hashCode() : 0);
        result = 31 * result + (section != null ? section.hashCode() : 0);
        return result;
    }
}
