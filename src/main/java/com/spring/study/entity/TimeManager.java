package com.spring.study.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AdministratorDao on 2016/10/23.
 */
@Entity
@Table
public class TimeManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(targetClass = Time.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "time_info",
    joinColumns = @JoinColumn(name = "timeManager_id",nullable = false))
    @OrderColumn(name="list_order")
    private List<Time> times = new ArrayList<Time>();//用来保存每节课的上下课时间；
    @Temporal(TemporalType.DATE)
    private Date use_time;//使用时间，管理员可能会直接创建一个新的时间表，可以保留之前的时间表查看历史时间表
    @Temporal(TemporalType.DATE)
    private Date school_time;
    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public Date getUse_time() {
        return use_time;
    }

    public void setUse_time(Date use_time) {
        this.use_time = use_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSchool_time() {
        return school_time;
    }

    public void setSchool_time(Date school_time) {
        this.school_time = school_time;
    }
}
