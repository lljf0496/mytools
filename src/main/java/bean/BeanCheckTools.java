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

import bean.annotation.VerificationMobile;
import bean.annotation.VerificationMust;
import bean.annotation.verificationNum;
import net.logstash.logback.encoder.org.apache.commons.lang.math.NumberUtils;

public class BeanCheckTools {
	
	private final static Logger log=LoggerFactory.getLogger(BeanCheckTools.class);
	/**�绰����**/
	public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))$";
	/**������ʽ����֤���֤*/
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    
	
	/**
	 * ��������
	 * @param entity
	 */
	public static VerficatResult checkMustEntity(Object entity){
		boolean flag=true;
		String msg=null;
		try {
			Field[] fields = entity.getClass().getDeclaredFields(); 
			for(Field field:fields){
				if(field.isAnnotationPresent(VerificationMust.class)){//��֤������
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj)){
						msg=varName+" ���벻Ϊ��";
						flag=false;
						break;
					}
				}
				if(field.isAnnotationPresent(verificationNum.class)){
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj) || !NumberUtils.isNumber(String.valueOf(obj))){
						 msg=varName+" ��������";
						 flag=false;
						 break;
					}
				}
				if(field.isAnnotationPresent(VerificationMobile.class)){
					String varName = field.getName();
					Object obj= getObjectValue(field,entity);
					if(ObjectUtils.isEmpty(obj) || !verificationMobile(String.valueOf(obj))){
						msg=varName+" ����11λ�绰����";
						flag=false;
						break;
					}
				}
			}
		} catch (Exception e) {
			msg="�������";
			flag=false;
			log.error(msg,e);
		}
		return new VerficatResult(flag,msg);
	}
	
	/**
	 * �绰����У��
	 * @authour ljf
	 * @time 2018��7��21��
	 * @param value
	 * @return
	 */
	private static boolean verificationMobile(String value){
		if(value.length()!=11) return false;
		if(NumberUtils.isNumber(value)==false) return false;
		return Pattern.matches(REGEX_MOBILE, value);
	}
	/**
	 * ���֤У��
	 * @authour ljf
	 * @time 2018��7��21��
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean verificationIDCard(String value){
		if(value.length()!=15 || value.length()!=18) return false;
		return !chineseWord(value);
	}
	/**
	 * ����У��У��
	 * @authour ljf
	 * @time 2018��7��21��
	 * @param value
	 * @return
	 */
	public static boolean chineseWord(String value){
		 return value.length()!=value.getBytes().length;
	}
	
	/**
	 * ���ݲ���ת��Ϊjson��ʽ
	 * @authour ljf
	 * @time 2018��7��21��
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
	 * ȡ��������ֵ
	 */
	public static Object getObjectValue(Field field,Object entity) throws IllegalArgumentException, IllegalAccessException{
		if(!field.isAccessible()){field.setAccessible(true);}
		Object obj= field.get(entity);
		return obj;
	}
	
	/**
	 * �ָ��ַ��������� 
	 * ������ԭ������
	 * @param value map ����
	 * @param filed ���ֶ�
	 * @param splidChar �ָ��
	 */
	public static void splitString(JSONObject value,String filed,String splidChar){
		Object denger=value.get(filed);
		if(denger!=null){
			String[] strs=StringUtils.split(String.valueOf(denger),splidChar);
			value.put(filed, strs);
		}
	}
	
	
	/**
	 * �б���
	 */
	public static Object  reverse(List list){
		if(list!=null && !list.isEmpty()){
			Collections.reverse(list);
		}
		return list;
	}
	

}
