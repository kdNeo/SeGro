package GUI;

import Database.DB;
import Utility.Helper;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sahan maduka
 */
public class Room extends javax.swing.JPanel {

    /**
     * Creates new form room
     */
    public Room() {
        initComponents();
        clearFields();
    }

    public void clearFields() {
        tf_bed.setText(null);
        tf_code.setText(null);
        tf_des.setText(null);
        tf_id.setText(null);
        tf_name.setText(null);
        tf_price.setText(null);
        loadCombo();
        loadAll(false, true);
        this.currentCode = null;
        this.currentRoom = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tf_id = new javax.swing.JTextField();
        tf_price = new javax.swing.JTextField();
        cb_cat = new javax.swing.JComboBox();
        tf_name = new javax.swing.JTextField();
        tf_code = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tf_des = new javax.swing.JTextArea();
        tf_bed = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1366, 580));
        setMinimumSize(new java.awt.Dimension(1366, 580));
        setPreferredSize(new java.awt.Dimension(1366, 580));

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setMaximumSize(new java.awt.Dimension(1366, 580));
        jPanel1.setMinimumSize(new java.awt.Dimension(1366, 580));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 580));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 83, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 83, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Price");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 87, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Catagary");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("BedCount");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Code");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 70, -1));

        tf_id.setEditable(false);
        jPanel1.add(tf_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 202, -1));

        tf_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_priceKeyTyped(evt);
            }
        });
        jPanel1.add(tf_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 202, -1));

        cb_cat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AC", "Non AC" }));
        jPanel1.add(cb_cat, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 202, -1));
        jPanel1.add(tf_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 202, -1));

        tf_code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_codeKeyTyped(evt);
            }
        });
        jPanel1.add(tf_code, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 202, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Catagary", "BedCount", "description", "Price", "Code", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 910, 420));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 530, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("Activate");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 530, -1, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Deactivate");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, -1, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton6.setText("Load All");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 530, -1, -1));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Description");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        tf_des.setColumns(20);
        tf_des.setRows(3);
        jScrollPane2.setViewportView(tf_des);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 202, 71));

        tf_bed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_bedKeyTyped(evt);
            }
        });
        jPanel1.add(tf_bed, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 70, 30));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, 210, -1));

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton8.setText("Active");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 530, -1, -1));

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton9.setText("Inactive");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 530, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return;
        }
        String cat = cb_cat.getSelectedItem().toString();
        if (cat.isEmpty()) {
            cb_cat.grabFocus();
            return;
        }
        String bed = tf_bed.getText();
        if (bed.isEmpty()) {
            tf_bed.grabFocus();
            return;
        }
        String price = tf_price.getText();
        if (price.isEmpty()) {
            tf_price.grabFocus();
            return;
        }
        String code = tf_code.getText();
        if (code.isEmpty()) {
            tf_code.grabFocus();
            return;
        }
        if (Helper.doesCodeExists(code)) {
            JOptionPane.showMessageDialog(this, "This code already exists!", null, JOptionPane.QUESTION_MESSAGE);
            tf_code.grabFocus();
            tf_code.selectAll();
            return;
        }
        String des = tf_des.getText();
        if (JOptionPane.showConfirmDialog(this, "Add room?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return;
        }
        if (DB.iud("INSERT into room  (name,description,price,bedcount,status,room_catagory_catagoryname,code)VALUE ('"
                + name + "','" + des + "','" + price + "','" + bed + "','" + 1 + "','" + cat + "','" + code + "')")) {
            JOptionPane.showMessageDialog(this, "Added successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't add!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        loadAll(true, true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.currentRoom == null) {
            JOptionPane.showMessageDialog(this, "Please select a room first.", "No room selected!", JOptionPane.QUESTION_MESSAGE);
            return;
        }
        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return;
        }
        String cat = cb_cat.getSelectedItem().toString();
        if (cat.isEmpty()) {
            cb_cat.grabFocus();
            return;
        }
        String bed = tf_bed.getText();
        if (bed.isEmpty()) {
            tf_bed.grabFocus();
            return;
        }
        String price = tf_price.getText();
        if (price.isEmpty()) {
            tf_price.grabFocus();
            return;
        }
        String code = tf_code.getText();
        if (code.isEmpty()) {
            tf_code.grabFocus();
            return;
        }
        if (!this.currentCode.equals(code) && Helper.doesCodeExists(code)) {
            JOptionPane.showMessageDialog(this, "This code already exists!", null, JOptionPane.QUESTION_MESSAGE);
            tf_code.grabFocus();
            tf_code.selectAll();
            return;
        }
        String des = tf_des.getText();
        if (JOptionPane.showConfirmDialog(this, "Update room?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return;
        }
        if (DB.iud("UPDATE room set name='" + name + "',room_catagory_catagoryname='" + cat + "',bedcount='" + bed + "',price='" + price + "',description='" + des + "',code='" + code + "' where idroom='" + this.currentRoom + "'")) {
            JOptionPane.showMessageDialog(this, "Updated successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't update!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int i = jTable1.getSelectedRow();
        if (i == -1) {
            return;
        }
        tf_id.setText(jTable1.getValueAt(i, 0).toString());
        tf_name.setText(jTable1.getValueAt(i, 1).toString());
        cb_cat.setSelectedItem(jTable1.getValueAt(i, 2).toString());
        tf_bed.setText(jTable1.getValueAt(i, 3).toString());
        tf_des.setText(jTable1.getValueAt(i, 4).toString());
        tf_price.setText(jTable1.getValueAt(i, 5).toString());
        tf_code.setText(jTable1.getValueAt(i, 6).toString());
        this.currentRoom = tf_id.getText();
        this.currentCode = tf_code.getText();
    }//GEN-LAST:event_jTable1MouseClicked

    private String currentCode;
    private String currentRoom;

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (this.currentRoom == null) {
            JOptionPane.showMessageDialog(this, "Please select a room first.", "No room selected!", JOptionPane.QUESTION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Deactivate room?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return;
        }
        if (DB.iud("UPDATE room set status='0' where idroom='" + this.currentRoom + "'")) {
            JOptionPane.showMessageDialog(this, "Updated successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't update!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void tf_bedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_bedKeyTyped
        Helper.codeIdd(tf_bed, evt, 2);
    }//GEN-LAST:event_tf_bedKeyTyped

    private void tf_priceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_priceKeyTyped
        Helper.numericDataAdd(tf_price, evt);
    }//GEN-LAST:event_tf_priceKeyTyped

    private void tf_codeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_codeKeyTyped
        Helper.codeIdd(tf_code, evt, 5);
    }//GEN-LAST:event_tf_codeKeyTyped

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        clearFields();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        loadAll(false, true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        loadAll(false, false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (this.currentRoom == null) {
            JOptionPane.showMessageDialog(this, "Please select a room first.", "No room selected!", JOptionPane.QUESTION_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Activate room?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return;
        }
        if (DB.iud("UPDATE room set status='1' where idroom='" + this.currentRoom + "'")) {
            JOptionPane.showMessageDialog(this, "Updated successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't update!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_cat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tf_bed;
    private javax.swing.JTextField tf_code;
    private javax.swing.JTextArea tf_des;
    private javax.swing.JTextField tf_id;
    private javax.swing.JTextField tf_name;
    private javax.swing.JTextField tf_price;
    // End of variables declaration//GEN-END:variables

    /**
     * call with all = true to get all the rooms. when all is true, second
     * parameter is ignored.
     *
     * @param all
     * @param active
     */
    public void loadAll(boolean all, boolean active) {
        String sql = null;
        if (all) {
            sql = "SELECT * from room";
        } else if (active) {
            sql = "SELECT * from room where status = '" + 1 + "'";
        } else {
            sql = "SELECT * from room where status = '" + 0 + "'";
        }
        try {
            ResultSet rs = DB.search(sql);

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("idroom"));
                v.add(rs.getString("name"));
                v.add(rs.getString("room_catagory_catagoryname"));
                v.add(rs.getString("bedcount"));
                v.add(rs.getString("description"));
                v.add(rs.getString("price"));
                v.add(rs.getString("code"));
                v.add(rs.getBoolean("status"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    public void loadCombo() {
        try (
                ResultSet rs = DB.search("select * from room_catagory");) {
            DefaultComboBoxModel dlm = new DefaultComboBoxModel();
            while (rs.next()) {
                dlm.addElement(rs.getString("catagoryname"));
            }
            cb_cat.setModel(dlm);
        } catch (Exception e) {
            DB.processException(e);
        }
    }
}
