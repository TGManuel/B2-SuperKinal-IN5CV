/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.manueltejeda.dao.Conexion;
import org.manueltejeda.model.Cliente;
import org.manueltejeda.model.DetalleFactura;
import org.manueltejeda.model.Empleado;
import org.manueltejeda.model.Producto;
import org.manueltejeda.system.Main;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MenuFacturasController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TextField tfFacturaId,tfHora,tfTotal, tfFecha, tfBuscarFactura;
    @FXML
    ComboBox cmbClientes,cmbEmpleados, cmbProductos;
    @FXML
    TableView tblFacturas;
    @FXML
    TableColumn colFacturaId,colFecha,colHora,colCliente,colProducto,colEmpleado,colTotal;
    @FXML
    Button btnRegresar, btnGuardar, btnVaciar, btnBuscar; 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbClientes.setItems(listarClientes());
        cmbEmpleados.setItems(listarEmpleados());
        cmbProductos.setItems(listarProductos());
    }   
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        } else if(event.getSource() == btnGuardar){
            if(tfFacturaId.getText().isEmpty()){
                agregarFacturas();
                cargarDatos();
            } else {
                agregarDetalleFactura();
                cargarDatos();
                vaciarCampos();
            }
        } else if(event.getSource() == btnBuscar){
            tblFacturas.getItems().clear();
            if(tfBuscarFactura.getText().equals("")){
                cargarDatos();
            }else{
                tblFacturas.getItems().add(buscarFactura());
                colFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("facturaId"));
                colFecha.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Date>("fecha"));
                colHora.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Time>("hora"));
                colCliente.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("cliente"));
                colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("producto"));
                colEmpleado.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("empleado"));
                colTotal.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("total"));
            }
        } else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    public void vaciarCampos(){
        tfFacturaId.clear();
        tfFecha.clear();
        tfHora.clear();
        cmbClientes.getSelectionModel().clearSelection();
        cmbProductos.getSelectionModel().clearSelection();
        cmbEmpleados.getSelectionModel().clearSelection();
        tfTotal.clear();
    }
    
    public void cargarDatos(){
       tblFacturas.setItems(listarDetalleFacturas());
       colFacturaId.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("facturaId"));
       colFecha.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Date>("fecha"));
       colHora.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Time>("hora"));
       colCliente.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("cliente"));
       colProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("producto"));
       colEmpleado.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("empleado"));
       colTotal.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("total"));
    }
    
    public void cargarDatosDetalle(){
        DetalleFactura df = (DetalleFactura)tblFacturas.getSelectionModel().getSelectedItem();
        if(df != null){
            tfFacturaId.setText(Integer.toString(df.getFacturaId()));
            tfFecha.setText(df.getFecha().toString());
            tfHora.setText(df.getHora().toString());
            cmbClientes.getSelectionModel().select(obtenerIndexCliente());
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
            cmbEmpleados.getSelectionModel().select(obtenerIndexEmpleado());
            tfTotal.setText(Double.toString(df.getTotal()));
        }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        for(int i = 0 ; i <  cmbClientes.getItems().size() ; i++){
            String clienteCmb = cmbClientes.getItems().get(i).toString();
            String clienteTbl = ((DetalleFactura)tblFacturas.getSelectionModel().getSelectedItem()).getCliente();
            if(clienteCmb.equals(clienteTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexEmpleado(){
        int index = 0;
        for(int i = 0 ; i < cmbEmpleados.getItems().size() ; i++){
            String empleadoCmb = cmbEmpleados.getItems().get(i).toString();
            int empleadoTbl = ((DetalleFactura)tblFacturas.getSelectionModel().getSelectedItem()).getEmpleadoId();
            if(empleadoCmb.equals(empleadoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        for(int i = 0 ; i < cmbProductos.getItems().size() ; i++){
            String productoCmb = cmbProductos.getItems().get(i).toString();
            String productoTbl = ((DetalleFactura)tblFacturas.getSelectionModel().getSelectedItem()).getProducto();
            if(productoCmb.equals(productoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public ObservableList<DetalleFactura> listarDetalleFacturas(){
        ArrayList<DetalleFactura> detalleFacturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleFactura()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                double total = resultSet.getDouble("total");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                String producto = resultSet.getString("producto");
                
                detalleFacturas.add(new DetalleFactura(producto,facturaId,fecha,hora,total,cliente,empleado));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(detalleFacturas);
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId,nombre,apellido,telefono,direccion,nit));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
           
        return FXCollections.observableList(clientes);
    }
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                int cargoId = resultSet.getInt("cargoId");
                int encargadoId = resultSet.getInt("encargadoId");
                
                empleados.add(new Empleado(empleadoId,nombreEmpleado,apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargoId,encargadoId));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());

            }
        }
        
        return FXCollections.observableArrayList(empleados);
    }
    
    public ObservableList<Producto>listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidores");
                String categoriaProductos = resultSet.getString("Categoria");
                productos.add(new Producto(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoriaProductos));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableArrayList(productos);
    }
    
    public void agregarFacturas(){
        LocalTime horaActual = LocalTime.now();
        Time hora = java.sql.Time.valueOf(horaActual);
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFacturas(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(2, ((Empleado)cmbEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.setInt(3, ((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void agregarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleFactura(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setInt(2, ((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public DetalleFactura buscarFactura(){
        DetalleFactura detallefactura = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarDetalleFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfBuscarFactura.getText()));
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                double total = resultSet.getDouble("total");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                String producto = resultSet.getString("producto");
                
                detallefactura = (new DetalleFactura(producto,facturaId,fecha,hora,total,cliente,empleado));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
              System.out.println(e.getMessage());
            }
        }
        
        return detallefactura;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    
}
