package czar.english.irregulars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import czar.english.irregulars.entities.Verb;

@Repository
public interface VerbRepository extends JpaRepository<Verb, Long> {

}
