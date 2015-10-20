var quiz;

$( document ).ready(function() {
	$("#newQuestionButton").click(function(){
		$.ajax("rest/newQuestion",{
			success: function(data, textStatus, jqXHR){
				$(data).insertBefore($("#newQuestionButton"));
			}
		});
	});

	$('body').on('click', '.getNewAnswerChoiceButton',  function(event){
		$.ajax("rest/answerChoice",{
			success: function(data, textStatus, jqXHR){
				$(data).insertBefore(event.target);
			}
		});
	});
	
	$("searchButton").click(function(event){
		var searchTerm = $("searchButton").val();
		$.ajax("rest/searchQuizzes?q="+searchTerm, {
			success: function(data, textStatus, jqXHR){
				//I expect an array of Quiz Names, Authors, and Date Created
				for(var shortQuiz in data){
					
				}
				
			}
		});
		

	});
	
	$("body").on('click', '#submitQuizButton', function(event){
		var quiz = {};
		quiz.quizName = $("#quizName").val();
		quiz.author = $("#quizAuthor").val();
		quiz.description = $("#quizDescription").val();
		quiz.questions = Array();
		//Parse Questions
		$(".question").each(function(element){
			var question = {};
			question.answerChoices = Array();
			question.questionText = $(".questionText", this).val();
			
			$(".answerChoice", this).each(function(){
				var answerChoice = {};
				answerChoice.isRight = $(".answerChoiceCorrect", this).is(":checked");
				answerChoice.answerText = $(".answerText", this).val();
				question.answerChoices.push(answerChoice);
			});
			
			quiz.questions.push(question);
		});
		
		$.ajax("rest/submitNewQuiz?quiz="+JSON.stringify(quiz), {
			success: function(data, textStatus, jqXHR){
				console.log(data);
				console.log(textStatus);
				
				if(data === false){
					alert("Error Quiz Name and Author already exist. One must be unique");
				}
			}
		});
	});
	
	$('body').on('click', '.quizName',  function(event){
		var element = event.target;
		var quizId = $(element).attr("quizId");
		var quizAuthor = $(element).attr("author");
		var quizPanel;

		$.ajax("rest/quizPanel",{
			success: function(data, textStatus, jqXHR){
				quizPanel = data;
				$("#contentPane").empty();
				//Now pull down the actual quiz and work with that
				$.ajax("rest/takeQuiz/"+quizAuthor+"/"+quizId, {
					success: function(data1, textStatus, jqXHR){
						quiz = data1;
						displayQuiz($("#contentPane"));
					}
				});
			}
		});
		
		
	});
	
	
	$('body').on('hover', '.quizName',  function(event){
		
	});
	
	init();
});

function init(){
	//Populate the recents table!
	$.ajax("rest/recentQuizzes", {
		success: function(data, textStatus, jqXHR){
			console.log(data);
			$("#recentQuizTable").append("<tr><td><b>Quiz Title</b></td><td><b>Quiz Author</b></td></tr>")
			$("#recentQuizTable").append("<tr><td colspan='2'>&nbsp</td></tr>")
			for(var i = 0; i < data.length; i++){
				$("#recentQuizTable").append("<tr><td class='quizName' author="+ data[i].author +" quizId="+data[i].id+">"+data[i].quizName +"</td><td>"+ data[i].author+"</td></tr>");
				$("#recentQuizTable").append("<tr><td colspan='2' class = 'quizDescription'>"+data[i].description+"</td></tr>");
				$("#recentQuizTable").append("<tr><td colspan='2'>&nbsp</td></tr>");
			}
		}
	});
}

function displayQuiz(root){
	var longDate = new Date(quiz.dateCreated);
	var month = longDate.getUTCMonth()+1;
	var day = longDate.getUTCDate();
	var year = longDate.getUTCFullYear();
	
	var date = month + "-" + day + "-" + year;
	
	var quizHeader = "<div id='quizHeader'>" +
			"<table>" +
				"<tr><td id='quizTitle'><h2>"+quiz.quizName+"</h2></td><td> &nbsp </td> <td>"+quiz.author+" </td></tr>" +
				"<tr><td> Date Created: "+ date +"</td> <td colspan = '2'> Description: "+ quiz.description +" </tr>"+
			"</table>" +
			" </div>";
	
	root.append(quizHeader);
	
	root.append("<div id='questionDiv'>" +
			"<button id='startQuizButton' onClick='startQuiz()'> Start Quiz </button>" +
			" </div>");
}

function startQuiz(){
	$("#startQuizButton").remove();
	var questionCounter = 1;
	var questionPanel = $("#questionDiv");
	var questions = quiz.questions;
	quiz.questionCounter = 0;
	var submitQuiz = false;
	
	displayQuestion(questions[quiz.questionCounter]);
	
}

function displayQuestion(question){
	questionPanel = $("#questionDiv");
	questionPanel.append("<h3>"+question.questionText+"</h3>");
	questionPanel.append("<ul>");
	for(var i = 0; i < question.answerChoices.length; i++){
		questionPanel.append("<li>"+question.answerChoices[i].answerText+"<input class='answerChoice' type='checkbox'></li>");
	}
	questionPanel.append("</ul>");
	questionPanel.append("<button id='prevQuestion' onClick = 'prevQuestion()'>Prev</button><button id='nextQuestion' onClick='nextQuestion()'>Next</button>");
	
}

function nextQuestion(){
	if(quiz.questionCounter < quiz.questions.length - 1){
		$("#questionDiv").empty();
	
	
		var chosenAnswers = quiz.questions[quiz.questionCounter].chosenAnswers = [];
		$(".answerChoice :checkbox:checked").each(function(){
			chosenAnswers.add(this.prev().val());
		});
	

		displayQuestion(quiz.questions[++quiz.questionCounter]);
	}else if($("#nextQuestion").attr("submit") === "true"){
		var div = $("#questionDiv");
		//Show results
		$("#questionDiv").empty();
		$("#questionDiv").append("<h1> " + quiz.quizName + " Results: </h1>");
		
		var questionsRight = calculateRightQuestions();
		div.append()
	}else{
		$("#nextQuestion").html("Submit!")
		$("#nextQuestion").attr("submit", true);
	}
	
	
}

function prevQuestion(){
	if(quiz.questionCounter > 0){
		$("#questionDiv").empty();
	

	var chosenAnswers = quiz.questions[quiz.questionCounter].chosenAnswers = [];
	$(".answerChoice :checkbox:checked").each(function(){
		chosenAnswers.add(this.prev().val());
	});
	

		displayQuestion(quiz.questions[--quiz.questionCounter]);
	}
}

function calculateRightAnswers(){
	for(var i = 0; i < quiz.questions.length; i++){
		var chosenAnswers = quiz.questions[i].chosenAnswers;
		var answerChoices = quiz.questions[i].answerChoices;
		
		for(var c = 0; c < chosenAnswers.length; c++){
			
		}
		
	}
}