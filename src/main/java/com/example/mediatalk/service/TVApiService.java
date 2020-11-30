package com.example.mediatalk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mediatalk.model.KorTVApi;
import com.example.mediatalk.model.TVApi;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TVApiService {

	private final String tmdbUrl="https://api.themoviedb.org/3/tv/popular?api_key=87fcc0847e8171d2fac22a0c2f3f35f8&language=ko&page=1";
	private final String tmdbKorUrl="https://api.themoviedb.org/3/discover/tv?api_key=87fcc0847e8171d2fac22a0c2f3f35f8&language=ko&sort_by=popularity.desc&first_air_date_year=2020&page=1&with_networks=213&include_null_first_air_dates=false&with_original_language=ko";
	private final RestTemplate restTemplate;
	
	@Transactional(readOnly = true)
	public List<TVApi> findTVList(){
		final HttpHeaders header = new HttpHeaders();
		final HttpEntity<String> entity = new HttpEntity<>(header);
		UriComponents uri = UriComponentsBuilder.fromHttpUrl(tmdbUrl).build();
		ResponseEntity<Map> responseMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class); 
		List<Map> resultList = (List<Map>) responseMap.getBody().get("results");
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
        List<TVApi> listStr = new ArrayList<TVApi>();
        for(int i=0; i<resultList.size(); i++) {
        	tempMap = resultList.get(i);
        	TVApi tvApi = new TVApi();
        	tvApi.setRank(i+1+"");
        	tvApi.setTvNm(tempMap.get("name").toString());
        	tvApi.setPoster_path("http://image.tmdb.org/t/p/w300_and_h450_bestv2"+tempMap.get("poster_path").toString());
        	tvApi.setId(tempMap.get("id").toString());
        	tvApi.setLink("https://www.themoviedb.org/tv/"+tempMap.get("id").toString());
        	listStr.add(tvApi);
        }
		return listStr;
	}
	
	@Transactional(readOnly = true)
	public List<KorTVApi> findKorTVList(){
		final HttpHeaders header = new HttpHeaders();
		final HttpEntity<String> entity = new HttpEntity<>(header);
		UriComponents uri = UriComponentsBuilder.fromHttpUrl(tmdbKorUrl).build();
		RestTemplate korTemplate=new RestTemplate();
		ResponseEntity<Map> responseMap = korTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class); 
		List<Map> resultList = (List<Map>) responseMap.getBody().get("results");
		
		Map<String, Object> tempMap = new HashMap<String, Object>();
        List<KorTVApi> korListStr = new ArrayList<KorTVApi>();
        for(int i=0; i<resultList.size(); i++) {
        	tempMap = resultList.get(i);
        	KorTVApi korTvApi = new KorTVApi();
        	korTvApi.setRank(i+1+"");
        	korTvApi.setTvNm(tempMap.get("name").toString());
        	korTvApi.setPoster_path("http://image.tmdb.org/t/p/w300_and_h450_bestv2"+tempMap.get("poster_path").toString());
        	korTvApi.setId(tempMap.get("id").toString());
        	korTvApi.setLink("https://www.themoviedb.org/tv/"+tempMap.get("id").toString());
        	korListStr.add(korTvApi);
        }
		return korListStr;
	}
}
