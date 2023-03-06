package GUI;

import Database.DB;
import Utility.Helper;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sahan maduka
 */
public class Supplier extends javax.swing.JPanel {

    public GRN parent;

    /**
     * Creates new form supplier
     */
    public Supplier() {
        initComponents();
    }
    public static String JTextField1;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        tf_tp = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tf_desc = new javax.swing.JTextArea();
        tf_no = new javax.swing.JTextField();
        tf_str1 = new javax.swing.JTextField();
        tf_str2 = new javax.swing.JTextField();
        tf_city = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(422, 524));
        setMinimumSize(new java.awt.Dimension(422, 524));
        setPreferredSize(new java.awt.Dimension(422, 524));

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setMaximumSize(new java.awt.Dimension(422, 524));
        jPanel1.setMinimumSize(new java.awt.Dimension(422, 524));
        jPanel1.setPreferredSize(new java.awt.Dimension(422, 524));

        jPanel2.setBackground(new java.awt.Color(55, 63, 75));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SUPPLIER", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(422, 524));
        jPanel2.setMinimumSize(new java.awt.Dimension(422, 524));
        jPanel2.setPreferredSize(new java.awt.Dimension(422, 524));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Telephone No");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Description");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NO");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Street1");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Street2");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("City");

        tf_tp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_tpKeyTyped(evt);
            }
        });

        tf_desc.setColumns(20);
        tf_desc.setRows(5);
        jScrollPane1.setViewportView(tf_desc);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_no)
                            .addComponent(jScrollPane1)
                            .addComponent(tf_name)
                            .addComponent(tf_tp)
                            .addComponent(tf_str2)
                            .addComponent(tf_city)
                            .addComponent(tf_str1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tf_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_str1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_str2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        addSupplier();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        clearFields();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tf_tpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_tpKeyTyped
        Helper.contactAdd(tf_tp, evt);
    }//GEN-LAST:event_tf_tpKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updateSupplier();
    }//GEN-LAST:event_jButton3ActionPerformed

    private boolean updateSupplier() {
        if (this.currentSupplier == null) {
            JOptionPane.showMessageDialog(this, "Please select a supplier first!", "No supplier selected!", JOptionPane.QUESTION_MESSAGE);
            return false;
        }

        String tp = tf_tp.getText();
        if (tp.isEmpty()) {
            tf_tp.grabFocus();

            return false;
        }
        if (!Helper.validateContact(tp)) {
            JOptionPane.showMessageDialog(this, "Please enter valid contact!");
            tf_tp.grabFocus();
            tf_tp.selectAll();
        }

        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return false;
        }

        String des = tf_desc.getText();
        if (des.isEmpty()) {
            tf_desc.grabFocus();
            return false;
        }

        String no = tf_no.getText();
        if (no.isEmpty()) {
            tf_no.grabFocus();
            return false;
        }
        String street1 = tf_str1.getText();
        if (street1.isEmpty()) {
            tf_str1.grabFocus();
            return false;
        }
        String street2 = tf_str2.getText();
        if (street2.isEmpty()) {
            tf_str2.grabFocus();
            return false;
        }
        String city = tf_city.getText();
        if (city.isEmpty()) {
            tf_city.grabFocus();
            return false;
        }
        if (!this.currentSupplier.equals(name)) {
            String sql = "select 1 from supplier where name = '" + name + "' limit 1";
            try (
                    ResultSet search = DB.search(sql);) {
                if (search.next()) {
                    JOptionPane.showMessageDialog(this, "This supplier already exists!");
                    tf_name.grabFocus();
                    tf_name.selectAll();
                    return false;
                }

            } catch (Exception e) {
                DB.processException(e);
            }
        }
        if (JOptionPane.showConfirmDialog(this, "Update supplier?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            return false;
        }
        String sql = "update supplier set tp ='" + tp + "', "
                + "name ='" + name + "', "
                + "description='" + des + "', "
                + "no ='" + no + "', "
                + "street1='" + street1 + "', "
                + "street2='" + street2 + "', "
                + "city='" + city + "' limit 1";
        if (DB.iud(sql)) {
            JOptionPane.showMessageDialog(this, "Successfully updated.", "Cuccess!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            parent.combo();
            return true;
        }
        JOptionPane.showMessageDialog(this, "Operation failed .", "", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    private boolean addSupplier() {
        String tp = tf_tp.getText();
        if (tp.isEmpty()) {
            tf_tp.grabFocus();

            return false;
        }
        if (!Helper.validateContact(tp)) {
            JOptionPane.showMessageDialog(this, "Please enter valid contact!");
            tf_tp.grabFocus();
            tf_tp.selectAll();
        }

        String name = tf_name.getText();
        if (name.isEmpty()) {
            tf_name.grabFocus();
            return false;
        }

        String des = tf_desc.getText();
        if (des.isEmpty()) {
            tf_desc.grabFocus();
            return false;
        }

        String no = tf_no.getText();
        if (no.isEmpty()) {
            tf_no.grabFocus();
            return false;
        }
        String street1 = tf_str1.getText();
        if (street1.isEmpty()) {
            tf_str1.grabFocus();
            return false;
        }
        String street2 = tf_str2.getText();
        if (street2.isEmpty()) {
            tf_str2.grabFocus();
            return false;
        }
        String city = tf_city.getText();
        if (city.isEmpty()) {
            tf_city.grabFocus();
            return false;
        }
        String sql = "select 1 from supplier where name = '" + name + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                JOptionPane.showMessageDialog(this, "This supplier already exists!");
                tf_name.grabFocus();
                tf_name.selectAll();
                return false;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        if (DB.iud("insert into supplier (tp,name,description,status,regdate,no,street1,street2,city) values("
                + "'" + tp + "','" + name + "','" + des + "','" + 1 + "','" + Helper.getDateTimeForDb(new Date()) + "','" + no + "','" + street1 + "','" + street2 + "','" + city + "' )")) {
            JOptionPane.showMessageDialog(this, "Successfully added.", "Cuccess!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            parent.combo();
            return true;
        }
        JOptionPane.showMessageDialog(this, "Operation failed .", "", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public void clearFields() {
        tf_city.setText(null);
        tf_desc.setText(null);
        tf_name.setText(null);
        tf_no.setText(null);
        tf_str1.setText(null);
        tf_str2.setText(null);
        tf_tp.setText(null);
        this.currentSupplier = null;
        this.currentTp = null;
    }

    public void loadSupplierDetails(String name) {
        try (
                ResultSet rs = DB.search("select * from supplier where name = '" + name + "' limit 1");) {
            if (rs.next()) {
                tf_city.setText(rs.getString("city"));
                tf_desc.setText(rs.getString("description"));
                tf_name.setText(rs.getString("name"));
                tf_no.setText(rs.getString("no"));
                tf_str1.setText(rs.getString("street1"));
                tf_str2.setText(rs.getString("street2"));
                tf_tp.setText(rs.getString("tp"));
                this.currentSupplier = rs.getString("name");
                this.currentTp = rs.getString("tp");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }
    private String currentSupplier;
    private String currentTp;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tf_city;
    private javax.swing.JTextArea tf_desc;
    private javax.swing.JTextField tf_name;
    private javax.swing.JTextField tf_no;
    private javax.swing.JTextField tf_str1;
    private javax.swing.JTextField tf_str2;
    private javax.swing.JTextField tf_tp;
    // End of variables declaration//GEN-END:variables

}