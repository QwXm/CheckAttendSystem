package com.spring.study.entity;

import javax.persistence.*;
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
    @ManyToMany(targetEntity = Teacher.class,fetch = FetchType.EAGER)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    private Set<Student> students;//上这门课的学生
    private Integer term;//学期
    private Integer week;//周次
    private Integer section;//节次

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
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
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", students=" + students +
                ", term=" + term +
                ", week=" + week +
                ", section=" + section +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!name.equals(course.name)) return false;
        if (!week.equals(course.week)) return false;
        return section.equals(course.section);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + week.hashCode();
        result = 31 * result + section.hashCode();
        return result;
    }
}
