package com.application.server;

import com.application.client.Actions;
import com.application.shared.UserDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionsImpl extends RemoteServiceServlet implements Actions {

    @Override
    public boolean login(UserDto user) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean register(UserDto user) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "INSERT INTO users (username, email, password, contact) VALUES (?, ?, ?, ?)";
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
        try(Connection con = DbConnection.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs =  ps.executeQuery();

            while (rs.next()) {
                UserDto user = new UserDto();
                user.setName(rs.getString("username"));
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

}
