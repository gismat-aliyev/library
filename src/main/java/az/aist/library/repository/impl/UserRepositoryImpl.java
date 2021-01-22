package az.aist.library.repository.impl;

import az.aist.library.model.Transactions;
import az.aist.library.model.User;
import az.aist.library.repository.inter.TransactionRepository;
import az.aist.library.repository.inter.UserRepository;
import az.aist.library.repository.mapper.UserMapper;
import az.aist.library.repository.sql.LibrarySQL;
import az.aist.library.util.MD5Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserMapper userMapper;

    public MD5Generator md5Generator;

    public TransactionRepository transactionRepository;

    @Autowired
    public UserRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              UserMapper userMapper,
                              MD5Generator md5Generator,
                              TransactionRepository transactionRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userMapper = userMapper;
        this.md5Generator = md5Generator;
        this.transactionRepository= transactionRepository;
    }

    @Override
    public List<User> getUserList() {
        try{
            List<User> userList;
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("status", 1);
            mapSqlParameterSource.addValue("roleId", 1);
            userList = namedParameterJdbcTemplate.query(LibrarySQL.GET_USER_LIST, mapSqlParameterSource, userMapper::getUserList);
            return userList;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        try{
            List<Transactions> pendingTr = transactionRepository.getPendingTransactionByUserId(userId);
            List<Transactions> deliveryTr = transactionRepository.getDeliveryTransactionByUserId(userId);
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId", userId);
            mapSqlParameterSource.addValue("status", 0);
            int count = namedParameterJdbcTemplate.update(LibrarySQL.DELETE_USER, mapSqlParameterSource);
            if (count > 0){
                pendingTr.forEach(i ->{
                    transactionRepository.deleteTransaction(i.getTrId());
                });
                deliveryTr.forEach(i->{
                    transactionRepository.deleteTransaction(i.getTrId());
                });
            }
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public boolean addUser(User user) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("username", user.getUsername().toLowerCase());
            mapSqlParameterSource.addValue("password", md5Generator.generateMd5(user.getPassword()));
            mapSqlParameterSource.addValue("email", user.getEmail());
            mapSqlParameterSource.addValue("phone", user.getPhoneNumber());
            mapSqlParameterSource.addValue("fullName", user.getFullName());
            mapSqlParameterSource.addValue("dateOfBirth", user.getDateOfBirth());
            int count = namedParameterJdbcTemplate.update(LibrarySQL.ADD_USER, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public User getUserById(Long userId) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", userId);
            User user = namedParameterJdbcTemplate.query(LibrarySQL.GET_USER_BY_ID, mapSqlParameterSource, userMapper::getUser);
            return user;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public List<User> getUserByUsername(String username) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            String name = "%" + username.toLowerCase() + "%";
            mapSqlParameterSource.addValue("username", name);
            List<User> user = namedParameterJdbcTemplate.query(LibrarySQL.GET_USER_BY_USERNAME, mapSqlParameterSource, userMapper::getUserList);
            return user;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public boolean updateUser(User user) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userId",user.getUserId());
            mapSqlParameterSource.addValue("username", user.getUsername().toLowerCase());
            mapSqlParameterSource.addValue("email", user.getEmail());
            mapSqlParameterSource.addValue("phone", user.getPhoneNumber());
            mapSqlParameterSource.addValue("fullName", user.getFullName());
            mapSqlParameterSource.addValue("dateOfBirth", user.getDateOfBirth());
            int count = namedParameterJdbcTemplate.update(LibrarySQL.UPDATE_USER, mapSqlParameterSource);
            return count > 0;
        }catch (Exception ex){
            log.error(""+ex);
            return false;
        }

    }

    @Override
    public User loginUser(String username, String password) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("username", username);
            mapSqlParameterSource.addValue("password", md5Generator.generateMd5(password));
            User user = namedParameterJdbcTemplate.query(LibrarySQL.LOGIN_USER, mapSqlParameterSource, userMapper::getUser);
            return user;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }

    @Override
    public User getUserByLogin(String username) {
        try{
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("username", username);
            User user = namedParameterJdbcTemplate.query(LibrarySQL.GET_USER_BY_LOGIN, mapSqlParameterSource, userMapper::getUser);
            return user;
        }catch (Exception ex){
            log.error(""+ex);
            return null;
        }

    }
}
