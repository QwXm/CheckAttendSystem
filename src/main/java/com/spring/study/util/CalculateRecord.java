package com.spring.study.util;

import com.spring.study.entity.Sign;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class CalculateRecord {

    public static Integer calculateRecord(List<Sign> signs, Integer studentId){

        Integer record = new Integer(0);
        for (Sign sign : signs) {
            if(sign.getStudent().getId() == studentId){
                record += sign.getDeduction();
            }
        }
        return record+100;
    }
}
