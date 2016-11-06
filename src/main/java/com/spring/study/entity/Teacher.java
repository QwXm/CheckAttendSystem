package com.spring.study.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by AdministratorDao on 2016/10/21.
 */
@Table
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//ID
    private String work_num;//职工号
    private String name;//姓名
    private String user;//用户名
    private String password;//密码
    @ManyToMany(targetEntity = Course.class, fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_course",
                joinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name="course_id",referencedColumnName = "id"))
    private Set<Course> courses;
    @ManyToMany(targetEntity = Student.class, fetch = FetchType.EAGER)
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "teacher_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    private Set<Student> students;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWork_num() {
        return work_num;
    }

    public void setWork_num(String work_num) {
        this.work_num = work_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +

                ", courses=" + courses +

                '}';
    }
}
