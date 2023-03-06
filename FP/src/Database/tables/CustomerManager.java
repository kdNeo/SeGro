/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Customer;
import java.sql.ResultSet;

/**
 *
 * @author Sachintha
 */
public class CustomerManager {

    public static Customer getRow(String tp) {
        String sql = "select * from customer where tp = '" + tp + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {

            if (search.next()) {
                Customer customer = new Customer();
                customer.setTp(tp);
                customer.setFname(search.getString("fname"));
                customer.setLname(search.getString("lname"));
                customer.setNic(search.getString("nic"));
                //customer.setGender(search.getString("gender"));
                customer.setNo(search.getString("no"));
                customer.setStreet1(search.getString("street1"));
                customer.setStreet2(search.getString("street2"));
                customer.setCity(search.getString("city"));
                return customer;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static boolean addRow(Customer customer) {
        String sql = "insert into customer (tp,fname,lname,nic,no,street1,street2,city) "
                + "values ('" + customer.getTp() + "','" + customer.getFname() + "','" + customer.getLname()
                + "','" + customer.getNic() + "','"
                + customer.getNo() + "','" + customer.getStreet1() + "','" + customer.getStreet2() + "','" + customer.getCity() + "')";
        return DB.iud(sql);
    }

    public static boolean isCustomerExists(String tp) {
        String sql = "select 1 from customer where tp = '" + tp + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return true;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return false;
    }
}
