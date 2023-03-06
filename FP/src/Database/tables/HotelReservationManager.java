/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.HotelReservation;
import Utility.Helper;
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
public class HotelReservationManager {

    public static boolean addRow(HotelReservation res) {
        String sql = "start transaction ";
        boolean flag = true;
        if (!DB.iud(sql)) {
            flag = false;
        }
        if (flag && !CustomerManager.isCustomerExists(res.getCustomer().getTp())) {
            flag = CustomerManager.addRow(res.getCustomer());
        }
        if (flag) {
            sql = "insert into hotel_reservation (idhotel_reservation,reservedtime,"
                    + "count,checkintime,status,total,advance,description,customer_ct,useraccount_iduseraccount)"
                    + " values ('" + res.getId() + "','" + Helper.getDateTimeForDb(res.getReservedTime()) + "','" + res.getCount() + "','" + Helper.getDateTimeForDb(res.getCheckInTime())
                    + "','" + res.getSatus() + "','" + res.getTotal().toString() + "','" + res.getAdvance().toString() + "','" + res.getDescription()
                    + "','" + res.getCustomer().getTp() + "','" + res.getUserAccount() + "')";
            flag = DB.iud(sql);
        }
        if (flag) {
            flag = addLocations(res.getLocations(), res.getId());
        }
        if (flag) {
            flag = addBlocks(res.getBlocks(), res.getId());
        }
        if (flag) {
            flag = addMenus(res.getMenus(), res.getId());
        }
        if (flag) {
            flag = addMenuItems(res.getItems(), res.getId());
        }
        if (flag) {
            sql = "commit";
        } else {
            sql = "rollback";

        }
        DB.iud(sql);
        return flag;
    }

    private static boolean addLocations(ArrayList<Integer> locations, int resId) {
        if (locations == null) {
            return true;
        }
        boolean flag = true;
        for (Integer i : locations) {
            if (flag) {
                String sql = "insert into hotel_reservation_has_location (location_idlocation,hotel_reservation_idhotel_reservation) "
                        + "values('" + i + "','" + resId + "')";
                flag = DB.iud(sql);
            }
        }
        return flag;
    }

    private static boolean addBlocks(ArrayList<Integer> blocks, int resId) {
        boolean flag = true;
        if (blocks == null) {
            return true;
        }
        for (Integer i : blocks) {
            if (flag) {
                String sql = "insert into hotel_reservation_has_block (hotel_reservation_idhotel_reservation,block_idblock) "
                        + "values('" + resId + "','" + i + "')";
                flag = DB.iud(sql);
            }
        }
        return flag;
    }

    private static boolean addMenus(HashMap<Integer, Integer> menus, int resId) {
        boolean flag = true;
        if (menus == null) {
            return true;
        }
        Set<Integer> keySet = menus.keySet();

        for (Integer i : keySet) {
            if (flag) {
                String sql = "insert into hotel_reservation_has_menu (hotel_reservation_idhotel_reservation,menu_idmenu,qty)"
                        + "values('" + resId + "','" + i + "','" + menus.get(i).toString() + "')";
                flag = DB.iud(sql);
            }
        }
        return flag;
    }

    private static boolean addMenuItems(HashMap<Integer, Integer> items, int resId) {
        if (items == null) {
            return true;
        }
        Set<Integer> keySet = items.keySet();
        boolean flag = true;
        for (Integer i : keySet) {
            if (flag) {
                String sql = "insert into hotel_reservation_has_menu_item (menu_item_idmenu_item,hotel_reservation_idhotel_reservation,qty)"
                        + "values('" + i + "','" + resId + "','" + items.get(i).toString() + "')";
                flag = DB.iud(sql);
            }
        }
        return flag;
    }

    public static HotelReservation getRow(String id) {
        String sql = "select *,date_format(reservedtime,'%Y-%m-%d %H:%i:%s') as reservedtime,"
                + "date_format(checkintime,'%Y-%m-%d %H:%i:%s') as checkintime from hotel_reservation "
                + "where idhotel_reservation='" + id + "' limit 1";
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {
                HotelReservation res = new HotelReservation();
                res.setId(rs.getInt("idhotel_reservation"));
                res.setAdvance(rs.getBigDecimal("advance"));
                res.setBlocks(getBlocks(id));
                res.setCheckInTime(rs.getDate("checkintime"));
                res.setCount(rs.getInt("count"));
                res.setCustomer(CustomerManager.getRow(rs.getString("customer_ct")));
                res.setDescription(rs.getString("description"));
                res.setInvoiceId(rs.getString("invoice_idinvoice") == null ? -1 : rs.getInt("invoice_idinvoice"));
                res.setItems(getItems(id));
                res.setLocations(getLocations(id));
                res.setMenus(getMenus(id));
                res.setReservedTime(rs.getDate("reservedtime"));
                res.setSatus(rs.getString("status"));
                res.setTotal(rs.getBigDecimal("total"));

                res.setUserAccount(rs.getInt("useraccount_iduseraccount"));
                return res;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static Date getCheckOutTime(int id) {
        String sql = "select i.datetime as time from invoice i inner join hotel_reservation hr on i.idinvoice = hr.invoice_idinvoice "
                + "where hr.idhotel_reservation ='" + id + "' limit 1";
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

    public static HashMap<Integer, Integer> getMenus(String id) {
        String sql = "select menu_idmenu,qty from hotel_reservation_has_menu where hotel_reservation_idhotel_reservation= '" + id + "'";
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

    public static HashMap<Integer, Integer> getItems(String id) {
        String sql = "select menu_item_idmenu_item,qty from hotel_reservation_has_menu_item where hotel_reservation_idhotel_reservation= '" + id + "'";
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

    public static ArrayList<Integer> getBlocks(String id) {
        String sql = "select block_idblock from hotel_reservation_has_block where hotel_reservation_idhotel_reservation = '" + id + "'";
        ArrayList<Integer> blocks = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                blocks.add(search.getInt("block_idblock"));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return blocks;
    }

    public static ArrayList<Integer> getLocations(String id) {
        String sql = "select location_idlocation from hotel_reservation_has_location where hotel_reservation_idhotel_reservation = '" + id + "'";
        ArrayList<Integer> items = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                items.add(search.getInt("location_idlocation"));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return items;
    }

    /* get munus
        get items
    get row
     */
    public static ArrayList<HotelReservation> getReservationsByDate(Calendar time) {
        ArrayList<HotelReservation> list = new ArrayList<>();
        String sql = "select idhotel_reservation from hotel_reservation where date_format(reservedtime,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idhotel_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<HotelReservation> getReservationsByCheckInDate(Calendar time) {
        ArrayList<HotelReservation> list = new ArrayList<>();
        String sql = "select idhotel_reservation from hotel_reservation where date_format(checkintime,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idhotel_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;

    }

    public static ArrayList<HotelReservation> getReservationsByCustomerTp(String tp) {
        ArrayList<HotelReservation> list = new ArrayList<>();
        String sql = "select idhotel_reservation from hotel_reservation where customer_ct = '" + tp + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idhotel_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;

    }

    public static String getDescriptionById(int id) {
        String sql = "select description from hotel_reservation where idhotel_reservation='" + id + "' limit 1";
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
}
