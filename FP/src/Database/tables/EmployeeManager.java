/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Employee;
import Utility.Helper;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author Sachintha
 */
public class EmployeeManager {

    public static ResultSet getAll(boolean isActive) {
        StringBuilder sb = new StringBuilder("select * from employee ");
        if (isActive) {
            sb.append("where status=1 ");
        }
        return DB.search(sb.toString());
    }

    public static ResultSet getAllLikeTp(String tp) {
        String sb = "select * from employee where tp like '" + tp + "%'";
        return DB.search(sb);
    }

    public static ResultSet getAllTpLikeTp(String tp) {
        String sql = "select tp from employee where ct like '" + tp + "%'";
        return DB.search(sql);
    }

    public static Employee getRow(int id) {
        StringBuilder sb = new StringBuilder("select * from employee  where idemployee='" + id + "' limit 1");
        ResultSet rs = DB.search(sb.toString());
        try {
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(id);
                emp.setNic(rs.getString("nic"));
                emp.setFname(rs.getString("fname"));
                emp.setLname(rs.getString("lname"));
                emp.setTp(rs.getString("tp"));
                emp.setStatus(rs.getInt("status"));
                emp.setGender(rs.getString("gender"));
                emp.setDate(rs.getDate("date"));
                emp.setDescriptioin(rs.getString("description"));
                emp.setCity(rs.getString("city"));
                emp.setNo(rs.getString("no"));
                emp.setRole(rs.getString("role"));
                emp.setStreet1(rs.getString("street1"));
                emp.setStreet2(rs.getString("street2"));
                return emp;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static int getIdByNic(String nic) {
        String sql = "select idemployee from employee where nic = '" + nic + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getInt("idemployee");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static boolean addRow(Employee employee) {
        String sql = "insert into employee (nic,fname,lname,tp,status,date,description,no,street1,street2,city,role,gender)"
                + "values ('" + employee.getNic() + "','" + employee.getFname() + "','" + employee.getLname()
                + "','" + employee.getTp() + "','" + employee.getStatus() + "','" + Helper.getDateForDb(employee.getDate())
                + "','" + employee.getDescriptioin() + "','" + employee.getNo() + "','" + employee.getStreet1() + "','" + employee.getStreet2() + "','" + employee.getCity() + "','" + employee.getRole() + "','" + employee.getGender() + "')";

        return DB.iud(sql);
    }

    public static boolean employeeExists(String nic) {
        String sql = "select 1 from employee where tp = '" + nic + "' ";
        try {
            if (DB.search(sql).next()) {
                return true;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return false;
    }

    public static boolean updateRow(Employee e) {

        String sql = "update employee set "
                + "nic = '" + e.getNic() + "', "
                + "fname =  '" + e.getFname() + "',"
                + "lname = '" + e.getLname() + "',  "
                + "tp = '" + e.getTp() + "', "
                + "status = '" + e.getStatus() + "', "
                + "description = '" + e.getDescriptioin() + "', "
                + "no = '" + e.getNo() + "', "
                + "street1= '" + e.getStreet1() + "', "
                + "street2= '" + e.getStreet2() + "', "
                + "city = '" + e.getCity() + "', "
                + "role = '" + e.getRole() + "', "
                + "gender = '" + e.getGender() + "' "
                + "where idemployee = '" + e.getId() + "' "
                + "limit 1 ";

        try {
            return DB.iud(sql);
        } catch (Exception e1) {
            DB.processException(e1);
        }
        return false;

    }

    public static boolean addEmployeeRole(String role) {
        String sql = "insert into employee_role(name) values('" + role + "')";
        return DB.iud(sql);
    }

    public static ResultSet getAllRoles() {
        String sql = "select  name from employee_role order by name";
        return DB.search(sql);
    }

}
