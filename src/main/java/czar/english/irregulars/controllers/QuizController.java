package czar.english.irregulars.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import czar.english.irregulars.dtos.Result;
import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.services.QuizService;

@Controller
public class QuizController {

	
	@Autowired
	private QuizService quizService;
	
	@RequestMapping(value = {"/quiz/init", "/", "/quiz", "/quiz/"}, method = GET)
	public String init(Model model) {
		model.addAttribute("quiz", new Quiz());
		return "quiz/init";
	}

	@RequestMapping(value = "/quiz/init", method = POST)
	public String postInit(Model model, Quiz quiz) {

		Attempt attempt = quizService.init(quiz);
		model.addAttribute("attempt", attempt);

		return "quiz/attempt";
	}

	@RequestMapping(value = "/quiz/attempt", method = POST)
	public String postAttempt(Model model, Attempt attempt) {
		Attempt dbAttempt = quizService.processAttempt(attempt);
		model.addAttribute("attempt", dbAttempt);
		return "quiz/answer";
	}

	@RequestMapping(value = "/quiz/next", method = POST)
	public String nextAttempt(Model model, Attempt attempt) {
		
		String view = "quiz/attempt";
		Long idQuiz = attempt.getQuiz().getId();
		
		if (!quizService.isLast(attempt)) {
			Attempt next = quizService.getNext(idQuiz);
			model.addAttribute("attempt", next);
		} else {
			Result result = quizService.getResult(idQuiz);
			model.addAttribute("result", result);
			view = "quiz/finish";
		}
		
		return view;
	}

	
}
