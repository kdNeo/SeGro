/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Menu;
import Database.beans.MenuItem;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Sachintha
 */
public class MenuItemManager {

    public static MenuItem getRowByCode(String code, boolean onlyActive) {
        String sql = "";
        if (onlyActive) {
            sql = "select * from menu_item where code ='" + code + "' and status='1' limit 1 ";
        } else {
            sql = "select * from menu_item where code ='" + code + "'  limit 1 ";
        }
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {
                MenuItem m = new MenuItem();
                m.setId(rs.getInt("idmenu_item"));
                m.setName(rs.getString("itemname"));
                m.setPrice(rs.getBigDecimal("price"));
                m.setCode(rs.getString("code"));
                m.setStatus(rs.getBoolean("status"));
                m.setCategory(rs.getString("menu_item_category_name"));
                return m;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static MenuItem getRowById(int id, boolean onlyActive) {
        String sql = "";
        if (onlyActive) {
            sql = "select * from menu_item where idmenu_item ='" + id + "' and status='1' limit 1 ";
        } else {
            sql = "select * from menu_item where idmenu_item ='" + id + "'  limit 1 ";
        }
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {
                MenuItem m = new MenuItem();
                m.setId(rs.getInt("idmenu_item"));
                m.setName(rs.getString("itemname"));
                m.setPrice(new BigDecimal(rs.getString("price")));
                m.setCode(rs.getString("code"));
                m.setStatus(rs.getBoolean("status"));
                m.setCategory(rs.getString("menu_item_category_name"));
                return m;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static String getCodeById(int id) {
        String sql = "select code from menu_item where idmenu_item = '" + id + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getString("code");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static int getIdByCode(String code) {
        String sql = "select idmenu_item from menu_item where code = '" + code + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getInt("idmenu_item");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static String getNameById(int id) {
        String sql = "select itemname from menu_item where idmenu_item = '" + id + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getString("itemname");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return "";
    }

    public static ResultSet getAll(boolean onlyActive) {
        String sql = "";
        if (onlyActive) {
            sql = "select *  from menu_item where status = '" + 1 + "' ";
        } else {
            sql = "select * from menu_item  ";
        }
        return DB.search(sql);
    }

}
