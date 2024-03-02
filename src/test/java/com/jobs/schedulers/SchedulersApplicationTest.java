package com.jobs.schedulers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
@EntityScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
public class SchedulersApplicationTest {
	
	@Test
	void contextLoads() {
	}
}
