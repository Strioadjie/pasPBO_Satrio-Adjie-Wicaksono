/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_mobil;
import com.koneksi.koneksi;
import com.view.form_mobil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
public class model_mobil implements controller_mobil {
    String jk;

    @Override
    public void Simpan(form_mobil mobil) throws SQLException {
        if (mobil.rbLaki.isSelected()) {
            jk = "Laki-laki";
        }else{
            jk = "Perempuan";
        }
        try {
            Connection con = koneksi.getcon();
            String sql = "Insert Into rental Values (?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement (sql);
            prepare.setString(1, mobil.txtNama.getText());
            prepare.setString(2, mobil.txtSewa.getText());
            prepare.setString(3, jk);
            prepare.setString(4, (String) mobil.cbJenis.getSelectedItem());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diSimpan");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(mobil);
            mobil.setLebarKolom();
        }
    }

    @Override
    public void baru(form_mobil mobil) throws SQLException {
        mobil.txtNama.setText("");
        mobil.txtSewa.setText("");
        mobil.rbLaki.setSelected(true);
        mobil.cbJenis.setSelectedIndex(0);

    }

    @Override
    public void ubah(form_mobil mobil) throws SQLException {
              if (mobil.rbLaki.isSelected()) {
            jk = "Laki-Laki";
        } else {
            jk = "Perempuan";
        }
        try {
            Connection con = koneksi.getcon();
            String sql = "UPDATE rental SET lama_sewa=?, kelamin_peminjam=?, "
                    +"jenis_mobil=? WHERE nama_peminjam=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(4, mobil.txtNama.getText());
            prepare.setString(1, mobil.txtSewa.getText());
            prepare.setString(2, jk);
            prepare.setString(3, (String)mobil.cbJenis.getSelectedItem());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Ubah");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(mobil);
            mobil.setLebarKolom();
            baru(mobil);
        }  
    }

    @Override
    public void Tampil(form_mobil mobil) throws SQLException {
      mobil.tblmodel.getDataVector().removeAllElements();
        mobil.tblmodel.fireTableDataChanged();
        
        try {
            Connection con = koneksi.getcon();
            Statement stt = con.createStatement();
            String sql = "SELECT * FROM rental ORDER BY nama_peminjam ASC";
            ResultSet rs = stt.executeQuery(sql);
            while (rs.next()) {                
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                mobil.tblmodel.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTabel(form_mobil mobil) throws SQLException {
 try {
            int pilih = mobil.tabel.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            mobil.txtNama.setText(mobil.tblmodel.getValueAt(pilih, 0).toString());
            mobil.txtSewa.setText(mobil.tblmodel.getValueAt(pilih, 1).toString());
            mobil.cbJenis.setSelectedItem(mobil.tblmodel.getValueAt(pilih, 3).toString());
            jk = String.valueOf(mobil.tblmodel.getValueAt(pilih, 2));
        }catch (Exception e) {
            
        }
        //memberi nilai jk pada radio button
        if (mobil.rbLaki.getText().equals(jk)) {
            mobil.rbLaki.setSelected(true);
        } else {
            mobil.rbPerempuan.setSelected(true);
        }
    }    

    @Override
    public void Hapus(form_mobil mobil) throws SQLException {
try {
            Connection con = koneksi.getcon();
        String sql = "DELETE FROM rental WHERE nama_peminjam=?";
        PreparedStatement prepare = con.prepareStatement(sql);
        prepare.setString(1, mobil.txtNama.getText());
        prepare.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(mobil);
            mobil.setLebarKolom();
            baru(mobil);
        }    
}
}
   

 
