/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.service;

import com.dekikurnia.rentalmobil.dao.KaryawanDao;
import com.dekikurnia.rentalmobil.model.Karyawan;
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
public class KaryawanService {
    
    private Connection connection;
    private KaryawanDao karyawanDao;
    
    public void setDataSource(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            karyawanDao = new KaryawanDao();
            karyawanDao.setConnection(connection);
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void save(Karyawan karyawan) {
        try {
            connection.setAutoCommit(false);
            karyawanDao.save(karyawan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void delete(Karyawan karyawan) {
        try {
            connection.setAutoCommit(false);
            karyawanDao.delete(karyawan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
     public void update(Karyawan karyawan) {
        try {
            connection.setAutoCommit(false);
            karyawanDao.update(karyawan);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
     
    public Karyawan getKaryawan(String nik) {
        try {
            return karyawanDao.getKaryawan(nik);
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public List<Karyawan> getKaryawan() {
        List<Karyawan> list = new ArrayList<Karyawan>();
        try {
            list = karyawanDao.getKaryawan();
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
