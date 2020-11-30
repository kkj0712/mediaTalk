package com.example.mediatalk.config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class VerifyRecaptcha {
	
	 	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	    private static final String USER_AGENT = "Mozilla/5.0";
	    private static String secret = "6LdtH-IZAAAAAExI9utYYO98qeVYtaKlHbBnm6fd";
	 
	    public static void setSecretKey(String key){
	        secret = key;
	    }
	    
	    public static int verify(String gRecaptchaResponse) throws IOException {
	        int flag=0; //실패
	        
	    	if(gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
	    		flag=0; //실패
	        }
	        
	        try {
	        URL obj = new URL(url);
	        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	        String postParams = "secret=" + secret + "&response="
	        		+ gRecaptchaResponse;
	 
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER_AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        con.setDoOutput(true);

	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(postParams);
	        wr.flush();
	        wr.close();
	 
	        System.out.println(wr);
	        
	        // 결과코드 (200이면 성공)
	        int responseCode = con.getResponseCode();
	        StringBuffer responseBody=new StringBuffer();
		        if(responseCode==200) {
		        	BufferedInputStream bis=new BufferedInputStream(con.getInputStream());
		        	BufferedReader reader=new BufferedReader(new InputStreamReader(bis));
		        	String line;
		        	while((line=reader.readLine())!=null) {
		        		responseBody.append(line);
		        	}
		        	bis.close();
		        	
		        	if(responseBody.toString().indexOf("\"success\": true")>-1) {
		        		flag=1; //성공
		        	}
		        }
		        
		        System.out.println(responseBody);
		        
	        }catch(Exception e){
	            e.printStackTrace();
	            flag=-1; //에러
	        }
			return flag;
			
	    }
}
