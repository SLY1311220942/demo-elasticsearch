package com.sly.demo.elasticsearch.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sly.demo.elasticsearch.model.Content;

/**
 * 
 * @author sly
 * @time 2019年9月24日
 */
@Configuration
public class ElasticSearchConfig {

	@Bean(name = "transportClient")
	public TransportClient getClient() {

		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		try {
			client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.100.101"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return client;
	}
	
	public static void main(String[] args) throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.100.101"), 9300));
		
		Content content = new Content();
		content.setId("1");
		content.setAuthor("盛领宜");
		content.setContent("我爱北京天安门");
		content.setCreateTime("2019-09-24");
		content.setType("随笔");
		XContentBuilder elcontent = XContentFactory.jsonBuilder().startObject()
				.field("content",content.getContent())
				.field("author",content.getAuthor())
				.field("type", content.getType())
				.field("analyzer")
				.endObject();
		
		IndexResponse indexResponse = client.prepareIndex("content", "suibi").setId("1").setSource(elcontent).get();
		System.out.println(indexResponse);
		
		//GetResponse getResponse = client.prepareGet("content", "novel", "sds").get();
		//System.out.println(getResponse);
	}

}
