package com.example.practice;

import java.sql.*;

public class UserDao {

    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext){
        this.jdbcContext = jdbcContext;
    }

    public User get(Integer id) throws SQLException {
        Object[] params = new Object[] {id};
        String sql = "select * from userinfo where id=?";
        return jdbcContext.get(params, sql);
    }
    public void insert(User user) throws SQLException {
        Object[] params = new Object[] { user.getName(), user.getPassword()};
        String sql = "insert into userinfo(name,password) values (?,?)";
        jdbcContext.insert(user, params, sql);
    }
    public void update(User user) {
        Object[] params = new Object[] {user.getId(), user.getName(), user.getPassword()};
        String sql = "update userinfo set name=?, password=? where id=?";
        jdbcContext.update(sql, params);
    }
    public void delete(int id) {
        Object[] params = new Object[] {id};
        String sql = "delete from userinfo where id=?";
        jdbcContext.update(sql, params);
    }

}
