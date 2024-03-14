package com.jobs.schedulers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.jobs.service.CurrencyService;

@RunWith(SpringRunner.class)
@ComponentScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
@EntityScan({"com.jobs.schedulers","com.jobs.model","com.jobs.controller","com.jobs.repository","com.jobs.service"})
@EnableAutoConfiguration
@SpringBootTest(classes = {CurrencyService.class, NotificationJob.class})
public class NotificationJobTest {

	@Autowired
	private NotificationJob notificationJob;
	
	@Test
	public void NotifyAt06pm()throws InterruptedException{
		notificationJob.NotifyAt06pm();
		int currencyCountExpected = 22 ;
		System.out.println("CurrencyCount="+notificationJob.getCurrencyCount());
		Assert.assertEquals(notificationJob.getCurrencyCount(),currencyCountExpected);		
	}
}



