/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.SellItem;
import Database.beans.SellItemProduct;
import java.sql.ResultSet;

/**
 *
 * @author Sachintha
 */
public class SellItemManager {

    public static int getQtyRemainingOfSellItem(int sellItemId) {
        String sql = "select (select sum(qty) from sell_item where idsell_item='" + sellItemId + "')-"
                + "coalesce((select sum(qty) from sell_item_has_invoice where sell_item_idsell_item='" + sellItemId + "'),0) as qty ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return search.getInt("qty");
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return 0;
    }

    public static SellItemProduct getRow(String id) {
        String sql = "select * from sell_item s inner join product p on "
                + "s.product_idproduct = p.idproduct where "
                + "idsell_item ='" + id + "' limit 1";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                SellItemProduct sp = new SellItemProduct();
                sp.setCode(search.getString("code"));
                sp.setId(Integer.parseInt(id));
                sp.setName(search.getString("name"));
                sp.setPrice(search.getBigDecimal("buyingprice").add(search.getBigDecimal("margin")));
                return sp;
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return null;
    }
}
