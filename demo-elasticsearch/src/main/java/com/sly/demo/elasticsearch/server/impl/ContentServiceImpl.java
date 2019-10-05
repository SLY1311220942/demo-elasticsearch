package com.sly.demo.elasticsearch.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sly.demo.elasticsearch.mapper.ContentMapper;
import com.sly.demo.elasticsearch.model.Content;
import com.sly.demo.elasticsearch.server.ContentService;

/**
 * 
 * @author sly
 * @time 2019年9月23日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {
	@Autowired
	private ContentMapper contentMapper;

	@Override
	public Content findContentById(String id) {
		return contentMapper.findById(id);
	}
	
	/**
	 * 
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	@Override
	public List<Content> findAllContent() {
		return contentMapper.findAllContent();
	}

}
