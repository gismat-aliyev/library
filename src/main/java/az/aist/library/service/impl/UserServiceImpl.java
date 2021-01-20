package az.aist.library.service.impl;

import az.aist.library.model.User;
import az.aist.library.repository.inter.UserRepository;
import az.aist.library.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserListService() {
        List<User> userList = new ArrayList<>();
        try{
            userList = userRepository.getUserList();
        }catch (Exception ex){
            log.info(""+ex);
        }
        return userList;
    }

    @Override
    public boolean deleteUserService(Long userId) {
        boolean isExist = false;
        try{
            isExist = userRepository.deleteUser(userId);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public boolean addUserService(User user) {
        boolean isExist = false;
        try{
            isExist = userRepository.addUser(user);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public User getUserByIdService(Long userId) {
        User user = new User();
        try{
            user = userRepository.getUserById(userId);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return user;
    }

    @Override
    public List<User> getUserByUsernameService(String username) {
        List<User> userList = new ArrayList<>();
        try{
            userList = userRepository.getUserByUsername(username);
        }catch (Exception ex){
            log.info(""+ex);
        }
        return userList;
    }

    @Override
    public boolean updateUserService(User user) {
        boolean isExist = false;
        try{
            isExist = userRepository.updateUser(user);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return isExist;
    }

    @Override
    public User loginUserService(String username, String password) {
        User user = new User();
        try{
            user = userRepository.loginUser(username,password);
        }catch (Exception ex){
            log.error(""+ex);
        }
        return user;
    }

    @Override
    public User getUserByLoginService(String username) {
        User user = new User();
        try{
            user = userRepository.getUserByLogin(username);
        }catch (Exception ex){
            log.info(""+ex);
        }
        return user;
    }
}
