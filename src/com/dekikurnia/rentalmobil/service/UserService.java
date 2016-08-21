/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.service;

import com.dekikurnia.rentalmobil.dao.UserDao;
import com.dekikurnia.rentalmobil.model.Role;
import com.dekikurnia.rentalmobil.model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author deki kurnia
 */
public class UserService {
    
    private UserDao userDao;
    private Connection connection;
    
     public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            userDao = new UserDao();
            userDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
     
     public User getUser(String idUser) {
        try {
            return userDao.getUser(idUser);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public boolean checkLogin(String userName, String password) {
        try {
            return userDao.checkLogin(userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public Role getUserRole(String userName) {
        try {
            return userDao.getUserRole(userName);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
