package com.example.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JejuConnectionMaker implements ConnectionMaker{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
                Class.forName("com.mysql.cj.jdbc.Driver");
        //connection
        return DriverManager.getConnection("jdbc:mysql://localhost/practice?serverTimezone=Asia/Seoul"
                , "user", "1234");
    }
}
