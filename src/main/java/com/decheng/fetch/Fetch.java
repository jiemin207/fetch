package com.decheng.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.decheng.domain.Fgk;
import com.decheng.tools.HttpUtil;

public class Fetch {

	private final static String url = "http://hd.chinatax.gov.cn/guoshui/main.jsp";
	private final static String codeurl = "http://hd.chinatax.gov.cn/guoshui/action/InitNewArticle.do";
	private final static Logger logger = LoggerFactory.getLogger(Fetch.class); 

	/**
	 * 每天抓取更新内容
	 */
	public static List<Fgk> fetchUpdateByDay(String beginDate, String endDate, Map<String, String> taxMap, String page)
			throws Exception {

		List<Fgk> list = new ArrayList<Fgk>();

		for (Map.Entry<String, String> map : taxMap.entrySet()) {
			Map<String, String> params = buildParams(beginDate, endDate, map.getKey(), map.getValue(), page);

			// 获得总页数
			String body = HttpUtil.getHtmlBody(url, codeurl, params);
			String pages = getPages(body);

			// 每页获取标题、发文日期、文号、链接
			for (int i = 1; i <= Integer.valueOf(pages); i++) {
				String cPage = String.valueOf(i);
				params.put("cPage", cPage);
				body = HttpUtil.getHtmlBody(url, codeurl, params);
				list.addAll(parser(body, map.getValue()));
			}
		}
		logger.info("链接抓取结束");
		// 根据链接取内容
		for (Fgk fgk : list) {
			fgk.setContent(getContent(fgk.getUrl()));		
		}
		logger.info("内容抓取结束");
		return list;
	}

	/**
	 * 通过条件查询抓取
	 */
	public static List<Fgk> fetchBySearch(Map<String, String> params) throws Exception {

		List<Fgk> list = new ArrayList<Fgk>();

		// 获得总页数
		String body = HttpUtil.getHtmlBody(url, codeurl, params);
		String pages = getPages(body);

		// 每页获取标题、发文日期、文号、链接
		for (int i = 1; i <= Integer.valueOf(pages); i++) {
			String cPage = String.valueOf(i);
			params.put("cPage", cPage);
			body = HttpUtil.getHtmlBody(url, codeurl, params);
			list.addAll(parser(body, params.get("shuizhong")));
		}
		// 根据链接取内容
		for (Fgk fgk : list) {
			fgk.setContent(getContent(fgk.getUrl()));
		}
		return list;
	}

	/**
	 * 解析获得标题、发文时间、文号、内容链接
	 */
	private static List<Fgk> parser(String bodyHtml, String taxType) throws Exception {
		Document doc = Jsoup.parseBodyFragment(bodyHtml);
		Elements eles = doc.select("[cellspacing=1]").first().select("tr");
		List<Fgk> list = new ArrayList<>();
		for (int i = 0; i < eles.size(); i++) {
			if (i != 0) {
				Element e = eles.get(i);
				Elements tds = e.select("td");
				String title = tds.get(0).text();
				String date = tds.get(1).text();
				String wh = tds.get(2).text();
				logger.info("税种："+taxType+"=="+"标题:"+title+"=="+"发文日期:"+date+"=="+"文号:"+wh);
				Fgk fgk = new Fgk();
				Element ehref = tds.get(0).select("a").first();
				String href = ehref.attr("href");
				String newURL = "http://hd.chinatax.gov.cn/guoshui" + href.substring(2);
				fgk.setTitle(title);
				fgk.setPostingDate(date);
				fgk.setReferenceNum(wh);
				fgk.setTaxKind(taxType);
				fgk.setUrl(newURL);
				list.add(fgk);
			}
		}
		return list;
	}

	/**
	 * 获得总页数
	 */
	private static String getPages(String bodyHtml) {
		String page = "";
		Document doc = Jsoup.parseBodyFragment(bodyHtml);
		Element element = doc.select("[style=margin-bottom:20px]").get(0);
		String text = element.select("td").text();
		Matcher tMatcher = Pattern.compile("\\/(\\d+)").matcher(text.replaceAll(" ", ""));
		if (tMatcher.find()) {
			page = tMatcher.group(1);
		}
		return page;
	}

	/**
	 * 获得内容
	 */
	private static String getContent(String url) throws Exception {
		String body = HttpUtil.doGet(url);
		Document doc = Jsoup.parseBodyFragment(body);
		Element content = doc.getElementById("Zoom2");
		String result = content.html();
		return result;
	}

	/**
	 * 参数初始化
	 */
	private static Map<String, String> buildParams(String beginDate, String endDate, String taxNum, String taxType,
			String cPage) {
		Map<String, String> params = new HashMap<>();
		params.put("articleField10", taxNum);
		params.put("shuizhong", taxType);
		params.put("articleField07_s", beginDate);
		params.put("articleField07_d", endDate);
		params.put("cPage", cPage); // 所在页数
		params.put("rtoken", "fgk");
		params.put("initFlag", "0");
		params.put("intvalue", "1");
		params.put("articleRole", "0000000");

		return params;
	}
}
