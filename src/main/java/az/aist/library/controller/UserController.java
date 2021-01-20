package az.aist.library.controller;

import az.aist.library.model.User;
import az.aist.library.security.CustomUserDetails;
import az.aist.library.service.inter.AuthenticationFacade;
import az.aist.library.service.inter.UserService;
import az.aist.library.util.DateConverter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public UserService userService;

    public AuthenticationFacade authenticationFacade;

    public DateConverter dateConverter;

    @Autowired
    public UserController(UserService userService, AuthenticationFacade authenticationFacade,DateConverter dateConverter) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
        this.dateConverter = dateConverter;
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getPage() {
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
        val rs = userService.getUserByLoginService(customUserDetails.getUsername());
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getUserList() {
        List<User> userList = userService.getUserListService();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteUser", method = {RequestMethod.PUT, RequestMethod.GET})
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId) {
        boolean result = userService.deleteUserService(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/addUser", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> addUser(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "password") String password,
                                     @RequestParam(value = "fullName") String fullName,
                                     @RequestParam(value = "email",required = false) String email,
                                     @RequestParam(value = "phoneNumber",required = false) String phone,
                                     @RequestParam(value = "dateOfBirth") String dateOfBirth
                                     ) {
        boolean result = userService.addUserService(new User(null,
                username,
                password,
                email,
                phone,
                fullName,
                null,
                dateConverter.stringToDate(dateOfBirth),
                null,
                null));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserById", method = {RequestMethod.GET})
    public ResponseEntity<?> getUserById(@RequestParam("userId") Long userId) {
        User user = userService.getUserByIdService(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<?> getUserByUsername(@RequestParam("username") String username) {
        List<User> userList = userService.getUserByUsernameService(username);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUser", method = {RequestMethod.PUT,RequestMethod.GET})
    public ResponseEntity<?> updateUser(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "username") String username,
                                        @RequestParam(value = "fullName") String fullName,
                                        @RequestParam(value = "email",required = false) String email,
                                        @RequestParam(value = "phoneNumber",required = false) String phone,
                                        @RequestParam(value = "dateOfBirth") String dateOfBirth) {
        boolean result = userService.updateUserService(new User(userId,
                username,
                null,
                email,
                phone,
                fullName,
                null,
                dateConverter.stringToDate(dateOfBirth),
                null,
                null));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("username") String username,
                                       @RequestParam("password") String password) {
        User user = userService.loginUserService(username, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
