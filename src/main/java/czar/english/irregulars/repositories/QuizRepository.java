package czar.english.irregulars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.english.irregulars.entities.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
