package function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class LamadaOption {
	
private List<JSONObject> list=new ArrayList<JSONObject>();
	
	public void initData() {
		JSONObject obj=null;
		for(int i=0;i<100;i++) {
			obj=new JSONObject();
			obj.put("id", i);
			obj.put("name", i);
			obj.put("parent",(int)(Math.random()*20+1));
			obj.put("leavl", (i/25)+1);
			list.add(obj);
		}
	}
	
	//单条件排序
	@Test
	public void demo1() {
		initData();
		List lists=list.stream()
		.sorted(( v1, v2)->v1.getIntValue("parent")-v2.getIntValue("parent"))
		.collect(Collectors.toList());
		System.out.println(lists);
	}
	//组合条件排序
	@Test
	public void demo2() {
		initData();
		List lists=list.stream()
				.sorted(Comparator.comparing((JSONObject v)->v.getIntValue("parent")).thenComparing(v->v.getIntValue("level")))
				.collect(Collectors.toList());
		System.out.println(lists);
	}

}
