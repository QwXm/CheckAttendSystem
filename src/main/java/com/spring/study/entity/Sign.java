package com.spring.study.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/21.
 *
 * 签到表
 */
@Table
@Entity
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//Id
    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id",referencedColumnName = "id",nullable = false)
    private Student student;//学生
    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id",referencedColumnName = "id",nullable = false)
    private Teacher teacher;//教师
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id",referencedColumnName = "id",nullable = false)
    private Course course;//课程
    @Temporal(TemporalType.DATE)
    private Date date;//日期
    private Integer deduction;//扣分情况
    private String des_deduction;//扣分描述
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(Integer deduction) {
        this.deduction = deduction;
    }

    public String getDes_deduction() {
        return des_deduction;
    }

    public void setDes_deduction(String des_deduction) {
        this.des_deduction = des_deduction;
    }
}
