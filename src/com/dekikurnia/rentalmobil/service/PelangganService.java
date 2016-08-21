/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.service;

import com.dekikurnia.rentalmobil.dao.PelangganDao;
import com.dekikurnia.rentalmobil.model.Pelanggan;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author deki kurnia
 */
public class PelangganService {
    
    private Connection connection;
    private PelangganDao pelangganDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            pelangganDao = new PelangganDao();
            pelangganDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Pelanggan Pelanggan) {
        try {
            connection.setAutoCommit(false);
            pelangganDao.save(Pelanggan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Pelanggan Pelanggan) {
        try {
            connection.setAutoCommit(false);
            pelangganDao.delete(Pelanggan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
     public void update(Pelanggan Pelanggan) {
        try {
            connection.setAutoCommit(false);
            pelangganDao.update(Pelanggan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
     
    public Pelanggan getPelanggan(String noKtp) {
        try {
            return pelangganDao.getPelanggan(noKtp);
        } catch (SQLException ex) {
            Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public List<Pelanggan> getPelanggan() {
        List<Pelanggan> list = new ArrayList<Pelanggan>();
        try {
            list = pelangganDao.getPelanggan();
        } catch (SQLException ex) {
            Logger.getLogger(PelangganService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
