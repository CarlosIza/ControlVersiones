/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos Iza
 */
public class autos extends javax.swing.JFrame {

    /**
     * Creates new form autos
     */
    ArrayList listaModelo;
    ArrayList listaMarca = new ArrayList();
    DefaultTableModel modelo;

    public autos() {
        initComponents();
        bloquear();
        bloquearbotones();

        cargarMarca();
        cargarTablaAutosPlaca("");
        tbautos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //listlselectionlistener permite cargar un metodo abstracto 
            //overrade controlar la cobre carga de metodos
            //quitar los espacios en blanco  --trim() utillizar con lso varchar
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tbautos.getSelectedRow() != -1) {
                    bloquearbotonesModificar();
                    desbloquearModificar();

                    int fila = tbautos.getSelectedRow();
                    txtAutoPlaca.setText(tbautos.getValueAt(fila, 0).toString().trim());
                    cbMarca.setSelectedItem(tbautos.getValueAt(fila, 1).toString().trim());
                    cbModelo.setSelectedItem(tbautos.getValueAt(fila, 2).toString().trim());
                    spnAnio.setValue(Integer.parseInt(tbautos.getValueAt(fila, 3).toString().trim()));
                    cbColor.setSelectedItem(tbautos.getValueAt(fila, 4).toString().trim());
                    spnCapacidad.setValue(Integer.parseInt(tbautos.getValueAt(fila, 5).toString().trim()));
                    txtObservacion.setText(tbautos.getValueAt(fila, 6).toString().trim());
                }
            }
        });
    }

    public void desbloquearCamposNuevo() {
        txtAutoPlaca.setEnabled(true);
        cbMarca.setEnabled(true);
        cbColor.setEnabled(true);
        cbModelo.setEnabled(true);
        spnAnio.setEnabled(true);
        spnCapacidad.setEnabled(true);
        txtObservacion.setEnabled(true);

    }

    public void bloquear() {
        txtAutoPlaca.setEnabled(false);
        cbMarca.setEnabled(false);
        cbColor.setEnabled(false);
        cbModelo.setEnabled(false);
        spnAnio.setEnabled(false);
        spnCapacidad.setEnabled(false);
        txtObservacion.setEnabled(false);

    }

    public void desbloquearModificar() {
        txtAutoPlaca.setEnabled(false);
        cbMarca.setEnabled(true);
        cbColor.setEnabled(true);
        cbModelo.setEnabled(true);
        spnAnio.setEnabled(true);
        spnCapacidad.setEnabled(true);
        txtObservacion.setEnabled(true);

    }

    public void desbloquear() {
        txtAutoPlaca.setEnabled(true);
        cbMarca.setEnabled(true);
        cbColor.setEnabled(true);
        cbModelo.setEnabled(true);
        spnAnio.setEnabled(true);
        spnCapacidad.setEnabled(true);
        txtObservacion.setEnabled(true);

    }

    public void bloquearbotonesModificar() {
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnSalir.setEnabled(true);

    }

    public void bloquearbotonesNuevo() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void bloquearbotones() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }

    public void desbloquearbotonNuevo() {
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnSalir.setEnabled(true);

    }
// cargar datos al combo box

    public void cargarMarca() {
        try {
            listaMarca.clear();
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            sql = "select * from marca";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                listaMarca.add(rs.getString("MAR_CODIGO"));
//                String id = rs.getString("MAR_CODIGO");
                String marca = rs.getString("MAR_NOMBRE");
                cbMarca.addItem(marca);
            }

        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CargarModelos() {
        try {
            listaModelo =   new ArrayList();
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            sql = "select * from modelos";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                listaModelo.add(rs.getString("MOD_NOMBRE"));
                String idMarca = rs.getString("MAR_CODIGO");
//                String idModelo = rs.getString("MOD_CODIGO");
                String nomModelo = rs.getString("MOD_NOMBRE");
                int num = cbMarca.getSelectedIndex()-1;
                System.out.println("numero "+num);               
                    if (idMarca.equals(listaMarca.get(num).toString().trim())) {
                        cbModelo.addItem(nomModelo);
                    }
                

            }

        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarModelosSQL() {
        try {
            conexion cn = new conexion();
            Connection cc = cn.conectar();
            String sql = "";
            int item = cbMarca.getSelectedIndex();
            sql = "select * from modelos where MAR_CODIGO = '" + item + "' ";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cbModelo.addItem(rs.getString("MOD_CODIGO" + "MOD_NOMBRE"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void limpiar() {
        txtAutoPlaca.setText("");
        //cbMarca.setSelectedIndex(0);
        //cbModelo.setSelectedIndex(0);
        spnAnio.setValue(0);
        cbColor.setSelectedIndex(0);
       spnCapacidad.setValue(0);
        txtObservacion.setText("");
    }

    public void cargarTablaAutosPlaca(String placa) {
        try {
            String[] titulos = {"PLACA", "MARCA", "MODELO", "AÑO", "COLOR", "CAPACIDAD", "OBSERVACION"};
            String[] registros = new String[7];
            modelo = new DefaultTableModel(null, titulos);
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select autos.AUT_PLACA, "
                    + "autos.MOD_CODIGO, autos.AUT_ANIO, "
                    + "autos.AUT_COLOR, autos.AUT_CAPACIDAD, "
                    + "autos.AUT_OBSERVACION, modelos.MOD_NOMBRE, "
                    + "marca.MAR_NOMBRE from autos,modelos,marca where autos.MOD_CODIGO = modelos.MOD_CODIGO and marca.MAR_CODIGO = modelos.MAR_CODIGO and autos.AUT_PLACA LIKE'%" + placa + "%'";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("MAR_NOMBRE");
                registros[2] = rs.getString("MOD_NOMBRE");
                registros[3] = rs.getString("AUT_ANIO");
                registros[4] = rs.getString("AUT_COLOR");
                registros[5] = rs.getString("AUT_CAPACIDAD");
                registros[6] = rs.getString("AUT_OBSERVACION");
                modelo.addRow(registros);
            }
            tbautos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarTablaAutos() {
        try {
            String[] titulos = {"PLACA", "MARCA", "MODELO", "AÑO", "COLOR", "CAPACIDAD", "OBSERVACION"};
            String[] registros = new String[7];
            modelo = new DefaultTableModel(null, titulos);
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select autos.AUT_PLACA, autos.MOD_CODIGO, autos.AUT_ANIO, autos.AUT_COLOR, autos.AUT_CAPACIDAD, autos.AUT_OBSERVACION, modelos.MOD_NOMBRE, marca.MAR_NOMBRE from autos,modelos,marca where autos.MOD_CODIGO = modelos.MOD_CODIGO and marca.MAR_CODIGO = modelos.MAR_CODIGO";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("AUT_PLACA");
                registros[1] = rs.getString("MAR_NOMBRE");
                registros[2] = rs.getString("MOD_NOMBRE");
                registros[3] = rs.getString("AUT_ANIO");
                registros[4] = rs.getString("AUT_COLOR");
                registros[5] = rs.getString("AUT_CAPACIDAD");
                registros[6] = rs.getString("AUT_OBSERVACION");
                modelo.addRow(registros);
            }
            tbautos.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardar() {
        try {
            if (txtAutoPlaca.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese placa");
                txtAutoPlaca.requestFocus();
            } else if (cbMarca.getSelectedItem().equals("seleccione..")) {
                JOptionPane.showMessageDialog(null, "Escoja la marca");
                cbMarca.requestFocus();
            } else if (cbModelo.getSelectedItem().equals("seleccione..")) {
                JOptionPane.showMessageDialog(null, "Escoja la modelo");
                cbModelo.requestFocus();
            } else if (Integer.valueOf(spnAnio.getValue().toString()) < 1960 || Integer.valueOf(spnAnio.getValue().toString()) > 2019) {
                JOptionPane.showMessageDialog(null, "Ingrese al año");
                spnAnio.requestFocus();
            } else if (cbColor.getSelectedItem().equals("seleccione..")) {
                JOptionPane.showMessageDialog(null, "Escoja la color");
                cbColor.requestFocus();
            } else if (Integer.valueOf(spnCapacidad.getValue().toString()) < 6 || Integer.valueOf(spnCapacidad.getValue().toString()) > 9) {
                JOptionPane.showMessageDialog(null, "Ingrese la capacidad entre 6 y 9");
                spnCapacidad.requestFocus();
            } else {
                String AUT_PLACA, MOD_CODIGO = "", AUT_COLOR, AUT_OBSERVACION;
                Integer AUT_ANIO, AUT_CAPACIDAD;
                conexion cc = new conexion();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "insert into autos(AUT_PLACA,MOD_CODIGO,AUT_ANIO,AUT_COLOR,AUT_CAPACIDAD,AUT_OBSERVACION,AUT_ESTADO)values(?,?,?,?,?,?,?)";
                PreparedStatement psd = cn.prepareStatement(sql);
                AUT_PLACA = txtAutoPlaca.getText();
                String valorModelo = cbModelo.getSelectedItem().toString();
                for (int i = 0; i < listaModelo.size(); i++) {
                    if (valorModelo.equals(listaModelo.get(i))) {
                        MOD_CODIGO = Integer.toString(i+1);
                    }
                }
                AUT_COLOR = cbColor.getSelectedItem().toString();
                AUT_OBSERVACION = txtObservacion.getText();
                AUT_ANIO = Integer.valueOf(spnAnio.getValue().toString());
                AUT_CAPACIDAD = Integer.valueOf(spnCapacidad.getValue().toString());
                

                psd.setString(1, AUT_PLACA);
                psd.setString(2, MOD_CODIGO);
                psd.setInt(3, AUT_ANIO);
                psd.setString(4, AUT_COLOR);
                psd.setInt(5, AUT_CAPACIDAD);
                

                if (AUT_OBSERVACION.isEmpty()) {
                    String vacio = "";
                    psd.setString(6, vacio);
                } else {
                    psd.setString(6, AUT_OBSERVACION);
                }
                psd.setInt(7, 1);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "datos ingresados");
                
                    
                    limpiar();
                    bloquearbotonesNuevo();
                    desbloquearCamposNuevo();
                   cargarTablaAutos();
                   
                    
                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error: Datos no ingresados" + ex);
        }

    }

    //metodos de control
    public void tamañoCaracteres(KeyEvent e) {
        String cadena = txtAutoPlaca.getText();
        if (cadena.length() > 6) {
            e.consume();
        }
    }

    public void soloMayusculas(KeyEvent e) {
        char letra = e.getKeyChar();
        if (Character.isLowerCase(letra)) {
            String cad = ("" + letra).toUpperCase();
            letra = cad.charAt(0);
            e.setKeyChar(letra);
        }

    }
    public void eliminar(){
        if (JOptionPane.showConfirmDialog(new JInternalFrame(), "Esta seguro de eliminar el registro","Borar Registro",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            
        
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "delete from autos where AUT_PLACA = '"+txtAutoPlaca.getText()+"'";
            PreparedStatement psd = cn.prepareStatement(sql);
           int b= psd.executeUpdate();
            if (b != -1) {
                JOptionPane.showMessageDialog(null,"se elimino correctamente");
                
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    public void modificarAuto(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        String mod = cbModelo.getSelectedItem().toString();
        String medCodigo="";
        for (int i = 0; i < listaModelo.size(); i++) {
            if (mod.equals(listaModelo.get(i))) {
                medCodigo = Integer.toString(i+1);
            }
        }
        sql = "update autos set MOD_CODIGO ='"+ medCodigo.toString().trim()+"',AUT_ANIO='"+spnAnio.getValue().toString()+"',AUT_COLOR ='"+cbColor.getSelectedItem().toString()+"',AUT_CAPACIDAD='"+spnCapacidad.getValue().toString()+"',AUT_OBSERVACION='"+txtObservacion.getText().toString()+"'  where AUT_PLACA = '"+txtAutoPlaca.getText()+"'";
       
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int a=psd.executeUpdate();
            if (a>0) {
                JOptionPane.showMessageDialog(null, "actulizacion correcta");
                cargarTablaAutosPlaca("");
            }   
        } catch (SQLException ex) {
            Logger.getLogger(autos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    public void soloNumerosSpinners() {
        String spinner = spnAnio.getValue().toString();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtAutoPlaca = new javax.swing.JTextField();
        cbMarca = new javax.swing.JComboBox<>();
        cbModelo = new javax.swing.JComboBox<>();
        spnAnio = new javax.swing.JSpinner();
        cbColor = new javax.swing.JComboBox<>();
        spnCapacidad = new javax.swing.JSpinner();
        txtObservacion = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbautos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("PLACA");

        jLabel2.setText("MARCA");

        jLabel3.setText("MODELO");

        jLabel4.setText("AÑO");

        jLabel5.setText("COLOR");

        jLabel6.setText("CAPACIDAD");

        jLabel7.setText("OBSERVACION");

        txtAutoPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAutoPlacaActionPerformed(evt);
            }
        });
        txtAutoPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAutoPlacaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAutoPlacaKeyTyped(evt);
            }
        });

        cbMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "seleccione.." }));
        cbMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMarcaItemStateChanged(evt);
            }
        });
        cbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMarcaActionPerformed(evt);
            }
        });

        cbModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "seleccione.." }));
        cbModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbModeloActionPerformed(evt);
            }
        });

        spnAnio.setModel(new javax.swing.SpinnerNumberModel(0, 0, 2019, 1));

        cbColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "seleccione..", "ROJO", "AZUL", "BEIGE" }));

        spnCapacidad.setModel(new javax.swing.SpinnerNumberModel(6, 6, 10, 1));

        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtObservacion)
                    .addComponent(spnAnio)
                    .addComponent(spnCapacidad)
                    .addComponent(txtAutoPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAutoPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(spnAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spnCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Calcelar");

        btnSalir.setText("Salir");

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addGap(4, 4, 4))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbautos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tbautos);

        jLabel8.setText("Buscar");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtbuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAutoPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAutoPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAutoPlacaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        desbloquear();
        desbloquearbotonNuevo();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtAutoPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutoPlacaKeyTyped
        soloMayusculas(evt);
        tamañoCaracteres(evt);

    }//GEN-LAST:event_txtAutoPlacaKeyTyped

    private void cbMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMarcaItemStateChanged

    }//GEN-LAST:event_cbMarcaItemStateChanged

    private void cbModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbModeloActionPerformed


    }//GEN-LAST:event_cbModeloActionPerformed

    private void cbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMarcaActionPerformed

        cbModelo.removeAllItems();
        CargarModelos();
    }//GEN-LAST:event_cbMarcaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
        cargarTablaAutos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        soloMayusculas(evt);
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        cargarTablaAutosPlaca(txtbuscar.getText());
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyTyped
        soloMayusculas(evt);
    }//GEN-LAST:event_txtbuscarKeyTyped

    private void txtAutoPlacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAutoPlacaKeyReleased
        btnNuevo.setEnabled(false);
    }//GEN-LAST:event_txtAutoPlacaKeyReleased

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
modificarAuto();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(autos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new autos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbColor;
    private javax.swing.JComboBox<String> cbMarca;
    private javax.swing.JComboBox<String> cbModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JSpinner spnAnio;
    private javax.swing.JSpinner spnCapacidad;
    private javax.swing.JTable tbautos;
    private javax.swing.JTextField txtAutoPlaca;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
