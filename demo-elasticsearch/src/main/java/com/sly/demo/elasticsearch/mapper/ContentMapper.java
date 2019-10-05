package com.sly.demo.elasticsearch.mapper;

import java.util.List;

import com.sly.demo.elasticsearch.model.Content;

/**
 * 
 * @author sly
 * @time 2019年9月23日
 */
public interface ContentMapper {
	/**
	 * 
	 * @param id
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	Content findById(String id);
	
	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	List<Content> findAllContent();

}
