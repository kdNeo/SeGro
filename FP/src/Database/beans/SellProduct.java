/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

import java.util.ArrayList;

/**
 *
 * @author Sachintha
 */
public class SellProduct extends Product {

    private ArrayList<SellItem> itemList;

    /**
     * @return the itemList
     */
    public ArrayList<SellItem> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(ArrayList<SellItem> itemList) {
        this.itemList = itemList;
    }

}
