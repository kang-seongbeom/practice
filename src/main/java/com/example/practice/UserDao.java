package com.example.practice;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public User get(Integer id) throws ClassNotFoundException, SQLException {
        //mysql
        //driver 로딩
        Connection connection = connectionMaker.getConnection();
        //query
        PreparedStatement preparedStatement
                = connection.prepareStatement("select * from userinfo where id=?");
        preparedStatement.setInt(1,id);
        //실행
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        //결과 매핑
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        //자원해지
        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    public void insert(User user) throws ClassNotFoundException, SQLException {
        //mysql
        //driver 로딩
        Connection connection = connectionMaker.getConnection();

        //id를 outo increment하기 위해 Statement.RETURN_GENERATED_KEYS 필요
        //자동으로 증가하는 특성의 컬럼을 검색
        //query
        //select가 아니기 때문에 executeUpdate 해야됨 https://mozi.tistory.com/26
        PreparedStatement preparedStatement
                = connection.prepareStatement("insert into userinfo(name,password) values (?,?)",Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.executeUpdate();

        //auto increment된 id를 가져와 셋팅
        //resultest에 increment된 값이 담길 수 있게 함
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        System.out.println("resultSet : "+resultSet);
        System.out.println("\nresultSet next : "+resultSet.next());

        System.out.printf("진입 전");
        //오류나는 이유는 mysql 생성 당시 id 값을 auto increment 설정을 하지 않아서 오류나는 것이다.
        if(resultSet.next()) {
            System.out.printf("진입");
            user.setId(resultSet.getInt(1));
            System.out.println("111111111111111111111");
        }
        System.out.println("222222222222222222222");

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
