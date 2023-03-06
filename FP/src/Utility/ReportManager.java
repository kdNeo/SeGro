/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Database.tables.ReservationManager;

/**
 *
 * @author Sachintha
 */
public class ReportManager {

    public static void printLastInvoice() {
        System.out.println("last invoce printing");
    }

    public static void printLastInvoiceCopy() {
        System.out.println("last invoce copy printing");
    }

    public static void printLastCaterinReservation() {
        System.out.println("last catering reservation");
    }

    public static void printLastRoomReservation() {
        System.out.println("last room reservation");
    }

    public static void printLastHotelReservatin() {
        System.out.println("last hotel reservation");
    }

    public static void printReservationById(String id) {
        System.out.println(id);
    }

    public static void printLastGrn() {
        System.out.println("last grn");
    }
}
