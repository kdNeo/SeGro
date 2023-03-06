package GUI;

import Database.DB;
import Utility.Helper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Vector;
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
public class Raw_Item extends javax.swing.JPanel {

    /**
     * Creates new form Raw_Item
     */
    public GRN parent;

    public Raw_Item() {
        initComponents();
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
        tbl_sell_items = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tf_buy_price = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tf_discount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tf_qty = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        tf_pcode = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        cb_date = new datechooser.beans.DateChooserCombo();

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(422, 524));
        setMinimumSize(new java.awt.Dimension(422, 524));
        setPreferredSize(new java.awt.Dimension(422, 524));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_sell_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_sell_items.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sell_itemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_sell_items);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 10, 390, 460));

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rawl Items", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Century Gothic", 1, 14))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(262, 418));
        jPanel1.setMinimumSize(new java.awt.Dimension(262, 418));
        jPanel1.setPreferredSize(new java.awt.Dimension(262, 418));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buying_Price");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        tf_buy_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_buy_priceActionPerformed(evt);
            }
        });
        tf_buy_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_buy_priceKeyTyped(evt);
            }
        });
        jPanel1.add(tf_buy_price, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 131, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Discount");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        tf_discount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_discountKeyTyped(evt);
            }
        });
        jPanel1.add(tf_discount, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 131, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Exp_Date");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Qty");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        tf_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_qtyKeyTyped(evt);
            }
        });
        jPanel1.add(tf_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 131, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 110, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Code");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        tf_pcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_pcodeActionPerformed(evt);
            }
        });
        tf_pcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_pcodeKeyTyped(evt);
            }
        });
        jPanel1.add(tf_pcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 131, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Refresh >>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 140, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("<< ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 140, -1));
        jPanel1.add(cb_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 110, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, 450));
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_sell_itemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sell_itemsMouseClicked
        loadSelectedProduct();
    }//GEN-LAST:event_tbl_sell_itemsMouseClicked

    private void loadSelectedProduct() {
        //clearFields(false);
        int selectedRow = tbl_sell_items.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        tf_pcode.setText(tbl_sell_items.getValueAt(selectedRow, 0).toString());
        tf_pcode.setEditable(false);

    }
    private void tf_buy_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_buy_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_buy_priceActionPerformed

    private void tf_buy_priceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_buy_priceKeyTyped
        Helper.numericDataAdd(tf_buy_price, evt);
    }//GEN-LAST:event_tf_buy_priceKeyTyped

    private void tf_discountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_discountKeyTyped
        Helper.numericDataAdd(tf_discount, evt);
    }//GEN-LAST:event_tf_discountKeyTyped

    private void tf_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_qtyKeyTyped
        Helper.numericDataAddOnlyDigits(tf_qty, evt);
    }//GEN-LAST:event_tf_qtyKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        clearFields(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void clearFields(boolean tablesToo) {
        tf_buy_price.setText(null);
        cb_date.setSelectedDate(Calendar.getInstance());
        tf_discount.setText(null);
        tf_pcode.setText(null);
        tf_qty.setText(null);
        tf_pcode.setEditable(true);
        if (tablesToo) {
            ((DefaultTableModel) tbl_sell_items.getModel()).setRowCount(0);
        }
    }

    private void tf_pcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_pcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_pcodeActionPerformed

    private void tf_pcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_pcodeKeyTyped
        Helper.codeIdd(tf_pcode, evt, 5);
    }//GEN-LAST:event_tf_pcodeKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadAllRawItems();
    }//GEN-LAST:event_jButton2ActionPerformed

    public void loadAllRawItems() {
        try (
                ResultSet rs = DB.search("select code,name from product where type='raw'");) {
            DefaultTableModel dtm = (DefaultTableModel) tbl_sell_items.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("code"));
                v.add(rs.getString("name"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        addSelectionToGrn();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void addSelectionToGrn() {
        String code = tf_pcode.getText();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product first.", "No product selected!", JOptionPane.QUESTION_MESSAGE);
            return;
        }
        String qty = tf_qty.getText();
        if (qty.isEmpty()) {
            tf_qty.grabFocus();
            return;
        }
        String buyingPrice = tf_buy_price.getText();
        if (buyingPrice.isEmpty()) {
            tf_buy_price.grabFocus();
            return;
        }

        if (!Helper.validateDate(cb_date)) {
            JOptionPane.showMessageDialog(this, "Please select a valid date!");
            cb_date.grabFocus();
            return;
        }
        String discount = tf_discount.getText();
        DefaultTableModel dtm = (DefaultTableModel) parent.tbl_grn.getModel();
        try (
                ResultSet search = DB.search("select * from product p inner join stock s on p.stock_idstock = s.idstock where code ='" + code + "' limit 1");) {
            if (!search.next()) {
                JOptionPane.showMessageDialog(this, "There was an error when adding.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal buy = new BigDecimal(buyingPrice);
            BigDecimal dis = BigDecimal.ZERO;
            BigDecimal tot = BigDecimal.ZERO;

            if (!discount.isEmpty()) {
                dis = new BigDecimal(discount);
                if (!(dis.compareTo(buy) == -1)) {
                    JOptionPane.showMessageDialog(this, "Discount is higher thatn the buying price!", "Invalid discount!", JOptionPane.QUESTION_MESSAGE);
                    tf_discount.grabFocus();
                    tf_discount.selectAll();
                    return;
                }
                tot = buy.subtract(dis).multiply(new BigDecimal(qty));
            } else {
                tot = buy.multiply(new BigDecimal(qty));
            }
            Vector v = new Vector();
            v.add(code);
            v.add(search.getString("p.name"));
            v.add(search.getString("s.stockname"));
            v.add(search.getString("p.type"));
            v.add(Helper.getDateForDb(cb_date.getSelectedDate().getTime()));
            v.add(qty);

            v.add(buy.setScale(2).toString());
            v.add(dis.setScale(2).toString());
            v.add("N/A");
            v.add("N/A");
            v.add(tot.setScale(2).toString());
            dtm.addRow(v);
            clearFields(false);
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo cb_date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_sell_items;
    private javax.swing.JTextField tf_buy_price;
    private javax.swing.JTextField tf_discount;
    private javax.swing.JTextField tf_pcode;
    private javax.swing.JTextField tf_qty;
    // End of variables declaration//GEN-END:variables
}
