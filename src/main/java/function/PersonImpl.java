package function;

import org.junit.Test;

//函数式接口使用
/**
 * 频繁调用改方法 内部实现不能确定 使用函数接口
 * @author ljf
 * @time 2018年9月27日
 */
public class PersonImpl {
	
	//定义函数接口
	public Integer addPersonAge(Integer x,Integer y,PersonService<Integer, Integer> func) {
		return func.excutePerson(x, y);
	}
	
	//调用改函数接口
	@Test	
	public void  execute() {
		int z=addPersonAge(1,2,(x,y)->x+y);
		System.out.println(z);
	}

	
	

}
