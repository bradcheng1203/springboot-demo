package com.jobs.service;

import org.json.JSONObject;

import com.jobs.model.CurrencyModel;

public interface CurrencyService {
	
	public void addCurrency(CurrencyModel currencyModel) throws Exception;
	
	public void deleteCurrencyAll() throws Exception;	
	
	public JSONObject forexquery(String startDate,String endDate, String curr) throws Exception;

}
