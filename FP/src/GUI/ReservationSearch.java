/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Database.DB;
import Database.beans.Block;
import Database.beans.Location;
import Database.beans.Menu;
import Database.beans.MenuItem;
import Database.beans.Room;
import Database.tables.BlockManager;
import Database.tables.CateringReservationManager;
import Database.tables.HotelReservationManager;
import Database.tables.LocationManager;
import Database.tables.MenuItemManager;
import Database.tables.MenuManager;
import Database.tables.ReservationManager;
import Database.tables.ReservationManager.ReservationType;
import Database.tables.RoomManager;
import Database.tables.RoomReservationManager;
import Utility.Helper;
import Utility.ReportManager;
import datechooser.beans.DateChooserCombo;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sachintha
 */
public class ReservationSearch extends javax.swing.JPanel {

    private DefaultListModel clearListModal = new DefaultListModel();

    private static String reserve_type_Catering = "Catering Reservation";
    private static String reserve_type_Hotel = "Hotel Reservation";
    private static String reserve_type_Room = "Room Reservation";
    private String currentReservation;

    /**
     * Creates new form ReservationSearch
     */
    public ReservationSearch() {
        initComponents();
        addHelperListeners();
    }

    private void addHelperListeners() {

        tbl_dialog_reservation_catering.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultTableModel dtmMenus = (DefaultTableModel) tbl_dialog_reservation_catering_menus.getModel();
                DefaultTableModel dtmItems = (DefaultTableModel) tbl_dialog_reservation_catering_items.getModel();
                dtmMenus.setRowCount(0);
                dtmItems.setRowCount(0);
                ta_tbl_dialog_reservation_catering_desc.setText(null);
                int selectedRow = tbl_dialog_reservation_catering.getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }
                int id = Integer.parseInt(tbl_dialog_reservation_catering.getValueAt(selectedRow, 0).toString());

                HashMap<Integer, Integer> menus = CateringReservationManager.getMenus(id);
                Set<Integer> keySet = menus.keySet();
                for (Integer i : keySet) {

                    Menu rowByCode = MenuManager.getRowByCode(MenuManager.getCodeById(i), false);
                    if (rowByCode == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(rowByCode.getCode());
                    v.add(rowByCode.getName());
                    v.add(rowByCode.getPrice().setScale(2).toString());
                    v.add(menus.get(i));
                    dtmMenus.addRow(v);
                }
                HashMap<Integer, Integer> items = CateringReservationManager.getItems(id);
                Set<Integer> keySet1 = items.keySet();
                for (Integer i : keySet1) {
                    MenuItem rowById = MenuItemManager.getRowById(i, false);
                    if (rowById == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(rowById.getCode());
                    v.add(rowById.getName());
                    v.add(rowById.getPrice().setScale(2).toString());
                    v.add(items.get(i));
                    dtmItems.addRow(v);
                }
                ta_tbl_dialog_reservation_catering_desc.setText(CateringReservationManager.getDescriptionById(id));
            }
        });
        tbl_dialog_reservation_hotel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultTableModel dtmBlocks = (DefaultTableModel) tbl_dialog_reservation_hotel_blocks.getModel();
                DefaultTableModel dtmLocations = (DefaultTableModel) tbl_dialog_reservation_hotel_locations.getModel();
                DefaultTableModel dtmItems = (DefaultTableModel) tbl_dialog_reservation_hotel_items.getModel();
                DefaultTableModel dtmMenues = (DefaultTableModel) tbl_dialog_reservation_hotel_menus.getModel();
                dtmBlocks.setRowCount(0);
                dtmLocations.setRowCount(0);
                dtmItems.setRowCount(0);
                dtmMenues.setRowCount(0);
                tal_dialog_reservation_hotel_desc.setText(null);

                int selectedRow = tbl_dialog_reservation_hotel.getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }
                int id = Integer.parseInt(tbl_dialog_reservation_hotel.getValueAt(selectedRow, 0).toString());

                HashMap<Integer, Integer> menus = HotelReservationManager.getMenus(id + "");
                Set<Integer> keySet = menus.keySet();
                for (Integer i : keySet) {

                    Menu rowByCode = MenuManager.getRowByCode(MenuManager.getCodeById(i), false);
                    if (rowByCode == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(rowByCode.getCode());
                    v.add(rowByCode.getName());
                    v.add(rowByCode.getPrice().setScale(2).toString());
                    v.add(menus.get(i));
                    dtmMenues.addRow(v);
                }
                HashMap<Integer, Integer> items = HotelReservationManager.getItems(id + "");
                Set<Integer> keySet1 = items.keySet();
                for (Integer i : keySet1) {
                    MenuItem rowById = MenuItemManager.getRowById(i, false);
                    if (rowById == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(rowById.getCode());
                    v.add(rowById.getName());
                    v.add(rowById.getPrice().setScale(2).toString());
                    v.add(items.get(i));
                    dtmItems.addRow(v);
                }

                ArrayList<Integer> locations = HotelReservationManager.getLocations(id + "");
                for (Integer i : locations) {
                    Location row = LocationManager.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(row.getCode());
                    v.add(row.getName());
                    v.add(row.getMax());
                    v.add(row.getPrice().setScale(2).toString());
                    dtmLocations.addRow(v);
                }
                ArrayList<Integer> blocks = HotelReservationManager.getBlocks(id + "");
                for (Integer i : blocks) {
                    Block row = BlockManager.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(row.getCode());
                    v.add(row.getName());
                    v.add(row.getMax());
                    v.add(row.getPrice().setScale(2).toString());
                    dtmBlocks.addRow(v);
                }
                tal_dialog_reservation_hotel_desc.setText(HotelReservationManager.getDescriptionById(id));
            }
        });

        tbl_dialog_reservation_room.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                DefaultTableModel dtm = (DefaultTableModel) tbl_dialog_reservation_room_rooms.getModel();
                dtm.setRowCount(0);
                tal_dialog_reservation_hotel_desc1.setText(null);
                int selectedRow = tbl_dialog_reservation_room.getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }
                int id = Integer.parseInt(tbl_dialog_reservation_room.getValueAt(selectedRow, 0).toString());
                ArrayList<Integer> rooms = RoomReservationManager.getRooms(id);
                for (Integer i : rooms) {
                    Room rowById = RoomManager.getRowById(i);
                    if (rowById == null) {
                        continue;
                    }
                    Vector v = new Vector();
                    v.add(rowById.getCode());
                    v.add(rowById.getName());
                    v.add(rowById.getBedCount());
                    v.add(rowById.getPrice().setScale(2).toString());
                    dtm.addRow(v);
                }
                tal_dialog_reservation_hotel_desc1.setText(RoomReservationManager.getDescriptionById(id));
            }

        });
    }

    /**
     * ****** searching reservations functions ******* Select the matching
     * reservation type and returns the appropriate enum.
     * <br>
     * <b>Parameters</b>
     * <br>
     * The component that used to select the reservation type
     * <br>
     * <b>Returns</b>
     * <br>
     * ReservationType
     */
    public ReservationType getSelectedReservationType(JComboBox selector) {
        String toString = selector.getSelectedItem().toString();
        if (toString.equals(reserve_type_Catering)) {
            return ReservationType.CATERING;
        }
        if (toString.equals(reserve_type_Hotel)) {
            return ReservationType.HOTEL;
        }
        if (toString.equals(reserve_type_Room)) {
            return ReservationType.ROOM;
        }
        return null;
    }

    private enum DateType {
        CHECK_IN, SESERVED
    }

    /**
     * send a date chooser component , reservation type that need to be queried
     * from , type of the sending date(reserved or checkin), customer tp
     * component which contain the customer tp
     *
     * first this will consider the reservation type then it will check the date
     * type if the date type is non from the given enums it will consider
     * customer tp if need to search for customer tp then call the function with
     * dtype as null
     */
    public void loadReservationsByDate(DateChooserCombo date, ReservationType type, DateType dtype, JTextComponent customerTp) {

        if (type == ReservationType.CATERING) {
            ArrayList<Database.beans.CateringReservation> reservations = null;
            if (dtype == DateType.SESERVED) {
                reservations = CateringReservationManager.getReservationsByDate(date.getSelectedDate());
            } else if (dtype == DateType.CHECK_IN) {
                reservations = CateringReservationManager.getReservationsByCheckInDate(date.getSelectedDate());
            } else {
                String text = customerTp.getText();
                if (text.isEmpty()) {
                    customerTp.grabFocus();
                    customerTp.selectAll();
                    return;
                }
                reservations = CateringReservationManager.getReservationsByCustomerTp(text);
            }
            if (reservations.size() == 0) {
                JOptionPane.showMessageDialog(this, "No resrevation fount !", null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            DefaultTableModel dtm = (DefaultTableModel) tbl_dialog_reservation_catering.getModel();
            dtm.setRowCount(0);
            for (Database.beans.CateringReservation res : reservations) {
                Vector v = new Vector();
                v.add(res.getId());
                v.add(res.getCustomer().getTp());
                v.add(Helper.getDateTimeForDb(res.getReservedTime()));
                v.add(Helper.getDateTimeForDb(res.getCheckInDateTime()));
                v.add(res.getStatus());
                v.add(res.getTotal().setScale(2).toString());
                v.add(res.getAdvance().setScale(2).toString());
                v.add(res.isDeliver() ? "Deliver" : "Take");
                v.add(res.getUserAccount());
                dtm.addRow(v);
            }
            dialog_serervation_catering.pack();
            dialog_serervation_catering.setLocationRelativeTo(null);
            dialog_serervation_catering.setVisible(true);
        } else if (type == ReservationType.HOTEL) {
            ArrayList<Database.beans.HotelReservation> reservations = null;
            if (dtype == DateType.SESERVED) {
                reservations = HotelReservationManager.getReservationsByDate(date.getSelectedDate());
            } else if (dtype == DateType.CHECK_IN) {
                reservations = HotelReservationManager.getReservationsByCheckInDate(date.getSelectedDate());
            } else {
                String text = customerTp.getText();
                if (text.isEmpty()) {
                    customerTp.grabFocus();
                    customerTp.selectAll();
                    return;
                }
                reservations = HotelReservationManager.getReservationsByCustomerTp(text);
            }
            if (reservations.size() == 0) {
                JOptionPane.showMessageDialog(this, "No resrevation fount !", null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            DefaultTableModel dtm = (DefaultTableModel) tbl_dialog_reservation_hotel.getModel();
            dtm.setRowCount(0);
            for (Database.beans.HotelReservation res : reservations) {
                Vector v = new Vector();
                v.add(res.getId());
                v.add(res.getCustomer().getTp());
                v.add(Helper.getDateTimeForDb(res.getReservedTime()));
                v.add(Helper.getDateTimeForDb(res.getCheckInTime()));
                v.add(res.getCount());
                v.add(res.getSatus());
                v.add(res.getTotal().setScale(2).toString());
                v.add(res.getAdvance().setScale(2).toString());
                v.add(res.getUserAccount());
                dtm.addRow(v);
            }
            dialog_serervation_hotel.pack();
            dialog_serervation_hotel.setLocationRelativeTo(null);
            dialog_serervation_hotel.setVisible(true);

        } else if (type == ReservationType.ROOM) {
            ArrayList<Database.beans.RoomReservation> reservations = null;
            if (dtype == DateType.SESERVED) {
                reservations = RoomReservationManager.getReservationsByDate(date.getSelectedDate());
            } else if (dtype == DateType.CHECK_IN) {
                reservations = RoomReservationManager.getReservationsByCheckInDate(date.getSelectedDate());
            } else {
                String text = customerTp.getText();
                if (text.isEmpty()) {
                    customerTp.grabFocus();
                    customerTp.selectAll();
                    return;
                }
                reservations = RoomReservationManager.getReservationsByCustomerTp(text);
            }
            if (reservations.size() == 0) {
                JOptionPane.showMessageDialog(this, "No resrevation fount !", null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            DefaultTableModel dtm = (DefaultTableModel) tbl_dialog_reservation_room.getModel();
            dtm.setRowCount(0);
            for (Database.beans.RoomReservation res : reservations) {
                Vector v = new Vector();
                v.add(res.getId());
                v.add(res.getCustomer().getTp());
                v.add(Helper.getDateTimeForDb(res.getReservedTime()));
                v.add(Helper.getDateTimeForDb(res.getChechInDate()));
                v.add(res.getNoOfDays());
                v.add("A-" + res.getAdultCount() + " C-" + res.getChildCount());
                v.add(res.getStatus());
                v.add(res.getTotal().setScale(2).toString());
                v.add(res.getAdvance().setScale(2).toString());
                v.add(res.getUserAccount());
                dtm.addRow(v);
            }
            dialog_serervation_room.pack();
            dialog_serervation_room.setLocationRelativeTo(null);
            dialog_serervation_room.setVisible(true);
        }
    }

    private void searchReservation() {

        String id = tf_search_id.getText();
        if (id.isEmpty()) {
            tf_search_id.grabFocus();
            return;
        }
        ReservationType typeById = ReservationManager.getTypeById(id);
        if (typeById == null) {
            JOptionPane.showMessageDialog(this, "No reservation found !", "", JOptionPane.QUESTION_MESSAGE);
            tf_search_id.grabFocus();
            tf_search_id.selectAll();
            return;
        }
        this.currentReservation = id;
        if (typeById == ReservationType.CATERING) {
            addCateringReservationToSearch(id);
        } else if (typeById == ReservationType.HOTEL) {
            addHotelReservationToSearch(id);
        } else if (typeById == ReservationType.ROOM) {
            addRoomReservationToSearch(id);
        }
        tf_search_id.grabFocus();
        tf_search_id.selectAll();
    }

    private void addCateringReservationToSearch(String id) {
        Database.beans.CateringReservation row = CateringReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        tf_search_advance.setText(row.getAdvance().setScale(2).toString());
        tf_search_checkin.setText(Helper.getDateTimeForDb(row.getCheckInDateTime()));
        DefaultListModel dlm = new DefaultListModel();

        HashMap<Integer, Integer> menus = row.getMenus();
        if (menus.size() > 0) {
            dlm.addElement("  ");
            dlm.addElement("-------- Menus --------");
            Set<Integer> keySet = menus.keySet();
            for (Integer i : keySet) {
                Menu rowByCode = MenuManager.getRowByCode(MenuManager.getCodeById(i), true);
                dlm.addElement("Menu - " + rowByCode.getCode() + " - " + rowByCode.getName() + " - qty(" + menus.get(i) + ") ");

            }
        }

        HashMap<Integer, Integer> items = row.getItems();
        if (items.size() > 0) {
            dlm.addElement("  ");
            dlm.addElement("-------- Items --------");
            Set<Integer> keySet = items.keySet();
            for (Integer i : keySet) {
                MenuItem rowById = MenuItemManager.getRowById(i, false);
                dlm.addElement("Item - " + rowById.getCode() + " - " + rowById.getName() + " - catagory(" + rowById.getCategory() + ") - qty(" + items.get(i) + ")");
            }
        }
        tf_search_list.setModel(dlm);
        tf_search_count.setText(row.getCount() + "");
        tf_search_time.setText(Helper.getDateTimeForDb(row.getReservedTime()));
        tf_search_total.setText(row.getTotal().setScale(2).toString());
        tf_search_tp.setText(row.getCustomer().getTp());
        tf_search_type.setText("Catering Reservation");
        tf_search_user.setText(String.valueOf(row.getUserAccount()));
        lb_status.setText(row.getStatus().toUpperCase());
    }

    private void addRoomReservationToSearch(String id) {
        Database.beans.RoomReservation row = RoomReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        DefaultListModel dlm = new DefaultListModel();
        tf_search_advance.setText(row.getAdvance().setScale(2).toString());
        tf_search_checkin.setText(Helper.getDateForDb(row.getChechInDate()));
        tf_search_count.setText("A-" + row.getAdultCount() + " C-" + row.getChildCount());
        ArrayList<Integer> rooms = row.getRooms();
        if (rooms.size() > 0) {
            for (Integer i : rooms) {
                dlm.addElement(" ");
                dlm.addElement("-------- Rooms --------");
                Room rowById = RoomManager.getRowById(i);
                dlm.addElement(rowById.getCode() + " - " + rowById.getName() + " - " + "bed(" + rowById.getBedCount() + ") - " + rowById.getCategory());
            }
        }

        tf_search_list.setModel(dlm);
        tf_search_time.setText(Helper.getDateTimeForDb(row.getReservedTime()));
        tf_search_total.setText(row.getTotal().setScale(2).toString());
        tf_search_tp.setText(row.getCustomerTp());
        tf_search_type.setText("Room Reservation");
        tf_search_user.setText(row.getUserAccount() + "");
        lb_status.setText(row.getStatus().toUpperCase());
    }

    private void addHotelReservationToSearch(String id) {
        Database.beans.HotelReservation row = HotelReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        tf_search_advance.setText(row.getAdvance().setScale(2).toString());
        tf_search_checkin.setText(Helper.getDateTimeForDb(row.getCheckInTime()));
        tf_search_count.setText(String.valueOf(row.getCount()));

        DefaultListModel dlm = new DefaultListModel();
        ArrayList<Integer> locations = row.getLocations();
        if (locations.size() > 0) {
            dlm.addElement("  ");
            dlm.addElement("-------- Locations --------");
            for (Integer i : locations) {
                Location row1 = LocationManager.getRow(i);
                dlm.addElement(row1.getCode() + " - " + row1.getName() + " - max(" + row1.getMax() + ")");
            }
        }
        ArrayList<Integer> blocks = row.getBlocks();
        if (blocks.size() > 0) {
            dlm.addElement("  ");
            dlm.addElement("-------- Blocks --------");
            for (Integer i : blocks) {
                Block row1 = BlockManager.getRow(i);
                dlm.addElement(row1.getCode() + " - " + row1.getName() + " - max(" + row1.getMax() + ") - Locations(" + row1.getNoOfLocations() + ")");

            }
        }
        HashMap<Integer, Integer> items = row.getItems();
        if (items.size() > 0) {
            dlm.addElement("  ");
            dlm.addElement("-------- Items --------");
            Set<Integer> keySet = items.keySet();
            for (Integer i : keySet) {
                MenuItem rowById = MenuItemManager.getRowById(i, false);
                dlm.addElement("Item - " + rowById.getCode() + " - " + rowById.getName() + " - catagory(" + rowById.getCategory() + ") - qty(" + items.get(i) + ")");
            }
        }
        HashMap<Integer, Integer> menus = row.getMenus();
        if (menus.size() > 0) {
            dlm.addElement(" ");
            dlm.addElement("-------- Menus --------");
            Set<Integer> keySet = menus.keySet();
            for (Integer i : keySet) {
                Menu rowByCode = MenuManager.getRowByCode(MenuManager.getCodeById(i), true);
                dlm.addElement("Menu - " + rowByCode.getCode() + " - " + rowByCode.getName() + " - qty(" + menus.get(i) + ") ");

            }
        }
        tf_search_list.setModel(dlm);
        tf_search_time.setText(Helper.getDateTimeForDb(row.getReservedTime()));
        tf_search_total.setText(row.getTotal().setScale(2).toString());
        tf_search_tp.setText(row.getCustomer().getTp());
        tf_search_type.setText("Hotel Reservation");
        tf_search_user.setText(row.getUserAccount() + "");
        lb_status.setText(row.getSatus().toUpperCase());
    }

    public void clearAllReservationSearchFields() {
        tf_search_advance.setText(null);
        tf_search_checkin.setText(null);
        tf_search_id.setText(null);
        tf_search_list.setModel(clearListModal);

        tf_search_time.setText(null);
        tf_search_total.setText(null);
        tf_search_tp.setText(null);
        tf_search_type.setText(null);
        tf_search_user.setText(null);
        this.currentReservation = null;
        tf_search_id.grabFocus();
        lb_status.setText(null);
    }

    private void clearTableContent(JTable tbl) {
        ((DefaultTableModel) tbl.getModel()).setRowCount(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dialog_serervation_room = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_room = new javax.swing.JTable();
        jScrollPane24 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_room_rooms = new javax.swing.JTable();
        jScrollPane29 = new javax.swing.JScrollPane();
        tal_dialog_reservation_hotel_desc1 = new javax.swing.JTextArea();
        dialog_serervation_hotel = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_hotel = new javax.swing.JTable();
        jScrollPane20 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_hotel_items = new javax.swing.JTable();
        jScrollPane21 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_hotel_locations = new javax.swing.JTable();
        jScrollPane22 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_hotel_blocks = new javax.swing.JTable();
        jScrollPane23 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_hotel_menus = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane28 = new javax.swing.JScrollPane();
        tal_dialog_reservation_hotel_desc = new javax.swing.JTextArea();
        dialog_serervation_catering = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_catering = new javax.swing.JTable();
        jScrollPane18 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_catering_items = new javax.swing.JTable();
        jScrollPane19 = new javax.swing.JScrollPane();
        tbl_dialog_reservation_catering_menus = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        ta_tbl_dialog_reservation_catering_desc = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        reserve_type = new com.alee.laf.combobox.WebComboBox();
        jLabel8 = new javax.swing.JLabel();
        res_report_checkout_date = new datechooser.beans.DateChooserCombo();
        jLabel11 = new javax.swing.JLabel();
        webButton7 = new com.alee.laf.button.WebButton();
        jLabel81 = new javax.swing.JLabel();
        res_report_reserve_date = new datechooser.beans.DateChooserCombo();
        res_report_customer_tp_search = new com.alee.laf.text.WebTextField();
        jLabel3 = new javax.swing.JLabel();
        reserve_search1 = new com.alee.laf.button.WebButton();
        reserve_search2 = new com.alee.laf.button.WebButton();
        reserve_search3 = new com.alee.laf.button.WebButton();
        tf_search_id = new com.alee.laf.text.WebTextField();
        jLabel4 = new javax.swing.JLabel();
        function_refresh1 = new com.alee.laf.button.WebButton();
        reserve_search = new com.alee.laf.button.WebButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lb_status = new javax.swing.JLabel();
        tf_search_user = new com.alee.laf.text.WebTextField();
        tf_search_total = new com.alee.laf.text.WebTextField();
        tf_search_advance = new com.alee.laf.text.WebTextField();
        tf_search_checkin = new com.alee.laf.text.WebTextField();
        tf_search_count = new com.alee.laf.text.WebTextField();
        tf_search_time = new com.alee.laf.text.WebTextField();
        tf_search_type = new com.alee.laf.text.WebTextField();
        tf_search_tp = new com.alee.laf.text.WebTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        tf_search_list = new javax.swing.JList<>();
        function_refresh2 = new com.alee.laf.button.WebButton();
        jLabel17 = new javax.swing.JLabel();
        function_refresh3 = new com.alee.laf.button.WebButton();

        dialog_serervation_room.setModal(true);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_dialog_reservation_room.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer", "Time", "Check In", "Days", "Count", "Status", "Total", "Advance", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_room.getTableHeader().setReorderingAllowed(false);
        tbl_dialog_reservation_room.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_dialog_reservation_roomKeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(tbl_dialog_reservation_room);

        jPanel9.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        tbl_dialog_reservation_room_rooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Qty", "Price"
            }
        ));
        tbl_dialog_reservation_room_rooms.getTableHeader().setReorderingAllowed(false);
        jScrollPane24.setViewportView(tbl_dialog_reservation_room_rooms);

        jPanel9.add(jScrollPane24, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 380, 600));

        tal_dialog_reservation_hotel_desc1.setColumns(20);
        tal_dialog_reservation_hotel_desc1.setRows(2);
        jScrollPane29.setViewportView(tal_dialog_reservation_hotel_desc1);

        jPanel9.add(jScrollPane29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1330, 70));

        javax.swing.GroupLayout dialog_serervation_roomLayout = new javax.swing.GroupLayout(dialog_serervation_room.getContentPane());
        dialog_serervation_room.getContentPane().setLayout(dialog_serervation_roomLayout);
        dialog_serervation_roomLayout.setHorizontalGroup(
            dialog_serervation_roomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialog_serervation_roomLayout.setVerticalGroup(
            dialog_serervation_roomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        dialog_serervation_hotel.setModal(true);

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_dialog_reservation_hotel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer", "Time", "Check In", "Count", "Status", "Total", "Advance", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_hotel.getTableHeader().setReorderingAllowed(false);
        tbl_dialog_reservation_hotel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_dialog_reservation_hotelKeyPressed(evt);
            }
        });
        jScrollPane16.setViewportView(tbl_dialog_reservation_hotel);

        jPanel8.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 690));

        tbl_dialog_reservation_hotel_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Price", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_hotel_items.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(tbl_dialog_reservation_hotel_items);

        jPanel8.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 540, 380, 150));

        tbl_dialog_reservation_hotel_locations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Qty", "Price"
            }
        ));
        tbl_dialog_reservation_hotel_locations.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(tbl_dialog_reservation_hotel_locations);

        jPanel8.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 30, 380, 150));

        tbl_dialog_reservation_hotel_blocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Qty", "Price"
            }
        ));
        tbl_dialog_reservation_hotel_blocks.getTableHeader().setReorderingAllowed(false);
        jScrollPane22.setViewportView(tbl_dialog_reservation_hotel_blocks);

        jPanel8.add(jScrollPane22, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 200, 380, 150));

        tbl_dialog_reservation_hotel_menus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Price", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_hotel_menus.getTableHeader().setReorderingAllowed(false);
        jScrollPane23.setViewportView(tbl_dialog_reservation_hotel_menus);

        jPanel8.add(jScrollPane23, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 370, 380, 150));

        jLabel1.setText("Items");
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 520, -1, 20));

        jLabel14.setText("Menus");
        jPanel8.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 350, -1, 20));

        jLabel15.setText("Blocks");
        jPanel8.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 180, -1, 20));

        jLabel16.setText("Locations");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 4, -1, 20));

        tal_dialog_reservation_hotel_desc.setColumns(20);
        tal_dialog_reservation_hotel_desc.setRows(2);
        jScrollPane28.setViewportView(tal_dialog_reservation_hotel_desc);

        jPanel8.add(jScrollPane28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 690, 1320, 60));

        javax.swing.GroupLayout dialog_serervation_hotelLayout = new javax.swing.GroupLayout(dialog_serervation_hotel.getContentPane());
        dialog_serervation_hotel.getContentPane().setLayout(dialog_serervation_hotelLayout);
        dialog_serervation_hotelLayout.setHorizontalGroup(
            dialog_serervation_hotelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialog_serervation_hotelLayout.setVerticalGroup(
            dialog_serervation_hotelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        dialog_serervation_catering.setModal(true);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_dialog_reservation_catering.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer", "Time", "Check In", "Status", "Total", "Advance", "Deliver", "User"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_catering.getTableHeader().setReorderingAllowed(false);
        tbl_dialog_reservation_catering.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_dialog_reservation_cateringKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(tbl_dialog_reservation_catering);

        jPanel6.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 510));

        tbl_dialog_reservation_catering_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Price", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_catering_items.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(tbl_dialog_reservation_catering_items);

        jPanel6.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 240, 370, 270));

        tbl_dialog_reservation_catering_menus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Price", "Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_dialog_reservation_catering_menus.getTableHeader().setReorderingAllowed(false);
        jScrollPane19.setViewportView(tbl_dialog_reservation_catering_menus);

        jPanel6.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 370, 240));

        ta_tbl_dialog_reservation_catering_desc.setColumns(20);
        ta_tbl_dialog_reservation_catering_desc.setRows(2);
        jScrollPane14.setViewportView(ta_tbl_dialog_reservation_catering_desc);

        jPanel6.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 1320, 50));

        javax.swing.GroupLayout dialog_serervation_cateringLayout = new javax.swing.GroupLayout(dialog_serervation_catering.getContentPane());
        dialog_serervation_catering.getContentPane().setLayout(dialog_serervation_cateringLayout);
        dialog_serervation_cateringLayout.setHorizontalGroup(
            dialog_serervation_cateringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1321, Short.MAX_VALUE)
        );
        dialog_serervation_cateringLayout.setVerticalGroup(
            dialog_serervation_cateringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(1058, 610));
        setMinimumSize(new java.awt.Dimension(1058, 610));
        setPreferredSize(new java.awt.Dimension(1058, 610));
        setLayout(new java.awt.GridBagLayout());

        jPanel12.setBackground(new java.awt.Color(55, 63, 75));
        jPanel12.setMaximumSize(new java.awt.Dimension(1058, 610));
        jPanel12.setMinimumSize(new java.awt.Dimension(1058, 610));
        jPanel12.setPreferredSize(new java.awt.Dimension(1058, 610));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reserve_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hotel Reservation", "Catering Reservation", "Room Reservation" }));
        reserve_type.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        reserve_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_typeActionPerformed(evt);
            }
        });
        jPanel12.add(reserve_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 190, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Checkout date:");
        jPanel12.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, 30));

        res_report_checkout_date.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        jPanel12.add(res_report_checkout_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 90, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Reserved Date:");
        jPanel12.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 30));

        webButton7.setText("Last Resrvation");
        webButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        webButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton7ActionPerformed(evt);
            }
        });
        jPanel12.add(webButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 160, 50));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Reservation Type:");
        jPanel12.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, -1, 30));

        res_report_reserve_date.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        jPanel12.add(res_report_reserve_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, 30));

        res_report_customer_tp_search.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        res_report_customer_tp_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                res_report_customer_tp_searchKeyPressed(evt);
            }
        });
        jPanel12.add(res_report_customer_tp_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 170, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Customer Contact:");
        jPanel12.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, 30));

        reserve_search1.setText("Search");
        reserve_search1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reserve_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_search1ActionPerformed(evt);
            }
        });
        jPanel12.add(reserve_search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 40));

        reserve_search2.setText("Search");
        reserve_search2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reserve_search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_search2ActionPerformed(evt);
            }
        });
        jPanel12.add(reserve_search2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, 40));

        reserve_search3.setText("Search");
        reserve_search3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reserve_search3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_search3ActionPerformed(evt);
            }
        });
        jPanel12.add(reserve_search3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, 40));

        tf_search_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_search_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_search_idKeyPressed(evt);
            }
        });
        jPanel12.add(tf_search_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 133, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Reservation ID:");
        jPanel12.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        function_refresh1.setText("Refresh");
        function_refresh1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refresh1ActionPerformed(evt);
            }
        });
        jPanel12.add(function_refresh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 130, 30));

        reserve_search.setText("Search");
        reserve_search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reserve_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_searchActionPerformed(evt);
            }
        });
        jPanel12.add(reserve_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 70, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Customer :");
        jPanel12.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, -1, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Type :");
        jPanel12.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, -1, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Time :");
        jPanel12.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, 40, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Check in :");
        jPanel12.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, -1, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Advance :");
        jPanel12.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, -1, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Count");
        jPanel12.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total :");
        jPanel12.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, -1, 30));

        lb_status.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_status.setForeground(new java.awt.Color(255, 255, 0));
        jPanel12.add(lb_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 180, 40));

        tf_search_user.setEditable(false);
        tf_search_user.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, 180, 30));

        tf_search_total.setEditable(false);
        tf_search_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 460, 180, 30));

        tf_search_advance.setEditable(false);
        tf_search_advance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_search_advance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_search_advanceActionPerformed(evt);
            }
        });
        jPanel12.add(tf_search_advance, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 420, 180, 30));

        tf_search_checkin.setEditable(false);
        tf_search_checkin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_checkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, 180, 30));

        tf_search_count.setEditable(false);
        tf_search_count.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, 180, 30));

        tf_search_time.setEditable(false);
        tf_search_time.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, 180, 30));

        tf_search_type.setEditable(false);
        tf_search_type.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 180, 30));

        tf_search_tp.setEditable(false);
        tf_search_tp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel12.add(tf_search_tp, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 180, 30));

        tf_search_list.setEnabled(false);
        jScrollPane15.setViewportView(tf_search_list);

        jPanel12.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 410, 250));

        function_refresh2.setText("Cancel Reservation");
        function_refresh2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refresh2ActionPerformed(evt);
            }
        });
        jPanel12.add(function_refresh2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 170, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("User :");
        jPanel12.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 500, -1, 30));

        function_refresh3.setText("Print");
        function_refresh3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refresh3ActionPerformed(evt);
            }
        });
        jPanel12.add(function_refresh3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 130, 30));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 56;
        gridBagConstraints.ipady = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel12, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void reserve_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reserve_typeActionPerformed

    private void webButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton7ActionPerformed
        tf_search_id.setText(String.valueOf(ReservationManager.getNextReservationId() - 1));
        searchReservation();
    }//GEN-LAST:event_webButton7ActionPerformed

    private void reserve_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_search1ActionPerformed

        loadReservationsByDate(res_report_reserve_date, getSelectedReservationType(reserve_type), DateType.SESERVED, null);
    }//GEN-LAST:event_reserve_search1ActionPerformed

    private void reserve_search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_search2ActionPerformed
        loadReservationsByDate(res_report_checkout_date, getSelectedReservationType(reserve_type), DateType.CHECK_IN, null);
    }//GEN-LAST:event_reserve_search2ActionPerformed

    private void reserve_search3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_search3ActionPerformed
        loadReservationsByDate(null, getSelectedReservationType(reserve_type), null, res_report_customer_tp_search);
    }//GEN-LAST:event_reserve_search3ActionPerformed

    private void reserve_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_searchActionPerformed
        searchReservation();
    }//GEN-LAST:event_reserve_searchActionPerformed

    private void tf_search_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_search_idKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchReservation();
        }
    }//GEN-LAST:event_tf_search_idKeyPressed

    private void function_refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refresh1ActionPerformed
        clearAllReservationSearchFields();
    }//GEN-LAST:event_function_refresh1ActionPerformed

    private void tf_search_advanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_search_advanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_search_advanceActionPerformed

    private void function_refresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refresh2ActionPerformed

        cancelReservation();
    }//GEN-LAST:event_function_refresh2ActionPerformed

    private void res_report_customer_tp_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_res_report_customer_tp_searchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loadReservationsByDate(null, getSelectedReservationType(reserve_type), null, res_report_customer_tp_search);
        }
    }//GEN-LAST:event_res_report_customer_tp_searchKeyPressed

    private void tbl_dialog_reservation_roomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_dialog_reservation_roomKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeAllDialog();
        }
    }//GEN-LAST:event_tbl_dialog_reservation_roomKeyPressed

    private void tbl_dialog_reservation_hotelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_dialog_reservation_hotelKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeAllDialog();
        }
    }//GEN-LAST:event_tbl_dialog_reservation_hotelKeyPressed

    private void tbl_dialog_reservation_cateringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_dialog_reservation_cateringKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeAllDialog();
        }
    }//GEN-LAST:event_tbl_dialog_reservation_cateringKeyPressed

    private void function_refresh3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refresh3ActionPerformed
        if (this.currentReservation == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation first.", "No reservation selected!", JOptionPane.QUESTION_MESSAGE);
            tf_search_id.grabFocus();
            return;
        } else if (JOptionPane.showConfirmDialog(this, "Print reservation?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            ReportManager.printReservationById(this.currentReservation);
        }
    }//GEN-LAST:event_function_refresh3ActionPerformed

    private void closeAllDialog() {
        dialog_serervation_catering.dispose();
        dialog_serervation_hotel.dispose();
        dialog_serervation_room.dispose();
    }

    private void cancelReservation() {
        if (this.currentReservation == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation first.", "No reservation selected!", JOptionPane.QUESTION_MESSAGE);
            tf_search_id.grabFocus();
            return;
        }
        String status = ReservationManager.getStatusById(this.currentReservation);
        if (status.equals(Reservation.RESERVATION_STATUS_CANCELLED)) {
            JOptionPane.showMessageDialog(this, "This reservation has already benn cancelled!");
            tf_search_id.grabFocus();
            tf_search_id.selectAll();
            return;
        } else if (status.equals(Reservation.RESERVATION_STATUS_CHECKEDOUT)) {
            JOptionPane.showMessageDialog(this, "This reservation has been checked out!");
            tf_search_id.grabFocus();
            tf_search_id.selectAll();
            return;
        } else if (status.equals(Reservation.RESERVATION_STATUS_CHECKEDIN)) {
            JOptionPane.showMessageDialog(this, "This reservation has been checked in!");
            tf_search_id.grabFocus();
            tf_search_id.selectAll();
            return;
        }

        if (!Users.Login.logedUserAccount.isReport()) {
            JOptionPane.showMessageDialog(this, "You don't have privilages for this function.", "Access denied!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(JOptionPane.showConfirmDialog(this, "Cancell reservation?", "Confirm!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
            return;
        }
        ReservationType typeById = ReservationManager.getTypeById(this.currentReservation);
        if (typeById == null) {
            JOptionPane.showMessageDialog(this, "No reservation found !", "", JOptionPane.QUESTION_MESSAGE);
            tf_search_id.grabFocus();
            tf_search_id.selectAll();
            return;
        }
        boolean flag = true;
        if (typeById == ReservationType.CATERING) {
            if (DB.iud("update caterin_reservation set status='" + Reservation.RESERVATION_STATUS_CANCELLED + "' where idcaterine_reservation='" + this.currentReservation + "' limit 1")) {
                JOptionPane.showMessageDialog(this, "Cancelled successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                clearAllReservationSearchFields();
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't cancell.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (typeById == ReservationType.HOTEL) {
            if (DB.iud("update hotel_reservation set status='" + Reservation.RESERVATION_STATUS_CANCELLED + "' where idhotel_reservation='" + this.currentReservation + "' limit 1")) {
                JOptionPane.showMessageDialog(this, "Cancelled successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                clearAllReservationSearchFields();
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't cancell.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (typeById == ReservationType.ROOM) {
            if (DB.iud("update room_reservation set status='" + Reservation.RESERVATION_STATUS_CANCELLED + "' where idroom_reservation='" + this.currentReservation + "' limit 1")) {
                JOptionPane.showMessageDialog(this, "Cancelled successfully.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                clearAllReservationSearchFields();
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't cancell.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDialog dialog_serervation_catering;
    public javax.swing.JDialog dialog_serervation_hotel;
    public javax.swing.JDialog dialog_serervation_room;
    private com.alee.laf.button.WebButton function_refresh1;
    private com.alee.laf.button.WebButton function_refresh2;
    private com.alee.laf.button.WebButton function_refresh3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane28;
    private javax.swing.JScrollPane jScrollPane29;
    private javax.swing.JLabel lb_status;
    private datechooser.beans.DateChooserCombo res_report_checkout_date;
    private com.alee.laf.text.WebTextField res_report_customer_tp_search;
    private datechooser.beans.DateChooserCombo res_report_reserve_date;
    private com.alee.laf.button.WebButton reserve_search;
    private com.alee.laf.button.WebButton reserve_search1;
    private com.alee.laf.button.WebButton reserve_search2;
    private com.alee.laf.button.WebButton reserve_search3;
    private com.alee.laf.combobox.WebComboBox reserve_type;
    private javax.swing.JTextArea ta_tbl_dialog_reservation_catering_desc;
    private javax.swing.JTextArea tal_dialog_reservation_hotel_desc;
    private javax.swing.JTextArea tal_dialog_reservation_hotel_desc1;
    private javax.swing.JTable tbl_dialog_reservation_catering;
    private javax.swing.JTable tbl_dialog_reservation_catering_items;
    private javax.swing.JTable tbl_dialog_reservation_catering_menus;
    private javax.swing.JTable tbl_dialog_reservation_hotel;
    private javax.swing.JTable tbl_dialog_reservation_hotel_blocks;
    private javax.swing.JTable tbl_dialog_reservation_hotel_items;
    private javax.swing.JTable tbl_dialog_reservation_hotel_locations;
    private javax.swing.JTable tbl_dialog_reservation_hotel_menus;
    private javax.swing.JTable tbl_dialog_reservation_room;
    private javax.swing.JTable tbl_dialog_reservation_room_rooms;
    private com.alee.laf.text.WebTextField tf_search_advance;
    private com.alee.laf.text.WebTextField tf_search_checkin;
    private com.alee.laf.text.WebTextField tf_search_count;
    private com.alee.laf.text.WebTextField tf_search_id;
    private javax.swing.JList<String> tf_search_list;
    private com.alee.laf.text.WebTextField tf_search_time;
    private com.alee.laf.text.WebTextField tf_search_total;
    private com.alee.laf.text.WebTextField tf_search_tp;
    private com.alee.laf.text.WebTextField tf_search_type;
    private com.alee.laf.text.WebTextField tf_search_user;
    private com.alee.laf.button.WebButton webButton7;
    // End of variables declaration//GEN-END:variables
}
