/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp;

import Database.tables.MenuManager;
import Utility.Helper;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Sachintha
 */
public class FP {

    /**
     * @param args the command line arguments
     */
    /* public static void main(String[] args) {
    }
     */
}

class CenterComponent {

    public static void main(String[] args) {
        MenuManager.getIdByName("menu one");

    }

    public CenterComponent() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    System.out.println(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel content = new JPanel(new GridBagLayout());
                content.setBackground(Color.GREEN);
                content.setBorder(new EmptyBorder(20, 20, 20, 20));
                frame.setContentPane(content);
                frame.add(new LoginPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class LoginPane extends JPanel {

        public LoginPane() {
            setLayout(new GridBagLayout());
            setBorder(new TitledBorder("Login"));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JLabel("Username:"), gbc);
            gbc.gridy++;
            add(new JLabel("Password:"), gbc);

            gbc.gridx++;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            add(new JTextField(10), gbc);
            gbc.gridy++;
            add(new JTextField(10), gbc);

            gbc.gridx = 1;
            gbc.gridy++;
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            add(new JButton("Login"), gbc);
            gbc.gridx++;
            add(new JButton("Cancel"), gbc);
        }

    }

}
