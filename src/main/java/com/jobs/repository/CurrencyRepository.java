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
		System.out.println("add Currency Date="+curr.getDate());
	    jdbcTemplate.update("INSERT INTO CurrencyInfo(Date, USDNTD, RMBNTD, EURUSD, USDJPY,  " +
	    	" GBPUSD, AUDUSD, USDHKD, USDRMB, USDZAR, NZDUSD, CREATE_DATE ) " +
	  		" VALUES ('"+curr.getDate()+"' , '"+curr.getUsdNtd()+"' ,'"+curr.getRmbNtd()+"' ,'"+curr.getEurUsd()+"' ,'"+curr.getUsdJpy() +"'," +
	  		" '"+curr.getGbpUsd()+"' , '"+curr.getAudUsd()+"' ,'"+curr.getUsdHkd()+"' ,'"+curr.getUsdRmb()+"' ,'"+curr.getUsdZar() +"', " +
	  		" '"+curr.getNzdUsd() + "', NOW()) " );
    }
	
	public void deleteAll() throws Exception {
		System.out.println("Delete Currency All.");
		jdbcTemplate.update("Delete from CurrencyInfo" );
	}
	
	public void delete(String date) throws Exception {
		System.out.println("Delete Currency Date="+date);
		jdbcTemplate.update("Delete from CurrencyInfo where date = " + date );
	}
	
	public List currQuery(String startDate,String endDate, String curr ) throws Exception {	
		List currLst = new ArrayList();
		List args = new ArrayList();
		args.add(startDate);
		args.add(endDate);
		currLst = jdbcTemplate.queryForList("SELECT Date,USDNTD, RMBNTD, EURUSD, USDJPY,GBPUSD, AUDUSD, USDHKD, USDRMB, USDZAR, NZDUSD FROM currencyinfo Where Date >=? and Date <= ? order by Date", args.toArray() );
		return currLst;
	}
}

