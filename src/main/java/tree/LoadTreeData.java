package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

public class LoadTreeData {
	
	private List<JSONObject> list=new ArrayList<JSONObject>();
	
	public void initData() {
		JSONObject obj=null;
			obj=loadJson("111","111","0",1);
			list.add(obj);
			obj=loadJson("112","112","0",1);
			list.add(obj);
			obj=loadJson("113","113","0",1);
			list.add(obj);
			
			obj=loadJson("221","221","111",2);
			list.add(obj);
			obj=loadJson("222","222","111",2);
			list.add(obj);
			obj=loadJson("223","223","111",2);
			list.add(obj);
			obj=loadJson("231","231","112",2);
			list.add(obj);
			obj=loadJson("232","232","112",2);
			list.add(obj);
			obj=loadJson("233","233","112",2);
			list.add(obj);
			
			obj=loadJson("311","311","221",3);
			list.add(obj);
			obj=loadJson("312","311","221",3);
			list.add(obj);
			obj=loadJson("313","311","221",3);
			list.add(obj);
			obj=loadJson("321","311","222",3);
			list.add(obj);
			obj=loadJson("322","322","231",3);
			list.add(obj);
			obj=loadJson("323","323","222",3);
			list.add(obj);
			obj=loadJson("331","331","223",3);
			list.add(obj);
			obj=loadJson("332","332","223",3);
			list.add(obj);
			obj=loadJson("333","333","233",3);
			list.add(obj);
			
			obj=loadJson("431","431","333",4);
			list.add(obj);
			obj=loadJson("432","432","333",4);
			list.add(obj);
			
			
	}
	
	public JSONObject loadJson(String id,String name,String parent,int level) {
		JSONObject obj=new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("parent",parent);
		obj.put("leval", level);
		return obj;
	}
	
	
	@Test
	public void demo() {
		initData();
		//排序
		/*List<JSONObject> lists=list.stream()
				.sorted(Comparator.comparing((JSONObject v)->v.getIntValue("level")).thenComparing(v->v.getIntValue("parent")))
				.collect(Collectors.toList());
		loadTree(lists);*/
		List lists=loadTree(list);
		System.out.println(lists);
	}
	
	@SuppressWarnings("unchecked")
	public List<JSONObject> loadTree(List<JSONObject> list) {
		 List<JSONObject> list1 = new LinkedList<JSONObject>();
		 List<JSONObject> list2 = new LinkedList<JSONObject>();
		 List<JSONObject> list3 = new LinkedList<JSONObject>();
		 int flag = list.get(0).getIntValue("leval");// 首次等级
		 for (JSONObject json : list) {
			json.put("children", new ArrayList<JSONObject>());
			if (flag == json.getIntValue("leval")){
				list1.add(json);
			} else {
				list2.clear();
				list2.addAll(list1);
				list1.clear();
				list1.add(json);
				flag = json.getIntValue("leval");
			}
			for (JSONObject json1 : list2) {
				if (json1.getString("id").equals(json.getString("parent"))) {
					json1.getObject("children", List.class).add(json);
				}
			}
			if (flag == list.get(0).getIntValue("leval")) {
				list3.add(json);
			}
		}
		return list3;
	}
}
