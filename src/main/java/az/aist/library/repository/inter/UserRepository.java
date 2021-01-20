package az.aist.library.repository.inter;

import az.aist.library.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getUserList();

    boolean deleteUser(Long userId);

    boolean addUser(User user);

    User getUserById(Long userId);

    List<User> getUserByUsername(String username);

    boolean updateUser(User user);

    User loginUser(String username,String password);

    User getUserByLogin(String username);
}
