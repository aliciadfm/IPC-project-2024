/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author unayd
 */
public class ContenedorPrincipalController implements Initializable {

    @FXML
    private BorderPane bp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void pulsarAñadirGasto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AñadirGasto.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void pulsarVisualizarGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void pulsarPerfil(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void pulsarCerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Cabecera");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Acount.getInstance().logOutUser();
            } catch (AcountDAOException ex) {
                Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
            Stage stage = (Stage) bp.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
        }
    }

    @FXML
    private void pulsarCerrarVantana(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Cabecera");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
        }
    }

    @FXML
    private void infoApp(ActionEvent event) {
    }

}
