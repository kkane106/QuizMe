
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
			}
		});
	});

});