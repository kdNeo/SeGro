/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Block;
import Database.beans.HotelReservation;
import GUI.Reservation;
import Utility.Helper;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 *
 * @author Sachintha
 */
public class BlockManager {

    public static ResultSet getAll(boolean isActive) {
        String sql = "";
        if (isActive) {
            sql = "select idblock,blockname,blockprice,code, count(maxvisitor) as max from "
                    + "block b inner join location l on b.idblock = l.block_idblock where l.status = 1 and b.status=1 "
                    + "group by idblock ";
        } else {
            sql = "select idblock,blockname,blockprice,code, count(maxvisitor) as max from "
                    + "block b inner join location l on b.idblock = l.block_idblock "
                    + "group by idblock ";
        }
        return DB.search(sql);
    }

    public static ResultSet getAll(Calendar day) {
        String date = Helper.getDateForDb(day.getTime());

        String sql = "select idblock,blockname,blockprice,code,"
                + "(select count(l.max) from location l inner join block b on l.block_idblock = b.idblock where b.idblock=b1.idblock and l.status = 1) as max "
                + "from block b1 where status = 1 and "
                + "(select count(*) from block b inner join location l on l.block_idblock = b.idblock where b.idblock=b1.idblock)>0"
                + " and b1.idblock not in ("
                + "select b.idblock from block b inner join hotel_reservation_has_block hrhb inner join hotel_reservation hr "
                + "on b.idblock = hrhb.block_idblock and hrhb.hotel_reservation_idhotel_reservation= hr.idhotel_reservation "
                + " where DATE_FORMAT(hr.checkintime, '%Y-%m-%d') = '" + date + "' and "
                + "(hr.status ='" + Reservation.RESERVATION_STATUS_RESERVED + "' or   hr.status ='" + Reservation.RESERVATION_STATUS_CHECKEDIN + "')  "
                + " union  select b.idblock from location l inner join block b on l.block_idblock = b.idblock where l.idlocation in "
                + "( select  idlocation from location l inner join hotel_reservation_has_location hrhl inner join hotel_reservation hr "
                + " on hr.idhotel_reservation = hrhl.hotel_reservation_idhotel_reservation and  hrhl.location_idlocation=l.idlocation "
                + " where DATE_FORMAT(hr.checkintime, '%Y-%m-%d') = '" + date + "' and "
                + "(hr.status ='" + Reservation.RESERVATION_STATUS_RESERVED + "' or   hr.status ='" + Reservation.RESERVATION_STATUS_CHECKEDIN + "')   ) ) ";
        return DB.search(sql);

    }

    public static ResultSet getLocations(String blockCode) {
        String sql = "select l.code as code,l.name as name,l.max as max,l.location_type_type as type from "
                + "block b inner join location l  on "
                + "b.idblock = l.block_idblock  "
                + "where b.code='" + blockCode + "' ";
        return DB.search(sql);

    }

    public static int getBlockIdByCode(String code) {
        String sql = "select idblock from block where code = '" + code + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getInt("idblock");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static Block getRow(int id) {
        String sql = "select *,sum(l.max) as max,count(*) as nooflocations  from block b inner join location l on b.idblock = l.block_idblock where idblock = '" + id + "' group by idblock ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                Block b = new Block();
                b.setCode(search.getString("code"));
                b.setId(id);
                b.setNoOfLocations(search.getInt("nooflocations"));
                b.setName(search.getString("blockname"));
                b.setMax(search.getInt("max"));
                b.setPrice(search.getBigDecimal("blockprice"));
                return b;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static boolean doesBlockHasTheLocation(String blockCode, String locationCode) {
        String sql = "select 1 from block b inner join location l on l.block_idblock = b.idblock where b.code='" + blockCode + "' and "
                + "l.code = '" + locationCode + "' limit 1";
        try {
            ResultSet search = DB.search(sql);
            if (search.next()) {
                return true;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return false;
    }

}
