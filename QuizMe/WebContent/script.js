
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
		})
		
		//Populate the recents table!
		$.ajax("/rest/recentQuizzes", {
			success: function(data, textStatus, jqXHR){
				for(quiz in data){
					
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

});