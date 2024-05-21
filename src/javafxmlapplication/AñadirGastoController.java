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
import javax.swing.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Category;

/**
 * FXML Controller class
 *
 * @author unayd
 */
public class AñadirGastoController implements Initializable {

    @FXML
    private ComboBox<Category> categoriaSelec;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelarAñadirGasto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void aceptarAñadirGasto(ActionEvent event) throws Exception {
        if(true){
            double coste = Double.parseDouble(costeText.getText());
            int unidades = Integer.parseInt(unidadesText.getText());
            Category categoria = categoriaSelec.getValue();
            Acount.getInstance().registerCharge(tituloText.getText(),descripcionText.getText(),coste,unidades,imagenAvatar.getImage(),fechaPicker.getValue(),categoria);

        }
        
        Parent root = FXMLLoader.load(getClass().getResource("ContenedorPrincipal.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Image añadirFoto(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de imagen", ImageIO.getReaderFileSuffixes()));
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try {
                return ImageIO.read(fileToOpen);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        return null;
    }   

    @FXML
    private void añadirImagen(ActionEvent event) {
    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
        
    }

}
