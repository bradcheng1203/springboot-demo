package com.jobs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
@EntityScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
public class SchedulersApplication {

	public static void main(String[] args) {
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.YEAR, -1);		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");		
		String oneYearAgo = sdf.format(calendar.getTime());
		
		SpringApplication.run(SchedulersApplication.class, args);
	}

}
