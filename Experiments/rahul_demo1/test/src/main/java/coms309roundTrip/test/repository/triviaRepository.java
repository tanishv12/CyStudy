package coms309roundTrip.test.repository;


import coms309roundTrip.test.models.trivia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface triviaRepository extends JpaRepository<trivia, Long> {

}
