/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.tables;

import Database.DB;
import Database.beans.SellItem;
import Database.beans.SellProduct;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Sachintha
 */
public class ProductManager {

    /**
     * Call this method with null name to get all the products. If onlyActive,
     * this will return only the product items with a quantity of more than 0.
     *
     * @param name
     * @param onlyActive
     * @return
     */
    public static ArrayList<SellProduct> getSellProductsLikeName(String name, boolean onlyActive) {
        String sql = null;

        if (name != null) {
            sql = "select * from product p inner join stock s on p.stock_idstock = s.idstock "
                    + "where p.name like '" + name + "%' ";
        } else {
            sql = "select * from product p inner join stock s on p.stock_idstock = s.idstock ";
        }

        ArrayList<SellProduct> list = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            SellProduct p = null;
            while (search.next()) {
                p = new SellProduct();
                p.setId(search.getInt("idproduct"));
                p.setItemList(getItemsForSellProduct(p.getId(), onlyActive));
                p.setName(search.getString("name"));
                p.setStockId(search.getInt("stock_idstock"));
                p.setCode(search.getString("code"));
                p.setStockName(search.getString("stockname"));
                p.setType(search.getString("type"));
                list.add(p);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    /**
     * *
     * return items of a given product with productId
     *
     * @param productId
     * @return
     */
    public static ArrayList<SellItem> getItemsForSellProduct(int productId, boolean onlyActive) {
        String sql = "select * from sell_item where product_idproduct = '" + productId + "' ";
        ArrayList<SellItem> list = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            SellItem i = null;
            while (search.next()) {
                i = new SellItem();
                i.setId(search.getInt("idsell_item"));
                System.out.println("while inside items of sell products");
                i.setQtyRemain(SellItemManager.getQtyRemainingOfSellItem(i.getId()));
                if (onlyActive) {
                    if (i.getQtyRemain() < 1) {
                        continue;
                    }
                }
                i.setSellPrice(search.getBigDecimal("unitprice"));
                list.add(i);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

    /**
     * Call this method with null productCode to get all the products. If
     * onlyActive, this will return only the product items with a quantity of
     * more than 0.
     *
     * @param productCode - code for the wildcard search
     * @return List of SellProducts
     */
    public static ArrayList<SellProduct> getSellProductsLikeCode(String productCode, boolean onlyActive) {
        String sql = null;
        if (productCode != null) {
            sql = "select * from product p inner join stock s on p.stock_idstock = s.idstock "
                    + "where p.code like '" + productCode + "%' ";
        } else {
            sql = "select * from product p inner join stock s on p.stock_idstock = s.idstock ";
        }
        ArrayList<SellProduct> list = new ArrayList<>();
        try (
                ResultSet search = DB.search(sql);) {
            SellProduct p = null;
            while (search.next()) {
                p = new SellProduct();
                p.setId(search.getInt("idproduct"));
                p.setItemList(getItemsForSellProduct(p.getId(), onlyActive));
                p.setCode(search.getString("code"));
                p.setName(search.getString("name"));
                p.setStockId(search.getInt("stock_idstock"));
                p.setStockName(search.getString("stockname"));
                p.setType(search.getString("type"));
                list.add(p);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return list;
    }

}
