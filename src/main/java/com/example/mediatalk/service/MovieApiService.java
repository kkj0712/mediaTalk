package com.example.mediatalk.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.mediatalk.model.MovieApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieApiService {

	private final String kobisBoxOfficeUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=421b7a6eed36246c4282b181e76f27a4&targetDt=";
	private final String kobisMovieInfoUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=421b7a6eed36246c4282b181e76f27a4&movieCd=";
	private final String kmdbInfoUrl = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=AH9MD9RXI04RVZ9Y7C80&title=";
	private final RestTemplate restTemplate;

	@Transactional(readOnly = true)
	public List<MovieApi> findByTargetDt(String targetDt) throws IOException {
		final HttpHeaders header = new HttpHeaders();
		final HttpEntity<String> entity = new HttpEntity<>(header);

		// 박스오피스에서 순위 rnum, 영화제목 movieNm 가져오기
		String targetUrl = kobisBoxOfficeUrl + targetDt;
		UriComponents uri = UriComponentsBuilder.fromHttpUrl(targetUrl).build();
		ResponseEntity<Map> responseMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class); 
		LinkedHashMap boxOfficeHashMap = (LinkedHashMap) responseMap.getBody().get("boxOfficeResult");
		List<Map> dboxoffList = (List<Map>) boxOfficeHashMap.get("dailyBoxOfficeList");

		LinkedHashMap boxMap = new LinkedHashMap<>();
		for (Map obj : dboxoffList) {
			boxMap.put(obj.get("rnum"), obj.get("movieCd")); // 순위별 영화코드를 얻기위해서 Map에 담음
		}

		// movieCd으로 영화진흥위원회 영화 상세정보에서 movieNm, movieNmEn, nation 찾기
		LinkedHashMap nameMap = new LinkedHashMap<>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		LinkedHashMap nameEnMap = new LinkedHashMap<>();
		LinkedHashMap nationMap = new LinkedHashMap<>();
		for (int i = 1; i <= 10; i++) {
			String movieCd = boxMap.get(i + "").toString(); // 순위별 영화코드
			String infoUrl = kobisMovieInfoUrl + movieCd;
			UriComponents movieUri = UriComponentsBuilder.fromHttpUrl(infoUrl).build();
			RestTemplate movieInfoRestTemplate = new RestTemplate();
			ResponseEntity<Map> movieInfoResultMap = movieInfoRestTemplate.exchange(movieUri.toString(), HttpMethod.GET,
					entity, Map.class);
			LinkedHashMap movieResultHashMap = (LinkedHashMap) movieInfoResultMap.getBody().get("movieInfoResult");
			LinkedHashMap movieInfoHashMap = (LinkedHashMap) movieResultHashMap.get("movieInfo");

			String movieNm = (String) movieInfoHashMap.get("movieNm");
			nameMap.put(i, movieNm);

			String movieNmEn = (String) movieInfoHashMap.get("movieNmEn");
			nameEnMap.put(i, movieNmEn);

			List<Map<String, Object>> nations = (List<Map<String, Object>>) movieInfoHashMap.get("nations");
			tempMap = nations.get(0);
			String nationNm = tempMap.get("nationNm").toString();
			nationMap.put(i, nationNm);
		}

		// movieNm과 movieNmEn으로 kmdb에서 link 찾음
		List<MovieApi> listStr = new ArrayList<MovieApi>();
		String kmDbUrl = null;
		UriComponents kmUri = null;
		String kmResponse = null;
		RestTemplate kmRestTemplate = new RestTemplate();
		Map<String, Object> kmResponseMap = new HashMap<String, Object>();
		List<Map<String, Object>> data = null;
		Map<String, Object> tempMap2 = new HashMap<String, Object>();
		ObjectMapper obm = new ObjectMapper();
		
		for (int i = 1; i <= 10; i++) {
			String title = nameMap.get(i).toString(); // 순위별 영화제목
			String titleEng = nameEnMap.get(i).toString(); // 순위별 영어 영화제목
			String nation = nationMap.get(i).toString().equals("한국") ? "대한민국" : nationMap.get(i).toString();

			kmDbUrl = kmdbInfoUrl + title + " " + titleEng + "&sort=prodYear,1";
			kmUri = UriComponentsBuilder.fromHttpUrl(kmDbUrl).build();
			kmResponse = kmRestTemplate.exchange(kmUri.toString(), HttpMethod.GET, entity, String.class).getBody();

			kmResponseMap = obm.readValue(kmResponse, new TypeReference<Map<String, Object>>() {
			});
			data = (List<Map<String, Object>>) kmResponseMap.get("Data");
			tempMap2 = data.get(0);

			int totalCount = Integer.parseInt(tempMap2.get("TotalCount").toString());

			if (totalCount > 1) {
				kmDbUrl = kmdbInfoUrl + title + " " + titleEng + "&sort=prodYear,1&nation=" + nation;
				kmUri = UriComponentsBuilder.fromHttpUrl(kmDbUrl).build();
				kmResponse = kmRestTemplate.exchange(kmUri.toString(), HttpMethod.GET, entity, String.class).getBody();

				kmResponseMap = obm.readValue(kmResponse, new TypeReference<Map<String, Object>>() {
				});
				data = (List<Map<String, Object>>) kmResponseMap.get("Data");
				tempMap2 = data.get(0);
			}

			List<Map<String, Object>> resultList = (List<Map<String, Object>>) tempMap2.get("Result");
			tempMap2 = resultList.get(0);

			MovieApi movieApi = new MovieApi();
			movieApi.setRnum(i + "");
			movieApi.setMovieNm(title);
			movieApi.setLink(tempMap2.get("kmdbUrl").toString());

			String urlStr = tempMap2.get("kmdbUrl").toString(); // 이 url로 들어가서 jsoup으로 포스터 정보 가져오기.
			String selector = "div.mProfile div.mImg1 span";
			String imageUrl=null;
			
			Document doc = Jsoup.connect(urlStr).get();
			Elements contents = doc.select(selector);
			String content=contents.toString();
			int index=content.indexOf("'");
			int endIndex=content.indexOf(".jpg");
			imageUrl=content.substring(index+1, endIndex+4);
			
			movieApi.setImage(imageUrl);
			listStr.add(movieApi);
		}
		return listStr;
	}
}
