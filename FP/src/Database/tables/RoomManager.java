/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.Room;
import Utility.Helper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 *
 * @author Sachintha
 */
public class RoomManager {

    public static boolean addRow(Room room) {
        String sql = "insert into room (name,description,price,bedcount,status,room_catagory_catagoryname,code) "
                + "values('" + room.getName() + "','" + room.getDescription() + "','" + room.getPrice().toString() + "','"
                + room.getBedCount() + "','" + (room.isStatus() ? 1 : 0) + "','" + room.getCategory() + "','" + room.getCode() + "')";

        return (DB.iud(sql));
    }

    public static ResultSet getAll(boolean isActive) {
        String sql = "";
        if (isActive) {
            sql = "select * from room where status='" + 1 + "' order by idroom asc";
        } else {
            sql = "select * from room order by idroom asc";
        }
        return DB.search(sql);
    }

    public static ResultSet getAll(Calendar day, int days) {
        String date = Helper.getDateForDb(day.getTime());
        String sql = "select * from room where idroom not in "
                + "(select room_idroom from room_reservation rr inner join room_reservation_has_room rri on "
                + "rr.idroom_reservation = rri.room_reservation_idroom_reservation where "
                + "'" + date + "' >= DATE_FORMAT(rr.checkindate, '%Y-%m-%d') && "
                + " DATE_FORMAT(DATE_ADD(rr.checkindate, interval days-1 day),'%Y-%m-%d') >='" + date + "' || "
                + " DATE_ADD('" + date + "', interval '" + (days - 1) + "' day) >= DATE_FORMAT(rr.checkindate, '%Y-%m-%d') && "
                + " DATE_FORMAT(DATE_ADD(rr.checkindate, interval days-1 day),'%Y-%m-%d') >= DATE_ADD('" + date + "', interval '" + (days - 1) + "' day) "
                + ")  and status = 1  order by idroom asc";

        /**
         * implement logic done! check ?
         */
        return DB.search(sql);
    }

    public static int findIdByName(String name) {
        String sql = "select idroom from room where name='" + name + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getInt("idroom");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static String getCodeById(int id) {
        String sql = "select code from room where idroom='" + id + "' limit 1";
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
        String sql = "select idroom from room where code='" + code + "' limit 1";
        ResultSet search = DB.search(sql);
        try {
            if (search.next()) {
                return search.getInt("idroom");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return -1;
    }

    public static Room getRowByCode(String code) {
        String sql = "select * from room where code ='" + code + "' limit 1";

        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                Room r = new Room();
                r.setCode(code);
                r.setId(search.getInt("idroom"));
                r.setName(search.getString("name"));
                r.setCategory(search.getString("room_catagory_catagoryname"));
                r.setDescription(search.getString("description"));
                r.setPrice(search.getBigDecimal("price"));
                r.setBedCount(search.getInt("bedcount"));
                r.setStatus(search.getBoolean("status"));
                return r;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static Room getRowById(int id) {
        String sql = "select * from room where idroom ='" + id + "' limit 1";

        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                Room r = new Room();
                r.setId(id);
                r.setCode(search.getString("code"));
                r.setName(search.getString("name"));
                r.setCategory(search.getString("room_catagory_catagoryname"));
                r.setDescription(search.getString("description"));
                r.setPrice(new BigDecimal(search.getString("price")));
                r.setBedCount(search.getInt("bedcount"));
                r.setStatus(search.getBoolean("status"));
                return r;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }

    public static ResultSet getAllCategories() {
        String sql = "select * from room_catagory";
        return DB.search(sql);
    }

    public static boolean addCatagory(String catagory) {
        String sql = "insert into room_catagory values('" + catagory + "')";
        return DB.iud(sql);
    }

    public static boolean isCatagoryExists(String catagory) {
        String sql = "select * from room_catagory where catagoryname='" + catagory + "'";
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

    public static boolean updateCatagory(String oldCat, String newCat) {
        String sql = "update room_catagory set catagoryname='" + newCat + "' where catagoryname ='" + oldCat + "' limit 1";
        return DB.iud(sql);
    }

}
