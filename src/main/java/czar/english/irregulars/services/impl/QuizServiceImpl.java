package czar.english.irregulars.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import czar.english.irregulars.dtos.Result;
import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.repositories.AttemptRepository;
import czar.english.irregulars.repositories.QuizRepository;
import czar.english.irregulars.repositories.VerbRepository;
import czar.english.irregulars.services.QuizService;

@Service("quizService")
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	private AttemptRepository attemptRepository;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private VerbRepository verbRepository;

	@Override
	public Attempt init(Quiz quiz) {
		quizRepository.save(quiz);

		List<Verb> verbs = verbRepository.findAll();
		Attempt attempt = AttemptBuilder.build(verbs);
		attempt.setQuiz(quiz);
		attemptRepository.save(attempt);
		return attempt;
	}
	
	@Override
	public Attempt processAttempt(Attempt attempt) {
		Attempt dbAttempt = attemptRepository.findOne(attempt.getId());
		dbAttempt.setSelected(attempt.getSelected());
		dbAttempt.setCorrect(dbAttempt.getTopic().equals(attempt.getSelected()));
		attemptRepository.save(dbAttempt);
		return dbAttempt;
	}
	
	@Override
	public boolean isLast(Attempt attempt) {
		Quiz quiz = quizRepository.getOne(attempt.getQuiz().getId());
		int currentAttempts = attemptRepository.countByQuiz(quiz);
		return !(currentAttempts < quiz.getLevel());
	}

	@Override
	public Attempt getNext(Long idQuiz) {
		Quiz quiz = quizRepository.getOne(idQuiz);
		List<Verb> verbs = verbRepository.findAll();
		Attempt attempt = AttemptBuilder.build(verbs);
		attempt.setQuiz(quiz);
		attemptRepository.save(attempt);
		return attempt;
	}

	@Override
	public Result getResult(Long idQuiz) {
		Quiz quiz = new Quiz();
		quiz.setId(idQuiz);
		int correct = attemptRepository.countByQuizAndCorrect(quiz, true);
		int answers = quiz.getLevel();
		Result result = new Result();
		result.setCorrectAnswers(correct);
		result.setTotalAnswers(answers);
		return result;
	}	
}
