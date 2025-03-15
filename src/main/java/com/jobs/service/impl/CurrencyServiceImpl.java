package com.jobs.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobs.model.CurrencyModel;
import com.jobs.repository.CurrencyRepository;
import com.jobs.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	public void addCurrency(CurrencyModel currencyModel) throws Exception{
		currencyRepository.addCurrency(currencyModel);
	}
	
	public void deleteCurrencyAll() throws Exception{
		currencyRepository.deleteAll();
	}
	
	public void deleteByDate(String date) throws Exception{
		currencyRepository.delete(date);
	}
	
	public JSONObject forexquery(String startDate,String endDate, String curr) throws Exception {
		JSONObject destJson = new JSONObject();
		JSONObject errjson = new JSONObject();
		errjson.put("code","E001");
		errjson.put("message","日期區間不符");
		
		JSONObject corrjson = new JSONObject();
		corrjson.put("code","0000");
		corrjson.put("message","成功");
		
		if(isValidFormat("yyyy/MM/dd",startDate) && isValidFormat("yyyy/MM/dd",endDate) && curr.equalsIgnoreCase("usd")) {
			String oneYearAgoDate = dateCalculator(-1,0);
			String yesterDay = dateCalculator(0,-1);
			
			if( startDate.compareTo(oneYearAgoDate) >= 0 && endDate.compareTo(startDate) >= 0 && yesterDay.compareTo(endDate)>=0 ) {
				try {
					List currList = currencyRepository.currQuery(startDate.replaceAll("/", "") , endDate.replaceAll("/", "") , curr );
					destJson.put("error", corrjson );
					if(currList.size()>0) {
						JSONArray arrayJson = new JSONArray();
						for(int i=0;i< currList.size() ;i++){
							Map map = (Map) currList.get(i);
							JSONObject obj = new JSONObject();
							obj.put("date", map.get("Date"));
							obj.put("usd", map.get("USDNTD"));
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
			} else {
				errjson.put("message","日期區間不符");
				destJson.put("error", errjson );
			}
		}else {
			destJson.put("error", errjson );
		}
		
		System.out.println("destJson="+destJson.toString());
		return destJson ;
	}
	
	private String dateCalculator(int year,int day){
		Date currentDate = new Date();	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		if(year!=0) calendar.add(Calendar.YEAR, year );
		if(day!=0) calendar.add(Calendar.DAY_OF_MONTH , day );
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");		
		String oneYearAgo = sdf.format(calendar.getTime());
		return oneYearAgo;
	}
	
	private static boolean isValidFormat(String format, String value) {
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
	            }
	        }
	    }
	    return false;
	}
}
