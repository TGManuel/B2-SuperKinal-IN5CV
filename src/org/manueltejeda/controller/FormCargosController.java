/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.manueltejeda.dao.Conexion;
import org.manueltejeda.dto.CargoDTO;
import org.manueltejeda.model.Cargo;
import org.manueltejeda.system.Main;
import org.manueltejeda.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormCargosController implements Initializable {
    private Main stage;
    private int op;
    
    @FXML
    TextField tfCargoId, tfNombre; 
    @FXML
    TextArea taDescripcion;
   
    @FXML
    Button btnGuardar, btnCancelar;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CargoDTO.getCargoDTO().getCargo() != null){
            cargarDatos(CargoDTO.getCargoDTO().getCargo());
        }
    }
        
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuCargosView();
            CargoDTO.getCargoDTO().getCargo();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                //if(tfNombre.getText().equals("") && tfApellido.getText())
                agregarCargo();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCargosView();
            }else if(op == 2){
                editarCargo();
                CargoDTO.getCargoDTO().setCargo(null);
                stage.menuCargosView();
            }
              
        }
    }
     
    public void cargarDatos(Cargo cargo){
        tfCargoId.setText(Integer.toString(cargo.getCargoId()));
        tfNombre.setText(cargo.getNombre());
        taDescripcion.setText(cargo.getDescripcion());
        
    }
    
    public void agregarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, taDescripcion.getText());
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
    
    public void editarCargo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargos(?,?,?)";
            statement  = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, taDescripcion.getText());
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
        // TODO
    }    

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public int getOp() {
        return op;
    }

    
    public void setOp(int op) {
        this.op = op;
    }
}
