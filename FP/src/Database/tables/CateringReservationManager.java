/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.CateringReservation;
import Users.UserAccount;
import Utility.Helper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Sachintha
 */
public class CateringReservationManager {

    public static boolean addRow(CateringReservation res) {
        String sql = "start transaction";
        boolean flag = true;
        if (!DB.iud(sql)) {
            return false;
        }
        if (!CustomerManager.isCustomerExists(res.getCustomer().getTp())) {
            if (!CustomerManager.addRow(res.getCustomer())) {
                flag = false;
            }
        }
        if (flag) {

            sql = "insert into caterin_reservation (idcaterine_reservation,reservedtime"
                    + ",count,checkintime,status,total,advance,description,deliver,customer_ct,useraccount_iduseraccount)"
                    + "values('" + res.getId() + "','" + Helper.getDateTimeForDb(res.getReservedTime()) + "','" + res.getCount()
                    + "','" + Helper.getDateTimeForDb(res.getCheckInDateTime()) + "','" + res.getStatus() + "','"
                    + res.getTotal().toString() + "','" + res.getAdvance().toString() + "','" + res.getDescription() + "','"
                    + (res.isDeliver() ? 1 : 0) + "','" + res.getCustomer().getTp() + "','" + res.getUserAccount() + "')";
            if (!DB.iud(sql)) {
                flag = false;
            }
        }
        if (flag) {
            if (res.getMenus() != null) {
                HashMap<Integer, Integer> menus = res.getMenus();
                Set<Integer> keySet = menus.keySet();
                for (Integer i : keySet) {
                    if (!addCateringReservationMenus(res.getId(), i, menus.get(i))) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag) {
            if (res.getItems() != null) {
                HashMap<Integer, Integer> items = res.getItems();
                Set<Integer> keySet = items.keySet();
                for (Integer i : keySet) {
                    if (!addCateringReservationItems(res.getId(), i, items.get(i))) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag) {
            sql = "commit";
            return DB.iud(sql);
        } else {
            sql = "rollback";
            return DB.iud(sql);
        }
    }

    public static boolean addCateringReservationMenus(int resId, int menuId, int qty) {
        String sql = "insert into caterin_reservation_has_menu (menu_idmenu,caterin_reservation_idcaterine_reservation,qty)"
                + " values('" + menuId + "','" + resId + "','" + qty + "')";
        return DB.iud(sql);

    }

    public static boolean addCateringReservationItems(int resId, int itemId, int qty) {
        String sql = "insert into caterin_reservation_has_menu_item (menu_item_idmenu_item,caterin_reservation_idcaterine_reservation,qty)"
                + " values('" + itemId + "','" + resId + "','" + qty + "')";
        return DB.iud(sql);

    }

    public static CateringReservation getRow(String id) {
        String sql = "select *,date_format(reservedtime,'%Y-%m-%d %H:%i:%s') as date_time,date_format(checkintime,'%Y-%m-%d %H:%i:%s') as checkin from caterin_reservation where idcaterine_reservation='" + id + "'";
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {

                CateringReservation res = new CateringReservation();
                res.setAdvance(new BigDecimal(rs.getString("advance")));
                res.setCheckInDateTime(Helper.getDateFromDb(rs.getString("checkin")));
                res.setCount(rs.getInt("count"));
                res.setReservedTime(Helper.getDateFromDb(rs.getString("date_time")));
                res.setDeliver(rs.getBoolean("deliver"));
                res.setDescription(rs.getString("description"));
                res.setId(Integer.parseInt(id));
                res.setInvoiceId(rs.getString("invoice_idinvoice") == null ? -1 : Integer.parseInt(rs.getString("invoice_idinvoice")));
                res.setItems(getItems(res.getId()));
                res.setMenus(getMenus(res.getId()));
                res.setStatus(rs.getString("status"));
                res.setTotal(new BigDecimal(rs.getString("total")));

                res.setUserAccount(rs.getInt("useraccount_iduseraccount"));
                res.setCustomer(CustomerManager.getRow(rs.getString("customer_ct")));
                return res;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static HashMap<Integer, Integer> getItems(int id) {
        String sql = "select menu_item_idmenu_item,qty from caterin_reservation_has_menu_item where caterin_reservation_idcaterine_reservation = '" + id + "' ";
        HashMap<Integer, Integer> items = new HashMap<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                items.put(search.getInt("menu_item_idmenu_item"), search.getInt("qty"));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return items;
    }

    public static HashMap<Integer, Integer> getMenus(int id) {
        String sql = "select menu_idmenu,qty from caterin_reservation_has_menu where caterin_reservation_idcaterine_reservation = '" + id + "' ";
        HashMap<Integer, Integer> menus = new HashMap<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                menus.put(search.getInt("menu_idmenu"), search.getInt("qty"));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return menus;
    }

    public static ArrayList<CateringReservation> getReservationsByDate(Calendar time) {
        ArrayList<CateringReservation> list = new ArrayList<>();
        String sql = "select idcaterine_reservation from caterin_reservation where date_format(reservedtime,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idcaterine_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<CateringReservation> getReservationsByCheckInDate(Calendar time) {

        ArrayList<CateringReservation> list = new ArrayList<>();
        String sql = "select idcaterine_reservation from caterin_reservation where date_format(checkintime,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idcaterine_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<CateringReservation> getReservationsByCustomerTp(String tp) {

        ArrayList<CateringReservation> list = new ArrayList<>();
        String sql = "select idcaterine_reservation from caterin_reservation where customer_ct='" + tp + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idcaterine_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static String getDescriptionById(int id) {
        String sql = "select description from caterin_reservation where idcaterine_reservation='" + id + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getString("description");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static Date getCheckOutTime(int id) {
        String sql = "select i.datetime as time from invoice i inner join caterin_reservation cr on i.idinvoice = cr.invoice_idinvoice "
                + "where cr.idcaterine_reservation ='" + id + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getDate("time");
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static boolean isCheckedOut(int id) {
        Date checkOutTime = getCheckOutTime(id);
        return checkOutTime != null;
    }
}
