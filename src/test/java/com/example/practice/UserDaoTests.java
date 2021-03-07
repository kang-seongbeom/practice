package com.example.practice;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {

    //id를 사용하여 id, username, password정보 가져오기
    @Test
    public void get() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        Integer id = 1;
        String name = "hulk";
        String password = "1234";

        User user = userDao.get(id);
        assertThat(user.getId(),is(id));
        assertThat(user.getName(),is(name));
        assertThat(user.getPassword(),is(password));
    }
}
