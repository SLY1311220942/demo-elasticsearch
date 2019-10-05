package com.sly.demo.elasticsearch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sly.demo.elasticsearch.model.Content;
import com.sly.demo.elasticsearch.server.ContentService;

/**
 * 
 * @author sly
 * @time 2019年9月24日
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @author sly
	 * @time 2019年9月24日
	 */
	@ResponseBody
	@RequestMapping("/findContentById")
	public Object findContentById(HttpServletRequest request, HttpServletResponse response, String id) {
		Content content = contentService.findContentById(id);

		return content;
	}

}
