/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

/**
 *
 * @author Sachintha
 */
public class ItemCode {

    private int itemCode;
    private int qty;

    public ItemCode(int itemCode, int qty) {
        this.itemCode = itemCode;
        this.qty = qty;
    }

    public ItemCode() {

    }

    /**
     * @return the itemCode
     */
    public int getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the itemCode to set
     */
    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

}
