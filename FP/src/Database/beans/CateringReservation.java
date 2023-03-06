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
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Sachintha
 */
public class CateringReservation {

    private HashMap<Integer, Integer> menus;
    private HashMap<Integer, Integer> items;

    private int id;
    private Date reservedTime;
    private int count;
    private Date checkInDateTime;
    private String status;
    private BigDecimal total;
    private BigDecimal advance;
    private String description;
    private boolean deliver;
    private Customer customer;
    private int userAccount;
    private int invoiceId;

    public CateringReservation() {

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
     * @return the checkInDateTime
     */
    public Date getCheckInDateTime() {
        return checkInDateTime;
    }

    /**
     * @param checkInDateTime the checkInDateTime to set
     */
    public void setCheckInDateTime(Date checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the deliver
     */
    public boolean isDeliver() {
        return deliver;
    }

    /**
     * @param deliver the deliver to set
     */
    public void setDeliver(boolean deliver) {
        this.deliver = deliver;
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

}
