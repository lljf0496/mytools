package io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class httpClient {
	
	//使用hutool  文件传输
	public static String  httpPostFile(){
		
		return null;
	}
	
	/**
	 * http json 发送
	 */
	public static String httpPostWithJson(String url, JSONObject json) {
		String returnValue = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			StringEntity requestEntity = new StringEntity(json.toJSONString(), "utf-8");
			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);
			returnValue = httpClient.execute(httpPost, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}


	/**
	 * post 文件发送
	 * @param encoding
	 * @param url
	 * @param data
	 * @return
	 */
	public static String httpPostFile(String url,File file){
		String  result=null;
		CloseableHttpClient httpClient=null;
		try {
			int connectTimeOut=20000; //连接超时时间
			//post 请求构建
			HttpPost httpPost = new HttpPost(url); 
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeOut).setSocketTimeout(connectTimeOut).build();
			httpPost.setConfig(requestConfig);
			//httpClient 构建
			httpClient=HttpClients.createDefault();
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			 //文件填充
			FileBody bin = new FileBody(file); 
			multipartEntityBuilder.addPart("file",bin);
			 //其他参数填充
			StringBody comment = new StringBody("otherFiledContent", ContentType.TEXT_PLAIN); 
			multipartEntityBuilder.addPart("filed_1",comment);
			 //数据构建
			HttpEntity httpEntity = multipartEntityBuilder.build();
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String resStr = EntityUtils.toString(resEntity);
					EntityUtils.consume(resEntity);//消费数据
					result = resStr;
				}
			}
			httpResponse.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(httpClient!=null) httpClient.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * post 请求
	 * @param encoding
	 * @param url
	 * @param data
	 * @return
	 */
	public static String httpPost(String encoding,String url,Map<String,Object> data){
		String result=null;
		//必须参数验证֤
		if(StringUtils.isEmpty(url)){ throw new RuntimeException("·����Ϊ��");}
		if(data==null || data.isEmpty()){ throw new RuntimeException("���ݲ�Ϊ��");}
		if(StringUtils.isEmpty(encoding)) encoding="UTF-8";
		//httpclient 构建
		CloseableHttpClient httpClient=HttpClients.createDefault();
		List<NameValuePair> nameValuePairArrayList = new ArrayList<NameValuePair>();
		for(Entry<String, Object> entry:data.entrySet()){
			nameValuePairArrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairArrayList, encoding);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String resStr = EntityUtils.toString(httpEntity);
				EntityUtils.consume(httpEntity);//消费消息
				result = resStr;
			}
			httpResponse.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(httpClient!=null) httpClient.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * get 请求
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		String result = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse httpResponse = null;
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String resStr = EntityUtils.toString(httpEntity);
				EntityUtils.consume(httpEntity);// 消费消息
				result = resStr;
			}
			httpResponse.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(httpClient!=null) httpClient.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}


