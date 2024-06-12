/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.manueltejeda.dao.Conexion;
import org.manueltejeda.model.Producto;
import org.manueltejeda.model.Promocion;
import org.manueltejeda.system.Main;
import org.manueltejeda.utils.SuperKinalAlert;
/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblPromociones;
    @FXML
    TableColumn colPromocionId, colPrecio, colDescripcion, colFechaInicio, colFechaFinalizacion, colProducto;
    @FXML
    TextField tfPromocionId, tfPrecio;
    @FXML
    TextArea taDescripcion;
    @FXML
    DatePicker dpFechaInicio, dpFechaFinalizacion;
    @FXML
    ComboBox cmbProducto;
    @FXML
    Button btnRegresar, btnGuardar, btnVaciar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbProducto.setItems(listarProducto());
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            stage.menuPrincipalView();
        } else if (event.getSource() == btnGuardar) {
            if (tfPromocionId.getText().equals("")) {
                if (tfPromocionId.getText().isEmpty() && tfPrecio.getText().isEmpty() && cmbProducto.getSelectionModel().getSelectedItem() == null) {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                } else {
                    agregarPromocion();
                    cargarDatos();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);

                }
            } else {
                if (!taDescripcion.getText().equals("")) {
                    if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(106).get() == ButtonType.OK) {
                        editarPromocion();
                        cargarDatos();
                    }
                } else {
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                }
            }
        } else if (event.getSource() == btnVaciar) {
            vaciarCampos();
        }
    }

    public void cargarDatos() {
        tblPromociones.setItems(listarPromocion());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion, Integer>("promocionId"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Promocion, Double>("precioPromocion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion, String>("descripcionPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaInicio"));
        colFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Promocion, Date>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promocion, String>("producto"));
        tblPromociones.getSortOrder().add(colPromocionId);

    }

    public void cargarDatosEditar() {
        Promocion pr = (Promocion) tblPromociones.getSelectionModel().getSelectedItem();
        if (pr != null) {
            tfPromocionId.setText(Integer.toString(pr.getPromocionId()));
            taDescripcion.setText(pr.getDescripcionPromocion());
            tfPrecio.setText(Double.toString(pr.getPrecioPromocion()));
            cmbProducto.getSelectionModel().select(obtenerIndexPromocion());
            dpFechaInicio.setValue(pr.getFechaInicio().toLocalDate());
            dpFechaFinalizacion.setValue(pr.getFechaFinalizacion().toLocalDate());
        }
    }

    public int obtenerIndexPromocion() {
        int index = 0;

        if (!cmbProducto.getItems().isEmpty()) {
            for (int i = 0; i < cmbProducto.getItems().size(); i++) {
                String productoCmb = cmbProducto.getItems().get(i).toString();
                String PromocionesTbl = ((Promocion) tblPromociones.getSelectionModel().getSelectedItem()).getProducto();
                if (productoCmb.equals(PromocionesTbl)) {
                    index = i;
                    break;
                }
            }

        }
        return index;
    }

    public ObservableList<Promocion> listarPromocion() {
        ArrayList<Promocion> promociones = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarPromocion()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int PromocionId = resultSet.getInt("promocionId");
                Double precio = resultSet.getDouble("precioPromocion");
                String descripcion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinalizacion = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("producto");

                promociones.add(new Promocion(PromocionId, precio, descripcion, fechaInicio, fechaFinalizacion, producto));
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
        return FXCollections.observableList(promociones);
    }

    public void agregarPromocion() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromocion(?,?,?,?,?)";

            statement = conexion.prepareStatement(sql);
            statement.setDouble(1, Double.parseDouble(tfPrecio.getText()));
            if (taDescripcion.getText() == null) {
                statement.setString(2, "xxxx");
            } else {

                statement.setString(2, taDescripcion.getText());
            }
            statement.setDate(3, Date.valueOf(dpFechaInicio.getValue()));
            statement.setDate(4, Date.valueOf(dpFechaFinalizacion.getValue()));
            statement.setInt(5, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

    public void editarPromocion() {
        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromocion(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setDouble(2, Double.parseDouble(tfPrecio.getText()));
            if (taDescripcion.getText() == null) {
                statement.setString(3, null);
            } else {

                statement.setString(3, taDescripcion.getText());
            }
            statement.setDate(4, Date.valueOf(dpFechaInicio.getValue()));
            statement.setDate(5, Date.valueOf(dpFechaFinalizacion.getValue()));
            statement.setInt(6, ((Producto) cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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

    public ObservableList<Producto> listarProducto() {
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarProducto()";
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
        }
        return FXCollections.observableList(productos);
    }

    public void vaciarCampos() {
        tfPromocionId.clear();
        tfPrecio.clear();
        taDescripcion.clear();
        cmbProducto.getSelectionModel().clearSelection();
        dpFechaInicio.getEditor().clear();
        dpFechaFinalizacion.getEditor().clear();
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
   
       
    

