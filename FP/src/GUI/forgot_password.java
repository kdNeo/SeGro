package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import Database.DB;

//import validation.MailApp;
import validate_Email.Validation;

/**
 *
 * @author Kaveesha Nadun
 */
public class forgot_password extends javax.swing.JFrame {

    /**
     * Creates new form forgot_password
     */
    public forgot_password() {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        eml = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        bckToLogin = new javax.swing.JButton();
        submit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 0, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Type your email here");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 143, 310, 10));

        eml.setBackground(new java.awt.Color(51, 0, 51));
        eml.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        eml.setForeground(new java.awt.Color(255, 255, 255));
        eml.setBorder(null);
        eml.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                emlKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emlKeyTyped(evt);
            }
        });
        jPanel1.add(eml, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 109, 310, 28));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 40));

        bckToLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bckToLogin.setText("Back to Login");
        bckToLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bckToLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bckToLoginActionPerformed(evt);
            }
        });
        jPanel1.add(bckToLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, 30));

        submit.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        submit.setText("Submit");
        submit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 90, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 255, 255));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 200, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Close Window_30px.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Address Book_20px_1.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        refresh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        refresh.setText("Refresh");
        refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        jPanel1.add(refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 100, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void emlKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emlKeyTyped

    }//GEN-LAST:event_emlKeyTyped

    private void emlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emlKeyReleased
        String valideml = Validation.isValidemail(eml.getText());
        //Validation.checkLength(evt,eml.getText(),15);
        if (valideml.equals("Valid Email ID")) {
            eml.setForeground(Color.WHITE);
        } else {
            eml.setForeground(Color.YELLOW);
        }
    }//GEN-LAST:event_emlKeyReleased

    private void bckToLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bckToLoginActionPerformed
        LoginWindow l = new LoginWindow();
        l.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_bckToLoginActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        try {
            String host = "smtp.gmail.com";
            String user = "kaveeshanadun7@gmail.com";
            String pass = "chong8855";
            //String to = "sahanchamara561@gmail.com";
            String from = "kaveeshanadun7@gmail.com";
            String subject = "This is confirmation ";
            String messageText = "Your Password is :";
            boolean sessionDebug = false;

            ResultSet rs = DB.search("select password from useraccount where email LIKE'" + eml.getText().trim() + "'");
            //ResultSet rs = DB.search("select email from leg'" + eml.getText().trim() + "'");

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            if (rs.next()) {
                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                String fetchedPassword = rs.getString("password");

                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(sessionDebug);
                Message msg = new MimeMessage(mailSession);

                msg.setFrom(new InternetAddress(from));
                InternetAddress[] address = {new InternetAddress(eml.getText())};
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setText(messageText + fetchedPassword);

                Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, user, pass);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                jLabel2.setText("Message Sent Successfully");

                jLabel2.setForeground(Color.LIGHT_GRAY);
                // eml.setText(null);
                //System.out.println("message send successfully");

            } else {
                jLabel2.setText("Invalid Email");

                jLabel2.setForeground(Color.red);

            }
        } catch (Exception ex) {
            jLabel2.setText("Message not sent ");
            System.out.println(ex);
        }


    }//GEN-LAST:event_submitActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        this.dispose();
        LoginWindow l = new LoginWindow();
        l.setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        eml.setText(null);
        jLabel2.setText(null);
    }//GEN-LAST:event_refreshActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(forgot_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(forgot_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(forgot_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(forgot_password.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new forgot_password().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bckToLogin;
    private javax.swing.JTextField eml;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton refresh;
    private javax.swing.JButton submit;
    // End of variables declaration//GEN-END:variables
}
