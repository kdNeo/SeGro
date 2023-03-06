package GUI;

import Database.DB;
import Utility.Helper;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Intel
 */
public class Product1 extends javax.swing.JPanel {

    /**
     * Creates new form product1
     */
    public Product1() {
        initComponents();
        combo();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cb_stock = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        cb_type = new javax.swing.JComboBox();
        tf_code = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(415, 479));
        setMinimumSize(new java.awt.Dimension(415, 479));
        setPreferredSize(new java.awt.Dimension(415, 479));
        setRequestFocusEnabled(false);

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setMaximumSize(new java.awt.Dimension(415, 479));
        jPanel1.setMinimumSize(new java.awt.Dimension(415, 479));
        jPanel1.setPreferredSize(new java.awt.Dimension(415, 479));

        jPanel2.setBackground(new java.awt.Color(55, 63, 75));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
        jPanel2.add(tf_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 120, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stock");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jPanel2.add(cb_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 120, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        cb_type.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cb_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "sell", "raw" }));
        jPanel2.add(cb_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 120, 30));

        tf_code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_codeKeyTyped(evt);
            }
        });
        jPanel2.add(tf_code, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 120, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Code");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, -1));

        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButton4.setText("clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addNew();
    }//GEN-LAST:event_jButton1ActionPerformed

    private boolean addNew() {
        if (this.currentProduct != null) {
            return false;
        }
        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return false;
        }
        String code = tf_code.getText();
        if (code.isEmpty()) {
            tf_code.grabFocus();
            return false;
        }
        String stock = cb_stock.getSelectedItem().toString();
        if (stock.isEmpty()) {
            cb_stock.grabFocus();
            return false;
        }
        String type = cb_type.getSelectedItem().toString();

        if (DB.iud("insert into product (name,stock_idstock,type,code) values('" + name + "',(select idstock from stock where stockname = '" + stock + "' limit 1),'" + type + "','" + code + "')")) {
            JOptionPane.showMessageDialog(this, "Added successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            return true;
        }
        return false;
    }

    public void clearFields() {
        tf_code.setText(null);
        tf_name.setText(null);
        currentProduct = null;
        currentCode = null;
        loadStock();
    }

    private void loadStock() {
        try (
                ResultSet rs = DB.search("select stockname from stock");) {
            DefaultComboBoxModel dcm = new DefaultComboBoxModel();
            while (rs.next()) {
                dcm.addElement(rs.getString("stockname"));
            }
            cb_stock.setModel(dcm);
        } catch (Exception e) {
            DB.processException(e);
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        search();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tf_codeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_codeKeyTyped
        Helper.codeIdd(tf_code, evt, 5);
    }//GEN-LAST:event_tf_codeKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearFields();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void search() {
        String code = tf_code.getText();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid product code!");
            tf_code.grabFocus();
            return;
        }
        try (
                ResultSet rs = DB.search("select * from product where code = '" + code + "' limit 1");) {
            if (rs.next()) {
                tf_code.setText(rs.getString("code"));
                tf_name.setText(rs.getString("name"));
                combo();
                ResultSet search = DB.search("select stockname from stock where idstock = '" + rs.getInt("stock_idstock") + "' limit 1");
                if (search.next()) {
                    cb_stock.setSelectedItem(search.getString("stockname"));
                }
                cb_type.setSelectedItem(rs.getString("type"));
                this.currentProduct = rs.getString("idproduct");
                this.currentCode = rs.getString("code");
            } else {
                JOptionPane.showMessageDialog(this, "No product found!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            DB.processException(e);
        }

    }
    private String currentCode;

    private void update() {
        if (currentProduct == null) {
            JOptionPane.showMessageDialog(this, "Please select a product first!");
            return;
        }
        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return;
        }
        String code = tf_code.getText();
        if (code.isEmpty()) {
            tf_code.grabFocus();
            return;
        }
        if (!this.currentCode.equals(code) && Helper.doesCodeExists(code)) {
            JOptionPane.showMessageDialog(this, "This code already exists!", "", JOptionPane.QUESTION_MESSAGE);
            tf_code.grabFocus();
            tf_code.selectAll();
            return;
        }

        String stock = cb_stock.getSelectedItem().toString();
        if (stock.isEmpty()) {
            cb_stock.grabFocus();
            return;
        }
        String type = cb_type.getSelectedItem().toString();
        String sql = "update product set "
                + "name='" + name + "', "
                + "stock_idstock= (select idstock from stock where stockname='" + stock + "' limit 1) , "
                + "code = '" + code + "' ";
        if (DB.iud(sql)) {
            JOptionPane.showMessageDialog(this, "Updated successfully!");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't update!", null, JOptionPane.ERROR_MESSAGE);
        }
    }
    private String currentProduct;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_stock;
    private javax.swing.JComboBox cb_type;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField tf_code;
    private javax.swing.JTextField tf_name;
    // End of variables declaration//GEN-END:variables

    public void combo() {
        try (
                ResultSet rs = DB.search("select stockname from stock");) {
            DefaultComboBoxModel dcm = new DefaultComboBoxModel();
            while (rs.next()) {
                dcm.addElement(rs.getString("stockname"));
            }
            cb_stock.setModel(dcm);
        } catch (Exception e) {
            DB.processException(e);
        }
    }
}
