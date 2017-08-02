package com.decheng.domain;

public class Fgk {

	private Integer id; // 自增Id
	private String title; // 标题
	private String postingDate; // 发文日期
	private String referenceNum; // 文号
	private String taxKind;// 税种
	private String content;// 内容
	private String url;// 内容链接

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}

	public String getReferenceNum() {
		return referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public String getTaxKind() {
		return taxKind;
	}

	public void setTaxKind(String taxKind) {
		this.taxKind = taxKind;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
