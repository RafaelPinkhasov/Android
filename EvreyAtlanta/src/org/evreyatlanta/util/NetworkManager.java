package org.evreyatlanta.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public final class NetworkManager {
	
	private NetworkManager() {}
	
	public static byte[] getBytes(String url) {
		Log.i("url", url);
		HttpURLConnection ucon = null;
		try {
			URL myURL = new URL(convertURL(url));	      
			ucon = (HttpURLConnection)myURL.openConnection(); 
			ucon.setConnectTimeout(3000);			
			ucon.setRequestMethod("GET");
			ucon.setUseCaches(true);
			ucon.connect();
			return toBytes(ucon.getInputStream());
		} catch (Exception ex) {			
			return null;
		} finally {
			if (ucon != null)
				ucon.disconnect();			
		}		
	}
	
	public static String getString(String url) {	
		byte[] bytes = getBytes(url);
		return (bytes != null) ? new String(bytes) : null;		
	}
	
	private static byte[] toBytes(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();                  
        int bytesRead;                 
        byte[] buffer = new byte[1024];                 
        while ((bytesRead = is.read(buffer)) != -1) {                         
        	os.write(buffer, 0, bytesRead);                 
        } 
        return os.toByteArray();
	}
	
	private static String convertURL(String str) {
	    String url = null;
	    try{
	    	url = new String(str.trim().replace("%", "%25").replaceAll(" ", "%20"));
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    return url;
	}
}

