package GUI;

import Database.DB;
import Database.beans.Employee;
import Database.tables.EmployeeManager;
import Utility.Helper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class EmployeeRegistration extends javax.swing.JPanel {

    private static Employee empUpdate;
    public Registation parent;

    public EmployeeRegistration() {
        initComponents();
        loadRoles();
        loadAllEmployees();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_fname = new javax.swing.JTextField();
        tf_lname = new javax.swing.JTextField();
        tp_emp_nic = new javax.swing.JTextField();
        emp_female = new javax.swing.JRadioButton();
        emp_male = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        tf_emp_tp = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tf_str1 = new javax.swing.JTextField();
        tf_str2 = new javax.swing.JTextField();
        tf_city = new javax.swing.JTextField();
        tf_no = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        emp_role = new javax.swing.JComboBox<String>();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_description = new javax.swing.JTextArea();
        emp_active = new javax.swing.JCheckBox();
        emp_editbtn = new javax.swing.JButton();
        emp_addbtn = new javax.swing.JButton();
        emp_resetbtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        emp_table = new javax.swing.JTable();
        emp_deactivetablebtn = new javax.swing.JButton();
        emp_activetablebtn = new javax.swing.JButton();
        employee_refreshtablebtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(55, 63, 75));
        setMaximumSize(new java.awt.Dimension(1366, 580));
        setMinimumSize(new java.awt.Dimension(1366, 580));
        setPreferredSize(new java.awt.Dimension(1366, 580));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(55, 63, 75));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Staff", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Generel Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel16.setText(" NIC");

        jLabel1.setText("First Name");

        jLabel3.setText("Last Name");

        jLabel6.setText("Gender");

        buttonGroup1.add(emp_female);
        emp_female.setText("Female");

        buttonGroup1.add(emp_male);
        emp_male.setText("Male");

        jLabel13.setText("Telephone");

        tf_emp_tp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_emp_tpKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(109, 109, 109)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tf_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tp_emp_nic, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(tf_emp_tp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(emp_male)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emp_female)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tp_emp_nic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_emp_tp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(emp_male)
                    .addComponent(emp_female))
                .addGap(72, 72, 72))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 390, 250));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Address");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel8.setText("No");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel9.setText("Street 1");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, -1, -1));

        jLabel10.setText("Street 2");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 101, -1, -1));

        jLabel11.setText("City");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));
        jPanel3.add(tf_str1, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 70, 160, -1));
        jPanel3.add(tf_str2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 117, 160, -1));
        jPanel3.add(tf_city, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 160, -1));
        jPanel3.add(tf_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 160, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 470, 250));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Job Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel15.setText("Role");

        jLabel14.setText("Description");

        emp_role.setMaximumRowCount(20);

        ta_description.setColumns(20);
        ta_description.setRows(5);
        jScrollPane1.setViewportView(ta_description);

        emp_active.setText("Active");
        emp_active.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_activeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emp_role, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addGap(18, 178, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(emp_active, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emp_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(emp_active, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 430, 250));

        emp_editbtn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        emp_editbtn.setText("Edit Information");
        emp_editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_editbtnActionPerformed(evt);
            }
        });
        jPanel1.add(emp_editbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 280, -1, -1));

        emp_addbtn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        emp_addbtn.setText("Add to Staff");
        emp_addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_addbtnActionPerformed(evt);
            }
        });
        jPanel1.add(emp_addbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, -1, -1));

        emp_resetbtn.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        emp_resetbtn.setText("Clear");
        emp_resetbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_resetbtnActionPerformed(evt);
            }
        });
        jPanel1.add(emp_resetbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 280, 170, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1340, 320));

        jPanel5.setBackground(new java.awt.Color(55, 63, 75));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Staff Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emp_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NIC", "Contact No.", "First Name", "Last Name", "Gender", "Address", "Role", "Description", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        emp_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emp_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(emp_table);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 1140, 210));

        emp_deactivetablebtn.setText("Deactivate Employee");
        emp_deactivetablebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_deactivetablebtnActionPerformed(evt);
            }
        });
        jPanel5.add(emp_deactivetablebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 140, -1));

        emp_activetablebtn.setText("Activate Employee");
        emp_activetablebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emp_activetablebtnActionPerformed(evt);
            }
        });
        jPanel5.add(emp_activetablebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 140, -1));

        employee_refreshtablebtn.setText("Refresh Table");
        employee_refreshtablebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_refreshtablebtnActionPerformed(evt);
            }
        });
        jPanel5.add(employee_refreshtablebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 140, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 1340, 270));
    }// </editor-fold>//GEN-END:initComponents

    private void loadRoles() {
        try (
                ResultSet allRoles = EmployeeManager.getAllRoles();) {
            if (allRoles == null) {
                return;
            }
            Vector v = new Vector();
            while (allRoles.next()) {

                v.add(allRoles.getString("name"));

            }
            emp_role.setModel(new DefaultComboBoxModel<>(v));
        } catch (SQLException ex) {
            DB.processException(ex);
        }
    }

    private void loadAllEmployees() {
        loadEmployeSupport(EmployeeManager.getAll(false));
    }

    private void loadEmployeSupport(ResultSet res) {
        try (ResultSet rs = res) {

            DefaultTableModel dtm = (DefaultTableModel) emp_table.getModel();

            rs.beforeFirst();
            dtm.setRowCount(0);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getInt("idemployee"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("tp"));
                v.add(rs.getString("fname"));
                v.add(rs.getString("lname"));
                v.add(rs.getString("gender"));
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("no") + ",");
                sb.append(rs.getString("street1") + ",");
                sb.append(rs.getString("street2") + ",");
                sb.append(rs.getString("city"));
                v.add(sb.toString());
                v.add(rs.getString("role"));
                v.add(rs.getString("description"));
                v.add(rs.getString("date"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            //DB.processException(e);
        }
    }

    private void emp_deactivetablebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_deactivetablebtnActionPerformed

        if (empUpdate == null) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.", "No employee selected!", JOptionPane.WARNING_MESSAGE);
        } else {
            if (empUpdate.getStatus() == 0) {
                JOptionPane.showMessageDialog(this, "This employee is already deactivated !");
            } else if (JOptionPane.showConfirmDialog(this, "Deactivate employee?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                setActivation(0);
            }
        }

    }//GEN-LAST:event_emp_deactivetablebtnActionPerformed

    private void emp_addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_addbtnActionPerformed
        if (EmployeeManager.employeeExists(tp_emp_nic.getText())) {
            JOptionPane.showMessageDialog(this, "Employe already exists!", "", JOptionPane.QUESTION_MESSAGE);
            return;
        }

        if (validateData() && JOptionPane.showConfirmDialog(this, "Register the employee ?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            Employee emp = new Employee();
            emp.setCity(tf_city.getText());
            emp.setDate(new Date());

            emp.setDescriptioin(ta_description.getText());
            emp.setFname(tf_fname.getText());

            if (emp_male.isSelected()) {
                emp.setGender(Employee.GENDER_MALE);
            } else {
                emp.setGender(Employee.GENDER_FEMALE);
            }
            emp.setId(-1);
            emp.setLname(tf_lname.getText());
            emp.setNic(tp_emp_nic.getText());
            emp.setNo(tf_no.getText());

            emp.setRole(emp_role.getSelectedItem().toString());

            if (emp_active.isSelected()) {
                emp.setStatus(1);
            } else {
                emp.setStatus(0);
            }
            emp.setStreet1(tf_str1.getText());
            emp.setStreet2(tf_str2.getText());

            emp.setTp(tf_emp_tp.getText());

            if (EmployeeManager.addRow(emp)) {
                JOptionPane.showMessageDialog(this, "Registered successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't register employee!", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_emp_addbtnActionPerformed

    private void employee_refreshtablebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_refreshtablebtnActionPerformed
        loadAllEmployees();
    }//GEN-LAST:event_employee_refreshtablebtnActionPerformed

    private void emp_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emp_tableMouseClicked

        empUpdate = EmployeeManager.getRow(Integer.parseInt(emp_table.getValueAt(emp_table.getSelectedRow(), 0).toString()));
        tp_emp_nic.setText(empUpdate.getNic());
        tf_emp_tp.setText(empUpdate.getTp());
        tf_fname.setText(empUpdate.getFname());
        tf_lname.setText(empUpdate.getLname());
        if (empUpdate.getGender().equals(Employee.GENDER_MALE)) {
            emp_male.setSelected(true);
        } else {
            emp_female.setSelected(true);
        }
        if (empUpdate.getStatus() == 1) {
            emp_active.setSelected(true);
        } else {
            emp_active.setSelected(false);
        }

        tf_no.setText(empUpdate.getNo());
        tf_str1.setText(empUpdate.getStreet1());
        tf_str2.setText(empUpdate.getStreet2());
        tf_city.setText(empUpdate.getCity());

        emp_role.setSelectedItem(empUpdate.getRole());
        ta_description.setText(empUpdate.getDescriptioin());
    }//GEN-LAST:event_emp_tableMouseClicked

    private void emp_resetbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_resetbtnActionPerformed
        clearFields();
    }//GEN-LAST:event_emp_resetbtnActionPerformed

    private void emp_editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_editbtnActionPerformed
        if (empUpdate == null) {
            return;
        }
        if (!empUpdate.getTp().equals(tf_emp_tp.getText())) {
            if (EmployeeManager.employeeExists(tf_emp_tp.getText())) {
                JOptionPane.showMessageDialog(this, "Employe already exists!", "", JOptionPane.QUESTION_MESSAGE);
                tf_emp_tp.grabFocus();
                tf_emp_tp.selectAll();
                return;
            }
        }
        if (validateData() && JOptionPane.showConfirmDialog(this, "Update employee details?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            empUpdate.setDescriptioin(ta_description.getText());
            empUpdate.setFname(tf_fname.getText());
            if (emp_male.isSelected()) {
                empUpdate.setGender(Employee.GENDER_MALE);
            } else {
                empUpdate.setGender(Employee.GENDER_FEMALE);
            }
            empUpdate.setLname(tf_lname.getText());
            empUpdate.setNic(tp_emp_nic.getText());
            empUpdate.setRole(emp_role.getSelectedItem().toString());
            if (emp_active.isSelected()) {
                empUpdate.setStatus(1);
            } else {
                empUpdate.setStatus(0);
            }
            empUpdate.setTp(tf_emp_tp.getText());

            empUpdate.setCity(tf_city.getText());
            empUpdate.setNo(tf_no.getText());
            empUpdate.setStreet1(tf_str1.getText());
            empUpdate.setStreet2(tf_str2.getText());

            if (EmployeeManager.updateRow(empUpdate)) {
                JOptionPane.showMessageDialog(this, "Updated successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Couldn't update employee!", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_emp_editbtnActionPerformed

    private void emp_activeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_activeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emp_activeActionPerformed

    private void emp_activetablebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emp_activetablebtnActionPerformed
        if (empUpdate == null) {
            JOptionPane.showMessageDialog(this, "Please select an employee first.", "No employee selected!", JOptionPane.QUESTION_MESSAGE);
        } else {
            if (empUpdate.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, "This employee is already activated !");
            } else if (JOptionPane.showConfirmDialog(this, "Activate employee?", "Confirm !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                setActivation(1);
            }
        }
    }//GEN-LAST:event_emp_activetablebtnActionPerformed

    private void tf_emp_tpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_emp_tpKeyTyped
        Helper.contactAdd(tf_emp_tp, evt);
    }//GEN-LAST:event_tf_emp_tpKeyTyped

    private void setActivation(int active) {
        empUpdate.setStatus(active);
        if (EmployeeManager.updateRow(empUpdate)) {
            JOptionPane.showMessageDialog(this, "Updated successfully.", "Done!", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Couldn't update employee.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateTp(String tp) {
        char[] tpArr = tp.toCharArray();
        for (char c : tpArr) {
            if (Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateData() {

        if (empty(tp_emp_nic) || !Helper.validateNic((tp_emp_nic.getText()))) {
            JOptionPane.showMessageDialog(this, "Please enter a valid nic.", "Invalid data!", JOptionPane.QUESTION_MESSAGE);
            tp_emp_nic.grabFocus();
            tp_emp_nic.selectAll();
            return false;
        }
        if (empty(tf_emp_tp) || !validateTp(tf_emp_tp.getText())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid telephone number.", "Invalid data!", JOptionPane.QUESTION_MESSAGE);
            tf_emp_tp.grabFocus();
            tf_emp_tp.selectAll();
            return false;
        }
        if (empty(tf_fname)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid first name.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_fname.grabFocus();
            return false;
        }
        if (empty(tf_lname)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid last name.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_lname.grabFocus();
            return false;
        }
        if (!(emp_male.isSelected() || emp_female.isSelected())) {
            JOptionPane.showMessageDialog(this, "Please select a gender.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            emp_male.grabFocus();
            return false;
        }
        if (empty(tf_no)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid address.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_no.grabFocus();
            return false;
        }
        if (empty(tf_str1)) {
            JOptionPane.showMessageDialog(this, "Please enter address.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_str1.grabFocus();
            return false;
        }
        if (empty(tf_str2)) {
            JOptionPane.showMessageDialog(this, "Please enter address.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_str2.grabFocus();
            return false;
        }
        if (empty(tf_city)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid last address.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            tf_city.grabFocus();
            return false;
        }
        if (emp_role.getSelectedItem().toString() == "") {
            JOptionPane.showMessageDialog(this, "Please select a valid role.", "Empty data!", JOptionPane.QUESTION_MESSAGE);
            emp_role.grabFocus();
            emp_role.setPopupVisible(true);
            return false;
        }
        return true;
    }

    private boolean empty(JTextComponent t) {
        String st = t.getText();
        if (st == null || st.trim().equals("")) {
            t.grabFocus();
            t.selectAll();
            return true;
        }
        return false;
    }

    public void clearFields() {
        tf_no.setText(null);
        tf_city.setText(null);
        tf_str1.setText(null);
        tf_str2.setText(null);

        ta_description.setText(null);

        tf_fname.setText(null);
        tf_lname.setText(null);
        tp_emp_nic.setText(null);
        tf_emp_tp.setText(null);

        buttonGroup1.clearSelection();
        emp_active.setSelected(false);
        tf_no.setText(null);
        empUpdate = null;
        tp_emp_nic.grabFocus();
        loadRoles();
        loadAllEmployees();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox emp_active;
    private javax.swing.JButton emp_activetablebtn;
    private javax.swing.JButton emp_addbtn;
    private javax.swing.JButton emp_deactivetablebtn;
    private javax.swing.JButton emp_editbtn;
    private javax.swing.JRadioButton emp_female;
    private javax.swing.JRadioButton emp_male;
    private javax.swing.JButton emp_resetbtn;
    private javax.swing.JComboBox<String> emp_role;
    private javax.swing.JTable emp_table;
    private javax.swing.JButton employee_refreshtablebtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ta_description;
    private javax.swing.JTextField tf_city;
    private javax.swing.JFormattedTextField tf_emp_tp;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_lname;
    private javax.swing.JTextField tf_no;
    private javax.swing.JTextField tf_str1;
    private javax.swing.JTextField tf_str2;
    private javax.swing.JTextField tp_emp_nic;
    // End of variables declaration//GEN-END:variables
}
