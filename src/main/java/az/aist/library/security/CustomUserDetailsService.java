package az.aist.library.security;

import az.aist.library.model.User;
import az.aist.library.repository.inter.UserRepository;
import az.aist.library.service.inter.UserService;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDomain = userService.getUserByLoginService(username);
        val user = new CustomUserDetails(userDomain);
//        log.info("User : {}", user);
        return user;
    }


}
