package com.decheng.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.decheng.dao.FgkDao;
import com.decheng.domain.Fgk;
import com.decheng.fetch.Fetch;
import com.decheng.service.FetchService;

public class FetchTest {
    
	@Test
	public void fetchBySearchTest() {
		Map<String, String> params = getParams();
		List<Fgk> list = new ArrayList<>();
		FgkDao dao = new FgkDao();
		dao.delete();
		try {
			list = Fetch.fetchBySearch(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dao.insertList(list);
	}
	
	@Test
	public void fetchAllTest() {
		FetchService.fetchAll();
	}
    
	@Test
	public void fetchAllByDateTest() {
		FetchService.fetchAllByDay("2016-09-06", "2016-09-06");
	}
    
	@Test
    public void updateByDayTest() {
    	FetchService.updateByDay();
    }
	
	private static Map<String, String> getParams() {
		Map<String, String> params = new HashMap<>();
		params.put("articleField07_s", "2010-07-26"); // 开始日期
		params.put("articleField07_d", "2010-07-26"); // 结束日期
		params.put("articleField10", "363"); // 类别
		params.put("rtoken", "fgk");
		params.put("shuizhong", "个人所得税");
		params.put("initFlag", "0");
		params.put("intvalue", "1");
		params.put("articleRole", "0000000");
		params.put("cPage", "1"); // 所在页数
		return params;
	}
}
