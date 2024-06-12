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
import org.manueltejeda.controller.FormCargosController;
import org.manueltejeda.controller.FormCategoriaProductosController;
import org.manueltejeda.controller.FormClientesController;
import org.manueltejeda.controller.FormDistribuidoresController;
import org.manueltejeda.controller.FormProductosController;
import org.manueltejeda.controller.MenuCargosController;
import org.manueltejeda.controller.MenuCategoriaProductoController;
import org.manueltejeda.controller.MenuClientesController;
import org.manueltejeda.controller.MenuComprasController;
import org.manueltejeda.controller.MenuDistribuidorController;
import org.manueltejeda.controller.MenuEmpleadosController;
import org.manueltejeda.controller.MenuFacturasController;
import org.manueltejeda.controller.MenuPrincipalController;
import org.manueltejeda.controller.MenuProductosController;
import org.manueltejeda.controller.MenuPromocionesController;
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
    
    public void formCargosView(int op){
        try{
            FormCargosController formCargosView = (FormCargosController)switchScene("FormCargosView.fxml", 400, 500);
            formCargosView.setOp(op);
            formCargosView.setStage(this);           
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidoresView(int op){
        try{
            FormDistribuidoresController formDistribuidoresView = (FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml", 500, 700);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);           
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaProductoView(int op){
        try{
            FormCategoriaProductosController formCategoriaProductoView = (FormCategoriaProductosController)switchScene("FormCategoriaProductosView.fxml", 400, 500);
            formCategoriaProductoView.setOp(op);
            formCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formProductosView(int op){
        try{
            FormProductosController formProductosView = (FormProductosController)switchScene("FormProductosView.fxml", 500, 700);
            formProductosView.setOp(op);
            formProductosView.setStage(this);
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
    
     public void menuEmpleadoView(){
        try{
            MenuEmpleadosController menuEmpeladosController = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 750);
            menuEmpeladosController.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuPromocionesView(){
        try{
            MenuPromocionesController menuPromocionesView = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200, 750);
            menuPromocionesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductosView = (MenuProductosController)switchScene("MenuProductosView.fxml", 1200,750);
            menuProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void menuComprasView(){
        try{
            MenuComprasController menuComprasView = (MenuComprasController)switchScene("MenuComprasView.fxml", 1200, 750);
            menuComprasView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuFacturasView(){
        try{
            MenuFacturasController menuFacturasView = (MenuFacturasController)switchScene("MenuFacturasView.fxml", 1200, 750);
            menuFacturasView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView(){
        try{
            MenuCargosController menuCargosView = (MenuCargosController)switchScene("MenuCargosView.fxml", 1200, 750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidorView(){
        try{
            MenuDistribuidorController menuDistribuidorView = (MenuDistribuidorController)switchScene("MenuDistribuidorView.fxml", 1200, 750);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaProductoView(){
        try{
            MenuCategoriaProductoController menuCategoriaProductoView = (MenuCategoriaProductoController)switchScene("MenuCategoriaProductoView.fxml",1200, 750);
            menuCategoriaProductoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    } 
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
