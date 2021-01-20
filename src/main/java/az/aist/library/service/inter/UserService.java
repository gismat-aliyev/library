package az.aist.library.service.inter;

import az.aist.library.model.User;

import java.util.List;

public interface UserService {

    List<User> getUserListService();

    boolean deleteUserService(Long userId);

    boolean addUserService(User user);

    User getUserByIdService(Long userId);

    List<User> getUserByUsernameService(String username);

    boolean updateUserService(User user);

    User loginUserService(String username,String password);

    User getUserByLoginService(String username);
}
