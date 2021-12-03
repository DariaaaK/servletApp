package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UsersRepository {

//    public static void main(String[] args) {
//        getConnection();
//
//       User users = new User();
//       users.setLogin("adbcvgdwwwn");
//       users.setPassword("123");
//        save(users);
//    }

    public static Connection getConnection() {

        Connection connection = null;
        String url = "jdbc:postgresql://localhost:5432/employee";
        String user = "postgres";
        String password = "postgres";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return connection;
    }

    public static int save (User users) {
        int status = 0;

        try {
            Connection connection = UsersRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into usersreg(login,password) values (?,?)");
            ps.setString(1,users.getLogin());
            ps.setString(2,users.getPassword());

            status = ps.executeUpdate();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static User getUserByLogin(String login){

        User users = new User();

        try {
            Connection connection = UsersRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from usersreg where login=?");
            ps.setString(2, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                users.setId(rs.getInt(1));
                users.setLogin(rs.getString(2));
                users.setPassword(rs.getString(3));

            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return users;
    }

    public static List<User> getAllUsers() {

        List<User> listUsers = new ArrayList<>();

        try {
            Connection connection = UsersRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from usersreg");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                User users = new User();

                users.setId(rs.getInt(1));
                users.setLogin(rs.getString(2));
                users.setPassword(rs.getString(3));

                listUsers.add(users);


            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    public  static  ResultSet getAllUsersRs() throws SQLException {
        ResultSet rs = null;
         Connection connection = UsersRepository.getConnection();
         PreparedStatement ps = connection.prepareStatement("select usersreg.login, usersreg.password from usersreg");
         rs = ps.executeQuery();
         return rs;
    }
}