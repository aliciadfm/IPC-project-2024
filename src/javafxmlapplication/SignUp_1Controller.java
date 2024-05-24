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
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    @FXML
    private Label errorRegistrarse;
    @FXML
    private Button botonAceptar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        botonAceptar.disableProperty().bind(
                Bindings.createBooleanBinding(()
                        -> usuarioText.getText().isEmpty()
                        || nombreText.getText().isEmpty()
                        || contraseñaText.getText().isEmpty()
                        || contraseñaText2.getText().isEmpty()
                        || apellidoText.getText().isEmpty()
                        || correoText.getText().isEmpty(),
                        usuarioText.textProperty(),
                        nombreText.textProperty(),
                        contraseñaText.textProperty(),
                        contraseñaText2.textProperty(),
                        apellidoText.textProperty(),
                        correoText.textProperty()
                )
        );
    }    

    @FXML
    private void cancelarRegistrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void aceptarRegistar(ActionEvent event) throws Exception {
        if(!checkEmail(correoText.getText())){
            errorRegistrarse.setText("El formato del correo es invalido.");
        }else if(!checkPassword(contraseñaText.getText())){
            errorRegistrarse.setText("El formato de la contraseña es invalido.");
        }else if(!contraseñaText2.getText().equals(contraseñaText.getText())){
            errorRegistrarse.setText("Las contraseñas no coinciden.");
        } else{
            Acount.getInstance().registerUser(nombreText.getText(), apellidoText.getText(), correoText.getText(), usuarioText.getText(), contraseñaText.getText(), imagenAvatar.getImage(), LocalDate.now());
            Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
        String regex = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean checkPassword(String password){    
        if (password == null) { 
            return false; 
        } 
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    }

}
