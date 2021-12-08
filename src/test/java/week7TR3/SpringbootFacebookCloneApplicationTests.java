package week7TR3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import week7TR3.utils.PasswordHashing;

@SpringBootTest
class SpringbootFacebookCloneApplicationTests {

    private String password = "testing0password123";
    private String secured = "dGVzdGluZzBwYXNzd29yZDEyMw==";

    @Test
    void passwordDecrypt() {
        Assertions.assertEquals(password, PasswordHashing.decryptPassword(secured));
        Assertions.assertNotNull(PasswordHashing.decryptPassword(secured));
    }

    @Test
    void passwordEncrypt(){
        Assertions.assertEquals(secured, PasswordHashing.encryptPassword(password));
        Assertions.assertNotNull(PasswordHashing.encryptPassword(password));
    }

}
