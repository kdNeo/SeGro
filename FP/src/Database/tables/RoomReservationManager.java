/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.RoomReservation;
import Utility.Helper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Sachintha
 */
public class RoomReservationManager {

    public static boolean addRow(RoomReservation res) {
        boolean flag = true;
        flag = DB.iud("start transaction");

        if (flag && !CustomerManager.isCustomerExists(res.getCustomerTp())) {
            flag = CustomerManager.addRow(res.getCustomer());
        }
        if (flag) {
            String sql = "insert into room_reservation ("
                    + "idroom_reservation,reservedtime,advance,checkindate,adultcount,"
                    + "childcount,total,status,description,customer_ct,useraccount_iduseraccount,days)"
                    + "values('" + res.getId() + "','" + Helper.getDateTimeForDb(res.getReservedTime()) + "','" + res.getAdvance().toString() + "','" + Helper.getDateForDb(res.getChechInDate())
                    + "','" + res.getAdultCount() + "','" + res.getChildCount() + "','" + res.getTotal()
                    + "','" + res.getStatus() + "','" + res.getDescription() + "','" + res.getCustomerTp() + "','" + res.getUserAccount()
                    + "','" + res.getNoOfDays() + "')";
            flag = DB.iud(sql);
        }
        if (flag) {
            for (Integer i : res.getRooms()) {
                flag = addRoomsForReservation(i, res.getId());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            DB.iud("rollback");
            return false;
        } else {
            DB.iud("commit");
            return true;
        }
    }

    private static boolean addRoomsForReservation(int room, int reservation) {
        String sql = "insert into room_reservation_has_room(room_idroom,room_reservation_idroom_reservation)"
                + " values('" + room + "', '" + reservation + "')";
        return DB.iud(sql);
    }

    public static RoomReservation getRow(String id) {
        String sql = "select *,date_format(reservedtime,'%Y-%m-%d %H:%i:%s') as reservedtime, "
                + "date_format(checkindate,'%Y-%m-%d') as checkindate from room_reservation where idroom_reservation ='" + id + "' limit 1";
        try (
                ResultSet rs = DB.search(sql);) {
            if (rs.next()) {
                RoomReservation res = new RoomReservation();
                res.setAdultCount(rs.getInt("adultcount"));
                res.setAdvance(rs.getBigDecimal("advance"));
                res.setChechInDate(rs.getDate("checkindate"));
                res.setChildCount(rs.getInt("childcount"));
                res.setCustomer(CustomerManager.getRow(rs.getString("customer_ct")));
                res.setCustomerTp(rs.getString("customer_ct"));
                res.setReservedTime(Helper.getDateFromDb(rs.getString("reservedtime")));
                res.setDescription(rs.getString("description"));
                res.setId(Integer.parseInt(id));
                res.setInvoice(rs.getString("invoice_idinvoice") == null ? -1 : Integer.parseInt(rs.getString("invoice_idinvoice")));
                res.setNoOfDays(rs.getInt("days"));
                res.setRooms(getRooms(res.getId()));
                res.setStatus(rs.getString("status"));
                res.setTotal(rs.getBigDecimal("total"));

                res.setUserAccount(rs.getInt("useraccount_iduseraccount"));
                return res;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static ArrayList<Integer> getRooms(int id) {
        String sql = "Select room_idroom from room_reservation_has_room where room_reservation_idroom_reservation = '" + id + "' ";
        ArrayList<Integer> rooms = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                rooms.add(search.getInt("room_idroom"));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return rooms;
    }

    public static ArrayList<RoomReservation> getReservationsByDate(Calendar time) {
        ArrayList<RoomReservation> list = new ArrayList<>();
        String sql = "select idroom_reservation from room_reservation where date_format(reservedtime,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idroom_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<RoomReservation> getReservationsByCheckInDate(Calendar time) {
        ArrayList<RoomReservation> list = new ArrayList<>();
        String sql = "select idroom_reservation from room_reservation where date_format(checkindate,'%Y-%m-%d') = '" + Helper.getDateForDb(time.getTime()) + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idroom_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static ArrayList<RoomReservation> getReservationsByCustomerTp(String tp) {
        ArrayList<RoomReservation> list = new ArrayList<>();
        String sql = "select idroom_reservation from room_reservation where customer_ct='" + tp + "' ";
        try (
                ResultSet search = DB.search(sql);) {
            while (search.next()) {
                list.add(getRow(search.getString("idroom_reservation")));
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    public static String getDescriptionById(int id) {
        String sql = "select description from room_reservation where idroom_reservation='" + id + "' limit 1";
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
        String sql = "select i.datetime as time from invoice i inner join room_reservation hr on i.idinvoice = hr.invoice_idinvoice "
                + "where hr.idroom_reservation ='" + id + "' limit 1";
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
