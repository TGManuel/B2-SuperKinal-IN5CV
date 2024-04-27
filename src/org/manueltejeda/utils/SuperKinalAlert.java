/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author informatica
 */
public class SuperKinalAlert {
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
        
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){// Campos Pendientes
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos pendientes");
            alert.setContentText("bgabenfbksadbhhbvksdv");
            alert.showAndWait();
        } else if(code == 401){// confirmacion de registro
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("confirmacion de registro");
            alert.setHeaderText("confirmacion de registro");
            alert.setContentText("El registro se a creado con exito");
            alert.showAndWait();
        }
        
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action = null;
        if(code == 405){// alerta de confirmacion para eliminar 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminacion de registro");
            alert.setHeaderText("Eliminacion de registro");
            alert.setContentText("bgabenfbksadbhhbvksdv");
            action = alert.showAndWait(); 
        } else if(code == 106){// alerta de confirmacion para editar 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("edicion de registro");
            alert.setHeaderText("edicion de registro");
            alert.setContentText("bgabenfbksadbhhbvksdv");
            action = alert.showAndWait(); 
        }
        
        return action;
    }
}
