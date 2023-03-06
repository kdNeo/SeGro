/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Database.DB;
import Database.beans.ItemCode;
import Database.beans.Menu;
import Database.beans.MenuItem;
import Database.tables.BlockManager;
import Database.tables.HotelReservationManager;
import Database.tables.LocationManager;
import Database.tables.MenuItemManager;
import Database.tables.MenuManager;
import Database.tables.ReservationManager;
import Utility.Helper;
import Utility.ReportManager;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sachintha
 */
public class HotelReservation extends javax.swing.JPanel {

    ArrayList<JTable> tableList = new ArrayList<JTable>();
    private DefaultListModel clearListModal = new DefaultListModel();
    public Reservation parentReservation;
    public Reservation.ReservationClearState clearState;
    private static final String LOCATION_BLOCK = "Block";
    private static final String LOCATION_LOCATION = "Location";

    private static final String ITEM_ITEM = "Item";
    private static final String ITEM_MENU = "Menu";

    /**
     * Creates new form HotelReservation
     */
    public HotelReservation() {
        initComponents();
        addHelperListeners();
        addTablesToList();
        setTableLook();
    }

    private void addTablesToList() {
        tableList.add(tb_h_items);
        tableList.add(tb_h_loc_blocks);
        tableList.add(tbl_c_menu_window);
        tableList.add(tbl_c_menuitem_window);
        tableList.add(tbl_h_block_dialog);
        tableList.add(tbl_h_location_dialog);

    }

    private void setTableLook() {
        Font hf = new Font("Tahoma", 1, 14);
        Font f = new Font("Tahoma", 0, 14);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(SwingConstants.CENTER);
        for (JTable tb : tableList) {
            tb.getTableHeader().setFont(hf);
            tb.setFont(f);
            tb.setDefaultRenderer(Object.class, r);
        }

    }

    /**
     * add selected menu's items to side list
     */
    private void addSelectedMenuDialogItemToSideList() {
        int row = tbl_c_menu_window.getSelectedRow();
        if (row == -1) {
            return;
        }
        try (
                ResultSet rs = MenuManager.getItemsOfMenuByMenuCode(tbl_c_menu_window.getValueAt(row, 0).toString())) {
            if (rs == null) {
                return;
            }
            DefaultListModel dlm = new DefaultListModel();
            while (rs.next()) {
                dlm.addElement(rs.getString("code") + " - " + rs.getString("name"));
            }
            list_c_menu_window_items.setModel(dlm);
        } catch (Exception e) {
            DB.processException(e);
        }

    }

    /**
     * add selected block's locations to side list
     */
    private void addSelectedBlockLocationsToList() {
        int selectedRow = tbl_h_block_dialog.getSelectedRow();

        if (selectedRow >= 0) {
            try (
                    ResultSet rs = BlockManager.getLocations(tbl_h_block_dialog.getValueAt(selectedRow, 1).toString());) {
                if (rs == null) {
                    return;
                }
                DefaultListModel<String> dlm = new DefaultListModel<String>();
                String str = " - ";
                while (rs.next()) {
                    dlm.addElement(rs.getString("code") + str + rs.getString("name") + str + rs.getString("type") + str + rs.getString("max"));
                }
                list_h_block_dialog.setModel(dlm);
            } catch (Exception e) {
                DB.processException(e);
            }

        }
    }

    private void addHelperListeners() {

        tbl_c_menu_window.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addSelectedMenuDialogItemToSideList();
            }
        });

        tbl_h_block_dialog.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addSelectedBlockLocationsToList();
            }
        });
        tb_h_items.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotalHotel();
            }
        });

        tb_h_loc_blocks.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotalHotel();
            }
        });

    }

    /**
     * Hotel reservation helper functions
     *
     */
    /*
    calculate the total price
     */
    private void calculateTotalHotel() {
        int rowCount = tb_h_loc_blocks.getRowCount();
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < rowCount; i++) {
            total = total.add(new BigDecimal(tb_h_loc_blocks.getValueAt(i, 4).toString()));
        }
        rowCount = tb_h_items.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total = total.add(new BigDecimal(tb_h_items.getValueAt(i, 5).toString()));
        }
        tf_h_total.setText(total.setScale(2).toString());
    }

    /*
    check whether a table contain a given <b>value</b> in the column <b>column</b>
    in the table <b>tbl</b>
     */
    private boolean doesTableContain(JTable tbl, int column, String value) {

        int rowCount = tbl.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (tbl.getValueAt(i, column).toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /*
    remove the last row from a given <b>tbl</b>
     */
    private void removeLastRow(JTable tbl) {
        int rowCount = tbl.getRowCount();
        if (rowCount > 0) {
            ((DefaultTableModel) tbl.getModel()).removeRow(rowCount - 1);
        }
    }

    /**
     * check whether previously added locations are selected again by the block
     * selection
     *
     * @param tbBase
     * @param out
     * @return
     */
    private boolean locationsAlreadyAdded(JTable tbBase, JTable out) {
        boolean flag = false;
        boolean rowSelected = false;
        int sourceCount = tbBase.getRowCount();
        int targetCount = out.getRowCount();
        int selectedRow = tbBase.getSelectedRow();
        StringBuilder sb = new StringBuilder("The following blocks contain previously selected locations!\nPlease re select blocks or remove locations.\n");
        for (int i = 0; i < sourceCount; i++) {
            if (Boolean.parseBoolean(tbBase.getValueAt(i, 0).toString())) {
                rowSelected = true;
                String block = tbBase.getValueAt(i, 1).toString();
                for (int j = 0; j < targetCount; j++) {
                    String location = out.getValueAt(j, 0).toString();
                    String targetType = out.getValueAt(j, 2).toString();
                    if (targetType.equals(LOCATION_LOCATION)) {
                        if (BlockManager.doesBlockHasTheLocation(block, location)) {
                            sb.append("Block - ").append(block).append(" has Location - ").append(location).append(" \n");
                            flag = true;
                        }
                    }

                }
            }
        }
        if (!rowSelected) {
            String block = tbBase.getValueAt(selectedRow, 1).toString();
            for (int j = 0; j < targetCount; j++) {
                String location = out.getValueAt(j, 0).toString();
                String targetType = out.getValueAt(j, 2).toString();
                if (targetType.equals(LOCATION_LOCATION)) {
                    if (BlockManager.doesBlockHasTheLocation(block, location)) {
                        sb.append("Block - ").append(block).append(" has Location - ").append(location).append(" \n");
                        flag = true;
                    }
                }

            }
        }
        if (flag) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Multiple selection of locations!", JOptionPane.QUESTION_MESSAGE);
        }
        return flag;
    }

    private boolean blockAlreadyAddedTheLocation(JTable tbase, JTable tout) {

        boolean flag = false;
        boolean rowSelected = false;
        int sourceCount = tbase.getRowCount();
        int targetCount = tout.getRowCount();
        int selectedRow = tbase.getSelectedRow();
        StringBuilder sb = new StringBuilder("The following locations were previously selected by blocks!\nPlease re select locations or remove blocks.\n");
        for (int i = 0; i < sourceCount; i++) {
            if (Boolean.parseBoolean(tbase.getValueAt(i, 0).toString())) {
                rowSelected = true;
                String location = tbase.getValueAt(i, 1).toString();
                for (int j = 0; j < targetCount; j++) {
                    if (tout.getValueAt(j, 2).toString().equals(LOCATION_BLOCK)) {
                        String block = tout.getValueAt(j, 0).toString();
                        if (BlockManager.doesBlockHasTheLocation(block, location)) {
                            sb.append("Location - ").append(location).append(" is in the Block - ").append(block).append(" \n");
                            flag = true;
                        }
                    }
                }
            }
        }
        if (!rowSelected) {

            String location = tbase.getValueAt(selectedRow, 1).toString();
            for (int j = 0; j < targetCount; j++) {
                if (tout.getValueAt(j, 2).toString().equals(LOCATION_BLOCK)) {
                    String block = tout.getValueAt(j, 0).toString();
                    if (BlockManager.doesBlockHasTheLocation(block, location)) {
                        sb.append("Location - ").append(location).append(" is in the Block - ").append(block).append(" \n");
                        flag = true;
                    }
                }

            }
        }
        if (flag) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Multiple selection of locations through blocks!", JOptionPane.QUESTION_MESSAGE);
        }
        return flag;

    }

    /*
            make the hotel reservation and return iud status
     */
    private boolean makeHotelReservation() {
        if (validateFieldsHotel() && validatePaymentHotel()) {
            if (Helper.isTheDateToday(hotel_date)) {
                if (JOptionPane.showConfirmDialog(this, "Confirm reservation on today ?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                    hotel_date.grabFocus();
                    return false;
                }
            }

            if (JOptionPane.showConfirmDialog(this, "Make reservation?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Database.beans.HotelReservation res = new Database.beans.HotelReservation();
                res.setAdvance(new BigDecimal(tf_h_advance.getText()).setScale(2));
                res.setBlocks(getBlocksLocationsFromLocationTableHotel(LOCATION_BLOCK));

                Calendar selectedDate = hotel_date.getSelectedDate();
                selectedDate.set(Calendar.HOUR, Integer.parseInt(hotel_hours.getValue().toString()));
                selectedDate.set(Calendar.MINUTE, Integer.parseInt(hotel_minutes.getValue().toString()));
                res.setCheckInTime(selectedDate.getTime());

                res.setCount(Integer.parseInt(hotel_count.getValue().toString()));
                res.setCustomer(parentReservation.customer);
                res.setDescription(hotel_description.getText());
                res.setId(ReservationManager.getNextReservationId());
                res.setItems(getCateringCollection(ITEM_ITEM, tb_h_items));
                res.setLocations(getBlocksLocationsFromLocationTableHotel(LOCATION_LOCATION));
                res.setMenus(getCateringCollection(ITEM_MENU, tb_h_items));
                res.setReservedTime(new Date());
                res.setSatus(Reservation.RESERVATION_STATUS_RESERVED);
                res.setTotal(new BigDecimal(tf_h_total.getText()).setScale(2));
                res.setUserAccount(Users.Login.logedUserAccount.getId());
                if (HotelReservationManager.addRow(res)) {
                    JOptionPane.showMessageDialog(this, "Reserved succesfully.", "Success !", JOptionPane.INFORMATION_MESSAGE);
                    this.clearState = Reservation.ReservationClearState.HOLD;
                    clearAllHotel(false);
                    parentReservation.clearFieldsCustomer();
                    ReportManager.printLastHotelReservatin();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Reserved failed.", "Error !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        return false;
    }

    /*
    validate the input fields for hotel reservation
     */
    private boolean validateFieldsHotel() {
        if (!parentReservation.isCustomerValid()) {
            return false;
        }
        if (!Helper.validateDate(hotel_date)) {
            JOptionPane.showMessageDialog(this, "Please select valid date location.", "Invalid date selected!", JOptionPane.QUESTION_MESSAGE);
            hotel_date.grabFocus();
            return false;
        }
        if (tb_h_loc_blocks.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select reservation location.", "No location selected!", JOptionPane.QUESTION_MESSAGE);
            hotel_show_locations.grabFocus();
            return false;
        }
        if (Reservation.isEmpty(tf_h_advance)) {
            tf_h_advance.grabFocus();
            tf_h_advance.selectAll();
            return false;
        }
        if (Reservation.isEmpty(tf_h_amount)) {
            tf_h_amount.grabFocus();
            tf_h_amount.selectAll();
            return false;
        }
        return true;
    }

    /**
     * validate the payment fields of hotel reservation and set the balance.
     */
    private boolean validatePaymentHotel() {

        BigDecimal tot = new BigDecimal(tf_h_total.getText());
        BigDecimal advance = new BigDecimal(tf_h_advance.getText());
        BigDecimal amount = new BigDecimal(tf_h_amount.getText());
        BigDecimal balance = BigDecimal.ZERO;

        if (advance.compareTo(tot) == 1) {
            JOptionPane.showMessageDialog(this, "Paying more than total.", "Invalid payment!", JOptionPane.QUESTION_MESSAGE);
            tf_h_advance.grabFocus();
            tf_h_advance.selectAll();
            return false;
        }
        if (advance.compareTo(amount) == 1) {
            JOptionPane.showMessageDialog(this, "Amount is not sufficient.", "Invalid payment!", JOptionPane.QUESTION_MESSAGE);
            tf_h_amount.grabFocus();
            tf_h_amount.selectAll();
            return false;
        }
        balance = amount.subtract(advance);
        lb_h_bal.setText(balance.setScale(2).toString());
        return true;
    }

    /**
     * clear hotel reservation fields
     *
     * @param allFields
     */
    public void clearAllHotel(boolean allFields) {
        if (allFields) {
            tf_h_advance.setText(null);
            tf_h_amount.setText(null);
            lb_h_bal.setText("0.00");
        }

        hotel_date.setSelectedDate(Calendar.getInstance());
        hotel_count.setValue(Integer.valueOf("0"));
        hotel_hours.setValue(Integer.valueOf("0"));
        hotel_minutes.setValue(Integer.valueOf("0"));

        tf_h_menu.setText(null);
        tf_h_menu_qty.setText(null);
        tf_h_items.setText(null);
        tf_h_menu_item_qty.setText(null);

        hotel_description.setText(null);

        ((DefaultTableModel) tb_h_loc_blocks.getModel()).setRowCount(0);
        ((DefaultTableModel) tb_h_items.getModel()).setRowCount(0);

    }

    /**
     * get the selected blocks or location ids as and ArrayList<integer>
     *
     * @param String type constant
     * @return ArrayList of blocks or locations ids
     */
    private ArrayList<Integer> getBlocksLocationsFromLocationTableHotel(String type) {
        ArrayList<Integer> list = new ArrayList<>();
        int rowCount = tb_h_loc_blocks.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            if (tb_h_loc_blocks.getValueAt(i, 2).toString().equals(type)) {
                String valueOf = tb_h_loc_blocks.getValueAt(i, 0).toString();
                list.add(type.equals(LOCATION_BLOCK) ? BlockManager.getBlockIdByCode(valueOf) : LocationManager.getLocationIdByCode(valueOf));
            }
        }
        return list;
    }

    /**
     * get the selected menus and items
     */
    private HashMap<Integer, Integer> getCateringCollection(String type, JTable tbl) {
        int rowCount = tbl.getRowCount();
        if (rowCount == 0) {
            return null;
        }
        HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
        for (int i = 0; i < rowCount; i++) {
            if (tbl.getValueAt(i, 2).toString().equals(type)) {
                String code = tbl.getValueAt(i, 0).toString();
                int id = 0;
                if (type.equals(ITEM_MENU)) {
                    id = MenuManager.getIdByCode(code);
                } else {
                    id = MenuItemManager.getIdByCode(code);
                }
                int qty = Integer.parseInt(tbl.getValueAt(i, 4).toString());
                items.put(id, qty);
            }
        }
        return items;
    }

    /**
     * clear only the payment fields
     */
    private void clearTotalHotel() {
        tf_h_advance.setText(null);
        tf_h_amount.setText(null);
        lb_h_bal.setText("0.00");
    }

    /**
     * add the selected blocks to reservation table from block dialog
     */
    private void addSelectedBlocksToReservation() {
        int rows = tbl_h_block_dialog.getRowCount();
        int addedRows = 0;
        DefaultTableModel dtm = (DefaultTableModel) tb_h_loc_blocks.getModel();
        for (int i = 0; i < rows; i++) {
            if (Boolean.parseBoolean(tbl_h_block_dialog.getValueAt(i, 0).toString())) {
                if (!doesTableContain(tb_h_loc_blocks, 0, tbl_h_block_dialog.getValueAt(i, 1).toString())) {
                    Vector v = new Vector();
                    v.add(tbl_h_block_dialog.getValueAt(i, 1));
                    v.add(tbl_h_block_dialog.getValueAt(i, 2));
                    v.add("Block");
                    v.add(tbl_h_block_dialog.getValueAt(i, 3));
                    v.add(tbl_h_block_dialog.getValueAt(i, 4));
                    dtm.addRow(v);
                    addedRows++;
                    if (this.clearState == Reservation.ReservationClearState.HOLD) {
                        this.clearState = Reservation.ReservationClearState.CLEAR;
                        clearTotalHotel();
                    }

                }
            }
        }
        if (addedRows == 0) {
            int i = tbl_h_block_dialog.getSelectedRow();
            if (i >= 0) {
                if (!doesTableContain(tb_h_loc_blocks, 0, tbl_h_block_dialog.getValueAt(i, 1).toString())) {
                    Vector v = new Vector();
                    v.add(tbl_h_block_dialog.getValueAt(i, 1));
                    v.add(tbl_h_block_dialog.getValueAt(i, 2));
                    v.add("Block");
                    v.add(tbl_h_block_dialog.getValueAt(i, 3));
                    v.add(tbl_h_block_dialog.getValueAt(i, 4));
                    dtm.addRow(v);
                    if (this.clearState == Reservation.ReservationClearState.HOLD) {
                        this.clearState = Reservation.ReservationClearState.CLEAR;
                        clearTotalHotel();
                    }
                }
            }
        }
    }

    /**
     * add the selected locations to reservation table from locations dialog
     */
    private void addSelectedLocationsToReservation() {
        int rows = tbl_h_location_dialog.getRowCount();
        int addedRows = 0;
        DefaultTableModel dtm = (DefaultTableModel) tb_h_loc_blocks.getModel();
        for (int i = 0; i < rows; i++) {
            if (Boolean.parseBoolean(tbl_h_location_dialog.getValueAt(i, 0).toString())) {
                if (!doesTableContain(tb_h_loc_blocks, 0, tbl_h_location_dialog.getValueAt(i, 1).toString())) {
                    Vector v = new Vector();
                    v.add(tbl_h_location_dialog.getValueAt(i, 1));
                    v.add(tbl_h_location_dialog.getValueAt(i, 2));
                    v.add("Location");
                    v.add(tbl_h_location_dialog.getValueAt(i, 4));
                    v.add(tbl_h_location_dialog.getValueAt(i, 5));
                    dtm.addRow(v);
                    addedRows++;
                    if (this.clearState == Reservation.ReservationClearState.HOLD) {
                        this.clearState = Reservation.ReservationClearState.CLEAR;
                        clearTotalHotel();
                    }
                }
            }
        }
        if (addedRows == 0) {

            int i = tbl_h_location_dialog.getSelectedRow();
            if (i >= 0) {
                if (!doesTableContain(tb_h_loc_blocks, 0, tbl_h_location_dialog.getValueAt(i, 1).toString())) {
                    Vector v = new Vector();
                    v.add(tbl_h_location_dialog.getValueAt(i, 1));
                    v.add(tbl_h_location_dialog.getValueAt(i, 2));
                    v.add("Location");
                    v.add(tbl_h_location_dialog.getValueAt(i, 4));
                    v.add(tbl_h_location_dialog.getValueAt(i, 5));
                    dtm.addRow(v);
                    addedRows++;
                    if (this.clearState == Reservation.ReservationClearState.HOLD) {
                        this.clearState = Reservation.ReservationClearState.CLEAR;
                        clearTotalHotel();
                    }
                }
            }
        }
    }

    private void addHotelMenuByCode() {
        if (tf_h_menu.getText().isEmpty()) {
            tf_h_menu.grabFocus();
            tf_h_menu.selectAll();
            return;
        }

        ItemCode ic = Helper.getItemCode(tf_h_menu);

        int qty = 0;
        int itemcode = 0;

        if (ic == null) {
            if (tf_h_menu_qty.getText().isEmpty()) {
                tf_h_menu_qty.grabFocus();
                tf_h_menu_qty.selectAll();
                return;
            }
            qty = Integer.parseInt(tf_h_menu_qty.getText());
            if (qty == 0) {
                tf_h_menu.grabFocus();
                tf_h_menu.selectAll();
                return;
            }
            itemcode = Integer.parseInt(tf_h_menu.getText());

        } else {
            if (ic.getQty() == -1) {
                if (tf_h_menu_qty.getText().isEmpty()) {
                    tf_h_menu_qty.grabFocus();
                    tf_h_menu_qty.selectAll();
                    return;
                }

                qty = Integer.parseInt(tf_h_menu_qty.getText());
                if (qty == 0) {
                    tf_h_menu.grabFocus();
                    tf_h_menu.selectAll();
                    return;
                }
                itemcode = ic.getItemCode();

            } else {
                itemcode = ic.getItemCode();
                qty = ic.getQty();
            }
        }
        // get menu by item code and set qty
        Menu row = MenuManager.getRowByCode(String.valueOf(itemcode), true);
        if (row == null) {
            Helper.selectAfterStar(tf_h_menu);
            return;
        }
        addItemToFinalTable(row.getCode(), row.getName(), "Menu", row.getPrice(), String.valueOf(qty), tb_h_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            this.clearState = Reservation.ReservationClearState.CLEAR;
            clearTotalHotel();
        }
        tf_h_menu.setText(null);
        tf_h_menu_qty.setText(null);
        tf_h_menu.grabFocus();
    }

    private void addItemToFinalTable(String code, String name, String type, BigDecimal price, String qty, JTable btl) {
        DefaultTableModel dtm = (DefaultTableModel) btl.getModel();
        String priceStr = price.setScale(2).toString();
        String total = price.multiply(new BigDecimal(qty)).setScale(2).toString();
        Vector<String> v = new Vector();
        v.add(code);
        v.add(name);
        v.add(type);
        v.add(priceStr);
        v.add(qty);
        v.add(total);
        dtm.addRow(v);
    }

    private void addHotelMenuItemByCode() {
        if (tf_h_items.getText().isEmpty()) {
            tf_h_items.grabFocus();
            tf_h_items.selectAll();
            return;
        }
        ItemCode ic = Helper.getItemCode(tf_h_items);

        int qty = 0;
        int itemcode = 0;

        if (ic == null) {
            if (tf_h_menu_item_qty.getText().isEmpty()) {
                tf_h_menu_item_qty.grabFocus();
                tf_h_menu_item_qty.selectAll();
                return;
            }
            qty = Integer.parseInt(tf_h_menu_item_qty.getText());
            if (qty == 0) {
                tf_h_menu_item_qty.grabFocus();
                tf_h_menu_item_qty.selectAll();
                return;
            }
            itemcode = Integer.parseInt(tf_h_items.getText());

        } else {
            if (ic.getQty() == -1) {
                if (tf_h_menu_item_qty.getText().isEmpty()) {
                    tf_h_menu_item_qty.grabFocus();
                    tf_h_menu_item_qty.selectAll();
                    return;
                }

                qty = Integer.parseInt(tf_h_menu_item_qty.getText());
                if (qty == 0) {
                    tf_h_menu_item_qty.grabFocus();
                    tf_h_menu_item_qty.selectAll();
                    return;
                }
                itemcode = ic.getItemCode();

            } else {
                itemcode = ic.getItemCode();
                qty = ic.getQty();
            }
        }
        // get item by item code and set qty
        MenuItem row = MenuItemManager.getRowByCode(String.valueOf(itemcode), true);
        if (row == null) {
            Helper.selectAfterStar(tf_h_items);
            return;
        }
        addItemToFinalTable(row.getCode(), row.getName(), "Item", row.getPrice(), String.valueOf(qty), tb_h_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            this.clearState = Reservation.ReservationClearState.CLEAR;
            clearTotalHotel();
        }
        tf_h_items.setText(null);
        tf_h_menu_item_qty.setText(null);
        tf_h_items.grabFocus();
    }

    void showBlocks() {
        block_dialog.pack();
        block_dialog.setLocationRelativeTo(null);
        loadAllBlocksToBlockModal();
        tbl_h_block_dialog.clearSelection();
        list_h_block_dialog.setModel(clearListModal);
        block_dialog.setVisible(true);
    }

    /* load blocks only for the date that is selected*/
    private void loadAllBlocksToBlockModal() {
        try (
                ResultSet rs = BlockManager.getAll(hotel_date.getSelectedDate())) {
            DefaultTableModel dtm = (DefaultTableModel) tbl_h_block_dialog.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(false);
                v.add(rs.getString("code"));
                v.add(rs.getString("blockname"));
                v.add(rs.getString("max"));
                v.add(new BigDecimal(rs.getString("blockprice")).setScale(2).toString());
                dtm.addRow(v);

            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    private void showLocations() {
        location_dialog.pack();
        location_dialog.setLocationRelativeTo(null);
        loadAllLocationsToLocationModal();
        tbl_h_location_dialog.clearSelection();
        location_dialog.setVisible(true);
    }

    private void loadAllLocationsToLocationModal() {
        try (
                ResultSet rs = LocationManager.getAll(hotel_date.getSelectedDate());) {
            DefaultTableModel dtm = (DefaultTableModel) tbl_h_location_dialog.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(false);
                v.add(rs.getString("code"));
                v.add(rs.getString("name"));
                v.add(rs.getString("type"));
                v.add(rs.getString("max"));
                v.add(new BigDecimal(rs.getString("price")).setScale(2).toString());
                dtm.addRow(v);
            }

        } catch (Exception e) {
            DB.processException(e);
        }
    }

    private void checkSelectedRow(JTable tbl) {

    }

    private void clearTableContent(JTable tbl) {
        ((DefaultTableModel) tbl.getModel()).setRowCount(0);

    }

    /**
     * ******ending
     *
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        menu_item_dialog = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_c_menuitem_window = new javax.swing.JTable();
        menu_dialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_c_menu_window = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_c_menu_window_items = new javax.swing.JList<>();
        location_dialog = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tbl_h_location_dialog = new javax.swing.JTable();
        block_dialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_h_block_dialog = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        list_h_block_dialog = new javax.swing.JList<>();
        panel_hotel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        hotel_description = new com.alee.laf.text.WebTextArea();
        jLabel91 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        function_refresh = new com.alee.laf.button.WebButton();
        hotel_date = new datechooser.beans.DateChooserCombo();
        hotel_hours = new javax.swing.JSpinner();
        hotel_minutes = new javax.swing.JSpinner();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        hotel_count = new javax.swing.JSpinner();
        catering_pay1 = new com.alee.laf.button.WebButton();
        tf_h_total = new com.alee.laf.text.WebTextField();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        tf_h_advance = new com.alee.laf.text.WebTextField();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        tf_h_amount = new com.alee.laf.text.WebTextField();
        lb_h_bal = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_h_loc_blocks = new javax.swing.JTable();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_h_items = new javax.swing.JTable();
        hotel_show_locations = new com.alee.laf.button.WebButton();
        hotel_show_blocks = new com.alee.laf.button.WebButton();
        catering_menu1 = new com.alee.laf.button.WebButton();
        tf_h_menu = new com.alee.laf.text.WebTextField();
        tf_h_items = new com.alee.laf.text.WebTextField();
        jLabel147 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        tf_h_menu_item_qty = new javax.swing.JTextField();
        tf_h_menu_qty = new javax.swing.JTextField();
        catering_view_selected2 = new com.alee.laf.button.WebButton();

        menu_item_dialog.setModal(true);
        menu_item_dialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                menu_item_dialogKeyPressed(evt);
            }
        });

        tbl_c_menuitem_window.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Catagory", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_c_menuitem_window.getTableHeader().setReorderingAllowed(false);
        tbl_c_menuitem_window.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_c_menuitem_windowKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_c_menuitem_window);

        javax.swing.GroupLayout menu_item_dialogLayout = new javax.swing.GroupLayout(menu_item_dialog.getContentPane());
        menu_item_dialog.getContentPane().setLayout(menu_item_dialogLayout);
        menu_item_dialogLayout.setHorizontalGroup(
            menu_item_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );
        menu_item_dialogLayout.setVerticalGroup(
            menu_item_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        );

        menu_dialog.setTitle("Select Menu");
        menu_dialog.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        menu_dialog.setModal(true);
        menu_dialog.setResizable(false);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_c_menu_window.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_c_menu_window.getTableHeader().setReorderingAllowed(false);
        tbl_c_menu_window.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_c_menu_windowKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_c_menu_window);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 500));

        jScrollPane3.setViewportView(list_c_menu_window_items);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 260, 500));

        javax.swing.GroupLayout menu_dialogLayout = new javax.swing.GroupLayout(menu_dialog.getContentPane());
        menu_dialog.getContentPane().setLayout(menu_dialogLayout);
        menu_dialogLayout.setHorizontalGroup(
            menu_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 911, Short.MAX_VALUE)
        );
        menu_dialogLayout.setVerticalGroup(
            menu_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        location_dialog.setModal(true);
        location_dialog.setResizable(false);

        tbl_h_location_dialog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Code", "Name", "Type", "Max", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_h_location_dialog.getTableHeader().setReorderingAllowed(false);
        tbl_h_location_dialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_h_location_dialogKeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(tbl_h_location_dialog);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout location_dialogLayout = new javax.swing.GroupLayout(location_dialog.getContentPane());
        location_dialog.getContentPane().setLayout(location_dialogLayout);
        location_dialogLayout.setHorizontalGroup(
            location_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        location_dialogLayout.setVerticalGroup(
            location_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        block_dialog.setModal(true);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_h_block_dialog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Code", "Name", "Max", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_h_block_dialog.getTableHeader().setReorderingAllowed(false);
        tbl_h_block_dialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_h_block_dialogKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_h_block_dialog);

        jPanel3.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 498));

        jScrollPane11.setViewportView(list_h_block_dialog);

        jPanel3.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 210, 500));

        javax.swing.GroupLayout block_dialogLayout = new javax.swing.GroupLayout(block_dialog.getContentPane());
        block_dialog.getContentPane().setLayout(block_dialogLayout);
        block_dialogLayout.setHorizontalGroup(
            block_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        block_dialogLayout.setVerticalGroup(
            block_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(1058, 610));
        setMinimumSize(new java.awt.Dimension(1058, 610));
        setPreferredSize(new java.awt.Dimension(1058, 610));
        setLayout(new java.awt.GridBagLayout());

        panel_hotel.setBackground(new java.awt.Color(55, 63, 75));
        panel_hotel.setMaximumSize(new java.awt.Dimension(1058, 610));
        panel_hotel.setMinimumSize(new java.awt.Dimension(1058, 610));
        panel_hotel.setPreferredSize(new java.awt.Dimension(1058, 610));
        panel_hotel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hotel_description.setColumns(20);
        hotel_description.setRows(3);
        jScrollPane8.setViewportView(hotel_description);

        panel_hotel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 300, 60));

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Description:");
        panel_hotel.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, 20));

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Count");
        panel_hotel.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 70, 30));

        function_refresh.setText("Clear");
        function_refresh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refreshActionPerformed(evt);
            }
        });
        panel_hotel.add(function_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 80, 30));

        hotel_date.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        hotel_date.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                hotel_dateOnSelectionChange(evt);
            }
        });
        panel_hotel.add(hotel_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, -1));

        hotel_hours.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        panel_hotel.add(hotel_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 50, 20));

        hotel_minutes.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        panel_hotel.add(hotel_minutes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 50, 20));

        jLabel130.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setText("Minutes");
        panel_hotel.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 26));

        jLabel131.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setText("Hours");
        panel_hotel.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 26));

        jLabel132.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setText("Date:");
        panel_hotel.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 26));

        hotel_count.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        panel_hotel.add(hotel_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 60, 30));

        catering_pay1.setText("Reserve");
        catering_pay1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        catering_pay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_pay1ActionPerformed(evt);
            }
        });
        panel_hotel.add(catering_pay1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 500, 160, 60));

        tf_h_total.setEditable(false);
        tf_h_total.setEnabled(false);
        tf_h_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_h_total.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tf_h_totalPropertyChange(evt);
            }
        });
        panel_hotel.add(tf_h_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 410, 210, 30));

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setText("Total :");
        panel_hotel.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, -1, 20));

        jLabel150.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(255, 255, 255));
        jLabel150.setText("Advance:");
        panel_hotel.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 450, -1, 30));

        tf_h_advance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_h_advance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_advanceKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_advanceKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_advance, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 210, 40));

        jLabel156.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel156.setForeground(new java.awt.Color(255, 255, 255));
        jLabel156.setText("Balence :");
        panel_hotel.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 460, -1, 30));

        jLabel157.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel157.setForeground(new java.awt.Color(255, 255, 255));
        jLabel157.setText("Amount :");
        panel_hotel.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 410, -1, 30));

        tf_h_amount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_h_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_amountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_amountKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 410, 210, 40));

        lb_h_bal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_h_bal.setForeground(new java.awt.Color(255, 255, 255));
        lb_h_bal.setText("0.00");
        panel_hotel.add(lb_h_bal, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 460, 130, 30));

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Locations", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 18))); // NOI18N
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_h_loc_blocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Type", "Max", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_h_loc_blocks.getTableHeader().setReorderingAllowed(false);
        tb_h_loc_blocks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_h_loc_blocksKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tb_h_loc_blocks);

        jPanel42.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 630, 160));

        panel_hotel.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 650, 200));

        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 18))); // NOI18N
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tb_h_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Type", "Price", "Qty", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_h_items.getTableHeader().setReorderingAllowed(false);
        tb_h_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_h_itemsKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tb_h_items);

        jPanel43.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 630, 140));

        panel_hotel.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 650, 180));

        hotel_show_locations.setText("Select Location");
        hotel_show_locations.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hotel_show_locations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotel_show_locationsActionPerformed(evt);
            }
        });
        panel_hotel.add(hotel_show_locations, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 180, 40));

        hotel_show_blocks.setText("Select Block");
        hotel_show_blocks.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        hotel_show_blocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotel_show_blocksActionPerformed(evt);
            }
        });
        panel_hotel.add(hotel_show_blocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 170, 40));

        catering_menu1.setText("Select Menu");
        catering_menu1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_menu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_menu1ActionPerformed(evt);
            }
        });
        panel_hotel.add(catering_menu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 170, 40));

        tf_h_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_h_menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_menuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_menuKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 180, 30));

        tf_h_items.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_h_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_itemsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_itemsKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 180, 30));

        jLabel147.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 255, 255));
        jLabel147.setText("Item");
        panel_hotel.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 50, 30));

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText("Menu");
        panel_hotel.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 50, 30));

        jButton6.setText("Add");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        panel_hotel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 60, 30));

        jButton5.setText("Add");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panel_hotel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 60, 30));

        tf_h_menu_item_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_menu_item_qtyKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_menu_item_qtyKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_menu_item_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 50, 30));

        tf_h_menu_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_h_menu_qtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_h_menu_qtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_h_menu_qtyKeyTyped(evt);
            }
        });
        panel_hotel.add(tf_h_menu_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 50, 30));

        catering_view_selected2.setText("Select Item");
        catering_view_selected2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_view_selected2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_view_selected2ActionPerformed(evt);
            }
        });
        panel_hotel.add(catering_view_selected2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 180, 40));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -1;
        gridBagConstraints.ipady = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        add(panel_hotel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void function_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refreshActionPerformed
        clearAllHotel(true);
    }//GEN-LAST:event_function_refreshActionPerformed

    private void hotel_dateOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_hotel_dateOnSelectionChange
        clearTableContent(tb_h_loc_blocks);
    }//GEN-LAST:event_hotel_dateOnSelectionChange

    private void catering_pay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_pay1ActionPerformed
        makeHotelReservation();
    }//GEN-LAST:event_catering_pay1ActionPerformed

    private void tf_h_menuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menuKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addHotelMenuByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tb_h_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_h_menuKeyPressed

    private void tf_h_menuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menuKeyTyped
        Helper.itemCodeAdd(tf_h_menu, evt);
    }//GEN-LAST:event_tf_h_menuKeyTyped

    private void hotel_show_locationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotel_show_locationsActionPerformed
        if (!Helper.validateDate(hotel_date)) {
            JOptionPane.showMessageDialog(this, "Please select valid date.", "Invalid date !", JOptionPane.QUESTION_MESSAGE);
            hotel_date.grabFocus();
        } else {
            showLocations();
        }
    }//GEN-LAST:event_hotel_show_locationsActionPerformed

    private void tf_h_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addHotelMenuItemByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuItemDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tb_h_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_h_itemsKeyPressed

    private void tf_h_itemsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_itemsKeyTyped
        Helper.itemCodeAdd(tf_h_items, evt);
    }//GEN-LAST:event_tf_h_itemsKeyTyped

    private void catering_menu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_menu1ActionPerformed
        showMenuDialog();
    }//GEN-LAST:event_catering_menu1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        addHotelMenuItemByCode();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        addHotelMenuByCode();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tf_h_menu_item_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menu_item_qtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addHotelMenuItemByCode();
        }
    }//GEN-LAST:event_tf_h_menu_item_qtyKeyPressed

    private void tf_h_menu_item_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menu_item_qtyKeyTyped
        Helper.numericDataAddOnlyDigits(tf_h_menu_item_qty, evt);
    }//GEN-LAST:event_tf_h_menu_item_qtyKeyTyped

    private void tf_h_menu_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menu_qtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addHotelMenuByCode();
        }
    }//GEN-LAST:event_tf_h_menu_qtyKeyPressed

    private void tf_h_menu_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menu_qtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_h_menu_qtyKeyReleased

    private void tf_h_menu_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_menu_qtyKeyTyped
        Helper.numericDataAddOnlyDigits(tf_h_menu_qty, evt);
    }//GEN-LAST:event_tf_h_menu_qtyKeyTyped

    private void catering_view_selected2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_view_selected2ActionPerformed
        showMenuItemDialog();
    }//GEN-LAST:event_catering_view_selected2ActionPerformed

    private void hotel_show_blocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotel_show_blocksActionPerformed
        if (!Helper.validateDate(hotel_date)) {
            JOptionPane.showMessageDialog(this, "Please select valid date.", "Invalid date !", JOptionPane.QUESTION_MESSAGE);
            hotel_date.grabFocus();
        } else {
            showBlocks();
        }
    }//GEN-LAST:event_hotel_show_blocksActionPerformed

    private void tf_h_totalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tf_h_totalPropertyChange

    }//GEN-LAST:event_tf_h_totalPropertyChange

    private void tf_h_advanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_advanceKeyTyped
        Helper.numericDataAdd(tf_h_advance, evt);
    }//GEN-LAST:event_tf_h_advanceKeyTyped

    private void tf_h_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_amountKeyTyped
        Helper.numericDataAdd(tf_h_amount, evt);
    }//GEN-LAST:event_tf_h_amountKeyTyped

    private void tb_h_loc_blocksKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_h_loc_blocksKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeSelectedRow(tb_h_loc_blocks);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tb_h_loc_blocksKeyPressed

    private void tb_h_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_h_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeSelectedRow(tb_h_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tb_h_itemsKeyPressed

    private void showMenuItemDialog() {
        menu_item_dialog.pack();
        menu_item_dialog.setLocationRelativeTo(null);
        addAllMenuItemsToMenuItemDialogTable();
        tbl_c_menuitem_window.clearSelection();
        menu_item_dialog.setVisible(true);
    }

    private void showMenuDialog() {

        menu_dialog.pack();
        menu_dialog.setLocationRelativeTo(null);
        addAllMenusToMenuDialogTable();
        tbl_c_menu_window.clearSelection();
        list_c_menu_window_items.setModel(clearListModal);
        menu_dialog.setVisible(true);
    }

    private void addAllMenuItemsToMenuItemDialogTable() {
        try (
                ResultSet rs = MenuItemManager.getAll(true);) {
            if (rs == null) {
                return;
            }
            DefaultTableModel dtm = (DefaultTableModel) tbl_c_menuitem_window.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> v = new Vector();
                v.add(rs.getString("code"));
                v.add(rs.getString("itemname"));
                v.add(rs.getString("menu_item_category_name"));
                v.add(new BigDecimal(rs.getString("price")).setScale(2).toString());
                dtm.addRow(v);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    private void addAllMenusToMenuDialogTable() {
        try (
                ResultSet rs = MenuManager.getAll(true)) {
            DefaultTableModel dtm = (DefaultTableModel) tbl_c_menu_window.getModel();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector<String> v = new Vector<String>();
                v.add(rs.getString("code"));
                v.add(rs.getString("menuname"));

                v.add(new BigDecimal(rs.getString("price")).setScale(2).toString());
                dtm.addRow(v);
            }
        } catch (Exception e) {
            DB.processException(e);
        }

    }

    private void removeSelectedRow(JTable tbl) {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
            dtm.removeRow(row);
        }
    }

    private void addSelectedCodeToTextField(JTable tbl, JTextComponent tf, int codeColumn) {
        int row = tbl.getSelectedRow();
        if (row == -1) {
            return;
        }
        String code = tbl.getValueAt(row, codeColumn).toString();
        String current = tf.getText();
        if (current.trim().isEmpty()) {
            tf.setText(code);
        } else {
            tf.setText(current + code);
        }
    }

    private void tbl_c_menuitem_windowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_c_menuitem_windowKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {

            addSelectedCodeToTextField(tbl_c_menuitem_window, tf_h_items, 0);
            menu_item_dialog.dispose();
            addHotelMenuItemByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            menu_item_dialog.dispose();
            tf_h_menu_item_qty.grabFocus();
        }
    }//GEN-LAST:event_tbl_c_menuitem_windowKeyPressed

    private void menu_item_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menu_item_dialogKeyPressed

    }//GEN-LAST:event_menu_item_dialogKeyPressed

    private void tbl_c_menu_windowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_c_menu_windowKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {

            addSelectedCodeToTextField(tbl_c_menu_window, tf_h_menu, 0);
            menu_dialog.dispose();
            addHotelMenuByCode();

        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            menu_dialog.dispose();
            tf_h_menu.grabFocus();
        }
    }//GEN-LAST:event_tbl_c_menu_windowKeyPressed

    private void tbl_h_location_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_h_location_dialogKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!blockAlreadyAddedTheLocation(tbl_h_location_dialog, tb_h_loc_blocks)) {
                addSelectedLocationsToReservation();
                location_dialog.dispose();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            checkSelectedRow(tbl_h_location_dialog);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            location_dialog.dispose();
            hotel_show_locations.grabFocus();
        }
    }//GEN-LAST:event_tbl_h_location_dialogKeyPressed

    private void tbl_h_block_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_h_block_dialogKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!locationsAlreadyAdded(tbl_h_block_dialog, tb_h_loc_blocks)) {
                addSelectedBlocksToReservation();
                block_dialog.dispose();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            checkSelectedRow(tbl_h_block_dialog);
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            block_dialog.dispose();
            hotel_show_blocks.grabFocus();
        }
    }//GEN-LAST:event_tbl_h_block_dialogKeyPressed

    private void tf_h_advanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_advanceKeyPressed
        if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_h_advanceKeyPressed

    private void tf_h_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_h_amountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            makeHotelReservation();
        } else if (evt.getKeyCode() == 109) {
            calculateTotalHotel();
            tf_h_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalHotel();
            tf_h_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_h_amountKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog block_dialog;
    private com.alee.laf.button.WebButton catering_menu1;
    private com.alee.laf.button.WebButton catering_pay1;
    private com.alee.laf.button.WebButton catering_view_selected2;
    private com.alee.laf.button.WebButton function_refresh;
    private javax.swing.JSpinner hotel_count;
    private datechooser.beans.DateChooserCombo hotel_date;
    private com.alee.laf.text.WebTextArea hotel_description;
    private javax.swing.JSpinner hotel_hours;
    private javax.swing.JSpinner hotel_minutes;
    private com.alee.laf.button.WebButton hotel_show_blocks;
    private com.alee.laf.button.WebButton hotel_show_locations;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lb_h_bal;
    private javax.swing.JList<String> list_c_menu_window_items;
    private javax.swing.JList<String> list_h_block_dialog;
    private javax.swing.JDialog location_dialog;
    private javax.swing.JDialog menu_dialog;
    private javax.swing.JDialog menu_item_dialog;
    private javax.swing.JPanel panel_hotel;
    private javax.swing.JTable tb_h_items;
    private javax.swing.JTable tb_h_loc_blocks;
    private javax.swing.JTable tbl_c_menu_window;
    private javax.swing.JTable tbl_c_menuitem_window;
    private javax.swing.JTable tbl_h_block_dialog;
    private javax.swing.JTable tbl_h_location_dialog;
    private com.alee.laf.text.WebTextField tf_h_advance;
    private com.alee.laf.text.WebTextField tf_h_amount;
    private com.alee.laf.text.WebTextField tf_h_items;
    private com.alee.laf.text.WebTextField tf_h_menu;
    private javax.swing.JTextField tf_h_menu_item_qty;
    private javax.swing.JTextField tf_h_menu_qty;
    private com.alee.laf.text.WebTextField tf_h_total;
    // End of variables declaration//GEN-END:variables
}
