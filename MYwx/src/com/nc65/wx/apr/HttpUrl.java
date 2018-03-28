package com.nc65.wx.apr;

import java.io.*;
import java.net.*;

public class HttpUrl{ 

	public static String ContentType1="INFOSEC_SIGN/1.0";
	public static String ContentType2="INFOSEC_VERIFY_SIGN/1.0";
	public static String ContentType3="text/html; charset=GB2312";
	public static String ContentType4="application/x-www-form-urlencoded";
	
	private String strCharSet;
	private String strContentType;
	private String strAccept;
	private String strAcceptEncoding;
	private String strUserAgent;
	private String strPragma;
	
	public HttpUrl() {
		strCharSet = "utf-8";
		strContentType = "application/x-www-form-urlencoded";
		strAccept = "text/xml; *.*";
		strAcceptEncoding = "gzip, deflate";
//		strAcceptEncoding = "GBK";
		strUserAgent = "HTTP Transparent";
		strPragma = "no-cache";
	}
	
	public String Submit(String url, String strData) throws Exception {
		
		URL reqUrl = null;
		HttpURLConnection reqConnection = null;
		InputStream returnStream = null;
		
		reqUrl = new URL(url);
		reqConnection = (HttpURLConnection) reqUrl.openConnection();
		reqConnection.setConnectTimeout(300);
		reqConnection.setRequestMethod("POST");
		reqConnection.setRequestProperty("Content-Type", strContentType);
		reqConnection.setRequestProperty("Accept", strAccept);
		reqConnection.setRequestProperty("Accept-Encoding", strAcceptEncoding);
		reqConnection.setRequestProperty("User-Agent", strUserAgent);
		reqConnection.setRequestProperty("Pragma", strPragma);
		reqConnection.setRequestProperty("content-encoding", strCharSet);
		//add by chenjma 
		reqConnection.setRequestProperty("Charset", "utf-8");
		reqConnection.setDoInput(true);
		reqConnection.setDoOutput(true);
		reqConnection.connect();
		   
		DataOutputStream out = new DataOutputStream(reqConnection.getOutputStream());
		byte[] byt = strData.getBytes(strCharSet);
		out.write(byt);
		
		int responseCode = reqConnection.getResponseCode();
		if ( responseCode != 200 )
			throw new Exception( "HttpComm failed! responseCode = " + responseCode + " msg="
					+ reqConnection.getResponseMessage() );
		
		returnStream = reqConnection.getInputStream();
		String retData = convertStreamToString(returnStream);
		
		System.out.println("BankRetunMsg:" + retData);
		return retData;		
	}
	
	private static String convertStreamToString(InputStream is){    
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));    
		StringBuilder sb = new StringBuilder();    
		String line = null ;    
		try {    
	    	while((line = reader.readLine()) != null){    
	    		sb.append(line);    
	    	}    
	    } catch (IOException e){
	    	e.printStackTrace();    
	    } finally {
    		try {
    			is.close();    
    		} catch (IOException e){    
    			e.printStackTrace();
    		}
	    }    
	    return sb.toString();    
	}

	public String getStrContentType() {
		return strContentType;
	}

	public void setStrContentType(String strContentType) {
		this.strContentType = strContentType;
	}

	public String getStrCharSet() {
		return strCharSet;
	}

	public void setStrCharSet(String strCharSet) {
		this.strCharSet = strCharSet;
	}

	public String getStrAccept() {
		return strAccept;
	}

	public void setStrAccept(String strAccept) {
		this.strAccept = strAccept;
	}

	public String getStrAcceptEncoding() {
		return strAcceptEncoding;
	}

	public void setStrAcceptEncoding(String strAcceptEncoding) {
		this.strAcceptEncoding = strAcceptEncoding;
	}

	public String getStrUserAgent() {
		return strUserAgent;
	}

	public void setStrUserAgent(String strUserAgent) {
		this.strUserAgent = strUserAgent;
	}

	public String getStrPragma() {
		return strPragma;
	}

	public void setStrPragma(String strPragma) {
		this.strPragma = strPragma;
	}  
	
}