/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.dao;

import com.dekikurnia.rentalmobil.model.Pelanggan;
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
public class PelangganDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getAllStatement;
    private final String insertQuery = "insert into t_pelanggan values(?,?,?,?)";
    private final String deleteQuery = "delete from t_pelanggan where no_ktp=?";
    private final String updateQuery = "update t_pelanggan set nama=?, alamat=?, telepon=? where no_ktp=?";
    private final String getByIdQuery = "select * from t_pelanggan where no_ktp=?";
    private final String getAllQuery = "select * from t_pelanggan";
    
    public void setConnection(Connection c) throws SQLException {
        connection = c;
        insertStatement = connection.prepareStatement(insertQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
        updateStatement = connection.prepareStatement(updateQuery);
        getByIdStatement = connection.prepareStatement(getByIdQuery);
        getAllStatement = connection.prepareStatement(getAllQuery);
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public void save(Pelanggan pelanggan) throws SQLException {
        insertStatement.setString(1, pelanggan.getNoKtp());
        insertStatement.setString(2, pelanggan.getNamaPelanggan());
        insertStatement.setString(3, pelanggan.getAlamatPelanggan());
        insertStatement.setString(4, pelanggan.getTelpPelanggan());
        insertStatement.executeUpdate();
    }
    
    public void delete(Pelanggan pelanggan) throws SQLException {
        deleteStatement.setString(1, pelanggan.getNoKtp());
        deleteStatement.executeUpdate();
    }
    
    public void update(Pelanggan pelanggan) throws SQLException {
        updateStatement.setString(1, pelanggan.getNamaPelanggan());
        updateStatement.setString(2, pelanggan.getAlamatPelanggan());
        updateStatement.setString(3, pelanggan.getTelpPelanggan());
        updateStatement.setString(4, pelanggan.getNoKtp());
        updateStatement.executeUpdate();
    }

    public Pelanggan getPelanggan(String nik) throws SQLException {
        getByIdStatement.setString(1, nik);
        ResultSet rs = getByIdStatement.executeQuery();
        if (rs.next()) {
            Pelanggan pelanggan = new Pelanggan();
            pelanggan.setNoKtp(rs.getString("no_ktp"));
            pelanggan.setNamaPelanggan(rs.getString("nama"));
            pelanggan.setAlamatPelanggan(rs.getString("alamat"));
            pelanggan.setTelpPelanggan(rs.getString("telepon"));
            return pelanggan;
        }
        return null;
    }
    
     public List<Pelanggan> getPelanggan() throws SQLException {
        ResultSet rs = getAllStatement.executeQuery();
        List<Pelanggan> list = new ArrayList<Pelanggan>();
        while (rs.next()) {
            Pelanggan pelanggan = new Pelanggan();
            pelanggan.setNoKtp(rs.getString("no_ktp"));
            pelanggan.setNamaPelanggan(rs.getString("nama"));
            pelanggan.setAlamatPelanggan(rs.getString("alamat"));
            pelanggan.setTelpPelanggan(rs.getString("telepon"));
            list.add(pelanggan);
        }
        return list;
    }
}

