/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import java.sql.ResultSet;

/**
 *
 * @author Sachintha
 */
public class RoomCatagoryManager {

    public static ResultSet getAllCategory() {
        String sql = "select * from room_catagory";
        return (DB.search(sql));
    }

    public static boolean addCategory(String category) {
        String sql = "insert into room_catagory values('" + category + "')";
        return DB.iud(sql);
    }
}
