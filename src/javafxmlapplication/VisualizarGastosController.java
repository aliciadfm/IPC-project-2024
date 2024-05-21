/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    ObservableList<Charge> lista = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaC.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        precioC.setCellValueFactory(new PropertyValueFactory<>("precio"));
        fechaC.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tableView.setItems(lista);
    }    

    @FXML
    private void volverVisualizarGastos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void eliminarCategoria(ActionEvent event)  throws Exception {
        
    }
    
}
