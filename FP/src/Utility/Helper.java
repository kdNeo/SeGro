/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Database.DB;
import Database.beans.ItemCode;
import datechooser.beans.DateChooserCombo;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sachintha
 */
public class Helper {

    private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd H:m:s");
    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateTimeForDb(Date date) {
        return sdfTime.format(date);
    }

    public static Date getDateFromDb(String date) {
        try {
            return sdfTime.parse(date);
        } catch (ParseException ex) {
            DB.processException(ex);
        }
        return null;
    }

    public static Date getDateOnlyFromDb(String date) {
        try {
            return sdfDate.parse(date);
        } catch (ParseException ex) {
            DB.processException(ex);
        }
        return null;
    }

    public static String getDateForDb(Date date) {
        return sdfDate.format(date);
    }

    /**
     * validate given string to be a valid NIC
     */
    public static boolean validateNic(String nic) {
        if (nic.isEmpty()) {
            return false;
        }
        boolean flag = true;
        char[] nicArr = nic.toCharArray();
        int length = nicArr.length;
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(nicArr[i])) {
                if (i != length - 1) {
                    flag = false;
                }
            }
        }
        if (flag) {
            if (!(nic.length() == 10 && (nic.endsWith("V") || nic.endsWith("v")))) {
                flag = false;
            } else if (nic.length() > 12) {
                flag = false;
            } else if (nic.length() < 9) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * validate the passed string to be a contact number
     */
    public static boolean validateContact(String ct) {
        if (ct.trim().isEmpty()) {
            return false;
        }
        char[] toCharArray = ct.toCharArray();
        if (toCharArray.length != 10) {
            return false;
        }
        for (char c : toCharArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * validate the selected date of the passed DateChooserCombo. If only the
     * selected day is either today or a day after today this will return true.
     * This function do not encounter the hours, minutes or seconds.
     */
    public static boolean validateDate(DateChooserCombo df) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.HOUR, 0);

        Calendar given = df.getSelectedDate();
        if (given == null) {
            return false;
        }
        given.set(Calendar.MINUTE, 0);
        given.set(Calendar.SECOND, 0);
        given.set(Calendar.MILLISECOND, 0);
        given.set(Calendar.HOUR, 0);

        return !given.before(now);
    }

    public static boolean isTheDateToday(DateChooserCombo dc) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.HOUR, 0);

        Calendar given = dc.getSelectedDate();
        given.set(Calendar.MINUTE, 0);
        given.set(Calendar.SECOND, 0);
        given.set(Calendar.MILLISECOND, 0);
        given.set(Calendar.HOUR, 0);
        if (now.compareTo(given) == 0) {
            return true;
        }
        return false;
    }

    public static void numericDataAdd(JTextComponent c, KeyEvent e) {
        String text = c.getText();
        char key = e.getKeyChar();
        if (text.equals("0.00")) {
            if (Character.isDigit(key)) {
                e.consume();
                c.setText(key + "");
            }
        } else if (text.isEmpty()) {
            if (Character.isDigit(key)) {
                if (key == '0') {
                    //e.consume();
                }
            } else {
                e.consume();
            }
        } else if (text.equals("0")) {
            if (key == '.') {

            } else if (Character.isDigit(key)) {
                c.setText(String.valueOf(key));
                e.consume();
            } else {
                e.consume();
            }
        } else {

            if (Character.isDigit(key)) {
                if (text.contains(".")) {
                    int dot = text.indexOf(".");
                    if (text.length() >= dot + 3) {
                        e.consume();
                    }
                }
                if (key == '.') {
                    e.consume();
                }
            } else if (key == '.') {
                if (text.contains(".")) {
                    e.consume();
                }
            } else {
                e.consume();
            }
        }
    }

    public static void numericDataAddOnlyDigits(JTextComponent c, KeyEvent e) {
        String text = c.getText();
        char key = e.getKeyChar();

        if (!Character.isDigit(key)) {
            e.consume();
        } else if (text.isEmpty()) {
            if (key == '0') {
                e.consume();
            }
        } else {
            e.consume();
            c.setText(text + key);
        }
    }

    public static void contactAdd(JTextComponent c, KeyEvent e) {
        String text = c.getText();
        char key = e.getKeyChar();
        if (text.length() >= 10) {
            e.consume();
        } else if (!Character.isDigit(key)) {
            e.consume();
        } else {
            e.consume();
            c.setText(text + key);
        }
    }

    public static void itemCodeAdd(JTextComponent c, KeyEvent e) {
        String text = c.getText();
        char key = e.getKeyChar();
        if (key == '*') {
            if (text.contains("*")) {
                e.consume();
            } else if (text.isEmpty()) {
                e.consume();
            }
        } else if (!Character.isDigit(key)) {
            e.consume();
        }
    }

    public static ItemCode getItemCode(JTextComponent c) {

        String text = c.getText();
        int pos = text.indexOf("*");
        if (pos == -1) {
            return null;
        }
        String qty = text.substring(0, pos);
        if (pos + 1 == text.length()) {
            return new ItemCode(Integer.parseInt(qty), -1);
        }
        String code = text.substring(pos + 1);
        return new ItemCode(Integer.parseInt(code), Integer.parseInt(qty));
    }

    public static void selectAfterStar(JTextComponent c) {
        String text = c.getText();
        int pos = text.indexOf("*");
        System.out.println(text);
        System.out.println(pos);
        if (pos == -1) {
            c.selectAll();
            c.grabFocus();
            return;
        }
        int length = text.length();
        c.grabFocus();
        c.setSelectionStart(pos + 1);
    }

    public static void codeIdd(JTextComponent c, KeyEvent e, int fieldSize) {
        if (c.getText().length() >= fieldSize) {
            e.consume();
            return;
        }
        numericDataAddOnlyDigits(c, e);
    }

    public static boolean doesCodeExists(String code) {

        String sql = " select 1 from menu where code='" + code + "' limit 1 ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = " select 1 from menu_item where code = '" + code + "' limit 1 ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = " select 1 from block where code = '" + code + "' limit 1  ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = " select 1 from room where code = '" + code + "' limit 1 ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = " select 1 from product where code = '" + code + "' limit 1 ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        sql = " select 1 from location where code = '" + code + "' limit 1 ";
        try (
                ResultSet search = DB.search(sql);) {
            if (search.next()) {
                return true;

            }
        } catch (Exception e) {
            DB.processException(e);
        }
        return false;
    }
}
