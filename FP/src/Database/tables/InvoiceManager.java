/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Invoice;
import Utility.Helper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Sachintha
 */
public class InvoiceManager {

    public static boolean addRow(Invoice invoice) {
        String sql = "start transaction ";
        boolean flag = true;
        int id = -1;
        flag = DB.iud(sql);
        if (flag) {
            sql = "insert into invoice (discount,subtotal,datetime,useraccount_iduseraccount) "
                    + "values ('" + invoice.getDiscount().setScale(2).toString() + "','" + invoice.getSubtotal().setScale(2).toString()
                    + "','" + Helper.getDateTimeForDb(invoice.getTime()) + "','" + invoice.getUserAccount() + "')";
            id = DB.iudReturnId(sql);
            if (id == -1) {
                flag = false;
            }
        }
        if (flag) {
            ArrayList<Integer> cr = invoice.getCaterinReservations();
            if (cr != null && cr.size() > 0) {
                flag = addCaterinReservations(cr, id);
            }
        }
        if (flag) {
            ArrayList<Integer> ress = invoice.getHotelReservations();
            if (ress != null && ress.size() > 0) {
                flag = addHotelReservations(ress, id);
            }
        }
        if (flag) {
            ArrayList<Integer> rr = invoice.getRoomReservations();
            if (rr != null && rr.size() > 0) {
                flag = addRoomReservations(rr, id);
            }
        }
        if (flag) {
            HashMap<Integer, Integer> menus = invoice.getMenus();
            if (menus != null && menus.size() > 0) {
                flag = addMenus(menus, id);
            }
        }
        if (flag) {
            HashMap<Integer, Integer> menutems = invoice.getMenutems();
            if (menutems != null && menutems.size() > 0) {
                flag = addMenuItems(menutems, id);
            }
        }
        if (flag) {
            HashMap<Integer, Integer> sellItems = invoice.getSellItems();
            if (sellItems != null && sellItems.size() > 0) {
                flag = addSellItems(sellItems, id);
            }
        }
        if (flag) {
            flag = DB.iud("commit");
        } else {
            DB.iud("rollback");
        }
        return flag;
    }

    private static boolean addRoomReservations(ArrayList<Integer> rooms, int invoiceId) {
        boolean flag = true;
        for (Integer i : rooms) {
            String sql = "update room_reservation set "
                    + "status='" + ReservationManager.CHECKED_OUT + "', "
                    + "invoice_idinvoice ='" + invoiceId + "' "
                    + "where idroom_reservation = '" + i + "' limit 1 ";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    private static boolean addCaterinReservations(ArrayList<Integer> caterins, int invoiceId) {
        boolean flag = true;
        for (Integer i : caterins) {
            String sql = "update caterin_reservation set "
                    + "status='" + ReservationManager.CHECKED_OUT + "', "
                    + "invoice_idinvoice ='" + invoiceId + "' "
                    + "where idcaterine_reservation = '" + i + "' limit 1 ";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    private static boolean addHotelReservations(ArrayList<Integer> hotels, int invoiceId) {
        boolean flag = true;
        for (Integer i : hotels) {
            String sql = "update hotel_reservation set "
                    + "status='" + ReservationManager.CHECKED_OUT + "', "
                    + "invoice_idinvoice ='" + invoiceId + "' "
                    + "where idhotel_reservation = '" + i + "' limit 1 ";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    private static boolean addSellItems(HashMap<Integer, Integer> items, int invoiceId) {
        Set<Integer> keySet = items.keySet();
        boolean flag = true;
        for (Integer i : keySet) {
            String sql = "insert into  sell_item_has_invoice (sell_item_idsell_item,invoice_idinvoice,qty) values ("
                    + "'" + i + "','" + invoiceId + "','" + items.get(i) + "' )";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    private static boolean addMenus(HashMap<Integer, Integer> menus, int invoiceId) {
        Set<Integer> keySet = menus.keySet();
        boolean flag = true;
        for (Integer i : keySet) {
            String sql = "insert into  menu_has_invoice (menu_idmenu,qty,invoice_idinvoice) values ("
                    + "'" + i + "','" + menus.get(i) + "','" + invoiceId + "' )";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    private static boolean addMenuItems(HashMap<Integer, Integer> items, int invoiceId) {
        Set<Integer> keySet = items.keySet();
        boolean flag = true;
        for (Integer i : keySet) {
            String sql = "insert into  menu_item_has_invoice (menu_item_idmenu_item,invoice_idinvoice,qty) values ("
                    + "'" + i + "','" + invoiceId + "','" + items.get(i) + "' )";
            flag = DB.iud(sql);
            if (!flag) {
                return flag;
            }
        }
        return flag;
    }

    public static String getNextInvoice() {
        String sql = "select coalesce(max(idinvoice),1) as next from invoice ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getString("next");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return "1";
    }
}
