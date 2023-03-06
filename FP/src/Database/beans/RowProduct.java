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
public class RowProduct {

    private ArrayList<RawItem> itemList;

    /**
     * @return the itemList
     */
    public ArrayList<RawItem> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(ArrayList<RawItem> itemList) {
        this.itemList = itemList;
    }
}
