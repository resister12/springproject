package com.netchus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netchus.service.SensorService;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/*")
public class SensorController {
	private SensorService service;

	public SensorController(SensorService service) {
		super();
		this.service = service;
	}
	
	// 모델에 담아서 경로 지정하기
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String getSensorData(Model model) {
		model.addAttribute("sensorList", service.getResponseWithData());
		return "/view";
	}
	
}
