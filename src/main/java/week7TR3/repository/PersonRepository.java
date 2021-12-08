package week7TR3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week7TR3.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByEmail(String email);
}
