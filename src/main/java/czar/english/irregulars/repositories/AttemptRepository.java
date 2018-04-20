package czar.english.irregulars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {

	int countByQuiz(Quiz quiz);
	
	int countByQuizAndCorrect(Quiz quiz, boolean correct);
}
