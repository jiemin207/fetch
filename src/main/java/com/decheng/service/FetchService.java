package com.decheng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.decheng.dao.FgkDao;
import com.decheng.domain.Fgk;
import com.decheng.fetch.Fetch;

public class FetchService {
	
	private final static Logger logger = LoggerFactory.getLogger(FetchService.class);

	public static void fetchAll() {
		List<Fgk> list = null;
		DateTime now = new DateTime();
		String today = now.toString("yyyy-MM-dd");
		try {
			list = Fetch.fetchUpdateByDay("1980-01-01", today, getAllTaxType(), "1");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if (!CollectionUtils.isEmpty(list)) {
			FgkDao dao = new FgkDao();
			dao.delete();
			dao.insertList(list);
		}
	}

	public static void fetchAllByDay(String beginDate, String endDate) {

		List<Fgk> list = null;
		try {
			list = Fetch.fetchUpdateByDay(beginDate, endDate, getAllTaxType(), "1");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if (!CollectionUtils.isEmpty(list)) {
			FgkDao dao = new FgkDao();
			dao.insertList(list);
		}
	}

	public static void fetchOneByDay(String beginDate, String endDate, Map<String, String> taxMap, String page) {

		List<Fgk> list = null;
		try {
			list = Fetch.fetchUpdateByDay(beginDate, endDate, taxMap, page);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if (!CollectionUtils.isEmpty(list)) {
			FgkDao dao = new FgkDao();
			dao.insertList(list);
		}
	}
	

	public static void updateByDay() {

		DateTime now = new DateTime();
		String dateString = now.plusDays(-1).toString("yyyy-MM-dd");
		logger.info("更新时间："+now.toLocalDateTime().toString());

		Map<String, String> taxMap = new HashMap<String, String>();
		try {
			for (Map.Entry<String, String> map : getAllTaxType().entrySet()) {
			taxMap.put(map.getKey(), map.getValue());
			fetchOneByDay(dateString, dateString, taxMap, "1");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 税目类别
	 */
	private static Map<String, String> getAllTaxType() {
		Map<String, String> params = new HashMap<>();
		params.put("359", "增值税");
		params.put("360", "消费税");
		params.put("361", "营业税");
		params.put("362", "企业所得税");
		params.put("363", "个人所得税");
		params.put("364", "资源税");
		params.put("365", "城市维护建设税");
		params.put("366", "房产税");
		params.put("367", "印花税");
		params.put("368", "城镇土地使用税");
		params.put("369", "土地增值税");
		params.put("370", "车船税");
		params.put("371", "车辆购置税");
		params.put("372", "烟叶税");
		params.put("374", "耕地占用税");
		params.put("375", "契税");
		params.put("377", "进出口税收");

		return params;
	}
}
