package com.example.mediatalk.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mediatalk.model.BookApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookApiService {

	@Transactional(readOnly = true)
	public List<BookApi> bestSeller() throws UnsupportedEncodingException, JsonProcessingException {
		String url="http://book.interpark.com/api/bestSeller.api";
		String serviceKey="2AC5E42421B7F45F7ACCF52B88028937FB81CFCF517A6D3E3288A8B7FED378B3";
		String decodeServiceKey=URLDecoder.decode(serviceKey, "UTF-8");
		
		HttpHeaders headers=new HttpHeaders();
		headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));

		RestTemplate restTemplate=new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
		restTemplate.getMessageConverters().add(0, converter);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
        		.queryParam("key", decodeServiceKey)
        		.queryParam("categoryId", "100")
        		.queryParam("output", "json")
        		.build(false); //자동으로 encode 방지
        
        String response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<String>(headers), String.class).getBody();
        
        //convertValue: object으로부터. readValue: string json으로부터.
        ObjectMapper obm = new ObjectMapper();
        Map<String, Object> responseMap=obm.readValue(response, new TypeReference<Map<String, Object>>(){});
        
        //item이란 이름으로 베스트셀러 순위가 Map형식 List에 담겨있다.
        List<Map<String, Object>> list = (List<Map<String, Object>>) responseMap.get("item");
        
        Map<String, Object> tempMap = new HashMap<String, Object>();
        List<BookApi> listStr = new ArrayList<BookApi>();
        for(int i=0; i<list.size(); i++) {
        	tempMap = list.get(i);
        	BookApi bookApi = new BookApi();
        	bookApi.setRank(i+1+"");
        	bookApi.setBookNm(tempMap.get("title").toString());
        	bookApi.setCover(tempMap.get("coverLargeUrl").toString());
        	bookApi.setLink(tempMap.get("link").toString());
        	listStr.add(bookApi);
        }
        return listStr;
	}
}
