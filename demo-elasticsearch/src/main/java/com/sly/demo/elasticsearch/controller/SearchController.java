package com.sly.demo.elasticsearch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sly.demo.elasticsearch.model.Content;
import com.sly.demo.elasticsearch.server.ContentService;

/**
 * 
 * @author sly
 * @time 2019年9月23日
 */
@Controller
@RequestMapping("/search")
public class SearchController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	
	@Autowired
	private TransportClient client;
	@Autowired
	private ContentService contentService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年9月23日
	 */
	@ResponseBody
	@RequestMapping("/initAllIndex")
	public Object initAllIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Content> contents = contentService.findAllContent();
			for (Content content : contents) {
				// 查询是否存在
				GetResponse getResponse = client.prepareGet("content", "suibi", content.getId()).get();
				if(!getResponse.isExists()) {
					// 不存在就新增
					XContentBuilder elcontent = XContentFactory.jsonBuilder().startObject()
							.field("content",content.getContent())
							.field("author",content.getAuthor())
							.field("type", content.getType())
							.endObject();
					IndexResponse indexResponse = client.prepareIndex("content", "suibi").setId(content.getId()).setSource(elcontent).get();
					System.out.println(indexResponse);
				} else {
					// 存在就更新
					XContentBuilder elcontent = XContentFactory.jsonBuilder().startObject()
							.field("content","")
							.field("author","")
							.field("type", "")
							.endObject();
					UpdateRequest updateRequest = new UpdateRequest("content", "suibi", content.getId());
				    updateRequest.doc(elcontent);
				    UpdateResponse updateResponse = client.update(updateRequest).get();
				    System.out.println(updateResponse);
				}
				
			}
			result.put("status", 200);
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
		}
		
		
		
		return result;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	@ResponseBody
	@RequestMapping("/find")
	public Object find(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		return result;
	}

}
