package com.jobs.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jobs.service.CurrencyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForexControllerTest {
   
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void forexquery_test1() throws Exception {
		String uri = "/schedulers/forexquery";
		try {			
			MvcResult  result = mvc.perform(MockMvcRequestBuilders.post("/schedulers/forexquery")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{ \"startDate\": \"2023/03/03\", \"endDate\": \"2024/03/01\", \"currency\": \"usd\" }"))
					.andDo(MockMvcResultHandlers.print())
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andReturn();
			
			int status = result.getResponse().getStatus();
			boolean content = result.getResponse().getContentAsString().contains("日期區間不符");
			// System.out.println("content="+content);			
			// assertEquals(200, status);
			assertEquals(true, content);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	@Test
	public void forexquery_test2() throws Exception {
		String uri = "/schedulers/forexquery";
		try {			
			MvcResult  result = mvc.perform(MockMvcRequestBuilders.post("/schedulers/forexquery")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{ \"startDate\": \"2024/03/01\", \"endDate\": \"2024/03/02\", \"currency\": \"usd\" }"))
					.andDo(MockMvcResultHandlers.print())
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andReturn();
			
			int status = result.getResponse().getStatus();
			boolean content = result.getResponse().getContentAsString().contains("成功");			
			assertEquals(true, content);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
}




