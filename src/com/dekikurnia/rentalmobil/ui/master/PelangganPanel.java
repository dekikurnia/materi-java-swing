/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dekikurnia.rentalmobil.ui.master;

import com.dekikurnia.rentalmobil.Main;
import com.dekikurnia.rentalmobil.model.Pelanggan;
import com.dekikurnia.rentalmobil.ui.main.FrameUtama;
import hauw.widget.TableHeaderRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author deki kurnia
 */
public class PelangganPanel extends javax.swing.JInternalFrame {
    
    private List<Pelanggan> listPelanggan;
    private Pelanggan pelanggan;
    private TableRowSorter sorter;
    /**
     * Creates new form PelangganPanel
     */
    public PelangganPanel() {
        initComponents();
        refreshTable();
        initVars();
        kondisiAwal();
        initListeners();
    }
    
    private boolean validateForm() {
        if (textKtp.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "No Ktp tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textNama.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama pelanggan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textAlamat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textTelepon.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Telepon tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        textAlamat.setText("");
        textKtp.setText("");
        textNama.setText("");
        textTelepon.setText("");
    }
    
    private void refreshTable() {
        listPelanggan = Main.getPelangganService().getPelanggan();
        tablePelanggan.setModel(new PelangganTableModel(listPelanggan));
        for (int i = 0; i < tablePelanggan.getColumnCount(); i++) {
            tablePelanggan.getColumnModel().getColumn(i).setHeaderRenderer(new TableHeaderRenderer());
        }

    }
    
    private void initVars() {
        sorter = new TableRowSorter(tablePelanggan.getModel());
        tablePelanggan.setRowSorter(sorter);
    }

    private void enableForm(boolean status) {
        textKtp.setEnabled(status);
        textNama.setEnabled(status);
        textAlamat.setEnabled(status);
        textTelepon.setEnabled(status);
    }

    private void kondisiAwal() {
        toolbarPanel.kondisiAwal();
        enableForm(false);
    }

    private void kondisiSimpan() {
        toolbarPanel.kondisiSimpan();
        enableForm(true);
    }

    private void kondisiUbahOrHapus() {
        toolbarPanel.kondisiUbahOrHapus();
        enableForm(true);
        textKtp.setEnabled(false);
    }

    private void loadModelToForm() {
        if (pelanggan != null) {
            textKtp.setText(pelanggan.getNoKtp());
            textNama.setText(pelanggan.getNamaPelanggan());
            textAlamat.setText(pelanggan.getAlamatPelanggan());
            textTelepon.setText(pelanggan.getTelpPelanggan());
        }
    }

    private Pelanggan loadFormToModel() {
        Pelanggan p = new Pelanggan();
        p.setNoKtp(textKtp.getText());
        p.setNamaPelanggan(textNama.getText());
        p.setAlamatPelanggan(textAlamat.getText());
        p.setTelpPelanggan(textTelepon.getText());
        return p;
    }
    
     private void initListeners() {
        tablePelanggan.getSelectionModel().addListSelectionListener(new PelangganSelectionListener());

        toolbarPanel.getButtonTambah().addActionListener((ActionEvent e) -> {
            kondisiSimpan();
            textKtp.requestFocusInWindow();
        });

        toolbarPanel.getButtonSimpan().addActionListener((ActionEvent e) -> {
            if (validateForm()) {
                Main.getPelangganService().save(loadFormToModel());
                refreshTable();
                clearForm();
                kondisiAwal();
            }
        });

        toolbarPanel.getButtonHapus().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, 
                        "Anda yakin ingin menghapus data ini ?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.getRootFrame().dispose(); 
                } else if (response == JOptionPane.YES_OPTION) {
                    if (pelanggan != null) {
                    Main.getPelangganService().delete(pelanggan);
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tablePelanggan.clearSelection();
                }
                } 
            }
        });
        
        toolbarPanel.getButtonUbah().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Main.getPelangganService().update(loadFormToModel());
                    refreshTable();
                    clearForm();
                    kondisiAwal();
                    tablePelanggan.clearSelection();
                }
            }
        });
        
        toolbarPanel.getButtonBatal().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kondisiAwal();
                clearForm();
                tablePelanggan.clearSelection();
            }
        });
        
         textCari.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = textCari.getText();
                if (text == null) {
                    sorter.setRowFilter(null);
                } else {
                    char[] charArray = text.toCharArray();
                    String[] stringArray = new String[charArray.length];

                    for (int i = 0; i < stringArray.length; i++) {
                        stringArray[i] = "[" + Character.toUpperCase(charArray[i])
                                + Character.toLowerCase(charArray[i]) + "]";
                    }

                    String regex = "";
                    for (String string : stringArray) {
                        regex += string;
                    }

                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(regex, 0));
                    } catch (PatternSyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablePelanggan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textNama = new javax.swing.JTextField();
        textKtp = new javax.swing.JTextField();
        textTelepon = new javax.swing.JTextField();
        textAlamat = new javax.swing.JTextField();
        toolbarPanel = new com.dekikurnia.rentalmobil.ui.toolbar.MasterToolbar();
        jLabel5 = new javax.swing.JLabel();
        textCari = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Karyawan");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        tablePelanggan.setAutoCreateRowSorter(true);
        tablePelanggan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tablePelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablePelanggan);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("No Ktp");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Cari");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Alamat");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Telepon");

        textNama.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        textKtp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        textTelepon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        textTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textTeleponKeyReleased(evt);
            }
        });

        textAlamat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nama Pelanggan");

        textCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textNama, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textKtp, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(136, 136, 136)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(textCari, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textKtp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(textCari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNama, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textTeleponKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textTeleponKeyReleased
String telepon=textTelepon.getText();
if (telepon.length()>13)
{
    JOptionPane.showMessageDialog(null, "Nomor terlalu Panjang", "Pesan",JOptionPane.WARNING_MESSAGE);
}        // TODO add your handling code here:
    }//GEN-LAST:event_textTeleponKeyReleased

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        FrameUtama.pelangganPanel = null;
        dispose();
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePelanggan;
    private javax.swing.JTextField textAlamat;
    private javax.swing.JTextField textCari;
    private javax.swing.JTextField textKtp;
    private javax.swing.JTextField textNama;
    private javax.swing.JTextField textTelepon;
    private com.dekikurnia.rentalmobil.ui.toolbar.MasterToolbar toolbarPanel;
    // End of variables declaration//GEN-END:variables

   
    private class PelangganTableModel extends AbstractTableModel {

        private List<Pelanggan> rows;
        private List<String> columns;

        public PelangganTableModel(List<Pelanggan> rows) {
            this.rows = rows;
            columns = new ArrayList<String>();
            columns.add("No Ktp");
            columns.add("Nama Pelanggan");
            columns.add("Alamat");
            columns.add("Telepon");
        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column);
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return rows.get(rowIndex).getNoKtp();
                case 1:
                    return rows.get(rowIndex).getNamaPelanggan();
                case 2:
                    return rows.get(rowIndex).getAlamatPelanggan();
                case 3:
                    return rows.get(rowIndex).getTelpPelanggan();
                default:
                    return "";
            }
        }
    }

    private class PelangganSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = tablePelanggan.getSelectedRow();
            if (index >= 0) {
                kondisiUbahOrHapus();
                pelanggan= listPelanggan.get(index);
                loadModelToForm();
            }
        }
    }
}
