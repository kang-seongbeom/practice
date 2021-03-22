package com.example.practice;

public class DaoFactory {
    public UserDao getUserDao() {
        return new UserDao(getConnectionMaker());
    }

    private JejuConnectionMaker getConnectionMaker(){
        return new JejuConnectionMaker();
    }
}
