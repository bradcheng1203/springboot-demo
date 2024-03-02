package com.jobs.schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jobs.model.CurrencyModel;
import com.jobs.service.CurrencyService;

@Component
public class NotificationJob {
	
	@Autowired
	CurrencyService currencyService;
	
	public static String getReturn(HttpsURLConnection connection) throws IOException {
		StringBuffer buffer = new StringBuffer();
		try (InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			String result = buffer.toString();
			return result;
		}
	}
	
    public static JSONArray readJsonFromUrl(String url) throws Exception {
     	SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
		sslcontext.init(null, new TrustManager[] { new MX509TrustManager() }, new java.security.SecureRandom());
		URL serverUrl = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
		conn.setSSLSocketFactory(sslcontext.getSocketFactory());
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setInstanceFollowRedirects(false);
		conn.connect();    	       
        String jsonText = getReturn(conn);
        JSONArray json = new JSONArray(jsonText);
        return json;
	}
    
    public void updateCurrency() throws Exception {    	
    	currencyService.deleteCurrencyAll();    	
		JSONArray json = readJsonFromUrl("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");	
		for(int i=0;i<json.length();i++) {						
			JSONObject jsobj = new JSONObject(json.get(i).toString());
			CurrencyModel curr = new CurrencyModel();
	    	curr.setDate( jsobj.get("Date").toString() );
	    	curr.setUsd_ntd( jsobj.get("USD/NTD").toString() );
	    	curr.setRmb_ntd( jsobj.get("RMB/NTD").toString() );
	    	curr.setEur_usd( jsobj.get("EUR/USD").toString() );
	    	curr.setUsd_jpy( jsobj.get("USD/JPY").toString() );
	    	curr.setGbp_usd( jsobj.get("GBP/USD").toString() );
	    	curr.setAud_usd( jsobj.get("AUD/USD").toString() );
	    	curr.setUsd_hkd( jsobj.get("USD/HKD").toString() );
	    	curr.setUsd_rmb( jsobj.get("USD/RMB").toString() );
	    	curr.setUsd_zar( jsobj.get("USD/ZAR").toString() );
	    	curr.setNzd_usd( jsobj.get("NZD/USD").toString() );	    	
	    	currencyService.addCurrency(curr);
		}
		if(json.length()>0) {
			System.out.println("Total Records="+ json.length() );
		} else {
			System.out.println("Total Records=0");
		}
    }
    
    @Scheduled(cron = "${six.pm}")
    public void NotifyAt06pm(){
        System.out.println("Its 6 pm job, Start...");
        
        try {
			updateCurrency();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
	        Date date = new Date();    	
	        System.out.println("DateFormat=" + formatter.format(date) );
	        
	        System.out.println("Its 6 pm job, End...");        
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Its 6 pm job, Exception:" + e.getMessage());
		}
    }
}


