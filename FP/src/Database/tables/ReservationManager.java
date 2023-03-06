/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sachintha
 */
public class ReservationManager {

    public static String RESERVED = "reserved";
    public static String CHECKED_IN = "checkedin";
    public static String CHECKED_OUT = "checkedout";
    public static String CANCELLED = "cancelled";

    public static String RESERVATION_CATERIN = "Caterin Reservation";
    public static String RESERVATION_ROOM = "Room Reservation";
    public static String RESERVATION_HOTEL = "Hotel Reservation";

    public enum ReservationType {
        CATERING, HOTEL, ROOM
    }

    public static int getNextReservationId() {
        String sql = "select (select count(*) from room_reservation) + "
                + "(select count(*) from hotel_reservation) +"
                + "(select count(*) from caterin_reservation) + 1 as total";
        ResultSet rs = DB.search(sql);
        try {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            DB.processException(ex);
        }
        return -1;
    }

    public static ReservationType getTypeById(String id) {

        String sql = "select 1 from room_reservation where idroom_reservation = '" + id + "' limit 1";
        try (ResultSet search = DB.search(sql)) {
            if (search.next()) {
                return ReservationType.ROOM;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = "select 1 from caterin_reservation where idcaterine_reservation = '" + id + "' limit 1";
        try (ResultSet search = DB.search(sql)) {
            if (search.next()) {
                return ReservationType.CATERING;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = "select 1 from hotel_reservation where idhotel_reservation = '" + id + "' limit 1";
        try (ResultSet search = DB.search(sql)) {
            if (search.next()) {
                return ReservationType.HOTEL;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static String getStatusById(String id) {
        ReservationType type = getTypeById(id);

        if (type == ReservationType.CATERING) {
            String sql = "select status from caterin_reservation where "
                    + "idcaterine_reservation ='" + id + "' limit 1";
            try (
                    ResultSet search = DB.search(sql);) {
                if (search.next()) {
                    return search.getString("status");
                }
            } catch (Exception e) {
                DB.processException(e);
            }
        } else if (type == ReservationType.HOTEL) {
            String sql = "select status from hotel_reservation where "
                    + "idhotel_reservation ='" + id + "' limit 1";
            try (
                    ResultSet search = DB.search(sql);) {
                if (search.next()) {
                    return search.getString("status");
                }
            } catch (Exception e) {
                DB.processException(e);
            }
        } else if (type == ReservationType.ROOM) {
            String sql = "select status from room_reservation where "
                    + "idroom_reservation ='" + id + "' limit 1";
            try (
                    ResultSet search = DB.search(sql);) {
                if (search.next()) {
                    return search.getString("status");
                }
            } catch (Exception e) {
                DB.processException(e);
            }
        }
        return null;
    }
}
