package az.aist.library;

import az.aist.library.model.User;
import az.aist.library.repository.inter.UserRepository;
import az.aist.library.util.DateConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    void getUserList(){
        System.out.println(userRepository.getUserList());
    }

    @Test
    void addUser(){
        User user = new User();
        user.setUsername("user1");
        user.setPassword("12345");
        user.setEmail("test@gmail.com");
        user.setPhoneNumber("994991234567");
        user.setFullName("Test User");
        user.setDateOfBirth(new Date());
        System.out.println(user);
//        System.out.println(userRepository.addUser(user));
    }

    @Test
    void deleteUser(){
        System.out.println(userRepository.deleteUser(3L));
    }

    @Test
    void getUserById(){
        System.out.println(userRepository.getUserById(3L));
    }

    @Test
    void getUserByUsername(){
        System.out.println(userRepository.getUserByUsername("user"));
    }

    @Test
    void loginUser(){
        System.out.println(userRepository.loginUser("user1","12345"));
    }

    @Autowired
    public DateConverter dateConverter;

    @Test
    void updateUser(){
        User user = new User();
        user.setUserId(3L);
        user.setUsername("user3");
        user.setEmail("test@gmail.com");
        user.setPhoneNumber("994991234599");
        user.setFullName("Test User");
        user.setDateOfBirth(dateConverter.stringToDate("2021-01-17"));
        System.out.println(userRepository.updateUser(user));
    }
}
