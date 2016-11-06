package com.spring.study.entity;

import org.hibernate.annotations.Parent;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by AdministratorDao on 2016/10/23.
 */
@Embeddable
public class Time {
    @Temporal(TemporalType.TIME)
    private Date start_time;
    @Temporal(TemporalType.TIME)
    private Date end_time;
    @Parent
    private TimeManager timeManager;

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    @Override
    public String toString() {
        return "Time{" +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", timeManager=" + timeManager +
                '}';
    }
}
