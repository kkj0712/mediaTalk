package com.example.mediatalk.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mediatalk.model.KorTVApi;
import com.example.mediatalk.model.TVApi;
import com.example.mediatalk.service.TVApiService;

@Controller
public class TVApiController {
	
	@Autowired
	private TVApiService tvApiService;
	
	@GetMapping("/tv")
	public String tvApiTest(Model model) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date today=new Date();
		String date=sdf.format(today);
		Date setDate=sdf.parse(date);

		Calendar cal=new GregorianCalendar(Locale.KOREA);
		cal.setTime(setDate);
		cal.add(Calendar.DATE, -1);
		String y_date=sdf.format(cal.getTime());
		String targetDt=y_date;
		
		List<TVApi> list=tvApiService.findTVList();
		List<KorTVApi> korList=tvApiService.findKorTVList();
		
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		String y_date2=sdf2.format(cal.getTime());
		String targetDt2=y_date2;
		
		model.addAttribute("targetDt", targetDt2);
		model.addAttribute("list", list);
		model.addAttribute("korList", korList);
		return "/tv/tvApi";
	}
}
