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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author alicia
 */
public class EditarGastoController implements Initializable {
   /**
     * Initializes the controller class.
     */
    
    public static Charge charge = null;
    @FXML
    private TextField tituloNueva;
    @FXML
    private ComboBox<String> categoriaNueva;
    @FXML
    private TextArea descripcionNueva;
    @FXML
    private ImageView imagenNueva;
    @FXML
    private DatePicker fechaNueva;
    ObservableList<String> listaCategorias;
    
    private User user = null;
    @FXML
    private Button confrimar;
    @FXML
    private TextField costeNuevo;
    @FXML
    private TextField unidadesNueva;
    
        public Charge getCharge() {
        return charge;
    }

    public static void setCharge(Charge charge) {
        EditarGastoController.charge = charge;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenNueva.setImage(charge.getImageScan());
        listaCategorias = FXCollections.observableArrayList();
        categoriaNueva.setItems(listaCategorias);
        
        try {
            for (int i = 0; i < Acount.getInstance().getUserCategories().size() - 1; i++) {
                listaCategorias.add(Acount.getInstance().getUserCategories().get(i).getName());
            }
        } catch (Exception e) {
        }
        
        costeNuevo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                    costeNuevo.setText(oldValue);
                }
            }
        });

        unidadesNueva.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                unidadesNueva.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        confrimar.disableProperty().bind(
                Bindings.createBooleanBinding(()
                        -> tituloNueva.getText().isEmpty()
                || descripcionNueva.getText().isEmpty()
                || costeNuevo.getText().isEmpty()
                || unidadesNueva.getText().isEmpty()
                || categoriaNueva.getSelectionModel().getSelectedIndex() == -1
                || fechaNueva.getValue() == null,
                        tituloNueva.textProperty(),
                        descripcionNueva.textProperty(),
                        costeNuevo.textProperty(),
                        unidadesNueva.textProperty(),
                        categoriaNueva.getSelectionModel().selectedIndexProperty(),
                        fechaNueva.valueProperty()
                )
        );
    }    

    @FXML
    private void cancelarEditarGasto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void aceptarEditarGasto(ActionEvent event) throws IOException, Exception {
        
        Acount.getInstance().removeCharge(charge);
        double coste = Double.parseDouble(costeNuevo.getText());
        int unidades = Integer.parseInt(unidadesNueva.getText());
        String categoria = categoriaNueva.getValue();
        Category categoria2 = buscarCategoria(categoria);
        Acount.getInstance().registerCharge(tituloNueva.getText(), descripcionNueva.getText(), coste, unidades, imagenNueva.getImage(), fechaNueva.getValue(), categoria2);
            
        
        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
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
 
    
    Image image;
    @FXML
    private void cambiarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen(.png,.jpg,*.jpeg)",

                "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                image = new javafx.scene.image.Image(selectedFile.toURI().toString());
                imagenNueva.setImage(image);
            } catch (Exception e) {
            }
        }
    }
    
}
