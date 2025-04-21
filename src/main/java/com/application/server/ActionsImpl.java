package com.application.server;

import com.application.client.Actions;
import com.application.shared.UserDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ActionsImpl extends RemoteServiceServlet implements Actions {

    private static final Logger logger  = Logger.getLogger(ActionsImpl.class.getName());

    @Override
    public boolean login(UserDto user) {
        try (Connection con = DbConnection.getConnection()) {
            PreparedStatement ps;
            String query;
            /*query = "select * from users  where name='" + user.getName() + "' and password = '" + user.getPassword() + "'";

            //ps = con.prepareStatement(query);
            logger.info(query);
            ResultSet rs = statement.executeQuery(query);
            return rs.next();*/

            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                query = "SELECT password FROM users WHERE email = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, user.getEmail());
            } else if(user.getName() != null && !user.getName().isEmpty()){
                query = "SELECT password FROM users WHERE name = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, user.getName());
            } else {
                return false;
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedPass = rs.getString("password");
                return storedPass.equals(user.getPassword());
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean register(UserDto user) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "INSERT INTO users (name, email, password, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getContact());

            int result = ps.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> users = new ArrayList<>();
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserDto user = new UserDto();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setContact(rs.getString("contact"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public boolean delete(String email) {
        try (Connection connection = DbConnection.getConnection()) {
            String query = "delete from users where email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            int deletedRows = statement.executeUpdate();
            logger.info("deleted users : " + deletedRows);
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
