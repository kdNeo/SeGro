/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Sachintha
 */
public class RoomReservation {

    private Customer customer;

    private int id;
    private Date reservedTime;
    private BigDecimal advance;
    private Date chechInDate;
    private int noOfDays;
    private int adultCount;
    private int childCount;
    private BigDecimal total;
    private String status;
    private String description;
    private int invoice;
    private String customerTp;
    private int userAccount;
    private ArrayList<Integer> rooms;
    private int invoiceId;

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
     * @return the chechInDate
     */
    public Date getChechInDate() {
        return chechInDate;
    }

    /**
     * @param chechInDate the chechInDate to set
     */
    public void setChechInDate(Date chechInDate) {
        this.chechInDate = chechInDate;
    }

    /**
     * @return the noOfDays
     */
    public int getNoOfDays() {
        return noOfDays;
    }

    /**
     * @param noOfDays the noOfDays to set
     */
    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    /**
     * @return the adultCount
     */
    public int getAdultCount() {
        return adultCount;
    }

    /**
     * @param adultCount the adultCount to set
     */
    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    /**
     * @return the childCount
     */
    public int getChildCount() {
        return childCount;
    }

    /**
     * @param childCount the childCount to set
     */
    public void setChildCount(int childCount) {
        this.childCount = childCount;
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
     * @return the invoice
     */
    public int getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    /**
     * @return the customerTp
     */
    public String getCustomerTp() {
        return customerTp;
    }

    /**
     * @param customerTp the customerTp to set
     */
    public void setCustomerTp(String customerTp) {
        this.customerTp = customerTp;
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
     * @return the rooms
     */
    public ArrayList<Integer> getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(ArrayList<Integer> rooms) {
        this.rooms = rooms;
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
