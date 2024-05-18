/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Acount;


/**
 * FXML Controller class
 *
 * @author unayd
 */
public class LoginFxmlController implements Initializable {
    @FXML
    private Button BotonAceptarLogin;
    @FXML
    private TextField userNick;
    @FXML
    private TextField userPass;
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AceptarLogin(ActionEvent event) throws Exception {
        if (Acount.getInstance().logInUserByCredentials(userNick.getText(), userPass.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Aquí puedes agregar un mensaje de error si la autenticación falla
            System.out.println("Error: credenciales inválidas");
        }
    }

    @FXML
    private void RegistarLogin(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SignUp_1.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
    
}
