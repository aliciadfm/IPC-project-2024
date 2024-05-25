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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author unayd
 */
public class ContenedorPrincipalController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private ImageView imagen;
    @FXML
    private Text bienvenido;
    private User user = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            imagen.setImage(Acount.getInstance().getLoggedUser().getImage());
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bienvenido.setText("Bienvenido " + user.getName() + " !!! :)");
    }

    @FXML
    private void pulsarAñadirGasto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AñadirGasto.fxml"));
        Stage stage = (Stage) bp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void pulsarVisualizarGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage) bp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @FXML
    private void pulsarPerfil(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Perfil.fxml"));
        Stage stage = (Stage) bp.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void pulsarCerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
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
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    private void pulsarCerrarVantana(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void infoApp(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sobre la aplicación");
        alert.setContentText("Esta aplicación ha sido desarrollada por:\n"
                + "Alicia de Felipe Machancoses\n"
                + "Adolfo González Tamarit\n"
                + "Unay David Figueroa\n"
                + "https://github.com/aliciadfm/IPC-project-2024");
        alert.showAndWait();
    }

    @FXML
    private void pulsarAñadirCategoria(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Crear nueva categoría");
        dialog.setHeaderText("Introduce los detalles de la nueva categoría");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField descripcionField = new TextField();
        descripcionField.setPromptText("Descripción");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(descripcionField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(nombreField.getText(), descripcionField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(nombreDescripcion -> {
            try {
                Acount.getInstance().registerCategory(nombreDescripcion.getKey(), nombreDescripcion.getValue());
            } catch (IOException | AcountDAOException e) {
            }
        });
    }

    @FXML
    private void pulsarEliminarCategoria(ActionEvent event){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Eliminar categoría");
        dialog.setHeaderText("Seleccione la categoría a eliminar");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        ObservableList<String> listaCategorias = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < Acount.getInstance().getUserCategories().size(); i++) {
                listaCategorias.add(Acount.getInstance().getUserCategories().get(i).getName());
            }
        } catch (IOException | AcountDAOException e) {
        }

        ComboBox<String> categoriaSelec = new ComboBox<>(listaCategorias);
        categoriaSelec.setItems(listaCategorias);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        categoriaSelec.setOnAction(e -> {
            if (categoriaSelec.getValue() != null) {
                okButton.setDisable(false);
            }
        });

        dialog.getDialogPane().setContent(categoriaSelec);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButtonType) {
            try {
                Acount.getInstance().removeCategory(AñadirGastoController.buscarCategoria(categoriaSelec.getSelectionModel().getSelectedItem()));
            } catch (AcountDAOException ex) {
                Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
