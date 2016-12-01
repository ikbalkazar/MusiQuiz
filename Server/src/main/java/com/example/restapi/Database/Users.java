package com.example.restapi.Database;

import com.example.restapi.Model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ikbalkazar on 29/11/16.
 */

public interface Users {
    String USERNAME = "username";
    String PASSWORD = "password";
    String SEC_QUESTION = "secquestion";
    String SEC_ANSWER = "secanswer";

    @Mapper(UserMapper.class)
    @SqlQuery("SELECT * FROM users WHERE " + USERNAME + " = :username")
    User getUser(@Bind("username") String username);

    @SqlUpdate("INSERT INTO users (" + USERNAME + "," + PASSWORD + "," + SEC_QUESTION + "," + SEC_ANSWER +  ")" + " VALUES (:username, :password, :secquestion, :secanswer)")
    void insertUser(@Bind("username") String username,
                    @Bind("password") String password,
                    @Bind("secquestion") String securityQuestion,
                    @Bind("secanswer") String securityAnswer);

    class UserMapper implements ResultSetMapper<User> {
        @Override
        public User map(int index, ResultSet resultSet, StatementContext context) throws SQLException {
            return new User(resultSet.getString(USERNAME), resultSet.getString(PASSWORD), resultSet.getString(SEC_QUESTION), resultSet.getString(SEC_ANSWER));
        }
    }
}

