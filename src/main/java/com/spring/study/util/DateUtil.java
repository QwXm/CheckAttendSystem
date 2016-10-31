package com.spring.study.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/30.
 */
public class DateUtil {
    public static boolean compareTime(Date current_data,Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String temp1 = simpleDateFormat.format(current_data);
        String temp2 = simpleDateFormat.format(date);
        String[] temp3 = temp1.split(":");
        String[] temp4 = temp2.split(":");
        if(Integer.parseInt(temp3[0])>=Integer.parseInt(temp4[0]))
        {
           if(Integer.parseInt(temp3[0])==Integer.parseInt(temp4[0]))
           {
               if(Integer.parseInt(temp3[1])>=Integer.parseInt(temp4[1]))
               {
                   return true;
               }
           }
           else
           {
               return true;
           }

        }
        return false;
    }
}
