package czar.english.irregulars.services;

import czar.english.irregulars.dtos.Result;
import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;

public interface QuizService {

	Attempt init(Quiz quiz);
	
	Attempt processAttempt(Attempt attempt);
	
	boolean isLast(Attempt attempt);
	
	Attempt getNext(Long idQuiz);
	
	Result getResult(Long idQuiz);
}
