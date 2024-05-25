/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author alicia
 */
public class VisualizarGastosController implements Initializable {

    @FXML
    private TableView<Charge> tableView;
    @FXML
    private TableColumn<Charge, String> nombreC;
    @FXML
    private TableColumn<Charge, String> categoriaC;
    @FXML
    private TableColumn<Charge, Integer> precioC;
    @FXML
    private TableColumn<Charge, Date> fechaC;
    @FXML
    private Button borrarGasto;
    @FXML
    private Button editarGasto;
    @FXML
    private ComboBox<String> selecCatBox;
    @FXML
    private Button botonEliminarCategoria;
    @FXML
    private Button botonVerGasto;

    private ObservableList<Charge> lista;
    private ObservableList<String> listaCategorias;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista = FXCollections.observableArrayList();
        listaCategorias = FXCollections.observableArrayList();
        tableView.setItems(lista);
        nombreC.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriaC.setCellValueFactory(cellData -> {
            Category category = cellData.getValue().getCategory();
            return new SimpleStringProperty(category != null ? category.getName() : "");
        });
        precioC.setCellValueFactory(new PropertyValueFactory<>("cost"));
        fechaC.setCellValueFactory(new PropertyValueFactory<>("date"));
        try {
            for (int i = 0; i < Acount.getInstance().getUserCategories().size(); i++) {
                listaCategorias.add(Acount.getInstance().getUserCategories().get(i).getName());
            }
        } catch (Exception e) {
        }
        selecCatBox.setItems(listaCategorias);
        borrarGasto.disableProperty().bind(Bindings.equal(tableView.getSelectionModel().selectedIndexProperty(), -1));
        editarGasto.disableProperty().bind(Bindings.equal(tableView.getSelectionModel().selectedIndexProperty(), -1));
        botonVerGasto.disableProperty().bind(Bindings.equal(tableView.getSelectionModel().selectedIndexProperty(), -1));
        botonEliminarCategoria.disableProperty().bind(Bindings.equal(selecCatBox.getSelectionModel().selectedIndexProperty(), -1));
        loadCharges();
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    try {
                        pulsarVerGasto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadCharges() {
        try {
            Acount acount = Acount.getInstance();
            lista.addAll(acount.getUserCharges());
        } catch (IOException | AcountDAOException e) {
        }
    }

    protected Charge getGastoSeleccionado() {
        return tableView.getFocusModel().getFocusedItem();
    }

    @FXML
    private void volverVisualizarGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    private Category buscarCategoria(String s) throws Exception {
        Category cat = null;
        for (int i = 0; i < Acount.getInstance().getUserCategories().size(); i++) {
            if (Acount.getInstance().getUserCategories().get(i).getName().equals(s)) {
                cat = Acount.getInstance().getUserCategories().get(i);
                return cat;
            }
        }
        return cat;
    }

    @FXML
    private void eliminarCategoria(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("ADVERTENCIA");
        alert.setContentText("La categoría será eliminada, al igual que los gastos asociados a ella. ¿Desea continuar?");
        ButtonType cancelButton = new ButtonType("Cancelar");
        alert.getButtonTypes().add(cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Category categoriaSeleccionada = buscarCategoria(selecCatBox.getValue());
            if (categoriaSeleccionada != null) {
                Acount.getInstance().removeCategory(categoriaSeleccionada);
                selecCatBox.getItems().remove(categoriaSeleccionada.getName());
                lista.clear();
                lista.addAll(Acount.getInstance().getUserCharges());
                tableView.refresh();
            }
        }
    }

    @FXML
    private void borrar(ActionEvent event) throws Exception {
        int index = tableView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Charge selectedCharge = tableView.getItems().get(index);
            Acount.getInstance().removeCharge(selectedCharge);
            lista.remove(index);
        }
    }

    @FXML
    private void editar(ActionEvent event) throws IOException {
        EditarGastoController.setCharge(getGastoSeleccionado());
        Parent root = FXMLLoader.load(getClass().getResource("EditarGasto.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void pulsarVerGasto() throws IOException {
        VisualizarGastoController.setCharge(getGastoSeleccionado());
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGasto.fxml"));
        Stage stage = (Stage) tableView.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void pulsarCerrarVentana(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación");
        alert.setContentText("¿Seguro que quieres continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void pulsarCerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
            Stage stage = (Stage) borrarGasto.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    @FXML
    private void pulsarAñadirGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AñadirGasto.fxml"));
        Stage stage = (Stage) borrarGasto.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
    private void pulsarEliminarCategoria(ActionEvent event) {
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

    @FXML
    private void inforApp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sobre la aplicación");
        alert.setContentText("Esta aplicación ha sido desarrollada por:\n"
                + "Alicia de Felipe Machancoses\n"
                + "Adolfo González Tamarit\n"
                + "Unay David Figueroa\n"
                + "https://github.com/aliciadfm/IPC-project-2024");
        alert.showAndWait();
    }
}
