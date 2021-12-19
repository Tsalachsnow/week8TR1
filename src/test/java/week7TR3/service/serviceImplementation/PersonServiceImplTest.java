package week7TR3.service.serviceImplementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import week7TR3.model.Person;
import week7TR3.repository.PersonRepository;
import week7TR3.service.PersonService;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
        person.setFirstname("emeka");
        person.setLastname("enyiocha");
        person.setPassword("er0swccd-snow");
        person.setEmail("snowshaddy@gmail.com");
        person.setGender("male");
        person.setDob("02/04/1995");
    }

    @Test
    void createUser() {
        //mock personRepository

        when(personRepository.save(any(Person.class))).thenReturn(person);

        //call the method to be tested

        personServiceImpl.createUser(person);

                verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void getUser() {
        //mock personRepository
        when(personRepository.findPersonByEmail(anyString())).thenReturn(person);
        //then call the method to be tested

        Person foundUser = personRepository.findPersonByEmail("prosper@email.com");
        personServiceImpl.getUser(person.getEmail(),person.getPassword());
        //assertions

        assertNotNull(foundUser);
        assertEquals("snowshaddy@gmail.com", person.getEmail());

//        verify(personRepository, times(1)).findPersonByEmail(anyString());
    }
}