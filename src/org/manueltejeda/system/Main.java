/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.manueltejeda.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.manueltejeda.controller.FormClientesController;
import org.manueltejeda.controller.MenuClientesController;
import org.manueltejeda.controller.MenuPrincipalController;
import org.manueltejeda.controller.MenuTicketSoporteController;

/**
 *
 * @author manuel
 */
public class Main extends Application {
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/manueltejeda/view/";
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("superKinal");
        menuPrincipalView();
        stage.show();
    }
    
    public Initializable switchScene (String fxmlName, int Width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new  FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        scene = new Scene((AnchorPane)loader.load(file), Width,height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("FondoMenuPrincipal.fxml", 950,700);
            menuPrincipalView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesView.fxml", 1200, 750);
            menuClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formClientesView(int op){
        try{
            FormClientesController formClientesView = (FormClientesController)switchScene("FormClientesView.fxml", 500, 700);
            formClientesView.setOp(op);
            formClientesView.setStage(this);           
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml", 1200, 750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
