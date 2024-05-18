/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Acount;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author adolf
 */
public class SignUp_1Controller implements Initializable {

    @FXML
    private TextField nombreText;
    @FXML
    private TextField correoText;
    @FXML
    private PasswordField contraseñaText;
    @FXML
    private PasswordField contraseñaText2;
    @FXML
    private TextField usuarioText;
    @FXML
    private ImageView imagenAvatar;
    @FXML
    private TextField apellidoText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void CancelarRegistrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void AceptarRegistar(ActionEvent event) throws Exception {
        if(checkEmail(correoText.getText()) && checkPassword(contraseñaText.getText()) 
                && contraseñaText2.getText().equals(contraseñaText.getText()) &&
                !Acount.getInstance().logInUserByCredentials(usuarioText.getText(),contraseñaText.getText())
                ){
            Acount.getInstance().registerUser(nombreText.getText(), apellidoText.getText(), correoText.getText(), usuarioText.getText(), contraseñaText.getText(), imagenAvatar.getImage(), LocalDate.now());
            Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Error");
        }
    }
    
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }

}
