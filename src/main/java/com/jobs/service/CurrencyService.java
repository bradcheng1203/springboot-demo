package com.jobs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobs.model.CurrencyModel;
import com.jobs.repository.CurrencyRepository;

@Service
public class CurrencyService {
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	public void addCurrency(CurrencyModel currencyModel) throws Exception{
		currencyRepository.addCurrency(currencyModel);
	}
	
	public void deleteCurrencyAll() throws Exception{
		currencyRepository.deleteAll();
	}
	
	public static boolean isValidFormat(String format, String value) {
	    LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH );
	    try {
	        ldt = LocalDateTime.parse(value, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(value, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(value);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(value, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(value);
	            } catch (DateTimeParseException e2) {
	                // Debugging purposes
	                //e2.printStackTrace();
	            }
	        }
	    }
	    return false;
	}
	
	public JSONObject forexquery(String startDate,String endDate, String curr) throws Exception {
		JSONObject destJson = new JSONObject();
		JSONObject errjson = new JSONObject();
		errjson.put("code","E001");		
		
		JSONObject corrjson = new JSONObject();
		corrjson.put("code","0000");
		corrjson.put("message","成功");			
		
		if(isValidFormat("yyyy/MM/dd",startDate) && isValidFormat("yyyy/MM/dd",endDate) && curr.equalsIgnoreCase("usd")) {
			try {
				List currList = currencyRepository.currQuery(startDate.replaceAll("/", "") , endDate.replaceAll("/", "") , curr );
				destJson.put("error", corrjson );
				if(currList.size()>0) {
					JSONArray arrayJson = new JSONArray();
					for(int i=0;i< currList.size() ;i++){
						Map map = (Map) currList.get(i);
						JSONObject obj = new JSONObject();
						obj.put("date", map.get("Date"));
						obj.put("usd", map.get("USD_NTD"));
						arrayJson.put(obj);
					}
					destJson.put("currency", arrayJson );
				}
			} catch (Exception e) {
				System.out.println("Exception:"+e.getMessage());
				errjson.put("code","E003");
				errjson.put("message","Exception:"+e.getMessage().substring(0,e.getMessage().indexOf(";")));
				destJson.put("error", errjson );
			}
		}else {
			destJson.put("error", errjson );
		}
		
		System.out.println("destJson="+destJson.toString());
		return destJson ;
	}
}
