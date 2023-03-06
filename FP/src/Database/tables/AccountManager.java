/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Employee;
import Users.UserAccount;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Sachintha
 */
public class AccountManager {

    public static boolean addRow(UserAccount ac) {
        String sql = "insert into useraccount (username,password,status,invoice,res,menu,grn,report,reg,employee_idemployee) "
                + "values('" + ac.getUserName() + "','" + ac.getPassword() + "','" + (ac.isStatus() ? 1 : 0) + "','" + (ac.isInvoice() ? 1 : 0)
                + "','" + (ac.isReservation() ? 1 : 0) + "','" + (ac.isMenu() ? 1 : 0) + "','" + (ac.isGrn() ? 1 : 0) + "','" + (ac.isReport() ? 1 : 0)
                + "','" + (ac.isRegistration() ? 1 : 0) + "','" + ac.getEmpId() + "')";
        return DB.iud(sql);
    }

    public static UserAccount getRow(int id) {
        String sql = "select * from useraccount where iduseraccount ='" + id + "' and iduseraccount<>1 limit 1";
        try (
                ResultSet s = DB.search(sql);) {
            if (s.next()) {
                UserAccount u = new UserAccount();
                u.setEmpId(s.getInt("employee_idemployee"));
                u.setGrn(s.getBoolean("grn"));
                u.setId(id);
                u.setInvoice(s.getBoolean("invoice"));
                u.setMenu(s.getBoolean("menu"));
                u.setPassword(s.getString("password"));
                u.setRegistration(s.getBoolean("reg"));
                u.setStatus(s.getBoolean("status"));
                u.setUserName(s.getString("username"));
                u.setReservation(s.getBoolean("res"));
                u.setReport(s.getBoolean("report"));
                return u;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static boolean updateRow(UserAccount ac, boolean pswToo) {
        String sql = "";
        if (pswToo) {
            sql = "update useraccount set "
                    + "password ='" + ac.getPassword() + "', "
                    + "status ='" + (ac.isStatus() ? 1 : 0) + "', "
                    + "invoice ='" + (ac.isInvoice() ? 1 : 0) + "', "
                    + "res ='" + (ac.isReservation() ? 1 : 0) + "', "
                    + "menu  ='" + (ac.isMenu() ? 1 : 0) + "', "
                    + "grn ='" + (ac.isGrn() ? 1 : 0) + "', "
                    + "report ='" + (ac.isReport() ? 1 : 0) + "', "
                    + "reg ='" + (ac.isRegistration() ? 1 : 0) + "' "
                    + "where iduseraccount='" + ac.getId() + "' limit 1";
        } else {
            sql = "update useraccount set "
                    + "status ='" + (ac.isStatus() ? 1 : 0) + "', "
                    + "invoice ='" + (ac.isInvoice() ? 1 : 0) + "', "
                    + "res ='" + (ac.isReservation() ? 1 : 0) + "', "
                    + "menu  ='" + (ac.isMenu() ? 1 : 0) + "', "
                    + "grn ='" + (ac.isGrn() ? 1 : 0) + "', "
                    + "report ='" + (ac.isReport() ? 1 : 0) + "', "
                    + "reg ='" + (ac.isRegistration() ? 1 : 0) + "' "
                    + "where iduseraccount='" + ac.getId() + "' limit 1";
        }
        return DB.iud(sql);
    }

    public static Employee getEmployeeOfAccount(int id) {
        String sql = "select employee_idemployee from useraccount where iduseraccount='" + id + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return EmployeeManager.getRow(search.getInt("employee_idemployee"));
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static ArrayList<Integer> getAccounts(int idEmployee) {
        String sql = "select iduseraccount from useraccount where employee_idemployee='" + idEmployee + "' ";
        ArrayList<Integer> list = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {

            while (search.next()) {
                list.add(search.getInt("iduseraccount"));
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<UserAccount> getAll() {
        String sql = "select iduseraccount from useraccount where iduseraccount<>1";
        ArrayList<UserAccount> list = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getInt("iduseraccount")));
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }
}
