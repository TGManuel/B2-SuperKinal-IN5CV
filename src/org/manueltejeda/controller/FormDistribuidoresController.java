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
import javafx.scene.control.TextField;
import org.manueltejeda.dao.Conexion;
import org.manueltejeda.dto.DistribuidorDTO;
import org.manueltejeda.model.Distribuidor;
import org.manueltejeda.system.Main;
import org.manueltejeda.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class FormDistribuidoresController implements Initializable {
    private Main stage;
    private int op;
    
    @FXML
    TextField tfDistribuidorId, tfNombre, tfDireccion, tfTelefono, tfNit, tfWeb;
   
    @FXML
    Button btnGuardar, btnCancelar;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuDistribuidorView();
            DistribuidorDTO.getDistribuidorDTO().getDistribuidor();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                //if(tfNombre.getText().equals("") && tfApellido.getText())
                agregarDistribuidor();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuDistribuidorView();
            }else if(op == 2){
                editarDistribuidor();
                DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                stage.menuDistribuidorView();
            }
              
        }
    }
    
    public void cargarDatos( Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombre.setText(distribuidor.getNombreDistribuidor());
        tfDireccion.setText(distribuidor.getDireccionDistribuidor());
        tfTelefono.setText(distribuidor.getTelefonoDistribuidor());
        tfNit.setText(distribuidor.getNitDistribuidor());
        tfWeb.setText(distribuidor.getWeb());
    }
    
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombre.getText());
            statement.setString(2, tfDireccion.getText());
            statement.setString(3, tfTelefono.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfWeb.getText());
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
    
    public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?,?,?,?,?,?)";
            statement  = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombre.getText());
            statement.setString(3, tfDireccion.getText());
            statement.setString(4, tfTelefono.getText());
            statement.setString(5, tfNit.getText());
            statement.setString(6, tfWeb.getText());
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
