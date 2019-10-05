package com.sly.demo.elasticsearch.server;

import java.util.List;

import com.sly.demo.elasticsearch.model.Content;

/**
 * 
 * @author sly
 * @time 2019年9月23日
 */
public interface ContentService {
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	Content findContentById(String id);
	
	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	List<Content> findAllContent();

}
