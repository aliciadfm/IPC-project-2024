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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author alicia
 */
public class VisualizarGastoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Charge gasto = null;
    @FXML
    private Label tituloLabel;
    @FXML
    private Label categoriaLabel;
    @FXML
    private Label descripcionLabel;
    @FXML
    private ImageView imagenAvatar;
    @FXML
    private Label fechaLabel;

    private User user;
    private static Charge charge;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            user = Acount.getInstance().getLoggedUser();
            tituloLabel.setText(charge.getName());
            categoriaLabel.setText(charge.getCategory().getName());
            descripcionLabel.setText(charge.getDescription());
            fechaLabel.setText(charge.getDate().toString());
            imagenAvatar.setImage(charge.getImageScan());
        } catch (Exception e) {
        }
    }

    public Charge getCharge() {
        return charge;
    }

    public static void setCharge(Charge charge) {
        VisualizarGastoController.charge = charge;
    }

    @FXML
    private void cancelarVisualizarGasto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    private void pulsarEditarGasto(ActionEvent event) throws IOException {
        EditarGastoController.setCharge(getCharge());
        Parent root = FXMLLoader.load(getClass().getResource("EditarGasto.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void pulsarEliminarGasto(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setHeaderText("Eliminaras el gasto.");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Acount.getInstance().removeCharge(charge);
            } catch (AcountDAOException ex) {
                Logger.getLogger(VisualizarGastoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VisualizarGastoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
        } else {
        }
    }
}
