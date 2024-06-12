/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.controller;
    
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.manueltejeda.system.Main;

/**
 *
 * @author manuel
 */
public class MenuPrincipalController implements Initializable{
    
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientes, btnTicketSoporte, btnCargos, btnDistribuidores, btnCategoria, btnEmpleado, btnPromocion, btnCompra, btnFactura, btnProductos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    } 

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientes){
           stage.menuClientesView(); 
        }else if(event.getSource() == btnTicketSoporte){
           stage.menuTicketSoporteView(); 
        }else if(event.getSource() == btnCargos){
           stage.menuCargosView(); 
        }else if(event.getSource() == btnDistribuidores){
           stage.menuDistribuidorView(); 
        }else if(event.getSource() == btnCategoria){
           stage.menuCategoriaProductoView(); 
        }else if(event.getSource() == btnEmpleado){
           stage.menuEmpleadoView();
        }else if(event.getSource() == btnPromocion){
           stage.menuPromocionesView();
        }else if(event.getSource() == btnCompra){
           stage.menuComprasView();
        }else if(event.getSource() == btnFactura){
           stage.menuFacturasView();
        }else if(event.getSource() == btnProductos){
           stage.menuProductosView();
        }
        
    }
    
}
