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
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Acount;
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
    private TableColumn<Charge, Category> categoriaC;
    @FXML
    private TableColumn<Charge, Integer> precioC;
    @FXML
    private TableColumn<Charge, Date> fechaC;

    /**
     * Initializes the controller class.
     */
    ObservableList<Charge> lista;
    ObservableList<String> listaCategorias;

    @FXML
    private Button borrarGasto;
    @FXML
    private Button editarGasto;
    @FXML
    private ComboBox<String> selecCatBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista = FXCollections.observableArrayList();
        listaCategorias = FXCollections.observableArrayList();
        tableView.setItems(lista);
        nombreC.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoriaC.setCellValueFactory(new PropertyValueFactory<>("category"));
        precioC.setCellValueFactory(new PropertyValueFactory<>("cost"));
        fechaC.setCellValueFactory(new PropertyValueFactory<>("date"));
        try {
            for (int i = 0; i < Acount.getInstance().getUserCategories().size() - 1; i++) {
                listaCategorias.add(Acount.getInstance().getUserCategories().get(i).getName());
            }
        } catch (Exception e) {
        }
        selecCatBox.setItems(listaCategorias);
        borrarGasto.disableProperty().bind(Bindings.equal(tableView.getSelectionModel().selectedIndexProperty(), -1));
        editarGasto.disableProperty().bind(Bindings.equal(tableView.getSelectionModel().selectedIndexProperty(), -1));

        loadCharges();
    }

    private void loadCharges() {
        try {
            Acount acount = Acount.getInstance();
            lista.addAll(acount.getUserCharges());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverVisualizarGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
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
        Parent root = FXMLLoader.load(getClass().getResource("EditarGasto.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
