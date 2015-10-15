package com.skilldistillery.quizme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
public class RestUtilityServices {
	
	@RequestMapping("/newQuestion")
	public String fetchNewQuestionFragment(HttpServletRequest request){
		try {
			ServletContext context = request.getServletContext();
			List<String> lines = Files.readAllLines(Paths.get(context.getRealPath("/questionFragment.html")));
			String fragment = "";
			for(String s : lines){
				fragment += s;
			}
			
			return fragment;
		} catch (IOException e) {
			e.printStackTrace();
			return "File I/O Error:";
		}
	}
	
	@RequestMapping("/answerChoice")
	@ResponseBody
	public String fetchAnswerChoiceFragment(HttpServletRequest request){
		ServletContext context = request.getServletContext();

		try {
			List<String> lines = Files.readAllLines(Paths.get(context.getRealPath("/answerChoiceFragment.html")));
			String fragment = "";
			for(String s : lines){
				fragment += s;
			}
			
			return fragment;
		} catch (IOException e) {
			e.printStackTrace();
			return "File I/O Error";
		}
	}
	
	@RequestMapping("/submitNewQuiz")
	public boolean submitQuiz(@RequestParam("quiz") String json){
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.readValue(json, Quiz.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}




