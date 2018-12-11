package com.farm.wcp.util;
 
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
 
public class ContextListener implements ServletContextListener{
 
	public ContextListener() {
	}
	
	 private java.util.Timer timer = null;  
	 
	/**
	 * 初始化定时器
	 * web 程序运行时候自动加载  
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		 /** 
         * 设置一个定时器 
         */  
        timer = new java.util.Timer(true);  
          
        arg0.getServletContext().log("定时器已启动"); 
  
        /** 
         * 定时器到指定的时间时,执行某个操作(如某个类,或方法) 
         */  
 
        
        //设置执行时间
        Calendar calendar =Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的1:00:00执行，
        calendar.set(year, month, day, 01, 00, 00);
        Date date = calendar.getTime();
//        System.out.println(date);
        
        int period =24*60*60 * 1000;
        
        TimerTask tt=new TimerTask() {
        	@Override
        	public void run() {
        		/**
            	 * 自己的业务代码
            	 * 
            	 * 删除farm_docfile中的数据和相应本地存储中的临时附件
            	 */
        		arg0.getServletContext().log("开始执行定时任务");
            	FarmFileManagerInter farmFileManagerInter = 
            	WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(FarmFileManagerInter.class);
            	List<FarmDocfile> list=farmFileManagerInter.getAllTempFile();
            	if(list.size()>0) {
            		Iterator<FarmDocfile> ite= list.iterator();
            		while(ite.hasNext()) {
            			FarmDocfile fdf=ite.next();
            			farmFileManagerInter.delUncheckedFile(fdf.getId());
            			arg0.getServletContext().log("删除了: "+fdf.getName());
            		}
            	}
        	}
        };
        
        
        //每天的date时刻执行task，每隔persion 时间重复执行
        //timer.schedule(new DelFileTask(arg0.getServletContext()), date, period);
        timer.schedule(tt, date, period);
//        在 指定的date时刻执行task, 仅执行一次
//        timer.schedule(new DelFileTask(arg0.getServletContext()), date);
        
        
        arg0.getServletContext().log("已经添加任务调度表");  
		
	}
 
	/**
	 * 销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0){
 
		timer.cancel();
	    arg0.getServletContext().log("定时器销毁");
	}
}