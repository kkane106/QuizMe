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
						quiz = JSON.parse(data1);
						console.log(quiz);
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

