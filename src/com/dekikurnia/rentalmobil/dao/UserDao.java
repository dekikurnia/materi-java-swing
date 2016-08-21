/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.dao;

import com.dekikurnia.rentalmobil.model.Role;
import com.dekikurnia.rentalmobil.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author deki kurnia
 */
public class UserDao {
    
    private Connection connection;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getPasswordStatement;
    private PreparedStatement getRoleStatement;
    private final String getByIdQuery = "select * from t_user where id_user = ?";
    private final String getPasswordQuery = "select password from t_user where id_user = ?";
    private final String getRoleQuery = "select role from t_user where id_user=?";
    
     public void setConnection(Connection c) throws SQLException {
        connection = c;
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getPasswordStatement = connection.prepareStatement(getPasswordQuery);
    }
    
    public User getUser(String idUser) throws SQLException {
        getByIdStatement.setString(1, idUser);
        ResultSet rs = getByIdStatement.executeQuery();
        if(rs.next()) {
            User user = new User();
            user.setIdUser(rs.getString("id_user"));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role")));
            return user;
        }
        return null;
    }
    
    public boolean checkLogin(String userName, String password) throws SQLException {
        getPasswordStatement.setString(1, userName);
        ResultSet rs = getPasswordStatement.executeQuery();
        if(rs.next()) {
            if(password.equals(rs.getString("password"))) {
                return true;
            }
        }
        return false;
    }
    
    public Role getUserRole(String userName) throws SQLException {
        getRoleStatement.setString(1, userName);
        ResultSet rs = getRoleStatement.executeQuery();
        if(rs.next()) {
            Role role = Role.valueOf(rs.getString("role"));
            return role;
        }
        return null;
    }
}
