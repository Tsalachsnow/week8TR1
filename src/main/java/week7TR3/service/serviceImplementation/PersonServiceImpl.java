package week7TR3.service.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import week7TR3.annotations.EmailValidator;
import week7TR3.model.Person;
import week7TR3.repository.PersonRepository;
import week7TR3.service.PersonService;
import week7TR3.utils.PasswordHashing;

@Service
public class PersonServiceImpl implements PersonService {
    private final
    PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public boolean createUser(Person person){
        boolean flag = false;
        person.setPassword(PasswordHashing.encryptPassword(person.getPassword()));

        Person userData = personRepository.findPersonByEmail(person.getEmail());

        if(userData == null){
            System.out.println(person);
            personRepository.save(person);
            flag = true;
        }
        return  flag;
    }


    public Person getUser(String email, String password){

        Person userData = personRepository.findPersonByEmail(email);

            if(!password.equals(PasswordHashing.decryptPassword(userData.getPassword()))){
                userData = null;
            }

        return userData;
    }
}
