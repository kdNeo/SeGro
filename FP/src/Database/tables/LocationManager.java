/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.HotelReservation;
import Database.beans.Location;
import GUI.Reservation;
import Utility.Helper;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 *
 * @author Sachintha
 */
public class LocationManager {

    public static ResultSet getAll(Calendar day) {
        String date = Helper.getDateForDb(day.getTime());

        String sql = "select idlocation,name,price,description,max,code,location_type_type as type from location "
                + " where idlocation not in "
                + "(select idlocation from location l inner join hotel_reservation_has_location hrl inner join hotel_reservation hr "
                + " on hr.idhotel_reservation = hrl.hotel_reservation_idhotel_reservation and  hrl.location_idlocation=l.idlocation "
                + " where DATE_FORMAT(hr.checkintime, '%Y-%m-%d') = '" + date + "' and  "
                + "(hr.status ='" + Reservation.RESERVATION_STATUS_RESERVED + "' or hr.status ='" + Reservation.RESERVATION_STATUS_CHECKEDIN + "' ) "
                + " union select  idlocation from "
                + "location l inner join block b inner join hotel_reservation_has_block hrb inner join hotel_reservation hr "
                + "on l.block_idblock = b.idblock and b.idblock = hrb.block_idblock and "
                + "hrb.hotel_reservation_idhotel_reservation =hr.idhotel_reservation "
                + " where DATE_FORMAT(hr.checkintime, '%Y-%m-%d') = '" + date + "' and  "
                + "(hr.status ='" + Reservation.RESERVATION_STATUS_RESERVED + "' or hr.status ='" + Reservation.RESERVATION_STATUS_CHECKEDIN + "' )) "
                + "and status =1 ";
        return DB.search(sql);
    }

    public static int getLocationIdByCode(String code) {
        String sql = "select idlocation from location where code = '" + code + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getInt("idlocation");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static Location getRow(int id) {
        String sql = "select * from location where idlocation = '" + id + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                Location l = new Location();
                l.setCode(search.getString("code"));
                l.setId(id);
                l.setMax(search.getInt("max"));
                l.setName(search.getString("name"));
                l.setPrice(search.getBigDecimal("price"));
                l.setDescription(search.getString("description"));
                l.setStatus(search.getBoolean("status"));
                l.setType(search.getString("location_type_type"));
                return l;
            }

        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

}
