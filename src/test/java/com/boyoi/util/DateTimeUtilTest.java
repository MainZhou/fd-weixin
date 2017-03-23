package com.boyoi.util;

import com.boyoi.core.utils.DateTimeUtil;

import org.junit.Test;

import java.util.Date;

/**
 * 时间工具类的测试
 * @author Chenj
 */
public class DateTimeUtilTest {

    @SuppressWarnings("deprecation")
	@Test
    public void testGet(){
        Date date = new Date(13,1,1);
        Date date1 = new Date(13,12,26);
        Long days = DateTimeUtil.getDays(date, date1);
        Long months = DateTimeUtil.getMonths(date, date1);
        Long years = DateTimeUtil.getYears(date, date1);
        System.out.println(days);
        System.out.println(months);
        System.out.println(years);
    }

    @SuppressWarnings("deprecation")
	public static void main(String[] avgs){
        Date date = new Date();
        Date date1 = new Date(1014,1,1);
        Thread thread = new Thread(new yyyymmddRun(date));
        Thread thread2 = new Thread(new yyyymmddRun(date1));
        thread.start();
        thread2.start();
    }


    public static class yyyymmddRun implements Runnable{

       private Date date;

        public yyyymmddRun(Date date) {
            this.date = date;
        }

        @Override
        public void run() {
            Per p = new Per();

            DateTimeUtil.yyyy(p.getTime());
            System.out.println(DateTimeUtil.yyyyMMdd(date));
        }
    }

    private static class Per{
        private Date time;

        public Date getTime() {
            return time;
        }

        @SuppressWarnings("unused")
		public void setTime(Date time) {
            this.time = time;
        }
    }

}
