package com.decheng.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static String getHtmlBody(String url, String codeurl, Map<String, String> params) throws Exception {
		return getHtmlBody(url, codeurl, buildParams(params));
	}

	public static String getHtmlBody(String url, String codeurl, List<BasicNameValuePair> buildParams) {
		String result = "";
		CloseableHttpClient client = getClinet();

		HttpGet get = new HttpGet(url);
		CloseableHttpResponse scr = null;
		CloseableHttpResponse srr = null;
		try {
			scr = client.execute(get);
			HttpPost post = new HttpPost(codeurl);
			post.setEntity(new UrlEncodedFormEntity(buildParams, "UTF-8"));
			srr = client.execute(post);
			if (srr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = srr.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			try {
				srr.close();
				scr.close();
				client.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}

		return result;
	}

	public static String doGet(String url) {
		String result = "";
		CloseableHttpClient client = getClinet();
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse scr = null;
		try {
			scr = client.execute(get);
			if (HttpStatus.SC_OK == scr.getStatusLine().getStatusCode()) {
				HttpEntity entity = scr.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			try {
				scr.close();
				client.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}

		}

		return result;
	}

	public static CloseableHttpClient getClinet() {
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(120000).setConnectTimeout(120000)
				.setSocketTimeout(120000).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		return client;
	}

	private static List<BasicNameValuePair> buildParams(Map<String, String> params) throws Exception {
		List<BasicNameValuePair> bnv = new ArrayList<BasicNameValuePair>();
		if (MapUtils.isNotEmpty(params)) {
			for (Map.Entry<String, String> e : params.entrySet()) {
				BasicNameValuePair bvp = new BasicNameValuePair(e.getKey(), e.getValue());
				bnv.add(bvp);
			}
		}
		return bnv;
	}
}
