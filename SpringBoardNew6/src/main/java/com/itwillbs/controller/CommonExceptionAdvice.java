package com.itwillbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/*
 * AOP 개념 사용
 * 보조 기능을 구현
 * 
 * => 공통의 예외를 처리하는 객체
 * */

// @ControllerAdvice
// 컨트롤러에서 발생한 예외(보조 기능)을 처리하는 객체라고 선언하는 것

@ControllerAdvice
public class CommonExceptionAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
//	@ExceptionHandler(예외처리 객체)
	@ExceptionHandler(Exception.class)
	public String common(Model model, Exception e) {
		logger.info(" CommonExceptionAdvice_common() 실행! ");
		logger.info(" 예외 발생! ");
		
		logger.info("" + e.toString());
		model.addAttribute("e", e);
		
//		ModelAndView 객체 사용해서 전달
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName(viewName);
//		mav.addObject(attributeValue);
		
		return "ITWILL";
	}
//	@ExceptionHandler(NumberFormatException.class)
//	@ExceptionHandler(NullPointerException.class)
//	public String common2() {
//		logger.info(" CommonExceptionAdvice_common2() 실행! ");
//		logger.info(" NumberFormatException 예외 발생! ");
//		
//		return "ITWILL";
//	}
	
}
