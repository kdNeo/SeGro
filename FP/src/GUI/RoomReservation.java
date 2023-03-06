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
import Database.tables.RoomManager;
import Database.tables.RoomReservationManager;
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
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sachintha
 */
public class RoomReservation extends javax.swing.JPanel {

    ArrayList<JTable> tableList = new ArrayList<JTable>();
    private DefaultListModel clearListModal = new DefaultListModel();
    public Reservation parentReservation;
    public Reservation.ReservationClearState clearState;

    /**
     * Creates new form RoomReservation
     */
    public RoomReservation() {
        initComponents();
        addHelperListeners();
        addTablesToList();
        setTableLook();
    }

    private void addHelperListeners() {
        room_table_selected.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateMaxRoom();
                calculateTotalRoom();
                addTablesToList();
                setTableLook();
            }
        });
    }

    private void addTablesToList() {
        tableList.add(room_table_all);
        tableList.add(room_table_selected);
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
     * ***** room reservation helper functions **********
     *
     * validateFields function will validate the customer fields and other
     * fields related to the particular type of reservation except the fields
     * that relate to the payments. Those will be validated by validate payment
     * function itself.
     */
    private void loadAllRoomsOnClick() {
        if (!Helper.validateDate(res_date)) {
            JOptionPane.showMessageDialog(this, "Please select a date and number dates.", "Invalid selection!", JOptionPane.QUESTION_MESSAGE);
            res_date.grabFocus();
        } else {
            loadAllRooms();
        }
    }

    /**
     * Make the room reservation helper initializer
     */
    private void makeRoomReservation() {
        if (validateFieldsRoom() && validatePaymentRoom()) {
            if (Helper.isTheDateToday(res_date)) {
                if (JOptionPane.showConfirmDialog(this, "Confirm reservation on today ?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                    res_date.grabFocus();
                    return;
                }
            }
            if (JOptionPane.showConfirmDialog(this, "Add reservation?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (addRoomReservation()) {
                    JOptionPane.showMessageDialog(this, "Successfull !");
                    this.clearState = Reservation.ReservationClearState.HOLD;
                    clearAllRoom(false);
                    parentReservation.clearFieldsCustomer();
                    ReportManager.printLastRoomReservation();
                } else {
                    JOptionPane.showMessageDialog(this, "Couldn't place reservation", "error !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void loadAllRooms() {
        try (
                ResultSet rs = RoomManager.getAll(res_date.getSelectedDate(), Integer.valueOf(days.getValue().toString()))) {
            DefaultTableModel dtm = (DefaultTableModel) room_table_all.getModel();
            dtm.setRowCount(0);
            clearAllRoomTables();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(false);
                v.add(rs.getString("code"));
                v.add(rs.getString("name"));
                v.add(rs.getString("room_catagory_catagoryname"));
                v.add(rs.getString("bedcount"));
                v.add(rs.getString("price"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            DB.processException(e);
        }
    }

    /**
     * Clear customer details*
     */
    private boolean addRoomReservation() {
        Database.beans.RoomReservation rr = new Database.beans.RoomReservation();
        ArrayList<Integer> rooms = new ArrayList();
        int count = room_table_selected.getRowCount();
        for (int i = 0; i < count; i++) {
            rooms.add(Integer.valueOf(RoomManager.getIdByCode(room_table_selected.getValueAt(i, 0).toString())));
        }
        rr.setAdultCount(Integer.parseInt(adults.getValue().toString()));
        rr.setChildCount(Integer.parseInt(childs.getValue().toString()));
        rr.setAdvance(new BigDecimal(room_advance.getText()));
        rr.setChechInDate(res_date.getSelectedDate().getTime());

        rr.setCustomer(parentReservation.customer);
        rr.setCustomerTp(parentReservation.customer.getTp());
        rr.setReservedTime(new Date());
        rr.setDescription(room_des.getText());
        rr.setId(ReservationManager.getNextReservationId());
        rr.setInvoice(-1);
        rr.setRooms(rooms);
        rr.setStatus(Reservation.RESERVATION_STATUS_RESERVED);
        rr.setTotal(new BigDecimal(room_tot.getText()));
        rr.setUserAccount(Users.Login.logedUserAccount.getId());
        rr.setNoOfDays(Integer.valueOf(days.getValue().toString()));
        return RoomReservationManager.addRow(rr);
    }

    private boolean validatePaymentRoom() {
        BigDecimal tot = new BigDecimal(room_tot.getText());
        BigDecimal advance = new BigDecimal(room_advance.getText());
        BigDecimal amount = new BigDecimal(tf_amount.getText());
        BigDecimal balance = BigDecimal.ZERO;
        if (advance.compareTo(tot) == 1) {
            JOptionPane.showMessageDialog(this, "Paying more than total.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            room_advance.grabFocus();
            room_advance.selectAll();
            return false;
        }
        if (advance.compareTo(amount) == 1) {
            JOptionPane.showMessageDialog(this, "Amount is not sufficient.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            tf_amount.grabFocus();
            tf_amount.selectAll();
            return false;
        }
        balance = amount.subtract(advance);
        lb_balance.setText(balance.setScale(2).toString());
        return true;
    }

    private void calculateMaxRoom() {
        int tot = room_table_selected.getRowCount();
        int max = 0;
        for (int i = 0; i < tot; i++) {
            max += Integer.parseInt(room_table_selected.getValueAt(i, 3).toString());
        }
        tf_max.setText(max + "");
    }

    private boolean validateFieldsRoom() {
        if (!parentReservation.isCustomerValid()) {
            return false;
        }

        if (room_table_selected.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select rooms for reservation.", "No room selected!", JOptionPane.QUESTION_MESSAGE);
            room_table_all.grabFocus();
            return false;
        }
        if (!(Integer.parseInt(days.getValue().toString()) > 0)) {
            JOptionPane.showMessageDialog(this, "Please select number of days", "", JOptionPane.QUESTION_MESSAGE);
            days.grabFocus();
            return false;
        }
        if (!Helper.validateDate(res_date)) {
            JOptionPane.showMessageDialog(this, "Please select a valid check in day.", "Invalid date!", JOptionPane.QUESTION_MESSAGE);
            res_date.grabFocus();
            return false;
        }
        if (!isCountCorrectRoom()) {
            JOptionPane.showMessageDialog(this, "Please select a valid number of guests.", "Invalid amount!", JOptionPane.QUESTION_MESSAGE);
            adults.grabFocus();
            return false;
        }

        if (Reservation.isEmpty(room_advance)) {
            room_advance.grabFocus();
            room_advance.selectAll();
            return false;
        }
        if (Reservation.isEmpty(tf_amount)) {
            tf_amount.grabFocus();
            tf_amount.selectAll();
            return false;
        }
        return true;
    }

    private boolean isCountCorrectRoom() {
        int max = Integer.parseInt(tf_max.getText());
        int c = Integer.parseInt(childs.getValue().toString());
        int a = Integer.parseInt(adults.getValue().toString());
        if (c + a == 0) {
            return false;
        }
        if (c + a > max) {
            return false;
        }
        return true;
    }

    private void calculateTotalRoom() {
        int totrows = room_table_selected.getRowCount();
        BigDecimal bd = BigDecimal.ZERO;
        for (int i = 0; i < totrows; i++) {
            bd = bd.add(new BigDecimal(room_table_selected.getValueAt(i, 4).toString()));
        }
        room_tot.setText(bd.setScale(2).toString());
    }

    public void clearAllRoom(boolean allFields) {
        if (allFields) {
            room_advance.setText(null);
            tf_amount.setText(null);
            lb_balance.setText("0.00");
        }

        res_date.setSelectedDate(Calendar.getInstance());
        childs.setValue(((SpinnerNumberModel) childs.getModel()).getMinimum());
        adults.setValue(((SpinnerNumberModel) adults.getModel()).getMinimum());
        days.setValue(1);
        room_des.setText(null);
        clearAllRoomTables();
    }

    private void clearAllRoomTables() {
        ((DefaultTableModel) room_table_all.getModel()).setRowCount(0);
        ((DefaultTableModel) room_table_selected.getModel()).setRowCount(0);
        tf_max.setText(0 + "");
    }

    private void clearTotalRoom() {
        room_advance.setText(null);
        room_tot.setText(null);
        lb_balance.setText("0.00");
    }

    private void addSelectedRoomToReservation() {
        int rowCount = room_table_all.getRowCount();
        DefaultTableModel dtmall = (DefaultTableModel) room_table_all.getModel();
        DefaultTableModel dtm = (DefaultTableModel) room_table_selected.getModel();

        for (int i = rowCount - 1; i >= 0; i--) {
            if (Boolean.parseBoolean(room_table_all.getValueAt(i, 0).toString())) {
                Vector v = new Vector();
                v.add(room_table_all.getValueAt(i, 1));
                v.add(room_table_all.getValueAt(i, 2));
                v.add(room_table_all.getValueAt(i, 3));
                v.add(room_table_all.getValueAt(i, 4));
                v.add(room_table_all.getValueAt(i, 5));
                dtm.addRow(v);
                dtmall.removeRow(i);
                if (this.clearState == Reservation.ReservationClearState.HOLD) {
                    this.clearState = Reservation.ReservationClearState.CLEAR;
                    clearTotalRoom();
                    calculateTotalRoom();
                }
            }
        }
    }

    private void removeSelectedRow(JTable tbl) {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
            dtm.removeRow(row);
        }
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

        panel_room = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        room_des = new com.alee.laf.text.WebTextArea();
        res_date = new datechooser.beans.DateChooserCombo();
        jPanel39 = new javax.swing.JPanel();
        jScrollPane34 = new javax.swing.JScrollPane();
        room_table_all = new javax.swing.JTable();
        jLabel151 = new javax.swing.JLabel();
        room_tot = new com.alee.laf.text.WebTextField();
        room_remove_table = new com.alee.laf.button.WebButton();
        room_advance = new com.alee.laf.text.WebTextField();
        jLabel155 = new javax.swing.JLabel();
        room_refresh = new com.alee.laf.button.WebButton();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        room_invoice1 = new com.alee.laf.button.WebButton();
        room_add_table = new com.alee.laf.button.WebButton();
        jScrollPane35 = new javax.swing.JScrollPane();
        room_table_selected = new javax.swing.JTable();
        days = new javax.swing.JSpinner();
        jLabel92 = new javax.swing.JLabel();
        adults = new javax.swing.JSpinner();
        childs = new javax.swing.JSpinner();
        jLabel94 = new javax.swing.JLabel();
        tf_max = new javax.swing.JTextField();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        tf_amount = new com.alee.laf.text.WebTextField();
        lb_balance = new javax.swing.JLabel();
        room_refresh1 = new com.alee.laf.button.WebButton();

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(1058, 610));
        setMinimumSize(new java.awt.Dimension(1058, 610));
        setPreferredSize(new java.awt.Dimension(1058, 610));
        setLayout(new java.awt.GridBagLayout());

        panel_room.setBackground(new java.awt.Color(55, 63, 75));
        panel_room.setMaximumSize(new java.awt.Dimension(1058, 610));
        panel_room.setMinimumSize(new java.awt.Dimension(1058, 610));
        panel_room.setPreferredSize(new java.awt.Dimension(1058, 610));
        panel_room.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        room_des.setColumns(20);
        room_des.setRows(5);
        jScrollPane12.setViewportView(room_des);

        panel_room.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 226, 80));

        res_date.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        res_date.addSelectionChangedListener(new datechooser.events.SelectionChangedListener() {
            public void onSelectionChange(datechooser.events.SelectionChangedEvent evt) {
                res_dateOnSelectionChange(evt);
            }
        });
        panel_room.add(res_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 133, 25));

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Available", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 18))); // NOI18N
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        room_table_all.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Room Code", "Room Name", "Category", "Bed Count", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        room_table_all.getTableHeader().setReorderingAllowed(false);
        jScrollPane34.setViewportView(room_table_all);

        jPanel39.add(jScrollPane34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 690, 150));

        panel_room.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 720, 190));

        jLabel151.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setText("Description:");
        panel_room.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        room_tot.setEditable(false);
        room_tot.setEnabled(false);
        room_tot.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panel_room.add(room_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 400, 170, 38));

        room_remove_table.setText("Remove Selected");
        room_remove_table.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        room_remove_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room_remove_tableActionPerformed(evt);
            }
        });
        panel_room.add(room_remove_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(837, 206, 160, 30));

        room_advance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        room_advance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                room_advanceFocusGained(evt);
            }
        });
        room_advance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                room_advanceKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                room_advanceKeyTyped(evt);
            }
        });
        panel_room.add(room_advance, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 450, 170, 30));

        jLabel155.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(255, 255, 255));
        jLabel155.setText("Advance:");
        panel_room.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 440, -1, 30));

        room_refresh.setText("Search");
        room_refresh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        room_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room_refreshActionPerformed(evt);
            }
        });
        panel_room.add(room_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 70, 50));

        jLabel158.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel158.setForeground(new java.awt.Color(255, 255, 255));
        jLabel158.setText("Check in date:");
        panel_room.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel159.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel159.setForeground(new java.awt.Color(255, 255, 255));
        jLabel159.setText("Total :");
        panel_room.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, -1, 20));

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Max");
        panel_room.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, 30));

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setText("Adults:");
        panel_room.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 30));

        room_invoice1.setText("Reserve");
        room_invoice1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        room_invoice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room_invoice1ActionPerformed(evt);
            }
        });
        panel_room.add(room_invoice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 490, 130, 70));

        room_add_table.setText("Add to Reservation");
        room_add_table.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        room_add_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room_add_tableActionPerformed(evt);
            }
        });
        panel_room.add(room_add_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(629, 206, 190, 30));

        room_table_selected.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Code", "Room Name", "Category", "Bed Count", "Price"
            }
        ));
        room_table_selected.getTableHeader().setReorderingAllowed(false);
        room_table_selected.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                room_table_selectedComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                room_table_selectedComponentRemoved(evt);
            }
        });
        room_table_selected.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                room_table_selectedHierarchyChanged(evt);
            }
        });
        room_table_selected.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                room_table_selectedPropertyChange(evt);
            }
        });
        room_table_selected.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                room_table_selectedKeyPressed(evt);
            }
        });
        jScrollPane35.setViewportView(room_table_selected);

        panel_room.add(jScrollPane35, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 720, 118));

        days.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        days.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                daysStateChanged(evt);
            }
        });
        panel_room.add(days, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 30));

        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Days");
        panel_room.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        adults.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        panel_room.add(adults, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 131, 60, 30));

        childs.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        panel_room.add(childs, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 131, 60, 30));

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("Children:");
        panel_room.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, 30));
        panel_room.add(tf_max, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 131, 40, 30));

        jLabel160.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(255, 255, 255));
        jLabel160.setText("Amount :");
        panel_room.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 64, 20));

        jLabel161.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(255, 255, 255));
        jLabel161.setText("Balance :");
        panel_room.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 450, 64, 25));

        tf_amount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tf_amountFocusGained(evt);
            }
        });
        tf_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_amountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_amountKeyTyped(evt);
            }
        });
        panel_room.add(tf_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 400, 154, 38));

        lb_balance.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_balance.setForeground(new java.awt.Color(255, 255, 255));
        lb_balance.setText("0.00");
        panel_room.add(lb_balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(874, 450, 150, -1));

        room_refresh1.setText("Clear");
        room_refresh1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        room_refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                room_refresh1ActionPerformed(evt);
            }
        });
        panel_room.add(room_refresh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 70, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 2);
        add(panel_room, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void res_dateOnSelectionChange(datechooser.events.SelectionChangedEvent evt) {//GEN-FIRST:event_res_dateOnSelectionChange
        clearAllRoomTables();
    }//GEN-LAST:event_res_dateOnSelectionChange

    private void room_remove_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room_remove_tableActionPerformed
        removeSelectedRow(room_table_selected);
    }//GEN-LAST:event_room_remove_tableActionPerformed

    private void room_advanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_room_advanceFocusGained
        room_advance.selectAll();
    }//GEN-LAST:event_room_advanceFocusGained

    private void room_advanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_room_advanceKeyTyped
        Helper.numericDataAdd(room_advance, evt);
    }//GEN-LAST:event_room_advanceKeyTyped

    private void room_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room_refreshActionPerformed
        loadAllRoomsOnClick();
    }//GEN-LAST:event_room_refreshActionPerformed

    private void room_invoice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room_invoice1ActionPerformed
        makeRoomReservation();
    }//GEN-LAST:event_room_invoice1ActionPerformed

    private void room_add_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room_add_tableActionPerformed
        addSelectedRoomToReservation();
    }//GEN-LAST:event_room_add_tableActionPerformed

    private void room_table_selectedComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_room_table_selectedComponentAdded

    }//GEN-LAST:event_room_table_selectedComponentAdded

    private void room_table_selectedComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_room_table_selectedComponentRemoved

    }//GEN-LAST:event_room_table_selectedComponentRemoved

    private void room_table_selectedHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_room_table_selectedHierarchyChanged

    }//GEN-LAST:event_room_table_selectedHierarchyChanged

    private void room_table_selectedPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_room_table_selectedPropertyChange

    }//GEN-LAST:event_room_table_selectedPropertyChange

    private void room_table_selectedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_room_table_selectedKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            removeSelectedRow(room_table_selected);
        }
    }//GEN-LAST:event_room_table_selectedKeyPressed

    private void daysStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_daysStateChanged
        clearAllRoomTables();
    }//GEN-LAST:event_daysStateChanged

    private void tf_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_amountFocusGained
        tf_amount.selectAll();
    }//GEN-LAST:event_tf_amountFocusGained

    private void tf_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_amountKeyTyped
        Helper.numericDataAdd(tf_amount, evt);
    }//GEN-LAST:event_tf_amountKeyTyped

    private void room_refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_room_refresh1ActionPerformed
        clearAllRoom(true);
    }//GEN-LAST:event_room_refresh1ActionPerformed

    private void room_advanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_room_advanceKeyPressed
        if (evt.getKeyCode() == 109) {
            calculateTotalRoom();;
            room_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalRoom();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_room_advanceKeyPressed

    private void tf_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_amountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            makeRoomReservation();
        } else if (evt.getKeyCode() == 109) {
            calculateTotalRoom();;
            room_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalRoom();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_amountKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner adults;
    private javax.swing.JSpinner childs;
    private javax.swing.JSpinner days;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane34;
    private javax.swing.JScrollPane jScrollPane35;
    private javax.swing.JLabel lb_balance;
    private javax.swing.JPanel panel_room;
    private datechooser.beans.DateChooserCombo res_date;
    private com.alee.laf.button.WebButton room_add_table;
    private com.alee.laf.text.WebTextField room_advance;
    private com.alee.laf.text.WebTextArea room_des;
    private com.alee.laf.button.WebButton room_invoice1;
    private com.alee.laf.button.WebButton room_refresh;
    private com.alee.laf.button.WebButton room_refresh1;
    private com.alee.laf.button.WebButton room_remove_table;
    private javax.swing.JTable room_table_all;
    private javax.swing.JTable room_table_selected;
    private com.alee.laf.text.WebTextField room_tot;
    private com.alee.laf.text.WebTextField tf_amount;
    private javax.swing.JTextField tf_max;
    // End of variables declaration//GEN-END:variables
}
