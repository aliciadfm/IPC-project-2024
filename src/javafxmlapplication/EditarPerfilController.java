/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author adolf
 */
public class EditarPerfilController implements Initializable {

    @FXML
    private TextField areaCorreo;
    @FXML
    private PasswordField areaContraseña1;
    @FXML
    private TextField areaNombre;
    @FXML
    private PasswordField areaContraseña2;

    private boolean haCambiado = false;
    private boolean haIntentadoCambiar = false;
    private String ogName;
    private String ogApellido;
    private String ogEmail;
    private String ogContraseña;
    private Image ogImagen;
    private Image newImage;
    private User user = null;
    @FXML
    private Button botonConfirmar;
    @FXML
    private TextField areaApellido;
    @FXML
    private ImageView fotoPerfil;

    @FXML
    private ImageView cache;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fotoPerfil.setImage(user.getImage());
        areaCorreo.setText(user.getEmail());
        areaNombre.setText(user.getName());
        areaApellido.setText(user.getSurname());
        areaContraseña1.setText(user.getPassword());
        areaContraseña2.setText(user.getPassword());
        
        ogName = user.getName();
        ogApellido = user.getSurname();
        ogEmail = user.getEmail();
        ogContraseña = user.getPassword();
        fotoPerfil.setImage(user.getImage());
        ogImagen = user.getImage();
        botonConfirmar.disableProperty().bind(
                Bindings.createBooleanBinding(()
                        -> areaCorreo.getText().isEmpty()
                && areaNombre.getText().isEmpty()
                && areaApellido.getText().isEmpty()
                && (areaContraseña1.getText().isEmpty() || areaContraseña2.getText().isEmpty())
                && cache.getImage() == null,
                        areaCorreo.textProperty(),
                        areaNombre.textProperty(),
                        areaApellido.textProperty(),
                        areaContraseña1.textProperty(),
                        areaContraseña2.textProperty(),
                        cache.imageProperty()
                ));
    }

    @FXML
    private void cancelarEditarPerfil(ActionEvent event) throws IOException {
        if (haCambiado) {
            user.setName(ogName);
            user.setSurname(ogApellido);
            user.setEmail(ogEmail);
            user.setPassword(ogContraseña);
            user.setImage(cache.getImage());
        }
        Parent root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void aceptarEditarPErfil(ActionEvent event) throws IOException {
        haIntentadoCambiar = false;
        
        if (!areaNombre.getText().equals("")) {
            user.setName(areaNombre.getText());
            haCambiado = true;
        }
        if (!areaApellido.getText().equals("")) {
            user.setSurname(areaApellido.getText());
            haCambiado = true;
        }
        if (!areaCorreo.getText().equals("")) {
            if (SignUp_1Controller.checkEmail(areaCorreo.getText())) {
                user.setEmail(areaCorreo.getText());
                haCambiado = true;
            } else {
                haIntentadoCambiar = true;
                user.setName(ogName);
                user.setSurname(ogApellido);
                user.setEmail(ogEmail);
                user.setPassword(ogContraseña);
                user.setImage(cache.getImage());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Error al editar el perfil");
                alert.setContentText("El email no es valido.");
                alert.showAndWait();
            }
        }
        if (!areaContraseña1.getText().equals("")) {
            if (!areaContraseña2.getText().equals("")) {
                if (SignUp_1Controller.checkPassword(areaContraseña1.getText())) {
                    if (areaContraseña1.getText().equals(areaContraseña2.getText())) {
                        user.setPassword(areaContraseña1.getText());
                        haCambiado = true;
                    } else {
                        haIntentadoCambiar = true;
                        user.setName(ogName);
                        user.setSurname(ogApellido);
                        user.setEmail(ogEmail);
                        user.setPassword(ogContraseña);
                        user.setImage(cache.getImage());
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Error al editar el perfil");
                        alert.setContentText("Las contraseñas no coniciden");
                        alert.showAndWait();
                    }
                } else {
                    haIntentadoCambiar = true;
                    user.setName(ogName);
                    user.setSurname(ogApellido);
                    user.setEmail(ogEmail);
                    user.setPassword(ogContraseña);
                    user.setImage(cache.getImage());
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error al editar el perfil");
                    alert.setContentText("Las contraseña no tiene un formato valido");
                    alert.showAndWait();
                }
            }
        }
        if (haCambiado && !haIntentadoCambiar) {
            Parent root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else if (haIntentadoCambiar) {

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("No ha habido ningún cambio.");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    private void pulsarEditarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen(*.png,*.jpg,*.jpeg)",
                "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                newImage = new Image(selectedFile.toURI().toString());
            } catch (Exception e) {
            }
            haCambiado = true;
            cache.setImage(ogImagen);
            fotoPerfil.setImage(newImage);
            user.setImage(newImage);
        }
    }

}