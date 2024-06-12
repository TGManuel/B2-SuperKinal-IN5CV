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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.manueltejeda.dao.Conexion;
import org.manueltejeda.model.DetalleCompra;
import org.manueltejeda.model.Producto;
import org.manueltejeda.system.Main;
import org.manueltejeda.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MenuComprasController implements Initializable {

    @FXML
    ComboBox cmbProductos, cmbCompraId;
    @FXML
    Button btnGuardar, btnRegresar, btnVaciar;
    @FXML
    TextField tfTotal, tfCantidad;
    @FXML
    DatePicker dpFecha;
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId, colProducto, colFecha, colCantidad, colTotal;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCompraId.setItems(listarCompraIds());
        cmbProductos.setItems(listarProducto());
    }

    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        } else if (event.getSource() == btnGuardar) {
            if (cmbCompraId.getSelectionModel().getSelectedItem() == null) {
                if (dpFecha.getValue() == null && tfTotal.getText().isEmpty()) {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    cmbProductos.requestFocus();
                    return;
                } else {
                    agregarCompra();
                    cargarDatos();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                }
            } else {
                if (cmbCompraId.getSelectionModel().getSelectedItem() == null && cmbProductos.getSelectionModel().getSelectedItem() == null) {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    cmbProductos.requestFocus();
                    return;
                } else {
                    agregarDetalleCompra();
                    cargarDatos();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                }
            }
        }
    }

    public void cargarDatos() {
        tblCompras.setItems(listarCompra());
        colCompraId.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("compraId"));
        colProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("producto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Date>("fechaCompra"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidadCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("totalCompra"));
        tblCompras.getSortOrder().add(colCompraId);
    }

    public void cargarDatosEditar() {
        DetalleCompra DC = (DetalleCompra) tblCompras.getSelectionModel().getSelectedItem();
        if (DC != null) {
            cmbCompraId.getSelectionModel().select(obtenerIndexCompra());
            tfTotal.setText(Double.toString(DC.getTotalCompra()));
            tfCantidad.setText(Integer.toString(DC.getCantidadCompra()));
            cmbProductos.getSelectionModel().select(obtenerIndexProducto());
            dpFecha.setValue(DC.getFechaCompra().toLocalDate());
        }
    }

    public void agregarDetalleCompra() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCantidad.getText()));
            statement.setInt(2, ((Producto) cmbProductos.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3, (Integer) cmbCompraId.getSelectionModel().getSelectedItem());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public void agregarCompra() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(dpFecha.getValue()));
            statement.setDouble(2, Double.parseDouble(tfTotal.getText()));
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public ObservableList<DetalleCompra> listarCompra() {
        ArrayList<DetalleCompra> compra = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                int cantidadCompra = resultSet.getInt("cantidadCompra");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
                String producto = resultSet.getString("nombreProducto");

                compra.add(new DetalleCompra(compraId, cantidadCompra, fecha, total, producto));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
        return FXCollections.observableList(compra);
    }

    public int obtenerIndexProducto() {
        int index = 0;
        for (int i = 0; i < cmbProductos.getItems().size(); i++) {
            String productoCmb = cmbProductos.getItems().get(i).toString();
            String compraTbl = ((DetalleCompra) tblCompras.getSelectionModel().getSelectedItem()).getProducto();
            if (productoCmb.equals(compraTbl)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public int obtenerIndexCompra() {
        int index = 0;
        int compraTbl = ((DetalleCompra) tblCompras.getSelectionModel().getSelectedItem()).getCompraId();
        for (int i = 0; i < cmbCompraId.getItems().size(); i++) {
            int compraCmb = (int) cmbCompraId.getItems().get(i);
            if (compraCmb == compraTbl) {
                index = i;
                break;
            }
        }
        return index;
    }
    

    public ObservableList<Producto> listarProducto() {
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcionProducto");
                int cantidad = resultSet.getInt("cantidadStock");
                Double precioU = resultSet.getDouble("precioVentaUnitario");
                Double precioM = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagenProducto");
                String distribuidorId = resultSet.getString("distribuidor");
                String categoriaProductoId = resultSet.getString("categoriaProductos");

                productos.add(new Producto(productoId, nombre, descripcion, cantidad, precioU, precioM, precioCompra, imagen, distribuidorId, categoriaProductoId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
        return FXCollections.observableList(productos);
    }

    public ObservableList<Integer> listarCompraIds() {
        HashSet<Integer> compraIds = new HashSet<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int compraId = resultSet.getInt("compraId");
                compraIds.add(compraId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return FXCollections.observableList(new ArrayList<>(compraIds));
    }

    public void vaciarCampos() {
        cmbCompraId.getSelectionModel().clearSelection();
        tfTotal.clear();
        tfCantidad.clear();
        cmbProductos.getSelectionModel().clearSelection();
        dpFecha.getEditor().clear();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
     
    
}
