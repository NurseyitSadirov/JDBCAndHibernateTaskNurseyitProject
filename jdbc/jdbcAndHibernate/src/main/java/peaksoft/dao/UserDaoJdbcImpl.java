package peaksoft.dao;

import org.dom4j.rule.RuleSet;
import org.hibernate.type.StringNVarcharType;
import peaksoft.model.User;
import peaksoft.util.Util;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {


    public void createUsersTable() {
        String sql = "create table if not exists users(" +
                "id serial primary key," +
                "name varchar," +
                "last_name varchar," +
                "age smallint);";
        try (Connection connection = Util.getconnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
    String sql = "drop table if exists users;";
    try (Connection connection = Util.getconnection();
        Statement statement = connection.createStatement()){
        statement.executeUpdate(sql);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    public void saveUser(String name, String lastName, byte age) {
    String sql = "insert into users(name, last_name, age) VALUES (?,?,?);";
    try (Connection connection = Util.getconnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)){
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,lastName);
        preparedStatement.setByte(3,age);
        preparedStatement.execute();
        System.out.println("Successfully saved!");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    public void removeUserById(long id) {
    String sql = "delete users where id = ?";
    try (Connection connection  = Util.getconnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
        preparedStatement.setLong(1,id);
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users;";
        try (Connection connection = Util.getconnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                users.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "delete from users;";
        try (Connection connection = Util.getconnection();
            Statement statement = connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}