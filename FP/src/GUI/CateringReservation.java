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
import Database.tables.CateringReservationManager;
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
public class CateringReservation extends javax.swing.JPanel {

    private static final String ITEM_ITEM = "Item";
    private static final String ITEM_MENU = "Menu";
    private DefaultListModel clearListModal = new DefaultListModel();
    public Reservation parentReservation;
    public Reservation.ReservationClearState clearState;
    ArrayList<JTable> tableList = new ArrayList<JTable>();

    /**
     * Creates new form CateringReservation
     */
    public CateringReservation() {
        initComponents();
        addHelperListeners();
        addTablesToList();
        setTableLook();

    }

    private void addTablesToList() {
        tableList.add(tb_c_items);
        tableList.add(tbl_c_menu_window);
        tableList.add(tbl_c_menuitem_window);

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

    private void addHelperListeners() {

        tbl_c_menu_window.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addSelectedMenuDialogItemToSideList();
            }
        });
        tb_c_items.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotalCatering();
            }
        });

    }

    /**
     * ******
     *
     * Catering reservation helper functions
     *
     * validateFields function will validate the customer fields and other
     * fields related to the particular type of reservation except the fields
     * that relate to the payments. Those will be validated by validate payment
     * function itself.
     *
     *
     */
    private void clearTotalCatering() {
        tf_c_advance.setText(null);
        tf_c_amount.setText(null);
        lb_c_bal.setText(null);
    }

    private void addCateringMenuByCode() {
        if (tf_c_menu.getText().isEmpty()) {
            tf_c_menu.grabFocus();
            tf_c_menu.selectAll();
            return;
        }

        ItemCode ic = Helper.getItemCode(tf_c_menu);

        int qty = 0;
        int itemcode = 0;

        if (ic == null) {
            if (tf_c_menu_qty.getText().isEmpty()) {
                tf_c_menu_qty.grabFocus();
                tf_c_menu_qty.selectAll();
                return;
            }
            qty = Integer.parseInt(tf_c_menu_qty.getText());
            if (qty == 0) {
                tf_c_menu.grabFocus();
                tf_c_menu.selectAll();
                return;
            }
            itemcode = Integer.parseInt(tf_c_menu.getText());

        } else {
            if (ic.getQty() == -1) {
                if (tf_c_menu_qty.getText().isEmpty()) {
                    tf_c_menu_qty.grabFocus();
                    tf_c_menu_qty.selectAll();
                    return;
                }

                qty = Integer.parseInt(tf_c_menu_qty.getText());
                if (qty == 0) {
                    tf_c_menu.grabFocus();
                    tf_c_menu.selectAll();
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
            Helper.selectAfterStar(tf_c_menu);
            return;
        }
        addItemToFinalTable(row.getCode(), row.getName(), "Menu", row.getPrice(), String.valueOf(qty), tb_c_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            clearTotalCatering();
            this.clearState = Reservation.ReservationClearState.CLEAR;
        }
        tf_c_menu.setText(null);
        tf_c_menu_qty.setText(null);
        tf_c_menu.grabFocus();
    }

    private void addCateringMenuItemByCode() {
        if (tf_c_items.getText().isEmpty()) {
            tf_c_items.grabFocus();
            tf_c_items.selectAll();
            return;
        }

        ItemCode ic = Helper.getItemCode(tf_c_items);

        int qty = 0;
        int itemcode = 0;

        if (ic == null) {
            if (tf_c_menu_item_qty.getText().isEmpty()) {
                tf_c_menu_item_qty.grabFocus();
                tf_c_menu_item_qty.selectAll();
                return;
            }
            qty = Integer.parseInt(tf_c_menu_item_qty.getText());
            if (qty == 0) {
                tf_c_menu_item_qty.grabFocus();
                tf_c_menu_item_qty.selectAll();
                return;
            }
            itemcode = Integer.parseInt(tf_c_items.getText());

        } else {
            if (ic.getQty() == -1) {
                if (tf_c_menu_item_qty.getText().isEmpty()) {
                    tf_c_menu_item_qty.grabFocus();
                    tf_c_menu_item_qty.selectAll();
                    return;
                }

                qty = Integer.parseInt(tf_c_menu_item_qty.getText());
                if (qty == 0) {
                    tf_c_menu_item_qty.grabFocus();
                    tf_c_menu_item_qty.selectAll();
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
            Helper.selectAfterStar(tf_c_items);
            return;
        }
        addItemToFinalTable(row.getCode(), row.getName(), "Item", row.getPrice(), String.valueOf(qty), tb_c_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            clearTotalCatering();
            this.clearState = Reservation.ReservationClearState.CLEAR;
        }
        tf_c_items.setText(null);
        tf_c_menu_item_qty.setText(null);
        tf_c_items.grabFocus();
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

    private boolean validateFieldsCatering() {
        if (!parentReservation.isCustomerValid()) {
            return false;
        }
        if (!Utility.Helper.validateDate(catering_date)) {
            JOptionPane.showMessageDialog(this, "Please select a valid date", "Invalid date selection!", JOptionPane.QUESTION_MESSAGE);
            catering_date.grabFocus();
            return false;
        }
        if (Integer.parseInt(catering_qty.getValue().toString()) <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a valid quentity!", "Invalid quentitiy", JOptionPane.QUESTION_MESSAGE);
            catering_qty.grabFocus();
            return false;
        }
        if (tb_c_items.getRowCount() < 1) {
            JOptionPane.showMessageDialog(this, "Please select reservation items.", "No menu or items selected!", JOptionPane.QUESTION_MESSAGE);
            tf_c_menu.grabFocus();
            tf_c_menu.selectAll();
            return false;
        }

        return true;
    }

    private boolean validatePaymentCatering() {
        if (tf_c_advance.getText().isEmpty()) {
            tf_c_advance.grabFocus();
            return false;
        }
        if (tf_c_amount.getText().isEmpty()) {
            tf_c_amount.grabFocus();
            return false;
        }
        BigDecimal tot = new BigDecimal(tf_c_total.getText());
        BigDecimal advance = new BigDecimal(tf_c_advance.getText());
        BigDecimal amount = new BigDecimal(tf_c_amount.getText());
        BigDecimal balance = BigDecimal.ZERO;
        if (advance.compareTo(tot) == 1) {
            JOptionPane.showMessageDialog(this, "Paying more than total.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            tf_c_advance.grabFocus();
            tf_c_advance.selectAll();
            return false;
        }
        if (advance.compareTo(amount) == 1) {
            JOptionPane.showMessageDialog(this, "Amount is not sufficient.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            tf_c_amount.grabFocus();
            tf_c_amount.selectAll();
            return false;
        }
        balance = amount.subtract(advance);
        lb_c_bal.setText(balance.setScale(2).toString());
        return true;
    }

    private void calculateTotalCatering() {
        int totrows = tb_c_items.getRowCount();
        BigDecimal bd = BigDecimal.ZERO;
        for (int i = 0; i < totrows; i++) {
            bd = bd.add(new BigDecimal(tb_c_items.getValueAt(i, 5).toString()));
        }
        tf_c_total.setText(bd.setScale(2).toString());
    }

    public void clearAllCatering(boolean allFields) {
        if (allFields) {
            tf_c_advance.setText(null);
            tf_c_amount.setText(null);
            lb_c_bal.setText("0.00");
        }

        catering_qty.setValue(Integer.valueOf("0"));
        catering_desc.setText(null);
        catering_date.setSelectedDate(Calendar.getInstance());
        catering_hours.setValue(Integer.valueOf("0"));
        catering_minutes.setValue(Integer.valueOf("0"));
        catering_deliver.setSelected(false);
        tf_c_menu.setText(null);
        tf_c_items.setText(null);
        tf_c_menu_qty.setText(null);
        tf_c_menu_item_qty.setText(null);

        ((DefaultTableModel) tb_c_items.getModel()).setRowCount(0);
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

    private boolean makeCateringReservation() {
        if (validateFieldsCatering() && validatePaymentCatering()) {
            if (Helper.isTheDateToday(catering_date)) {
                if (JOptionPane.showConfirmDialog(this, "Confirm reservation on today ?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                    catering_date.grabFocus();
                    return false;
                }
            }
            if (JOptionPane.showConfirmDialog(this, "Make reservation?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Database.beans.CateringReservation res = new Database.beans.CateringReservation();

                Calendar selectedDate = catering_date.getSelectedDate();
                selectedDate.set(Calendar.HOUR, Integer.parseInt(catering_hours.getValue().toString()));
                selectedDate.set(Calendar.MINUTE, Integer.parseInt(catering_minutes.getValue().toString()));
                selectedDate.set(Calendar.SECOND, 0);

                res.setAdvance(new BigDecimal(tf_c_advance.getText()).setScale(2));
                res.setCheckInDateTime(selectedDate.getTime());
                res.setCount(Integer.parseInt(catering_qty.getValue().toString()));
                res.setReservedTime(new Date());
                res.setCustomer(parentReservation.customer);
                res.setDeliver(catering_deliver.isSelected() ? true : false);
                res.setDescription(catering_desc.getText());
                res.setId(ReservationManager.getNextReservationId());

                res.setItems(getCateringCollection(ITEM_ITEM, tb_c_items));
                res.setMenus(getCateringCollection(ITEM_MENU, tb_c_items));
                res.setStatus(Reservation.RESERVATION_STATUS_RESERVED);
                res.setTotal(new BigDecimal(tf_c_total.getText()).setScale(2));
                res.setUserAccount(Users.Login.logedUserAccount.getId());
                if (CateringReservationManager.addRow(res)) {
                    JOptionPane.showMessageDialog(this, "Reserved succesfully.", "Success !", JOptionPane.INFORMATION_MESSAGE);
                    clearAllCatering(false);
                    this.clearState = Reservation.ReservationClearState.HOLD;
                    ReportManager.printLastCaterinReservation();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Reserved failed.", "Error !", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return false;
    }

    private void removeSelectedRow(JTable tbl) {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
            dtm.removeRow(row);
        }
    }

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
        jPanel35 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        catering_desc = new com.alee.laf.text.WebTextArea();
        catering_date = new datechooser.beans.DateChooserCombo();
        jLabel126 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        tf_c_total = new com.alee.laf.text.WebTextField();
        jLabel142 = new javax.swing.JLabel();
        tf_c_amount = new com.alee.laf.text.WebTextField();
        lb_c_bal = new javax.swing.JLabel();
        webButton28 = new com.alee.laf.button.WebButton();
        catering_pay = new com.alee.laf.button.WebButton();
        jLabel148 = new javax.swing.JLabel();
        catering_deliver = new com.alee.laf.checkbox.WebCheckBox();
        catering_qty = new javax.swing.JSpinner();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        tf_c_advance = new com.alee.laf.text.WebTextField();
        catering_minutes = new javax.swing.JSpinner();
        catering_hours = new javax.swing.JSpinner();
        jLabel127 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        tf_c_menu_item_qty = new javax.swing.JTextField();
        tf_c_menu_qty = new javax.swing.JTextField();
        tf_c_menu = new com.alee.laf.text.WebTextField();
        tf_c_items = new com.alee.laf.text.WebTextField();
        jLabel141 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        catering_menu = new com.alee.laf.button.WebButton();
        catering_view_selected = new com.alee.laf.button.WebButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_c_items = new javax.swing.JTable();

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

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(1058, 610));
        setMinimumSize(new java.awt.Dimension(1058, 610));
        setPreferredSize(new java.awt.Dimension(1058, 610));
        setLayout(new java.awt.GridBagLayout());

        jPanel35.setBackground(new java.awt.Color(55, 63, 75));
        jPanel35.setMaximumSize(new java.awt.Dimension(1058, 610));
        jPanel35.setMinimumSize(new java.awt.Dimension(1058, 610));
        jPanel35.setPreferredSize(new java.awt.Dimension(1058, 610));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        catering_desc.setColumns(20);
        catering_desc.setRows(3);
        catering_desc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catering_descKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                catering_descKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                catering_descKeyTyped(evt);
            }
        });
        jScrollPane9.setViewportView(catering_desc);

        jPanel35.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 260, 70));

        catering_date.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 14));
        jPanel35.add(catering_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, -1));

        jLabel126.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Minutes");
        jPanel35.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 26));

        jLabel139.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setText("Description:");
        jPanel35.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 20));

        tf_c_total.setEditable(false);
        tf_c_total.setEnabled(false);
        tf_c_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel35.add(tf_c_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 390, 210, 30));

        jLabel142.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText("Qty");
        jPanel35.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, 30));

        tf_c_amount.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tf_c_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tf_c_amountFocusGained(evt);
            }
        });
        tf_c_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_amountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_amountKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 390, 210, 40));

        lb_c_bal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lb_c_bal.setForeground(new java.awt.Color(255, 255, 255));
        lb_c_bal.setText("0.00");
        jPanel35.add(lb_c_bal, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 440, 210, 30));

        webButton28.setText("Clear");
        webButton28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        webButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton28ActionPerformed(evt);
            }
        });
        jPanel35.add(webButton28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, 30));

        catering_pay.setText("Reserve");
        catering_pay.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        catering_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_payActionPerformed(evt);
            }
        });
        jPanel35.add(catering_pay, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 480, 150, 60));

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setText("Total :");
        jPanel35.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, -1, 20));

        catering_deliver.setForeground(new java.awt.Color(255, 255, 255));
        catering_deliver.setText("Deliver");
        catering_deliver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel35.add(catering_deliver, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 100, 30));

        catering_qty.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel35.add(catering_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 60, 30));

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText("Amount :");
        jPanel35.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 390, -1, 30));

        jLabel145.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Balence :");
        jPanel35.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 440, -1, 30));

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Advance:");
        jPanel35.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, -1, 30));

        tf_c_advance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_c_advance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tf_c_advanceFocusGained(evt);
            }
        });
        tf_c_advance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_advanceKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_advanceKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_advance, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 430, 210, 40));

        catering_minutes.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        jPanel35.add(catering_minutes, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 50, 20));

        catering_hours.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        jPanel35.add(catering_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 50, 20));

        jLabel127.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setText("Date:");
        jPanel35.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 26));

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 255, 255));
        jLabel129.setText("Hours");
        jPanel35.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 26));

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel35.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 60, 30));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel35.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, 60, 30));

        tf_c_menu_item_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_menu_item_qtyKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_menu_item_qtyKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_menu_item_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 50, 30));

        tf_c_menu_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_menu_qtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_c_menu_qtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_menu_qtyKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_menu_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 50, 30));

        tf_c_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_c_menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_menuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_menuKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 180, 30));

        tf_c_items.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_c_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_itemsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_itemsKeyTyped(evt);
            }
        });
        jPanel35.add(tf_c_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 180, 30));

        jLabel141.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 255, 255));
        jLabel141.setText("Item");
        jPanel35.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 50, 30));

        jLabel140.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 255, 255));
        jLabel140.setText("Menu");
        jPanel35.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 50, 30));

        catering_menu.setText("Select Menu");
        catering_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_menuActionPerformed(evt);
            }
        });
        jPanel35.add(catering_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 170, 40));

        catering_view_selected.setText("Select Item");
        catering_view_selected.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_view_selected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_view_selectedActionPerformed(evt);
            }
        });
        jPanel35.add(catering_view_selected, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 170, 40));

        tb_c_items.setBackground(new java.awt.Color(204, 204, 204));
        tb_c_items.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_c_items.getTableHeader().setReorderingAllowed(false);
        tb_c_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_c_itemsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tb_c_items);

        jPanel35.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 630, 360));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel35, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void catering_descKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catering_descKeyPressed

    }//GEN-LAST:event_catering_descKeyPressed

    private void catering_descKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catering_descKeyReleased

    }//GEN-LAST:event_catering_descKeyReleased

    private void catering_descKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catering_descKeyTyped

    }//GEN-LAST:event_catering_descKeyTyped

    private void tf_c_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_c_amountFocusGained
        tf_c_amount.selectAll();
    }//GEN-LAST:event_tf_c_amountFocusGained

    private void tf_c_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_amountKeyTyped
        Helper.numericDataAdd(tf_c_amount, evt);
    }//GEN-LAST:event_tf_c_amountKeyTyped

    private void webButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton28ActionPerformed
        clearAllCatering(true);
    }//GEN-LAST:event_webButton28ActionPerformed

    private void catering_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_payActionPerformed
        makeCateringReservation();
    }//GEN-LAST:event_catering_payActionPerformed

    private void tf_c_advanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_c_advanceFocusGained
        tf_c_advance.selectAll();
    }//GEN-LAST:event_tf_c_advanceFocusGained

    private void tf_c_advanceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_advanceKeyTyped
        Helper.numericDataAdd(tf_c_advance, evt);
    }//GEN-LAST:event_tf_c_advanceKeyTyped

    private void tf_c_menuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menuKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tb_c_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalCatering();
            tf_c_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalCatering();
            tf_c_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_menuKeyPressed

    private void tf_c_menuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menuKeyTyped
        Helper.itemCodeAdd(tf_c_menu, evt);
    }//GEN-LAST:event_tf_c_menuKeyTyped

    private void catering_view_selectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_view_selectedActionPerformed
        showMenuItemDialog();
    }//GEN-LAST:event_catering_view_selectedActionPerformed

    private void tf_c_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuItemByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuItemDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tb_c_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalCatering();
            tf_c_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalCatering();
            tf_c_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_itemsKeyPressed

    private void tf_c_itemsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_itemsKeyTyped
        Helper.itemCodeAdd(tf_c_items, evt);
    }//GEN-LAST:event_tf_c_itemsKeyTyped

    private void catering_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_menuActionPerformed
        showMenuDialog();
    }//GEN-LAST:event_catering_menuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addCateringMenuItemByCode();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addCateringMenuByCode();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tf_c_menu_item_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menu_item_qtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuItemByCode();
        }
    }//GEN-LAST:event_tf_c_menu_item_qtyKeyPressed

    private void tf_c_menu_item_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menu_item_qtyKeyTyped
        Helper.numericDataAddOnlyDigits(tf_c_menu_item_qty, evt);
    }//GEN-LAST:event_tf_c_menu_item_qtyKeyTyped

    private void tf_c_menu_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menu_qtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuByCode();
        }
    }//GEN-LAST:event_tf_c_menu_qtyKeyPressed

    private void tf_c_menu_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menu_qtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_c_menu_qtyKeyReleased

    private void tf_c_menu_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menu_qtyKeyTyped
        Helper.numericDataAddOnlyDigits(tf_c_menu_qty, evt);
    }//GEN-LAST:event_tf_c_menu_qtyKeyTyped

    private void tbl_c_menuitem_windowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_c_menuitem_windowKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {
            addSelectedCodeToTextField(tbl_c_menuitem_window, tf_c_items, 0);

            addCateringMenuItemByCode();
            menu_item_dialog.dispose();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            tf_c_items.grabFocus();
            menu_item_dialog.dispose();
        }
    }//GEN-LAST:event_tbl_c_menuitem_windowKeyPressed

    private void menu_item_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menu_item_dialogKeyPressed

    }//GEN-LAST:event_menu_item_dialogKeyPressed

    private void tbl_c_menu_windowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_c_menu_windowKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_SPACE) {

            addSelectedCodeToTextField(tbl_c_menu_window, tf_c_menu, 0);
            menu_dialog.dispose();
            addCateringMenuByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            menu_dialog.dispose();
            tf_c_menu.grabFocus();
        }
    }//GEN-LAST:event_tbl_c_menu_windowKeyPressed

    private void tb_c_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_c_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeSelectedRow(tb_c_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotalCatering();
            tf_c_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalCatering();
            tf_c_amount.grabFocus();
        }
    }//GEN-LAST:event_tb_c_itemsKeyPressed

    private void tf_c_advanceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_advanceKeyPressed
        if (evt.getKeyCode() == 109) {
            calculateTotalCatering();
            tf_c_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalCatering();
            tf_c_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_advanceKeyPressed

    private void tf_c_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_amountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            makeCateringReservation();
        } else if (evt.getKeyCode() == 109) {
            calculateTotalCatering();
            tf_c_advance.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotalCatering();
            tf_c_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_amountKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo catering_date;
    private com.alee.laf.checkbox.WebCheckBox catering_deliver;
    private com.alee.laf.text.WebTextArea catering_desc;
    private javax.swing.JSpinner catering_hours;
    private com.alee.laf.button.WebButton catering_menu;
    private javax.swing.JSpinner catering_minutes;
    private com.alee.laf.button.WebButton catering_pay;
    private javax.swing.JSpinner catering_qty;
    private com.alee.laf.button.WebButton catering_view_selected;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lb_c_bal;
    private javax.swing.JList<String> list_c_menu_window_items;
    private javax.swing.JDialog menu_dialog;
    private javax.swing.JDialog menu_item_dialog;
    private javax.swing.JTable tb_c_items;
    private javax.swing.JTable tbl_c_menu_window;
    private javax.swing.JTable tbl_c_menuitem_window;
    private com.alee.laf.text.WebTextField tf_c_advance;
    private com.alee.laf.text.WebTextField tf_c_amount;
    private com.alee.laf.text.WebTextField tf_c_items;
    private com.alee.laf.text.WebTextField tf_c_menu;
    private javax.swing.JTextField tf_c_menu_item_qty;
    private javax.swing.JTextField tf_c_menu_qty;
    private com.alee.laf.text.WebTextField tf_c_total;
    private com.alee.laf.button.WebButton webButton28;
    // End of variables declaration//GEN-END:variables
}
