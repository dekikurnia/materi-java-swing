/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.dao;

import com.dekikurnia.rentalmobil.model.Karyawan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deki kurnia
 */
public class KaryawanDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into t_karyawan values(?,?,?,?)";
    private final String deleteQuery = "delete from t_karyawan where nik=?";
    private final String updateQuery = "update t_karyawan set nama=?, alamat=?, telepon=? where nik=?";
    private final String getByIdQuery = "select * from t_karyawan where nik=?";
    private final String getAllQuery = "select * from t_karyawan";
    
    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public void save(Karyawan karyawan) throws SQLException {
        insertStatement.setString(1, karyawan.getNik());
        insertStatement.setString(2, karyawan.getNamaKaryawan());
        insertStatement.setString(3, karyawan.getAlamatKaryawan());
        insertStatement.setString(4, karyawan.getTelpKaryawan());
        insertStatement.executeUpdate();
    }
    
    public void delete(Karyawan karyawan) throws SQLException {
        deleteStatement.setString(1, karyawan.getNik());
        deleteStatement.executeUpdate();
    }
    
    public void update(Karyawan karyawan) throws SQLException {
        updateStatement.setString(1, karyawan.getNamaKaryawan());
        updateStatement.setString(2, karyawan.getAlamatKaryawan());
        updateStatement.setString(3, karyawan.getTelpKaryawan());
        updateStatement.setString(4, karyawan.getNik());
        updateStatement.executeUpdate();
    }

    public Karyawan getKaryawan(String nik) throws SQLException {
        getByIdStatement.setString(1, nik);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            Karyawan karyawan = new Karyawan();
            karyawan.setNik(rs.getString("nik"));
            karyawan.setNamaKaryawan(rs.getString("nama"));
            karyawan.setAlamatKaryawan(rs.getString("alamat"));
            karyawan.setTelpKaryawan(rs.getString("telepon"));
            return karyawan;
        }
        return null;
    }
    
     public List<Karyawan> getKaryawan() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<Karyawan> list = new ArrayList<Karyawan>();
        while (rs.next()) {
            Karyawan karyawan = new Karyawan();
            karyawan.setNik(rs.getString("nik"));
            karyawan.setNamaKaryawan(rs.getString("nama"));
            karyawan.setAlamatKaryawan(rs.getString("alamat"));
            karyawan.setTelpKaryawan(rs.getString("telepon"));
            list.add(karyawan);
        }
        return list;
    }
}

