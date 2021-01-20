package az.aist.library.repository.mapper;

import az.aist.library.model.User;
import az.aist.library.model.UserRole;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public List<User> getUserList(ResultSet rs) throws SQLException {
        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            UserRole userRole = new UserRole();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setFullName(rs.getString("full_name"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setRegisterDate(rs.getDate("added_date"));
            userRole.setRoleName(rs.getString("role_name"));
            user.setRole(userRole);
            userList.add(user);
        }
        return userList;
    }

    public User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        if (rs.next()) {
            UserRole userRole = new UserRole();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setFullName(rs.getString("full_name"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setRegisterDate(rs.getDate("added_date"));
            userRole.setRoleName(rs.getString("role_name"));
            user.setRole(userRole);
        }
        return user;
    }
}
