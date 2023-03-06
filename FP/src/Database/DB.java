/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Sachintha
 */
public class DB {

    private static Connection c;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String CON_STRING = "jdbc:mysql://localhost:3307/hotel_california";

    public static synchronized Connection getConnection() throws Exception {
        if (c == null) {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(CON_STRING, USERNAME, PASSWORD);
        }
        return c;
    }

    public static boolean iud(String query) {
        System.out.println(query);
        try {
            getConnection().createStatement().executeUpdate(query);
            return true;
        } catch (Exception e) {
            processException(e);
            return false;
        }
    }

    public static ResultSet search(String query) {
        System.out.println(query);
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (Exception e) {
            processException(e);
            return null;
        }
    }

    public static void processException(Exception e) {
        System.out.println(e.getMessage());
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("F:/exceptin_log.txt", true));
            out.write(new Date().toString() + " -- " + e.getMessage() + "\n");
            out.flush();
            out.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Couldn't log exception!\nPlease contact support. \n" + ex.getMessage(), "Ooops!", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static int iudReturnId(String sql) {
        try {
            PreparedStatement prepareStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            processException(e);
        }
        return -1;
    }
}
