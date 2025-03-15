package com.jobs.schedulers;

import java.io.BufferedReader;
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
import javax.net.ssl.*;
import java.security.cert.X509Certificate;


@Component
public class NotificationJob {
	
	private int currencyCount = 0;
	
	@Autowired
	CurrencyService currencyService;	
	
    public static JSONArray readJsonFromUrl(String url) throws Exception {    	
    	// 建立信任所有憑證的 TrustManager
    	TrustManager[] trustAllCerts = new TrustManager[] {
    	    new X509TrustManager() {
    	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    	            return null;
    	        }
    	        public void checkClientTrusted(X509Certificate[] certs, String authType) {
    	        }
    	        public void checkServerTrusted(X509Certificate[] certs, String authType) {
    	        }
    	    }
    	};

    	// 安裝 TrustManager
    	SSLContext sc = SSLContext.getInstance("SSL");
    	sc.init(null, trustAllCerts, new java.security.SecureRandom());
    	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    	// 建立不驗證 host name 的 HostnameVerifier
    	HostnameVerifier allHostsValid = new HostnameVerifier() {
    	    public boolean verify(String hostname, SSLSession session) {
    	        return true;
    	    }
    	};

    	// 安裝 HostnameVerifier
    	HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		URL serverUrl = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setInstanceFollowRedirects(false);
		conn.connect();
		
		StringBuffer buffer = new StringBuffer();
		try (InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
		}		
        return new JSONArray(buffer.toString());
	}
    
    public void updateCurrency() throws Exception { 	
    	// currencyService.deleteCurrencyAll();
		JSONArray json = readJsonFromUrl("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");
		for(int i=0;i<json.length();i++) {
			JSONObject jsobj = new JSONObject(json.get(i).toString());
			CurrencyModel curr = new CurrencyModel();
	    	curr.setDate( jsobj.get("Date").toString() );
	    	curr.setUsdNtd( jsobj.get("USD/NTD").toString() );
	    	curr.setRmbNtd( jsobj.get("RMB/NTD").toString() );
	    	curr.setEurUsd( jsobj.get("EUR/USD").toString() );
	    	curr.setUsdJpy( jsobj.get("USD/JPY").toString() );
	    	curr.setGbpUsd( jsobj.get("GBP/USD").toString() );
	    	curr.setAudUsd( jsobj.get("AUD/USD").toString() );
	    	curr.setUsdHkd( jsobj.get("USD/HKD").toString() );
	    	curr.setUsdRmb( jsobj.get("USD/RMB").toString() );
	    	curr.setUsdZar( jsobj.get("USD/ZAR").toString() );
	    	curr.setNzdUsd( jsobj.get("NZD/USD").toString() );	    	
	    	currencyService.deleteByDate( jsobj.get("Date").toString() );	    	
	    	currencyService.addCurrency(curr);
	    }
		System.out.println("Total Records="+ json.length() );
    }
    
    @Scheduled(cron = "${six.pm}")
    public void NotifyAt06pm(){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
    	System.out.println("Its 6 pm job, Start..." + formatter.format(new Date()) );
        
        try {
        	System.out.println("updateCurrency()" );
			this.updateCurrency();	        
	        System.out.println("Its 6 pm job, End..."+ formatter.format(new Date()) );
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Its 6 pm job, Exception:" + e.getMessage());
		}
    }
    
    public int getCurrencyCount() {
    	return currencyCount;
    }
}


