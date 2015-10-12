package com.skilldistillery.quizme;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/test")
	public ModelAndView test(){
		System.out.println("Testing");
		return new ModelAndView("test");
	}

}
