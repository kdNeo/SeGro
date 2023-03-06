/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Database.beans.Customer;
import Database.tables.CustomerManager;
import Users.UserAccount;
import Utility.Helper;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sachintha
 */
public class Reservation extends javax.swing.JFrame {

    /**
     * Creates new form Reservation
     */
    public static final String RESERVATION_STATUS_RESERVED = "reserved";
    public static final String RESERVATION_STATUS_CHECKEDIN = "checkedin";
    public static final String RESERVATION_STATUS_CHECKEDOUT = "checkedout";
    public static final String RESERVATION_STATUS_CANCELLED = "cancelled";

    public enum ReservationClearState {
        CLEAR, HOLD
    }
    private HotelReservation hotelReservation;
    private RoomReservation roomReservation;
    private CateringReservation cateringReservation;

    public static Customer customer;

    public Reservation() {
        initComponents();
        addPanels();
        pack();
        Dimension size = getSize();

        setMinimumSize(new Dimension((int) (size.getWidth() + 20), (int) (size.getHeight() + 20)));
        addHelperListeners();
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        ActionListener updateClockAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jLabel12.setText(Helper.getDateTimeForDb(new Date()));
            }

        };
        Timer t = new Timer(1000, updateClockAction);

        t.start();
    }

    private void addPanels() {
        hotelReservation = new HotelReservation();
        hotelReservation.parentReservation = this;
        jPanel2.add(hotelReservation, "H");
        roomReservation = new RoomReservation();
        roomReservation.parentReservation = this;
        jPanel2.add(roomReservation, "R");
        cateringReservation = new CateringReservation();
        cateringReservation.parentReservation = this;
        jPanel2.add(cateringReservation, "C");
        jPanel2.add(new ReservationSearch(), "S");
    }

    public void clearFields() {
        if (this.hotelReservation != null) {
            this.hotelReservation.clearAllHotel(true);
        }
        if (this.cateringReservation != null) {
            this.cateringReservation.clearAllCatering(true);
        }
        if (this.roomReservation != null) {
            this.roomReservation.clearAllRoom(true);
        }
    }

    private void addHelperListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                Frames.showMain();
            }
        });
    }

    public static boolean isEmpty(JTextComponent c) {
        return (c.getText() == null || c.getText().trim().equals(""));
    }

    /*
    validate the customer fields and set the static Customer object of Reservation class
     */
    public boolean isCustomerValid() {
        if (customer != null) {
            return true;
        } else {
            if (!Helper.validateContact((tf_mobile.getText()))) {
                JOptionPane.showMessageDialog(this, "Contact number is not valid!", "", JOptionPane.QUESTION_MESSAGE);
                tf_mobile.grabFocus();
                tf_mobile.selectAll();
                return false;
            }
            if (isEmpty(tf_fname)) {
                JOptionPane.showMessageDialog(this, "Please enter customer first name!", "", JOptionPane.QUESTION_MESSAGE);
                tf_fname.grabFocus();
                return false;
            }
            if (isEmpty(tf_lname)) {
                JOptionPane.showMessageDialog(this, "Please enter customer last name!", "", JOptionPane.QUESTION_MESSAGE);
                tf_lname.grabFocus();
                return false;
            }
            if (!Helper.validateNic(tf_nic.getText())) {
                JOptionPane.showMessageDialog(this, "Please enter a valid nic.", "Invalid NIC", JOptionPane.QUESTION_MESSAGE);
                tf_nic.grabFocus();
                tf_nic.selectAll();
                return false;
            }

            customer = new Customer();
            customer.setTp(tf_mobile.getText());
            customer.setFname(tf_fname.getText());
            customer.setLname(tf_lname.getText());
            customer.setNic(tf_nic.getText());
            customer.setNo(tf_no.getText());
            customer.setStreet1(tf_street1.getText());
            customer.setStreet2(tf_street2.getText());
            customer.setCity(tf_city.getText());
        }
        return true;
    }

    /**
     * clear the customer fields
     */
    public void clearFieldsCustomer() {
        tf_no.setText(null);
        tf_mobile.setText(null);
        tf_fname.setText(null);
        tf_lname.setText(null);
        tf_nic.setText(null);
        tf_city.setText(null);
        tf_street1.setText(null);
        tf_street2.setText(null);
        setFieldsEditable(true);
        customer = null;
    }

    /**
     * make the customer fields editable or not
     *
     * @param edit
     */
    private void setFieldsEditable(boolean edit) {
        tf_no.setEditable(edit);
        tf_mobile.setEditable(edit);
        tf_fname.setEditable(edit);
        tf_nic.setEditable(edit);
        tf_lname.setEditable(edit);
        tf_city.setEditable(edit);
        tf_street2.setEditable(edit);
        tf_street1.setEditable(edit);
    }

    /**
     * find customer from database and set the customer to the static Customer
     * field
     */
    private void loadCustomer() {
        if (!Helper.validateContact(tf_mobile.getText())) {
            JOptionPane.showMessageDialog(this, "Please enter valid mobile.", "", JOptionPane.QUESTION_MESSAGE);
            tf_mobile.grabFocus();
            tf_mobile.selectAll();
            return;
        }
        Customer c = CustomerManager.getRow(tf_mobile.getText());
        if (c == null) {
            JOptionPane.showMessageDialog(this, "No customer found!", "", JOptionPane.INFORMATION_MESSAGE);
            tf_mobile.grabFocus();
            tf_mobile.selectAll();
        } else {
            tf_fname.setText(c.getFname());
            tf_lname.setText(c.getLname());
            tf_nic.setText(c.getNic());
            tf_no.setText(c.getNo());
            tf_city.setText(c.getCity());
            tf_street1.setText(c.getStreet1());
            tf_street2.setText(c.getStreet2());
            customer = c;
            setFieldsEditable(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lb_user = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        registationpnl = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        tf_street2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tf_street1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tf_mobile = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_fname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tf_no = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tf_lname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_nic = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tf_city = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel4.setMaximumSize(new java.awt.Dimension(1366, 768));
        jPanel4.setMinimumSize(new java.awt.Dimension(1366, 768));
        jPanel4.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(55, 63, 75));
        jPanel2.setMaximumSize(new java.awt.Dimension(1058, 610));
        jPanel2.setMinimumSize(new java.awt.Dimension(1058, 610));
        jPanel2.setPreferredSize(new java.awt.Dimension(1058, 610));
        jPanel2.setLayout(new java.awt.CardLayout());
        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 1050, 600));

        jPanel5.setBackground(new java.awt.Color(255, 204, 153));
        jPanel5.setMaximumSize(new java.awt.Dimension(1366, 768));
        jPanel5.setMinimumSize(new java.awt.Dimension(1366, 768));
        jPanel5.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel11.setText("RESERVATION");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 32, -1, -1));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setText("10.34 AM");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel13.setText("TIME");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jButton8.setText("CLOSE");
        jPanel5.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1552, 0, -1, -1));

        jButton9.setText("LOG OUT");
        jPanel5.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1374, 29, 116, 45));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel14.setText("LOG USER");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lb_user.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lb_user.setText("Name");
        jPanel5.add(lb_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setText("Hotel Reservation");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, 40));

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton13.setText("Search");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 120, 130, 40));

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton14.setText("HOME");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 80, 130, 60));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton15.setText("Catering Reservation");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 120, 190, 40));

        registationpnl.setMaximumSize(new java.awt.Dimension(1366, 580));
        registationpnl.setMinimumSize(new java.awt.Dimension(1366, 580));
        registationpnl.setPreferredSize(new java.awt.Dimension(1366, 580));
        registationpnl.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(55, 63, 75));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tf_street2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_street2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 200, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NO");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        tf_street1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_street1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 200, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Street 1");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        tf_mobile.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_mobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_mobileKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_mobileKeyTyped(evt);
            }
        });
        jPanel3.add(tf_mobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Street 2");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        tf_fname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 200, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("First Name");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        tf_no.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 200, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mobile");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        tf_lname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 200, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Last Name");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        tf_nic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_nic, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 200, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NIC");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        tf_city.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(tf_city, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 200, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("City");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 40, 20));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 80, -1));

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 100, 30));

        registationpnl.add(jPanel3, "card2");

        jPanel5.add(registationpnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 167, -1, 613));

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton16.setText("Room Reservation");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 180, 40));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel4, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        loadCustomer();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tf_mobileKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_mobileKeyTyped
        Helper.contactAdd(tf_mobile, evt);
    }//GEN-LAST:event_tf_mobileKeyTyped

    private void tf_mobileKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_mobileKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loadCustomer();
        }
    }//GEN-LAST:event_tf_mobileKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearFieldsCustomer();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        jPanel2.revalidate();
        ((CardLayout) jPanel2.getLayout()).show(jPanel2, "H");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        ((CardLayout) jPanel2.getLayout()).show(jPanel2, "S");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Frames.showMain();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        ((CardLayout) jPanel2.getLayout()).show(jPanel2, "C");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        ((CardLayout) jPanel2.getLayout()).show(jPanel2, "R");
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JLabel lb_user;
    private javax.swing.JPanel registationpnl;
    private javax.swing.JTextField tf_city;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_lname;
    private javax.swing.JTextField tf_mobile;
    private javax.swing.JTextField tf_nic;
    private javax.swing.JTextField tf_no;
    private javax.swing.JTextField tf_street1;
    private javax.swing.JTextField tf_street2;
    // End of variables declaration//GEN-END:variables
}
