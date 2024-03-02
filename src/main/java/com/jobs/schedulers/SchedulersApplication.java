package com.jobs.schedulers;

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
		SpringApplication.run(SchedulersApplication.class, args);
	}

}
