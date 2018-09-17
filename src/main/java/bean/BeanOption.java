package bean;

import cn.hutool.core.convert.Convert;

public class BeanOption {
	
	public <T>T ConvertBean(Object obj,T t){
		String result=Convert.toStr(obj);
		return null;
	}

}
