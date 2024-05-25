/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author unayd
 */
public class AñadirGastoController implements Initializable {

    @FXML
    private ComboBox<String> categoriaSelec;
    @FXML
    private TextField tituloText;
    @FXML
    private TextField descripcionText;
    @FXML
    private DatePicker fechaPicker;
    @FXML
    private TextField costeText;
    @FXML
    private TextField unidadesText;
    @FXML
    private ImageView imagenAvatar;

    ObservableList<String> listaCategorias;
    @FXML
    private Button botonAceptarAñadirGasto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaCategorias = FXCollections.observableArrayList();
        categoriaSelec.setItems(listaCategorias);
        try {
            for (int i = 0; i < Acount.getInstance().getUserCategories().size() - 1; i++) {
                listaCategorias.add(Acount.getInstance().getUserCategories().get(i).getName());
            }
        } catch (Exception e) {
        }
        costeText.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                    costeText.setText(oldValue);
                }
            }
        });

        unidadesText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                unidadesText.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        botonAceptarAñadirGasto.disableProperty().bind(
                Bindings.createBooleanBinding(()
                        -> tituloText.getText().isEmpty()
                || descripcionText.getText().isEmpty()
                || costeText.getText().isEmpty()
                || unidadesText.getText().isEmpty()
                || categoriaSelec.getSelectionModel().getSelectedIndex() == -1
                || fechaPicker.getValue() == null,
                        tituloText.textProperty(),
                        descripcionText.textProperty(),
                        costeText.textProperty(),
                        unidadesText.textProperty(),
                        categoriaSelec.getSelectionModel().selectedIndexProperty(),
                        fechaPicker.valueProperty()
                )
        );
    }

    @FXML
    private void cancelarAñadirGasto(ActionEvent event) throws IOException {
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
    private void aceptarAñadirGasto(ActionEvent event) throws Exception {
        double coste = Double.parseDouble(costeText.getText());
        int unidades = Integer.parseInt(unidadesText.getText());
        String categoria = categoriaSelec.getValue();
        Category categoria2 = buscarCategoria(categoria);
        Acount.getInstance().registerCharge(tituloText.getText(), descripcionText.getText(), coste, unidades, imagenAvatar.getImage(), fechaPicker.getValue(), categoria2);

        Parent root = FXMLLoader.load(getClass().getResource("VisualizarGastos.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    Image image;
    @FXML
    private void añadirImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen(.png,.jpg,*.jpeg)" ,
                ".png", ".jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                image = new javafx.scene.image.Image(selectedFile.toURI().toString());
                imagenAvatar.setImage(image);
            } catch (Exception e) {
            }
        }
    }

    String nombre;
    String descripcion;

    @FXML
    private void añadirCategoria(ActionEvent event) throws Exception {
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
                nombre = nombreDescripcion.getKey();
                descripcion = nombreDescripcion.getValue();
                System.out.println("Nombre: " + nombre);            
                System.out.println("Descripción: " + descripcion);  
                Acount.getInstance().registerCategory(nombre, descripcion);
                listaCategorias.add(nombre);
            } catch (IOException | AcountDAOException e) {
            }
        });
    }
}
