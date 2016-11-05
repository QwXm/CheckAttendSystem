package com.spring.study.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/21.
 */
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//学生ID
    private String name;//姓名
    private String studentNumber;//学号
    @ManyToMany(targetEntity = Course.class,fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
                joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id"))
    private Set<Course> courses = new HashSet<Course>();//课程
    @ManyToMany(targetEntity = Teacher.class,fetch = FetchType.EAGER)
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "id"))
    private Set<Teacher> teachers = new HashSet<Teacher>();//教师
    @OneToMany(targetEntity = Sign.class)
    private Set<Sign> signs;    //签到表
    @Transient
    private Integer record;     //该学生的平时成绩，不存放到数据库中


    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
    public Set<Sign> getSigns() {
        return signs;
    }

    public void setSigns(Set<Sign> signs) {
        this.signs = signs;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
