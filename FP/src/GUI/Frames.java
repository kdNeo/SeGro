/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Sachintha
 */
public class Frames {

    public static Invoice invoice;
    public static Reservation reservation;
    public static Main main;
    public static LoginWindow login;
    public static MenuManagement menuManagement;
    public static GRN grn;
    public static Stock stock;

    public static Registation registration;

    public static void initialize() {
        invoice = new Invoice();
        reservation = new Reservation();
        main = new Main();
        login = new LoginWindow();
        registration = new Registation();
        menuManagement = new MenuManagement();
        grn = new GRN();
        stock = new Stock();
    }

    /*
    login user id in frames
     */
    public static void setLoginUser() {
        if (invoice != null) {
            invoice.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
            invoice.lb_user1.setText(Users.Login.logedUserAccount.getId() + "");
        }
        if (reservation != null) {
            reservation.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }
        if (main != null) {
            main.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }

        if (registration != null) {
            registration.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }
        if (menuManagement != null) {
            menuManagement.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }
        if (grn != null) {
            grn.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }
        if (stock != null) {
            stock.lb_user.setText(Users.Login.logedUserAccount.getId() + "");
        }
    }

    /**
     * Hide all the frame windows
     */
    private static void closeAll() {
        if (invoice != null) {
            invoice.dispose();
        }
        if (reservation != null) {
            reservation.dispose();
        }
        if (main != null) {
            main.dispose();
        }
        if (login != null) {
            login.dispose();
        }
        if (registration != null) {
            registration.dispose();
        }
        if (menuManagement != null) {
            menuManagement.dispose();
        }
        if (grn != null) {
            grn.dispose();
        }
        if (stock != null) {
            stock.dispose();
        }
    }

    /*
    clear all the fields in the frames
     */
    public static void clearFields() {
        if (invoice != null) {
            invoice.clearFields();
        }
        if (registration != null) {
            registration.clearFields();
        }
        if (reservation != null) {
            reservation.clearFields();
        }
        if (menuManagement != null) {
            menuManagement.clearField();
        }
        if (grn != null) {
            grn.clearFields();
        }
        if (stock != null) {
            stock.clearFields();
        }
    }

    /*
    Following functions will show only the related window and close all others.
     */
    public static void showMain() {
        if (main == null) {
            main = new Main();
        }
        closeAll();
        main.pack();
        main.setVisible(true);
    }

    public static void showRegistration() {
        if (registration == null) {
            registration = new Registation();
        }
        closeAll();
        registration.pack();
        registration.setVisible(true);
    }

    public static void showReservation() {
        if (reservation == null) {
            reservation = new Reservation();
        }
        closeAll();
        reservation.pack();
        reservation.setVisible(true);
    }

    public static void showInvoice() {
        if (invoice == null) {
            invoice = new Invoice();
        }
        closeAll();
        invoice.pack();
        invoice.setVisible(true);
    }

    public static void showLoginWindow() {
        if (login == null) {
            login = new LoginWindow();
        }
        closeAll();
        login.pack();
        login.setVisible(true);
    }

    public static void showMenuManagement() {
        if (menuManagement == null) {
            menuManagement = new MenuManagement();
        }
        closeAll();
        menuManagement.pack();
        menuManagement.setVisible(true);
    }

    public static void showGrn() {
        if (grn == null) {
            grn = new GRN();
        }
        closeAll();
        grn.pack();
        grn.setVisible(true);
    }

    public static void showStock() {
        if (stock == null) {
            stock = new Stock();
        }
        closeAll();
        stock.pack();
        stock.setVisible(true);
    }
}
