package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class to add parts that are made in house
 */
public class AddPartInHouse implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button AddPartIhCancBtn;

    @FXML
    private TextField AddPartIhIdTxt;

    @FXML
    private RadioButton AddPartIhIhRdBtn;

    @FXML
    private TextField AddPartIhInvTxt;

    @FXML
    private TextField AddPartIhMachIdTxt;

    @FXML
    private TextField AddPartIhMaxTxt;

    @FXML
    private TextField AddPartIhMinTxt;

    @FXML
    private TextField AddPartIhNameTxt;

    @FXML
    private RadioButton AddPartIhOsRdBtn;

    @FXML
    private TextField AddPartIhPcTxt;

    @FXML
    private Button AddPartIhSaveBtn;

    /** This function will return the user to the main menu
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This function will save the data entered in the text fields and create a new Part object and add it to the allParts Observable list
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionAddPartSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(AddPartIhIdTxt.getText());
            String name = String.valueOf(AddPartIhNameTxt.getText());
            double price = Double.parseDouble(AddPartIhPcTxt.getText());
            int stock = Integer.parseInt(AddPartIhInvTxt.getText());
            int min = Integer.parseInt(AddPartIhMinTxt.getText());
            int max = Integer.parseInt(AddPartIhMaxTxt.getText());
            int machineId = Integer.parseInt(AddPartIhMachIdTxt.getText());

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0 || machineId <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {

                if (min <= stock && stock <= max) {

                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else {
                    dialogBox("Inventory does not fall between Min and Max", "Error Message", "Input error!");
                }
            }
        } catch (Exception e) {
            dialogBox("Please enter the correct data types.", "Error Message", "Input error!");
        }

    }

    /** This function will be called whenever we want to create a dialog box to convey information to the user
     * @param infoMessage
     * @param titleBar
     * @param headerMessage
     *
     */
    public static void dialogBox(String infoMessage, String titleBar, String headerMessage)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }

    /** This function will allow us to go back and forth between InHouse and Outsourced Part forms without losing the data in the text fields
     * @param id
     * @param name
     * @param inv
     * @param price
     * @param min
     * @param max
     *
     */
    public void transferPart(String id, String name, String inv, String price, String min, String max) {
        AddPartIhIdTxt.setText(String.valueOf(id));
        AddPartIhNameTxt.setText(String.valueOf(name));
        AddPartIhInvTxt.setText(String.valueOf(String.valueOf(inv)));
        AddPartIhPcTxt.setText(String.valueOf(String.valueOf(price)));
        AddPartIhMinTxt.setText(String.valueOf(String.valueOf(min)));
        AddPartIhMaxTxt.setText(String.valueOf(String.valueOf(max)));
    }

    /** This function activates when we toggle the radio buttons and it will send us to the correct FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDisplayCompanyName(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddPartOutsourced.fxml"));
        loader.load();
        AddPartOutsourced APOSController = loader.getController();
        APOSController.transferPart(AddPartIhIdTxt.getText(), AddPartIhNameTxt.getText(), AddPartIhInvTxt.getText(), AddPartIhPcTxt.getText(), AddPartIhMinTxt.getText(), AddPartIhMaxTxt.getText());
        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Initialize function. The code written here will ensure that the Id field is autogenerated
     * @param url
     * @param resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int maxId = 1;
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() > maxId) {
                maxId = part.getId();
            } else {
                continue;
            }
        }   AddPartIhIdTxt.setText(String.valueOf(maxId + 1));


    }
}
