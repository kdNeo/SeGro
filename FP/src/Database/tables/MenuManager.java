/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Menu;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 *
 * @author Sachintha
 */
public class MenuManager {

    public static ResultSet getAll(boolean onlyActive) {
        String sql = "";
        if (onlyActive) {
            sql = "select * from menu where status='" + 1 + "' order by code, menuname";
        } else {
            sql = "select * from menu  order by code, menuname";
        }
        return DB.search(sql);
    }

    public static Menu getRowByCode(String code, boolean onlyActive) {
        String sql = "";
        if (onlyActive) {
            sql = "select * from menu where code ='" + code + "' and status='1' limit 1 ";
        } else {
            sql = "select * from menu where code ='" + code + "'  limit 1 ";
        }
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getInt("idmenu"));
                m.setName(rs.getString("menuname"));
                m.setPrice(new BigDecimal(rs.getString("price")));
                m.setCode(rs.getString("code"));
                m.setStatus(rs.getBoolean("status"));
                return m;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static String getCodeById(int id) {
        String sql = "select code from menu where idmenu = '" + id + "' limit 1";
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
        String sql = "select idmenu from menu where code = '" + code + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getInt("idmenu");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static ResultSet getItemsOfMenuByMenuCode(String code) {
        String sql = "select mi.code as code,mi.itemname as name from menu m inner join menu_has_item mhi inner join"
                + " menu_item mi on m.idmenu = mhi.menu_idmenu and mhi.menu_item_idmenu_item = mi.idmenu_item "
                + " where mi.status =1 and m.status=1 and m.code = '" + code + "' order by mi.code, mi.itemname";
        return DB.search(sql);
    }

    public static String getNameById(int id) {
        String sql = "select menuname from menu where idmenu = '" + id + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getString("menuname");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static int getIdByName(String name) {
        String sql = "select idmenu from menu where  menuname='" + name + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getInt("idmenu");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

}
