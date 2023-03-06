/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Database.DB;
import Database.beans.Block;
import Database.beans.ItemCode;
import Database.beans.Location;
import Database.beans.Menu;
import Database.beans.MenuItem;
import Database.beans.Room;
import Database.beans.SellItem;
import Database.beans.SellItemProduct;
import Database.beans.SellProduct;
import Database.tables.BlockManager;
import Database.tables.CateringReservationManager;
import Database.tables.HotelReservationManager;
import Database.tables.InvoiceManager;
import Database.tables.LocationManager;
import Database.tables.MenuItemManager;
import Database.tables.MenuManager;
import Database.tables.ProductManager;
import Database.tables.ReservationManager;
import Database.tables.ReservationManager.ReservationType;
import Database.tables.RoomManager;
import Database.tables.RoomReservationManager;
import Database.tables.SellItemManager;
import Users.UserAccount;
import Utility.Helper;
import Utility.ReportManager;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
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
public class Invoice extends javax.swing.JFrame {

    public Reservation.ReservationClearState clearState;
    private DefaultListModel clearListModal = new DefaultListModel();
    ArrayList<JTable> tableList = new ArrayList<JTable>();

    /**
     * Creates new form Invoice
     */
    public Invoice() {
        initComponents();
        tableList.add(tbl_c_menu_window);
        tableList.add(tbl_c_menuitem_window);
        tableList.add(tbl_item_dialog);
        tableList.add(tbl_items);
        tableList.add(tbl_reservations);
        addHelperListeners();
        setMinimumSize(getSize());
        setTableLook();
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                Frames.showMain();
            }
        });
        tbl_c_menu_window.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addSelectedMenuDialogItemToSideList();
            }
        });
        tbl_items.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotal();
            }
        });
        tbl_items.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        tbl_reservations.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotal();
            }
        });

        ActionListener updateClockAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jLabel20.setText(Helper.getDateTimeForDb(new Date()));
            }

        };
        Timer t = new Timer(1000, updateClockAction);

        t.start();
        clearInvoiceData();

    }

    public void setLogin(int id) {
        this.lb_user.setText(id + "");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu_item_dialog = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_c_menuitem_window = new javax.swing.JTable();
        menu_dialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_c_menu_window = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        list_c_menu_window_items = new javax.swing.JList<>();
        item_dialog = new javax.swing.JDialog();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_item_dialog = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        reservation_dialog = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        tf_search_time = new com.alee.laf.text.WebTextField();
        function_refresh1 = new com.alee.laf.button.WebButton();
        tf_search_type = new com.alee.laf.text.WebTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tf_search_count = new com.alee.laf.text.WebTextField();
        tf_search_advance = new com.alee.laf.text.WebTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tf_search_id = new com.alee.laf.text.WebTextField();
        jLabel18 = new javax.swing.JLabel();
        reserve_search = new com.alee.laf.button.WebButton();
        tf_search_tp = new com.alee.laf.text.WebTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tf_search_checkin = new com.alee.laf.text.WebTextField();
        tf_search_user = new com.alee.laf.text.WebTextField();
        tf_search_total = new com.alee.laf.text.WebTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tf_search_list = new javax.swing.JList<>();
        function_refresh2 = new com.alee.laf.button.WebButton();
        lb_status = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        lb_user1 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        registationpnl = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_items = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lb_user = new javax.swing.JLabel();
        lb_time = new javax.swing.JLabel();
        lb_invoice = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lb_customer = new javax.swing.JLabel();
        catering_view_selected = new com.alee.laf.button.WebButton();
        catering_menu = new com.alee.laf.button.WebButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_reservations = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel141 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        tf_items = new com.alee.laf.text.WebTextField();
        tf_c_menu_item_qty = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        tf_c_items = new com.alee.laf.text.WebTextField();
        jLabel140 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        tf_item_qty = new javax.swing.JTextField();
        tf_c_menu_qty = new javax.swing.JTextField();
        tf_c_menu = new com.alee.laf.text.WebTextField();
        jButton1 = new javax.swing.JButton();
        jLabel143 = new javax.swing.JLabel();
        tf_reservation = new com.alee.laf.text.WebTextField();
        jPanel4 = new javax.swing.JPanel();
        tf_discount = new javax.swing.JTextField();
        tf_total = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lb_balence = new javax.swing.JLabel();
        tf_sub_total = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_amount = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        catering_view_selected2 = new com.alee.laf.button.WebButton();
        catering_view_selected1 = new com.alee.laf.button.WebButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

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

        item_dialog.setModal(true);
        item_dialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                item_dialogKeyPressed(evt);
            }
        });

        tbl_item_dialog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock", "Code", "Name", "Item Code", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_item_dialog.getTableHeader().setReorderingAllowed(false);
        tbl_item_dialog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_item_dialogKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_item_dialog);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout item_dialogLayout = new javax.swing.GroupLayout(item_dialog.getContentPane());
        item_dialog.getContentPane().setLayout(item_dialogLayout);
        item_dialogLayout.setHorizontalGroup(
            item_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(item_dialogLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        item_dialogLayout.setVerticalGroup(
            item_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(item_dialogLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tf_search_time.setEditable(false);
        tf_search_time.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 224, 180, 30));

        function_refresh1.setText("Refresh");
        function_refresh1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refresh1ActionPerformed(evt);
            }
        });
        jPanel5.add(function_refresh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 188, 130, 30));

        tf_search_type.setEditable(false);
        tf_search_type.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 166, 180, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Customer :");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(276, 108, -1, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Time :");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 214, 40, 30));

        tf_search_count.setEditable(false);
        tf_search_count.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_count, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 272, 180, 30));

        tf_search_advance.setEditable(false);
        tf_search_advance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_advance, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 386, 180, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Type :");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 156, -1, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Check in :");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 330, -1, 30));

        tf_search_id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_search_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_search_idKeyPressed(evt);
            }
        });
        jPanel5.add(tf_search_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 138, 133, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("User :");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, -1, 30));

        reserve_search.setText("Search");
        reserve_search.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reserve_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reserve_searchActionPerformed(evt);
            }
        });
        jPanel5.add(reserve_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 138, 70, 30));

        tf_search_tp.setEditable(false);
        tf_search_tp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_tp, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 108, 180, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Total :");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 440, -1, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Reservation ID:");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 88, -1, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Count :");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, -1, 30));

        tf_search_checkin.setEditable(false);
        tf_search_checkin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_checkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 330, 180, 30));

        tf_search_user.setEditable(false);
        tf_search_user.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, 180, 30));

        tf_search_total.setEditable(false);
        tf_search_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(tf_search_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 180, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Advance :");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, -1, 30));

        tf_search_list.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tf_search_list.setEnabled(false);
        jScrollPane15.setViewportView(tf_search_list);

        jPanel5.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(576, 63, 410, 497));

        function_refresh2.setText("Add To Invoice");
        function_refresh2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        function_refresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                function_refresh2ActionPerformed(evt);
            }
        });
        jPanel5.add(function_refresh2, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 262, 143, 59));

        lb_status.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lb_status.setForeground(new java.awt.Color(0, 51, 255));
        lb_status.setText(" ");
        jPanel5.add(lb_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 368, 204, 60));

        javax.swing.GroupLayout reservation_dialogLayout = new javax.swing.GroupLayout(reservation_dialog.getContentPane());
        reservation_dialog.getContentPane().setLayout(reservation_dialogLayout);
        reservation_dialogLayout.setHorizontalGroup(
            reservation_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1057, Short.MAX_VALUE)
            .addGroup(reservation_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reservation_dialogLayout.setVerticalGroup(
            reservation_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 599, Short.MAX_VALUE)
            .addGroup(reservation_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel6.setBackground(new java.awt.Color(255, 204, 153));
        jPanel6.setMaximumSize(new java.awt.Dimension(1366, 768));
        jPanel6.setMinimumSize(new java.awt.Dimension(1366, 768));
        jPanel6.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel19.setText("INVOICE");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 32, -1, -1));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel20.setText("10.34 AM");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 29, -1, -1));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel21.setText("TIME");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jButton9.setText("CLOSE");
        jPanel6.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1552, 0, -1, -1));

        jButton10.setText("LOG OUT");
        jPanel6.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1374, 29, 116, 45));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel22.setText("LOG USER");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lb_user1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lb_user1.setText("Name");
        jPanel6.add(lb_user1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        jButton14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButton14.setText("HOME");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 60, 120, 50));

        registationpnl.setMaximumSize(new java.awt.Dimension(1366, 580));
        registationpnl.setMinimumSize(new java.awt.Dimension(1366, 580));
        registationpnl.setPreferredSize(new java.awt.Dimension(1366, 580));
        registationpnl.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setMaximumSize(new java.awt.Dimension(1366, 580));
        jPanel1.setMinimumSize(new java.awt.Dimension(1366, 580));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 580));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Type", "Name", "Unit Price", "Qty", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_items.setRowHeight(25);
        tbl_items.getTableHeader().setReorderingAllowed(false);
        tbl_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_itemsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_items);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 1140, 150));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Time :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("User :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Invoice :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        lb_user.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lb_user.setForeground(new java.awt.Color(255, 255, 255));
        lb_user.setText(" ");
        jPanel1.add(lb_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 80, -1));

        lb_time.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lb_time.setForeground(new java.awt.Color(255, 255, 255));
        lb_time.setText(" ");
        jPanel1.add(lb_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, 220, -1));

        lb_invoice.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lb_invoice.setForeground(new java.awt.Color(255, 255, 255));
        lb_invoice.setText(" ");
        jPanel1.add(lb_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 100, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Customer :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        lb_customer.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lb_customer.setForeground(new java.awt.Color(255, 255, 255));
        lb_customer.setText(" ");
        jPanel1.add(lb_customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 210, -1));

        catering_view_selected.setText("Items");
        catering_view_selected.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_view_selected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_view_selectedActionPerformed(evt);
            }
        });
        jPanel1.add(catering_view_selected, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 110, 50));

        catering_menu.setText("Menu");
        catering_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_menuActionPerformed(evt);
            }
        });
        jPanel1.add(catering_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 110, 50));

        tbl_reservations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date Time", "Type", "Total", "Advance", "Balance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_reservations.getTableHeader().setReorderingAllowed(false);
        tbl_reservations.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_reservationsKeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_reservations);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 1140, 140));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel141.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel141.setText("Menu Item");
        jPanel3.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, 70, 30));

        jButton7.setText("Add");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 60, 30));

        tf_items.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_itemsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_itemsKeyTyped(evt);
            }
        });
        jPanel3.add(tf_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 11, 180, 30));

        tf_c_menu_item_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_menu_item_qtyKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_menu_item_qtyKeyTyped(evt);
            }
        });
        jPanel3.add(tf_c_menu_item_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 91, 50, 30));

        jButton8.setText("Add");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 11, 60, 30));

        jButton6.setText("Add");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 91, 60, 30));

        tf_c_items.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_c_items.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_itemsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_itemsKeyTyped(evt);
            }
        });
        jPanel3.add(tf_c_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 91, 180, 30));

        jLabel140.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel140.setText("Menu");
        jPanel3.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 50, 30));

        jLabel142.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel142.setText("Items");
        jPanel3.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 50, 30));

        tf_item_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_item_qtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_item_qtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_item_qtyKeyTyped(evt);
            }
        });
        jPanel3.add(tf_item_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 11, 50, 30));

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
        jPanel3.add(tf_c_menu_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 50, 30));

        tf_c_menu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_c_menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_c_menuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_c_menuKeyTyped(evt);
            }
        });
        jPanel3.add(tf_c_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 180, 30));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 110, 30));

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel143.setText("Reservation");
        jPanel3.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 84, 30));

        tf_reservation.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_reservation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_reservationKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_reservationKeyTyped(evt);
            }
        });
        jPanel3.add(tf_reservation, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 180, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 400, 170));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tf_discount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_discount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_discountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_discountKeyTyped(evt);
            }
        });
        jPanel4.add(tf_discount, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 41, 100, -1));

        tf_total.setEditable(false);
        tf_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(tf_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 71, 100, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Balance :");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Sub Total :");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Discount :");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 41, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Total :");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 71, -1, -1));

        lb_balence.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel4.add(lb_balence, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 120, 40));

        tf_sub_total.setEditable(false);
        tf_sub_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(tf_sub_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 11, 100, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Amount :");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        tf_amount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tf_amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_amountKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_amountKeyTyped(evt);
            }
        });
        jPanel4.add(tf_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 130, 40));

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setText("Pay");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 170, 120));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, -1, -1));

        catering_view_selected2.setText("Reservation");
        catering_view_selected2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_view_selected2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_view_selected2ActionPerformed(evt);
            }
        });
        jPanel1.add(catering_view_selected2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, 110, 50));

        catering_view_selected1.setText("Menu Item");
        catering_view_selected1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        catering_view_selected1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catering_view_selected1ActionPerformed(evt);
            }
        });
        jPanel1.add(catering_view_selected1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 110, 50));

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 480, 250, 40));

        jButton3.setText("Print Last Copy");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 480, 250, 40));

        registationpnl.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel6.add(registationpnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 167, -1, 613));

        getContentPane().add(jPanel6, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        addCateringMenuItemByCode();
    }//GEN-LAST:event_jButton6ActionPerformed

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        addCateringMenuByCode();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tf_c_menuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menuKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tbl_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_menuKeyPressed

    /*
    remove the last row from a given <b>tbl</b>
     */
    private void removeLastRow(JTable tbl) {
        int rowCount = tbl.getRowCount();
        if (rowCount > 0) {
            ((DefaultTableModel) tbl.getModel()).removeRow(rowCount - 1);
        }
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
                tf_c_menu_qty.grabFocus();
                tf_c_menu_qty.selectAll();
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
                    tf_c_menu_qty.grabFocus();
                    tf_c_menu_qty.selectAll();
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
        addItemToFinalTable(row.getCode(), "Menu", row.getName(), row.getPrice(), String.valueOf(qty), tbl_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            clearTotalInvoice();
            this.clearState = Reservation.ReservationClearState.CLEAR;
            calculateTotal();
        }
        tf_c_menu.setText(null);
        tf_c_menu_qty.setText(null);
        tf_c_menu.grabFocus();
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

    private void clearTotalInvoice() {
        tf_amount.setText(null);
        tf_sub_total.setText(null);
        tf_discount.setText(null);
        tf_total.setText(null);
        lb_balence.setText("0.00");
    }

    public void clearInvoiceData() {
        ((DefaultTableModel) tbl_items.getModel()).setRowCount(0);
        ((DefaultTableModel) tbl_reservations.getModel()).setRowCount(0);
        tf_c_items.setText(null);
        tf_c_menu.setText(null);
        tf_c_menu_item_qty.setText(null);
        tf_c_menu_qty.setText(null);
        tf_item_qty.setText(null);
        tf_items.setText(null);
        tf_reservation.setText(null);
        tf_items.setText(null);
        tf_items.grabFocus();
        lb_customer.setText(null);
        lb_invoice.setText(InvoiceManager.getNextInvoice());
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
        addItemToFinalTable(row.getCode(), "Item", row.getName(), row.getPrice(), String.valueOf(qty), tbl_items);
        if (this.clearState == Reservation.ReservationClearState.HOLD) {
            clearTotalInvoice();
            this.clearState = Reservation.ReservationClearState.CLEAR;
            calculateTotal();
        }
        tf_c_items.setText(null);
        tf_c_menu_item_qty.setText(null);
        tf_c_items.grabFocus();
    }

    private void showMenuDialog() {

        menu_dialog.pack();
        menu_dialog.setLocationRelativeTo(null);
        addAllMenusToMenuDialogTable();
        tbl_c_menu_window.clearSelection();
        list_c_menu_window_items.setModel(clearListModal);
        menu_dialog.setVisible(true);
    }

    private void showMenuItemDialog() {
        menu_item_dialog.pack();
        menu_item_dialog.setLocationRelativeTo(null);
        addAllMenuItemsToMenuItemDialogTable();
        tbl_c_menuitem_window.clearSelection();
        menu_item_dialog.setVisible(true);
    }

    private void showItemDialog(boolean withTfCode) {

        item_dialog.pack();
        item_dialog.setLocationRelativeTo(null);
        if (withTfCode) {
            addAllItemsToItemDialogTable(tf_items.getText().equals("") ? null : tf_items.getText(), null);
        } else {
            addAllItemsToItemDialogTable(null, null);
        }
        tbl_item_dialog.clearSelection();
        item_dialog.setVisible(true);
    }

    private String currentItemId;

    private void addSellItemByCode(boolean fromDialog) {
        if (tf_items.getText().isEmpty()) {
            tf_items.grabFocus();
            tf_items.selectAll();
            return;
        }

        ItemCode ic = Helper.getItemCode(tf_items);

        int qty = 0;
        int itemcode = 0;

        if (ic == null) {
            if (tf_item_qty.getText().isEmpty()) {
                tf_item_qty.grabFocus();
                tf_item_qty.selectAll();
                return;
            }
            qty = Integer.parseInt(tf_item_qty.getText());
            if (qty == 0) {
                tf_item_qty.grabFocus();
                tf_item_qty.selectAll();
                return;
            }
            itemcode = Integer.parseInt(tf_items.getText());

        } else {
            if (ic.getQty() == -1) {
                if (tf_item_qty.getText().isEmpty()) {
                    tf_item_qty.grabFocus();
                    tf_item_qty.selectAll();
                    return;
                }

                qty = Integer.parseInt(tf_item_qty.getText());
                if (qty == 0) {
                    tf_item_qty.grabFocus();
                    tf_item_qty.selectAll();
                    return;
                }
                itemcode = ic.getItemCode();

            } else {
                itemcode = ic.getItemCode();
                qty = ic.getQty();
            }
        }

        // get menu by item code and set qty
        // here, the itemcode is actually the product code
        //need to find sell items of that product
        boolean rowAdded = false;
        if (fromDialog) {
            SellItemProduct row = SellItemManager.getRow(currentItemId);
            if (row == null) {
                tf_items.grabFocus();
                tf_items.selectAll();
                JOptionPane.showMessageDialog(this, "There was a system error selecting items!");
                return;
            }
            addItemToFinalTable(row.getCode(), currentItemId, row.getName(), row.getPrice(), String.valueOf(qty), tbl_items);
            rowAdded = true;
        } else {
            ArrayList<SellProduct> pl = ProductManager.getSellProductsLikeCode(String.valueOf(itemcode), true);
            int size = pl.size();
            if (size > 1) {
                tf_items.setText(String.valueOf(itemcode));
                showItemDialog(true);
            } else if (size == 1) {
                SellProduct s = pl.get(0);
                if (s.getItemList().size() > 1) {
                    tf_items.setText(String.valueOf(itemcode));

                    // model make the thread wait. so has to return
                    showItemDialog(true);
                    return;
                } else if (s.getItemList().size() == 1) {
                    SellItem i = s.getItemList().get(0);
                    addItemToFinalTable(s.getCode(), String.valueOf(i.getId()), s.getName(), i.getSellPrice(), String.valueOf(qty), tbl_items);
                    rowAdded = true;
                }

            }

        }
        if (rowAdded) {
            if (this.clearState == Reservation.ReservationClearState.HOLD) {
                clearTotalInvoice();
                this.clearState = Reservation.ReservationClearState.CLEAR;
                calculateTotal();
            }
            tf_items.setText(null);
            tf_item_qty.setText(null);
            tf_items.grabFocus();
        } else {
            JOptionPane.showMessageDialog(this, "No item found!", null, JOptionPane.QUESTION_MESSAGE);
            tf_items.grabFocus();
            tf_items.selectAll();
        }
    }

    private void addAllItemsToItemDialogTable(String code, String name) {
        DefaultTableModel dtm = (DefaultTableModel) tbl_item_dialog.getModel();
        dtm.setRowCount(0);
        ArrayList<SellProduct> pl = null;
        if (name == null) {
            pl = ProductManager.getSellProductsLikeCode(code, true);
        } else {
            pl = ProductManager.getSellProductsLikeName(name, true);
        }
        for (SellProduct p : pl) {
            for (SellItem i : p.getItemList()) {
                Vector v = new Vector();
                v.add(p.getStockName());
                v.add(p.getCode());
                v.add(p.getName());
                v.add(i.getId());
                v.add(i.getQtyRemain());
                v.add(i.getSellPrice().setScale(2).toString());
                dtm.addRow(v);
            }
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

    private void calculateTotal() {
        int rowCount = tbl_items.getRowCount();
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < rowCount; i++) {
            total = total.add(new BigDecimal(tbl_items.getValueAt(i, 5).toString()));
        }
        rowCount = tbl_reservations.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total = total.add(new BigDecimal(tbl_reservations.getValueAt(i, 5).toString()));
        }
        tf_sub_total.setText(total.setScale(2).toString());
    }

    private void calculateTotalFromDiscount() {
        String discount = tf_discount.getText();
        if (discount.isEmpty()) {
            discount = "0.00";
        }
        BigDecimal subtot = new BigDecimal(tf_sub_total.getText());
        BigDecimal dis = new BigDecimal(discount);
        tf_total.setText(subtot.subtract(dis).setScale(2).toString());
    }

    private boolean validatePaymentInvoice() {
        String discount = tf_discount.getText();
        String amount = tf_amount.getText();
        if (discount.isEmpty()) {
            tf_discount.grabFocus();
            return false;
        }
        if (amount.isEmpty()) {
            tf_amount.grabFocus();
            return false;
        }
        BigDecimal subtot = new BigDecimal(tf_sub_total.getText());
        BigDecimal dis = new BigDecimal(discount);
        BigDecimal tot = new BigDecimal(tf_total.getText());
        BigDecimal amt = new BigDecimal(amount);
        BigDecimal balance = BigDecimal.ZERO;

        if (dis.compareTo(subtot) == 1) {
            JOptionPane.showMessageDialog(this, "Discount is more than subtotal.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            tf_discount.grabFocus();
            tf_discount.selectAll();
            return false;
        }

        if (tot.compareTo(amt) == 1) {
            JOptionPane.showMessageDialog(this, "Amount is not sufficient.", "Invalid payment!", JOptionPane.WARNING_MESSAGE);
            tf_amount.grabFocus();
            tf_amount.selectAll();
            return false;
        }
        balance = amt.subtract(tot);
        lb_balence.setText(balance.setScale(2).toString());
        return true;
    }

    private void removeSelectedRow(JTable tbl) {
        int row = tbl.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) tbl.getModel();
            dtm.removeRow(row);
        }
    }

    private void tf_c_menuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_menuKeyTyped
        Helper.itemCodeAdd(tf_c_menu, evt);
    }//GEN-LAST:event_tf_c_menuKeyTyped

    private void tf_c_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addCateringMenuItemByCode();
        } else if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showMenuItemDialog();
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeLastRow(tbl_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_c_itemsKeyPressed

    private void tf_c_itemsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_c_itemsKeyTyped
        Helper.itemCodeAdd(tf_c_items, evt);
    }//GEN-LAST:event_tf_c_itemsKeyTyped

    private void catering_view_selectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_view_selectedActionPerformed
        showItemDialog(false);
    }//GEN-LAST:event_catering_view_selectedActionPerformed

    private void catering_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_menuActionPerformed
        showMenuDialog();
    }//GEN-LAST:event_catering_menuActionPerformed

    private void catering_view_selected1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_view_selected1ActionPerformed
        showMenuItemDialog();
    }//GEN-LAST:event_catering_view_selected1ActionPerformed

    private void tf_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            showItemDialog(true);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.state == DialogState.FROM_DIALOG) {
                addSellItemByCode(true);
            } else {
                addSellItemByCode(false);
            }
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_itemsKeyPressed

    private void tf_itemsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_itemsKeyTyped
        this.state = DialogState.INSIDE;
        Helper.numericDataAddOnlyDigits(tf_items, evt);
    }//GEN-LAST:event_tf_itemsKeyTyped

    private enum DialogState {
        FROM_DIALOG, INSIDE
    }
    private DialogState state;
    private void tf_item_qtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_item_qtyKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.state == DialogState.FROM_DIALOG) {
                addSellItemByCode(true);
            } else {
                addSellItemByCode(false);
            }
        }
    }//GEN-LAST:event_tf_item_qtyKeyPressed

    private void tf_item_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_item_qtyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_item_qtyKeyReleased

    private void tf_item_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_item_qtyKeyTyped
        Helper.itemCodeAdd(tf_item_qty, evt);
    }//GEN-LAST:event_tf_item_qtyKeyTyped

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (this.state == DialogState.FROM_DIALOG) {
            addSellItemByCode(true);
        } else {
            addSellItemByCode(false);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void tbl_item_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_item_dialogKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int selectedRow = tbl_item_dialog.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            tf_items.setText(null);
            addSelectedCodeToTextField(tbl_item_dialog, tf_items, 1);
            this.state = DialogState.FROM_DIALOG;
            currentItemId = tbl_item_dialog.getValueAt(tbl_item_dialog.getSelectedRow(), 3).toString();
            item_dialog.dispose();
            tf_item_qty.grabFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            item_dialog.dispose();
        }
    }//GEN-LAST:event_tbl_item_dialogKeyPressed

    private void item_dialogKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_dialogKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_dialogKeyPressed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped

    }//GEN-LAST:event_jTextField1KeyTyped

    private void catering_view_selected2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catering_view_selected2ActionPerformed
        reservation_dialog.pack();
        reservation_dialog.setLocationRelativeTo(null);
        reservation_dialog.setVisible(true);
        tf_search_id.grabFocus();
    }//GEN-LAST:event_catering_view_selected2ActionPerformed

    private void tf_search_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_search_idKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchReservation();
        }
    }//GEN-LAST:event_tf_search_idKeyPressed

    private void function_refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refresh1ActionPerformed
        clearAllReservationSearchFields();
    }//GEN-LAST:event_function_refresh1ActionPerformed

    private void reserve_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserve_searchActionPerformed
        searchReservation();
    }//GEN-LAST:event_reserve_searchActionPerformed

    private void function_refresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_function_refresh2ActionPerformed
        addReservationFromTextField(tf_search_id);

    }//GEN-LAST:event_function_refresh2ActionPerformed

    private void tf_reservationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_reservationKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addReservationFromTextField(tf_reservation);
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_reservationKeyPressed

    private void tf_reservationKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_reservationKeyTyped
        Helper.numericDataAddOnlyDigits(tf_reservation, evt);
    }//GEN-LAST:event_tf_reservationKeyTyped

    private void tf_discountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_discountKeyTyped
        Helper.numericDataAdd(tf_discount, evt);
    }//GEN-LAST:event_tf_discountKeyTyped

    private void tf_amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_amountKeyTyped
        Helper.numericDataAdd(tf_amount, evt);
    }//GEN-LAST:event_tf_amountKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        makeInvoice();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addReservationFromTextField(tf_reservation);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_discountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_discountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calculateTotalFromDiscount();
            tf_amount.grabFocus();
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_discountKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clearTotalInvoice();
        clearInvoiceData();
        this.clearState = Reservation.ReservationClearState.CLEAR;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        addAllItemsToItemDialogTable(null, jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void tbl_reservationsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_reservationsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeSelectedRow(tbl_reservations);
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tbl_reservationsKeyPressed

    private void tbl_itemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_itemsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            removeSelectedRow(tbl_items);
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tbl_itemsKeyPressed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Frames.showMain();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void tf_amountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_amountKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            makeInvoice();
        } else if (evt.getKeyCode() == 109) {
            calculateTotal();
            tf_discount.grabFocus();
        } else if (evt.getKeyCode() == 107) {
            calculateTotal();
            tf_amount.grabFocus();
        }
    }//GEN-LAST:event_tf_amountKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ReportManager.printLastInvoiceCopy();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void makeInvoice() {
        if (!validateInvoiceFields()) {
            return;
        }
//initializing hashmaps for possible item lists
        HashMap<Integer, Integer> menus = new HashMap<>();
        HashMap<Integer, Integer> menuItems = new HashMap<>();
        HashMap<Integer, Integer> sellItems = new HashMap<>();
        int rowCount = tbl_items.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String code = tbl_items.getValueAt(i, 0).toString();
            String type = tbl_items.getValueAt(i, 1).toString();
            int qty = Integer.parseInt(tbl_items.getValueAt(i, 4).toString());
            if (type.equals("Item")) {
                menuItems.put(MenuItemManager.getIdByCode(code), qty);
            } else if (type.equals("Menu")) {
                menus.put(MenuManager.getIdByCode(code), qty);
            } else {
                int id = Integer.parseInt(tbl_items.getValueAt(i, 1).toString());
                sellItems.put(id, qty);
            }
        }
        ArrayList<Integer> caterins = new ArrayList<>();
        ArrayList<Integer> rooms = new ArrayList<>();
        ArrayList<Integer> hotels = new ArrayList<>();
        rowCount = tbl_reservations.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int id = Integer.parseInt(tbl_reservations.getValueAt(i, 0).toString());
            String type = tbl_reservations.getValueAt(i, 2).toString();
            if (type.equals(ReservationManager.RESERVATION_CATERIN)) {
                caterins.add(id);
            } else if (type.equals(ReservationManager.RESERVATION_HOTEL)) {
                hotels.add(id);
            } else if (type.equals(ReservationManager.RESERVATION_ROOM)) {
                rooms.add(id);
            }
        }
        Database.beans.Invoice in = new Database.beans.Invoice();
        in.setCaterinReservations(caterins);
        in.setDiscount(new BigDecimal(tf_discount.getText()));
        in.setHotelReservations(hotels);
        in.setMenus(menus);
        in.setMenutems(menuItems);
        in.setRoomReservations(rooms);
        in.setSellItems(sellItems);
        in.setSubtotal(new BigDecimal(tf_sub_total.getText()));
        in.setTime(new Date());
        in.setUserAccount(Users.Login.logedUserAccount.getId());

        if (JOptionPane.showConfirmDialog(this, "Confirm payment ?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (InvoiceManager.addRow(in)) {
                JOptionPane.showMessageDialog(this, "Payment successful !", "Success !", JOptionPane.INFORMATION_MESSAGE);
                clearInvoiceData();
                this.clearState = Reservation.ReservationClearState.HOLD;
                ReportManager.printLastInvoice();

            } else {
                JOptionPane.showMessageDialog(this, "Couldn't do payment !", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private boolean validateInvoiceFields() {
        int rowCount = tbl_items.getRowCount();
        int rowCount1 = tbl_reservations.getRowCount();
        if (rowCount < 1 && rowCount1 < 1) {
            JOptionPane.showMessageDialog(this, "Please select items for invoice.", "No invoice items selected!", JOptionPane.QUESTION_MESSAGE);
            return false;
        }
        String amt = tf_amount.getText();
        if (amt.isEmpty()) {
            tf_amount.grabFocus();
            return false;
        }
        String dis = tf_discount.getText();
        if (dis.isEmpty()) {
            tf_discount.setText(dis = "0.00");
            tf_total.setText(tf_sub_total.getText());
        }
        BigDecimal subTotal = new BigDecimal(tf_sub_total.getText());
        BigDecimal discount = new BigDecimal(tf_discount.getText());
        BigDecimal total = subTotal.subtract(discount);
        BigDecimal amount = new BigDecimal(tf_amount.getText());
        if (total.compareTo(amount) == 1) {
            JOptionPane.showMessageDialog(this, "Amount is less than total.", "Invalid payment!", JOptionPane.QUESTION_MESSAGE);
            tf_amount.grabFocus();
            tf_amount.selectAll();
            return false;
        }
        BigDecimal balance = amount.subtract(total);
        lb_balence.setText(balance.setScale(2).toString());

        return true;
    }

    /*
    Reservation adding helpar functions
     */
    private void addReservationFromTextField(JTextComponent tf) {

        String id = tf.getText();
        if (id.isEmpty()) {
            tf.grabFocus();
            return;
        }
        ReservationType typeById = ReservationManager.getTypeById(id);
        if (typeById == null) {
            JOptionPane.showMessageDialog(this, "No reservation found !", null, JOptionPane.QUESTION_MESSAGE);
            tf.grabFocus();
            tf.selectAll();
            return;
        }

        String status = ReservationManager.getStatusById(id);
        if (status.equals(ReservationManager.CHECKED_OUT)) {
            JOptionPane.showMessageDialog(this, "This reservation has already been checked out.", "Checked out reservation !", JOptionPane.INFORMATION_MESSAGE);
            tf.grabFocus();
            tf.selectAll();
            return;
        }
        if (status.equals(ReservationManager.CHECKED_IN)) {
            JOptionPane.showMessageDialog(this, "This reservation is checked in.", "Checked in reservation !", JOptionPane.INFORMATION_MESSAGE);
            tf.grabFocus();
            tf.selectAll();
            return;
        }
        if (status.equals(ReservationManager.CANCELLED)) {
            JOptionPane.showMessageDialog(this, "This reservation has been cancelled.", "Cancelled reservation !", JOptionPane.INFORMATION_MESSAGE);
            tf.grabFocus();
            tf.selectAll();
            return;
        }
        int rowCount = tbl_reservations.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String idRs = tbl_reservations.getValueAt(i, 0).toString();
            if (id.equals(idRs)) {
                JOptionPane.showMessageDialog(this, "This reservation has already been added!");
                return;
            }
        }
        if (typeById == ReservationType.CATERING) {
            addCateringReservationToTable(id);
        } else if (typeById == ReservationType.HOTEL) {
            addHotelReservationToTable(id);
        } else if (typeById == ReservationType.ROOM) {
            addRoomReservationToTable(id);
        }
        tf.setText(null);
    }

    private void addCateringReservationToTable(String id) {

        Database.beans.CateringReservation row = CateringReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        DefaultTableModel dtm = (DefaultTableModel) tbl_reservations.getModel();
        Vector v = new Vector();
        v.add(row.getId());
        v.add(Helper.getDateTimeForDb(row.getReservedTime()));
        v.add(ReservationManager.RESERVATION_CATERIN);
        v.add(row.getTotal().setScale(2).toString());
        v.add(row.getAdvance().setScale(2).toString());
        v.add(row.getTotal().subtract(row.getAdvance()).setScale(2).toString());
        dtm.addRow(v);
        lb_customer.setText(row.getCustomer().getTp());
        closeReservationDialog();
    }

    private void addRoomReservationToTable(String id) {
        Database.beans.RoomReservation row = RoomReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        DefaultTableModel dtm = (DefaultTableModel) tbl_reservations.getModel();
        Vector v = new Vector();
        v.add(row.getId());
        v.add(Helper.getDateTimeForDb(row.getReservedTime()));
        v.add(ReservationManager.RESERVATION_ROOM);
        v.add(row.getTotal().setScale(2).toString());
        v.add(row.getAdvance().setScale(2).toString());
        v.add(row.getTotal().subtract(row.getAdvance()).setScale(2).toString());
        dtm.addRow(v);
        lb_customer.setText(row.getCustomer().getTp());
        closeReservationDialog();
    }

    private void addHotelReservationToTable(String id) {
        Database.beans.HotelReservation row = HotelReservationManager.getRow(id);
        if (row == null) {
            return;
        }
        DefaultTableModel dtm = (DefaultTableModel) tbl_reservations.getModel();
        Vector v = new Vector();
        v.add(row.getId());
        v.add(Helper.getDateTimeForDb(row.getReservedTime()));
        v.add(ReservationManager.RESERVATION_HOTEL);
        v.add(row.getTotal().setScale(2).toString());
        v.add(row.getAdvance().setScale(2).toString());
        v.add(row.getTotal().subtract(row.getAdvance()).setScale(2).toString());
        dtm.addRow(v);
        lb_customer.setText(row.getCustomer().getTp());
        closeReservationDialog();
    }

    private void closeReservationDialog() {
        reservation_dialog.dispose();
        clearAllReservationSearchFields();
        tf_amount.grabFocus();
    }

    /*
    Reservation dialog search helpar functions
     */
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
        if (typeById == ReservationType.CATERING) {
            addCateringReservationToSearch(id);
        } else if (typeById == ReservationType.HOTEL) {
            addHotelReservationToSearch(id);
        } else if (typeById == ReservationType.ROOM) {
            addRoomReservationToSearch(id);
        }
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
        lb_status.setText(row.getStatus());
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
        lb_status.setText(row.getStatus());
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
        lb_status.setText(row.getSatus());
    }

    public void clearFields() {
        clearAllReservationSearchFields();
        clearInvoiceData();
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
        lb_status.setText(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Invoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new Invoice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton catering_menu;
    private com.alee.laf.button.WebButton catering_view_selected;
    private com.alee.laf.button.WebButton catering_view_selected1;
    private com.alee.laf.button.WebButton catering_view_selected2;
    private com.alee.laf.button.WebButton function_refresh1;
    private com.alee.laf.button.WebButton function_refresh2;
    private javax.swing.JDialog item_dialog;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb_balence;
    private javax.swing.JLabel lb_customer;
    private javax.swing.JLabel lb_invoice;
    private javax.swing.JLabel lb_status;
    private javax.swing.JLabel lb_time;
    public javax.swing.JLabel lb_user;
    public javax.swing.JLabel lb_user1;
    private javax.swing.JList<String> list_c_menu_window_items;
    private javax.swing.JDialog menu_dialog;
    private javax.swing.JDialog menu_item_dialog;
    private javax.swing.JPanel registationpnl;
    private javax.swing.JDialog reservation_dialog;
    private com.alee.laf.button.WebButton reserve_search;
    private javax.swing.JTable tbl_c_menu_window;
    private javax.swing.JTable tbl_c_menuitem_window;
    private javax.swing.JTable tbl_item_dialog;
    private javax.swing.JTable tbl_items;
    private javax.swing.JTable tbl_reservations;
    private javax.swing.JTextField tf_amount;
    private com.alee.laf.text.WebTextField tf_c_items;
    private com.alee.laf.text.WebTextField tf_c_menu;
    private javax.swing.JTextField tf_c_menu_item_qty;
    private javax.swing.JTextField tf_c_menu_qty;
    private javax.swing.JTextField tf_discount;
    private javax.swing.JTextField tf_item_qty;
    private com.alee.laf.text.WebTextField tf_items;
    private com.alee.laf.text.WebTextField tf_reservation;
    private com.alee.laf.text.WebTextField tf_search_advance;
    private com.alee.laf.text.WebTextField tf_search_checkin;
    private com.alee.laf.text.WebTextField tf_search_count;
    public com.alee.laf.text.WebTextField tf_search_id;
    private javax.swing.JList<String> tf_search_list;
    private com.alee.laf.text.WebTextField tf_search_time;
    private com.alee.laf.text.WebTextField tf_search_total;
    private com.alee.laf.text.WebTextField tf_search_tp;
    private com.alee.laf.text.WebTextField tf_search_type;
    private com.alee.laf.text.WebTextField tf_search_user;
    private javax.swing.JTextField tf_sub_total;
    private javax.swing.JTextField tf_total;
    // End of variables declaration//GEN-END:variables
}
