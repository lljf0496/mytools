package mybatis;

import java.util.List;

import org.apache.commons.collections.ListUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * mybatis 分页
 * @author ljf
 * @time 2018年9月15日
 */
public class Mybatis {
	
	//pageHelper 分页
	public void doHeaper(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<JSONObject> list=ListUtils.EMPTY_LIST;
		PageInfo<JSONObject> pageInfo=new PageInfo<JSONObject>(list);
		long pageTotal=pageInfo.getTotal();
		int nowpageSize=pageInfo.getPageSize();
		int nowPageNum=pageInfo.getPageNum();
	}

}
