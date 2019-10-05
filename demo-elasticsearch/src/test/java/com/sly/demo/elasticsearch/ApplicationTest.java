package com.sly.demo.elasticsearch;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ApplicationTest {
	public TransportClient client;

	@Before
	public void init() throws Exception {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
		client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.100.101"), 9300));
	}

	
	@Test
	public void search() {
		QueryBuilder queryBuilder=new MatchAllQueryBuilder();
		
		SearchResponse searchResponse = client.prepareSearch("content").setQuery(queryBuilder).setFrom(0).setSize(100).get();
		
		System.out.println(searchResponse);
	}
	
	@Test
	public void search2() {
		String keyword = "盛领宜来北京了";
		
		// 构造查询条件,使用标准分词器.
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("content", keyword).analyzer("ik_smart").operator(Operator.OR);
		
		// 单个字段高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder().field("content").preTags("<em>").postTags("</em>");
		
		// 设置查询字段
		SearchResponse searchResponse = client.prepareSearch("content").setQuery(queryBuilder)
				.highlighter(highlightBuilder).setFrom(0).setSize(100).get();
		
		SearchHits hits = searchResponse.getHits();
		for (SearchHit hit : hits) {
			Text[] texts = hit.getHighlightFields().get("content").getFragments();
			for (Text text : texts) {
				System.out.print(text);
			}
			System.out.println();
		}
		
		System.out.println(searchResponse);
	}
	
	
	@Test
	public void search3() {

		
		// 设置多个字段高亮,使用默认的highlighter高亮器
		List<HighlightBuilder.Field> highlightFields = new ArrayList<>();
		HighlightBuilder.Field highlightField1 = new HighlightBuilder.Field("content");
		highlightField1.preTags("<em>");
		highlightField1.postTags("</em>");
		HighlightBuilder.Field highlightField2 = new HighlightBuilder.Field("author");
		highlightField2.preTags("<em>");
		highlightField2.postTags("</em>");
		highlightFields.add(highlightField1);
		highlightFields.add(highlightField2);
		

		
		String keyword = "北京黄河";
		
		// 构造查询条件,使用标准分词器.
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "content","author").analyzer("ik_smart").operator(Operator.OR);
		
		
		// 多个字段高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder(new HighlightBuilder(), queryBuilder, highlightFields);
		
		
		// 设置查询字段
		SearchResponse searchResponse = client.prepareSearch("content").setQuery(queryBuilder)
				.highlighter(highlightBuilder).setFrom(0).setSize(100).get();
		
		SearchHits hits = searchResponse.getHits();
		for (SearchHit hit : hits) {
			Text[] texts = hit.getHighlightFields().get("content").getFragments();
			for (Text text : texts) {
				System.out.print(text);
			}
			System.out.println();
		}
		
		System.out.println(searchResponse);
	}

}
