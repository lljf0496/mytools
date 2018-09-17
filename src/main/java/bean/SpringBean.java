package bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * spring bean ����
 * @author ljf
 * @time 2018��8��27��
 */
@Component
public class SpringBean {
	
	@Autowired
	private  WebApplicationContext web;
	
	/**
	 * 获取bean 根据sping 注入
	 */
	public void getMethod(){
		String beanName="";//bean 名称
		String methodeName="";//方法名称
		Object params=null; //参数
		Object beanNameClass=web.getBean(beanName);
		Method method=ReflectionUtils.findMethod(beanNameClass.getClass(), methodeName,new Class[]{Object.class});
		Object obj=ReflectionUtils.invokeMethod(method,beanNameClass,params);	
	}
	
	/**
	 * 启动初始化数据
	 * @authour ljf
	 */
	
	@PostConstruct
	public synchronized void initParam(){
		//TODO 初始化数据
	}
	
	
	
	/**
	 * 获取实体数据ֵ
	 * @authour ljf
	 * @time 2018��8��27��
	 * @param obj
	 * @param filedname
	 * @return
	 */
	public static Object  getEntityByFiled(Object entity,String filedname){
		try {
			Field field=entity.getClass().getDeclaredField(filedname);
			if(!field.isAccessible()){field.setAccessible(true);}
			Object obj= field.get(entity);
			return obj;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 放入数据ֵ
	 * @authour ljf
	 * @time 2018��8��27��
	 * @param obj
	 * @param filedname
	 * @param value
	 */
	public static void  putEntityByFiled(Object entity,String filedname,Object value){
		try {
			Field field=entity.getClass().getDeclaredField(filedname);
			if(!field.isAccessible()){field.setAccessible(true);}
			field.set(entity, value);
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
