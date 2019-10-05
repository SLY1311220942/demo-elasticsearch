package com.sly.demo.elasticsearch.model;

import java.io.Serializable;

/**
 * 
 * @author sly
 * @time 2019年9月23日
 */
public class Content implements Serializable {

	private static final long serialVersionUID = 6446240167726512350L;
	
	/** varchar(32) NOT NULL主键UUID */
	private String id;
	/** text NULL文章内容 */
	private String content;
	/** varchar(32) NULL作者 */
	private String author;
	/** varchar(32) NULL文章类型 */
	private String type;
	/** varchar(24) NULL文章时间 */
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
