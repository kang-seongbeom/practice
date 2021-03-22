package com.example.practice;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {

    String name = "ksb";
    String password = "123456789";

    private static UserDao userDao;

    @BeforeAll
    public static void setUp(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("userDao",UserDao.class);
    }

    //제주

    //id를 사용하여 id, username, password정보 가져오기
    @Test
    public void get() throws SQLException, ClassNotFoundException {


        Integer id = 1;

        User user = userDao.get(id);
        assertThat(user.getId(),is(id));
        assertThat(user.getName(),is(name));
        assertThat(user.getPassword(),is(password));
    }

    //get으로 하니깐, 내가 일일이 db에 유저 정보를 입력해 주어야 해서 너무 불편하다.
    //user와 password 정보를 받아 올 테니깐 db에 알아서 저장시켜 줘.
    @Test
    public void insert() throws SQLException, ClassNotFoundException {
        //front에서 user 정보를 받아 오는 부분
        User user = new User();
        user.setName(name);
        user.setPassword(password);

        //받아온 user 정보를 dao를 통해 db에 저장


        userDao.insert(user);

        //db에 저장된 유저 id가 0보다 큰지 확인
        //assertThat(user.getId(), greaterThan(0));

        //입력 정보가 맞는지 확인
        User insertedUser = userDao.get(user.getId());
        assertThat(insertedUser.getName(), is(name));
        assertThat(insertedUser.getPassword(), is(password));

    }

    //수정
    @Test
    public void update() throws SQLException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        String updateName="kan";
        String updatePassword="1234";
        user.setName(updateName);
        user.setPassword(updatePassword);

        userDao.update(user);

        User updateUser = userDao.get(user.getId());
        assertThat(updateUser.getPassword(), is(updateName));
        assertThat(updateUser.getPassword(), is(updatePassword));
    }

    @Test
    public void delete() throws SQLException {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userDao.insert(user);

        userDao.delete(user.getId());

        User deleteUser = userDao.get(user.getId());

        assertThat(deleteUser, IsNull.nullValue());

    }
}
