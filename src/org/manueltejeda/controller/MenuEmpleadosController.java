/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.manueltejeda.model.Cargo;
import org.manueltejeda.model.Empleado;
import org.manueltejeda.system.Main;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class MenuEmpleadosController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colEmpleadoId, colNombreEmpleado, colApellidoEmpleado, colSueldo, colHoraEntrada,colHoraSalida,colCargoId,colEncargadoId;
    @FXML
    ComboBox cmbCargoId, cmbEncargado;
    @FXML
    Button btnGuardar, btnRegresar, btnVaciar;
    @FXML
    TextField tfEmpleadoId, tfNombreEmpleado, tfApellidoEmpleado, tfSueldo, tfHoraEntrada, tfHoraSalida;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfEmpleadoId.getText().equals("")){
                agregarEmpleado();
                cargarDatos();
            }else{
                editarEmpleado();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCargoId.setItems(listarCargo());
        cargarDatos();
    }    
    
    public void vaciarCampos(){
        tfEmpleadoId.clear();
        tfNombreEmpleado.clear();
        tfApellidoEmpleado.clear();
        tfSueldo.clear();
        tfHoraEntrada.clear();
        tfHoraSalida.clear();
        cmbCargoId.getSelectionModel().clearSelection();
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleados());
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaSalida"));
        colCargoId.setCellValueFactory(new PropertyValueFactory<Empleado, String>("cargo"));
        colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("encargadoId"));
        tblEmpleados.getSortOrder().add(colEmpleadoId);
    }
    
    public int obtenerIndexCargo(){
        int index = 0;
        for(int i = 0; i >= cmbCargoId.getItems().size() ; i++ ){
            String cargoCmb = cmbCargoId.getItems().get(i).toString();
            String cargoTbl = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getCargo();
            if(cargoCmb.equals(cargoTbl)){
                index = i;
                break;
            }
        }
        return index;
    }
        
    public void cargarDatosEditar(){       
        Empleado ts = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfEmpleadoId.setText(Integer.toString(ts.getEmpleadoId()));
            tfNombreEmpleado.setText(ts.getNombreEmpleado());
            tfApellidoEmpleado.setText(ts.getApellidoEmpleado());
            tfSueldo.setText(Double.toString(ts.getSueldo()));
            tfHoraEntrada.setText(ts.getHoraEntrada().toString());
            tfHoraSalida.setText(ts.getHoraSalida().toString());
            cmbCargoId.getSelectionModel().select(obtenerIndexCargo());
            
        }
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
                String cargo = resultSet.getString("cargo");
                int encargadoId = resultSet.getInt("encargadoId");
                
                empleados.add(new Empleado(empleadoId,nombreEmpleado,apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargo,encargadoId));
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
    
    public ObservableList<Cargo> listarCargo(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId, nombreCargo, descripcionCargo));
            }
            
        }catch(Exception e){
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
        return FXCollections.observableList(cargos);
    }
    
    public void agregarEmpleado(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        Time horaEntrada = null;
        Time  horaSalida = null;
        try{
            horaEntrada = new Time(sdf.parse(tfHoraEntrada.getText()).getTime());
            horaSalida = new Time(sdf.parse(tfHoraSalida.getText()).getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql ="call sp_AgregarEmpleado(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreEmpleado.getText());
            statement.setString(2, tfApellidoEmpleado.getText());
            statement.setDouble(3, Double.parseDouble(tfSueldo.getText()));
            statement.setTime(4, horaEntrada);
            statement.setTime(5, horaSalida);
            statement.setInt(6,((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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
    
    public void editarEmpleado(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        Time horaEntrada = null;
        Time  horaSalida = null;
        try{
            horaEntrada = new Time(sdf.parse(tfHoraEntrada.getText()).getTime());
            horaSalida = new Time(sdf.parse(tfHoraSalida.getText()).getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2, tfNombreEmpleado.getText());
            statement.setString(3, tfApellidoEmpleado.getText());
            statement.setDouble(4,Double.parseDouble(tfSueldo.getText()));
            statement.setTime(5, horaEntrada);
            statement.setTime(6, horaSalida);
            statement.setInt(7,((Cargo)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
