package system;

import org.junit.Test;

public class SystemOption {
	
	
	@Test
	public void getSysInfoAll() {
		String info=System.getProperty("java.library.path");//环境配置 path
		split(info,";");
	}
	
	public void split(String strs,String chars) {
		for(String str:strs.split(chars)) {
			System.out.println(str);
		}
	}

}
