package com.jobs.controller;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.jobs.service.CurrencyService;

@RestController
@RequestMapping(value="/schedulers")
public class ForexController {

	@Autowired
	CurrencyService currencyService;
	
	//http://localhost:8080/schedulers/forexquery2
	@RequestMapping(value = "/forexquery2", method = RequestMethod.GET )
    public String forexquery2( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		return "OK";
    }
	
	// http://localhost:8080/schedulers/forexquer
	@RequestMapping(value = "/forexquery", method = RequestMethod.POST )
    public String forexquery( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		JSONObject destJson = new JSONObject();
		try {
			String requestData = request.getReader().lines().collect(Collectors.joining());
			System.out.println("requestData="+requestData);
			JSONObject json = new JSONObject(requestData);
			
			String startDate = json.get("startDate").toString().trim();
			String endDate = json.get("endDate").toString().trim();
			String currency = json.get("currency").toString().trim();
			
			destJson = currencyService.forexquery(startDate, endDate, currency );	
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject errjson = new JSONObject();
			errjson.put("code","E002");
			errjson.put("message","參數格式錯誤");
			destJson.put("error", errjson );
		}
		return destJson.toString();
    }
}


