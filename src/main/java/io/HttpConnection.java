package io;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {
	
	/**
	 * http ֱ连接
	 * @authour ljf
	 * @time 2018��8��27��
	 * @param url ����·��
	 * @param content ����
	 * @return
	 */
	private String  getConnection(String  url,String content){
		String str=null;
		try {
			URL uri = new URL(url);  
			HttpURLConnection connection = (HttpURLConnection)uri.openConnection();  
			connection.setDoInput(true);  
			connection.setDoOutput(true);  
			connection.setRequestMethod("POST");  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.setRequestProperty("Content-Type","application/json");  
			connection.connect(); 
			if(connection.hashCode()==HttpURLConnection.HTTP_OK){
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());  
				out.writeBytes(content);  
				out.flush();  
				out.close();
				str=connection.getResponseMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
