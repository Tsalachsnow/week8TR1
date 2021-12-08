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
    @Autowired
    PersonRepository personRepository;


    public boolean createUser(Person person){
        boolean flag = false;

        try {
            //password encryption
            person.setPassword(PasswordHashing.encryptPassword(person.getPassword()));

            Person userData = personRepository.findPersonByEmail(person.getEmail());


            if(userData == null){
                System.out.println(person);
                personRepository.save(person);
                flag = true;
            } else flag = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  flag;
    }


    public Person getUser(String email, String password){

        Person userData = null;

        try {

            userData = personRepository.findPersonByEmail(email);

            if(!password.equals(PasswordHashing.decryptPassword(userData.getPassword()))){
                userData = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userData;
    }
}
