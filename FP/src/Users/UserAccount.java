/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

/**
 *
 * @author Sachintha
 */
public class UserAccount {

    private int id;
    private boolean status;
    private String userName;
    private String password;
    private int empId;

    private boolean invoice;
    private boolean reservation;
    private boolean menu;
    private boolean grn;
    private boolean report;
    private boolean registration;
    private int currentSessionId;

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
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the empId
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @return the invoice
     */
    public boolean isInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    /**
     * @return the reservation
     */
    public boolean isReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    /**
     * @return the menu
     */
    public boolean isMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    /**
     * @return the grn
     */
    public boolean isGrn() {
        return grn;
    }

    /**
     * @param grn the grn to set
     */
    public void setGrn(boolean grn) {
        this.grn = grn;
    }

    /**
     * @return the report
     */
    public boolean isReport() {
        return report;
    }

    /**
     * @param report the report to set
     */
    public void setReport(boolean report) {
        this.report = report;
    }

    /**
     * @return the registration
     */
    public boolean isRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    /**
     * @return the currentSessionId
     */
    public int getCurrentSessionId() {
        return currentSessionId;
    }

    /**
     * @param currentSessionId the currentSessionId to set
     */
    public void setCurrentSessionId(int currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

}
