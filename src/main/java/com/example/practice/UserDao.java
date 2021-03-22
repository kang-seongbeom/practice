package com.example.practice;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public User get(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            //mysql
            //driver 로딩
            connection = dataSource.getConnection();
            //query
            preparedStatement = connection.prepareStatement("select * from userinfo where id=?");
            preparedStatement.setInt(1,id);
            //실행
            resultSet = preparedStatement.executeQuery();
            user = new User();
            if(resultSet.next()) {
                //결과 매핑
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            //자원해지
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return user;
    }

    public void insert(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //mysql
            //driver 로딩
            connection = dataSource.getConnection();

            //id를 outo increment하기 위해 Statement.RETURN_GENERATED_KEYS 필요
            //자동으로 증가하는 특성의 컬럼을 검색
            //query
            //select가 아니기 때문에 executeUpdate 해야됨 https://mozi.tistory.com/26
            preparedStatement = connection.prepareStatement("insert into userinfo(name,password) values (?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.executeUpdate();

            //auto increment된 id를 가져와 셋팅
            //resultest에 increment된 값이 담길 수 있게 함
            resultSet = preparedStatement.getGeneratedKeys();
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
        } finally {

            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void update(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("update userinfo set name=?, password=? where id=?");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setInt(3,user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }


    public void delete(int id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("delete from userinfo where id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
