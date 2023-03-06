/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import GUI.Frames;
import Users.Login;
import Users.UserAccount;
import Utility.Helper;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Sachintha
 */
public class LoginManager {

    public static boolean login(String username, String psw) {
        String sql = "select * from useraccount where username = '" + username + "' "
                + "and password = '" + psw + "' and status = '" + 1 + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                UserAccount u = new UserAccount();
                u.setEmpId(search.getInt("employee_idemployee"));
                u.setGrn(search.getBoolean("grn"));
                u.setId(search.getInt("iduseraccount"));
                u.setInvoice(search.getBoolean("invoice"));
                u.setMenu(search.getBoolean("menu"));
                u.setRegistration(search.getBoolean("reg"));
                u.setReport(search.getBoolean("report"));
                u.setReservation(search.getBoolean("res"));
                u.setStatus(search.getBoolean("status"));
                Login.logedUserAccount = u;
                addLog(u.getId());
                Frames.clearFields();
                return true;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return false;
    }

    private static void addLog(int accountId) {

        String sql = "insert into loghistory (logintime,useraccount_iduseraccount) "
                + "values( '" + Helper.getDateTimeForDb(new Date()) + "', '" + accountId + "' )";
        Login.logedUserAccount.setCurrentSessionId(DB.iudReturnId(sql));
    }

    public static boolean logOut() {

        String sql = "update loghistory set logouttime = '" + Helper.getDateTimeForDb(new Date()) + "' "
                + "where idloghistory = '" + Login.logedUserAccount.getCurrentSessionId() + "' limit 1";
        Frames.clearFields();
        return DB.iud(sql);
    }

}
