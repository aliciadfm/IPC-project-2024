/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.awt.ActiveEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author unayd
 */
public class SceneController {
    private Scene scene;
    private Stage stage;
    private Parent root;
    
    public void cambiarAñadirGasto(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("AñadirGasto.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarContenedorPrincipal(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarLogin(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarPerfikl(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarRegistrar(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("SignUp_1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarVisualizarGastos(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("VisualizarGasstos.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void cambiarVisualizarGasto(ActionEvent event)throws IOException{
        root = FXMLLoader.load(getClass().getResource("VisuaalizarGasto.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
