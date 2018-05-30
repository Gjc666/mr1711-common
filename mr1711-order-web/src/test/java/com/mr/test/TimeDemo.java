package com.mr.test;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Created by ydd on 2018/5/24.
 */
public class TimeDemo {

    @Test
    public void test(){
        DateTime now = new DateTime();
        System.out.println(now);
        DateTime tomorrow = now.plusDays(1);
        System.out.println(tomorrow);
    }

}
