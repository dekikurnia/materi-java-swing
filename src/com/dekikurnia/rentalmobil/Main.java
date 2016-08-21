/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil;

import com.dekikurnia.rentalmobil.service.KaryawanService;
import com.dekikurnia.rentalmobil.service.ReportService;
import com.dekikurnia.rentalmobil.service.UserService;
import com.dekikurnia.rentalmobil.ui.main.FrameUtama;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author deki kurnia
 */
public class Main {
    
    private static FrameUtama frameUtama;
    private static KaryawanService karyawanService;
    private static UserService userService;
    private static ReportService reportService;

    public static UserService getUserService() {
        return userService;
    }
    
    public static KaryawanService getKaryawanService() {
        return karyawanService;
    }
     
    public static FrameUtama getFrameUtama() {
        return frameUtama;
    }
    
    public static ReportService getReportService() {
        return reportService;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
           UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
        }
        SwingUtilities.invokeLater(new Runnable() {
       
            @Override
            public void run() {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setDatabaseName("rental_mobil");
                dataSource.setServerName("localhost");
                dataSource.setPort(3306);
                dataSource.setUser("root");
                dataSource.setPassword("madrasah257");
                
                karyawanService = new KaryawanService();
                karyawanService.setDataSource(dataSource);
                
                userService = new UserService();
                userService.setDataSource(dataSource);
                
                reportService = new ReportService();
                reportService.setDataSource(dataSource);
                
                frameUtama = new FrameUtama();
                frameUtama.setVisible(true);
                frameUtama.showLoginPanel();                
            }
        });
    }
    
}
