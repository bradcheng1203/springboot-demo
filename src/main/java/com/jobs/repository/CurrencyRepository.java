package com.jobs.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jobs.model.CurrencyModel;

@Repository
public class CurrencyRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addCurrency(CurrencyModel curr ) throws Exception {
		// System.out.println("EXCUTE INSERT Currency");
	    jdbcTemplate.update("INSERT INTO CurrencyInfo( Date,USD_NTD, RMB_NTD, EUR_USD, USD_JPY,  " +
	    	" GBP_USD, AUD_USD, USD_HKD, USD_RMB, USD_ZAR, " +
	        " NZD_USD, CREATE_DATE ) " +
	  		" VALUES ('"+curr.getDate()+"' , '"+curr.getUsd_ntd()+"' ,'"+curr.getRmb_ntd()+"' ,'"+curr.getEur_usd()+"' ,'"+curr.getUsd_jpy() +"'," +
	  		" '"+curr.getGbp_usd()+"' , '"+curr.getAud_usd()+"' ,'"+curr.getUsd_hkd()+"' ,'"+curr.getUsd_rmb()+"' ,'"+curr.getUsd_zar() +"', " +
	  		" '"+curr.getNzd_usd() + "', NOW()) " );
    }
	
	public void deleteAll() throws Exception {
		System.out.println("Delete Currency All.");
		jdbcTemplate.update("Delete from CurrencyInfo" );
	}	
	
	public List currQuery(String startDate,String endDate, String curr ) throws Exception {	
		List currLst = new ArrayList();
		List args = new ArrayList();
		args.add(startDate);
		args.add(endDate);
		currLst = jdbcTemplate.queryForList("SELECT Date,USD_NTD, RMB_NTD, EUR_USD, USD_JPY,GBP_USD, AUD_USD, USD_HKD, USD_RMB, USD_ZAR, NZD_USD FROM currencyinfo Where Date >=? and Date <= ? ", args.toArray() );
		return currLst;
	}
}

