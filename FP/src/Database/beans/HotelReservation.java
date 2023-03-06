/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

import Users.UserAccount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Sachintha
 */
public class HotelReservation {

    private int id;
    private Date reservedTime;
    private int count;
    private Date checkInTime;
    private String satus;
    private BigDecimal total;
    private BigDecimal advance;
    private String description;
    private Customer customer;
    private int invoiceId;
    private int userAccount;
    private ArrayList<Integer> blocks;
    private ArrayList<Integer> locations;
    private HashMap<Integer, Integer> menus;
    private HashMap<Integer, Integer> items;

    public HotelReservation() {
    }

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
     * @return the reservedTime
     */
    public Date getReservedTime() {
        return reservedTime;
    }

    /**
     * @param reservedTime the reservedTime to set
     */
    public void setReservedTime(Date reservedTime) {
        this.reservedTime = reservedTime;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the checkInTime
     */
    public Date getCheckInTime() {
        return checkInTime;
    }

    /**
     * @param checkInTime the checkInTime to set
     */
    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * @return the satus
     */
    public String getSatus() {
        return satus;
    }

    /**
     * @param satus the satus to set
     */
    public void setSatus(String satus) {
        this.satus = satus;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the advance
     */
    public BigDecimal getAdvance() {
        return advance;
    }

    /**
     * @param advance the advance to set
     */
    public void setAdvance(BigDecimal advance) {
        this.advance = advance;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the invoiceId
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId the invoiceId to set
     */
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
     * @return the blocks
     */
    public ArrayList<Integer> getBlocks() {
        return blocks;
    }

    /**
     * @param blocks the blocks to set
     */
    public void setBlocks(ArrayList<Integer> blocks) {
        this.blocks = blocks;
    }

    /**
     * @return the locations
     */
    public ArrayList<Integer> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(ArrayList<Integer> locations) {
        this.locations = locations;
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
     * @return the items
     */
    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(HashMap<Integer, Integer> items) {
        this.items = items;
    }

}
