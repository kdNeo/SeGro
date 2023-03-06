/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Sachintha
 */
public class Invoice {

    private int id;
    private BigDecimal discount;
    private BigDecimal subtotal;
    private int userAccount;
    private Date time;

    private ArrayList<Integer> roomReservations;
    private ArrayList<Integer> caterinReservations;
    private ArrayList<Integer> hotelReservations;

    private HashMap<Integer, Integer> sellItems;
    private HashMap<Integer, Integer> menutems;
    private HashMap<Integer, Integer> menus;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the userAccount
     */
    public int getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(int userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return the roomReservations
     */
    public ArrayList<Integer> getRoomReservations() {
        return roomReservations;
    }

    /**
     * @param roomReservations the roomReservations to set
     */
    public void setRoomReservations(ArrayList<Integer> roomReservations) {
        this.roomReservations = roomReservations;
    }

    /**
     * @return the caterinReservations
     */
    public ArrayList<Integer> getCaterinReservations() {
        return caterinReservations;
    }

    /**
     * @param caterinReservations the caterinReservations to set
     */
    public void setCaterinReservations(ArrayList<Integer> caterinReservations) {
        this.caterinReservations = caterinReservations;
    }

    /**
     * @return the hotelReservations
     */
    public ArrayList<Integer> getHotelReservations() {
        return hotelReservations;
    }

    /**
     * @param hotelReservations the hotelReservations to set
     */
    public void setHotelReservations(ArrayList<Integer> hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

    /**
     * @return the sellItems
     */
    public HashMap<Integer, Integer> getSellItems() {
        return sellItems;
    }

    /**
     * @param sellItems the sellItems to set
     */
    public void setSellItems(HashMap<Integer, Integer> sellItems) {
        this.sellItems = sellItems;
    }

    /**
     * @return the menutems
     */
    public HashMap<Integer, Integer> getMenutems() {
        return menutems;
    }

    /**
     * @param menutems the menutems to set
     */
    public void setMenutems(HashMap<Integer, Integer> menutems) {
        this.menutems = menutems;
    }

    /**
     * @return the menus
     */
    public HashMap<Integer, Integer> getMenus() {
        return menus;
    }

    /**
     * @param menus the menus to set
     */
    public void setMenus(HashMap<Integer, Integer> menus) {
        this.menus = menus;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

}
