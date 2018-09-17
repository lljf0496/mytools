package bean;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSONObject;

import anntoations.VerificationMobile;
import anntoations.VerificationMust;
import anntoations.verificationNum;
import net.logstash.logback.encoder.org.apache.commons.lang.math.NumberUtils;

public class BeanCheckTools {
	
	private final static Logger log=LoggerFactory.getLogger(BeanCheckTools.class);
	/**电话号码**/
	public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))$";
	/**正则表达式：验证身份证*/
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    
	
	/**
	 * 检查必须项
	 * @param entity
	 */
	public static VerficatResult checkMustEntity(Object entity){
		boolean flag=true;
		String msg=null;
		try {
			Field[] fields = entity.getClass().getDeclaredFields(); 
			for(Field field:fields){
				if(field.isAnnotationPresent(VerificationMust.class)){//验证必须项
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj)){
						msg=varName+" 必须不为空";
						flag=false;
						break;
					}
				}
				if(field.isAnnotationPresent(verificationNum.class)){
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj) || !NumberUtils.isNumber(String.valueOf(obj))){
						 msg=varName+" 必须数字";
						 flag=false;
						 break;
					}
				}
				if(field.isAnnotationPresent(VerificationMobile.class)){
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj) || !verificationMobile(String.valueOf(obj))){
						msg=varName+" 必须11位电话号码";
						flag=false;
						break;
					}
				}
			}
		} catch (Exception e) {
			msg="处理出错";
			flag=false;
			log.error(msg,e);
		}
		return new VerficatResult(flag,msg);
	}
	
	/**
	 * 电话号码校验
	 * @authour ljf
	 * @time 2018年7月21日
	 * @param value
	 * @return
	 */
	private static boolean verificationMobile(String value){
		if(value.length()!=11) return false;
		if(NumberUtils.isNumber(value)==false) return false;
		return Pattern.matches(REGEX_MOBILE, value);
	}
	/**
	 * 身份证校验
	 * @authour ljf
	 * @time 2018年7月21日
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean verificationIDCard(String value){
		if(value.length()!=15 || value.length()!=18) return false;
		return !chineseWord(value);
	}
	/**
	 * 中文校验校验
	 * @authour ljf
	 * @time 2018年7月21日
	 * @param value
	 * @return
	 */
	public static boolean chineseWord(String value){
		 return value.length()!=value.getBytes().length;
	}
	
	/**
	 * 传递参数转换为json格式
	 * @authour ljf
	 * @time 2018年7月21日
	 * @param req
	 * @return
	 */
	public static JSONObject getParamToJson(HttpServletRequest req){
		Enumeration<String> params=req.getAttributeNames();
		JSONObject result=new JSONObject();
		while(params.hasMoreElements()){
			String key=params.nextElement();
			String value=req.getParameter(key);
			result.put(key, value);
		}
		return result;
		
	}
	
	/**
	 * 取对象属性值
	 */
	public static Object getObjectValue(Field field,Object entity) throws IllegalArgumentException, IllegalAccessException{
		if(!field.isAccessible()){field.setAccessible(true);}
		Object obj= field.get(entity);
		return obj;
	}
	
	/**
	 * 分割字符串成数组 
	 * 并覆盖原来数据
	 * @param value map 对象
	 * @param filed 改字段
	 * @param splidChar 分割符
	 */
	public static void splitString(JSONObject value,String filed,String splidChar){
		Object denger=value.get(filed);
		if(denger!=null){
			String[] strs=StringUtils.split(String.valueOf(denger),splidChar);
			value.put(filed, strs);
		}
	}
	
	
	/**
	 * 列表倒序
	 */
	public static Object  reverse(List list){
		if(list!=null && !list.isEmpty()){
			Collections.reverse(list);
		}
		return list;
	}
	

}
