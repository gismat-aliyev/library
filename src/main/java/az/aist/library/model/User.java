package az.aist.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private Date registerDate;
    private Date dateOfBirth;
    private String status;
    private UserRole role;
}
