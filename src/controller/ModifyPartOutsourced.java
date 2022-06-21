package controller;

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
import model.Outsourced;
import model.Part;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Modify Part Outsourced controller class
 */
public class ModifyPartOutsourced implements Initializable {

    Stage stage;
    Parent scene;
    int index;

    @FXML
    private Button ModPartOsCancBtn;

    @FXML
    private TextField ModPartOsCompNameTxt;

    @FXML
    private TextField ModPartOsIdTxt;

    @FXML
    private RadioButton ModPartOsIhRdBtn;

    @FXML
    private TextField ModPartOsInvTxt;

    @FXML
    private TextField ModPartOsMaxTxt;

    @FXML
    private TextField ModPartOsMinTxt;

    @FXML
    private TextField ModPartOsNameTxt;

    @FXML
    private RadioButton ModPartOsOsRdBtn;

    @FXML
    private TextField ModPartOsPcTxt;

    @FXML
    private Button ModPartOsSaveBtn;

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

    /** This function will save the data entered into the form and create a new part and add it to the Part Observable list
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionModPartSave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(ModPartOsIdTxt.getText());
            String name = ModPartOsNameTxt.getText();
            double price = Double.parseDouble(ModPartOsPcTxt.getText());
            int stock = Integer.parseInt(ModPartOsInvTxt.getText());
            int min = Integer.parseInt(ModPartOsMinTxt.getText());
            int max = Integer.parseInt(ModPartOsMaxTxt.getText());
            String companyName = ModPartOsCompNameTxt.getText();

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {

                if (min <= stock && stock <= max) {

                    Part updatedOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);

                    Inventory.updatePart(index, updatedOutsourced);

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

    /** This function will take data from the part table in the main menu and transfer the data to populate the fields in the modify part in house form
     * @param part
     *
     */
    public void sendPart(Part part) {
        index = Inventory.getAllParts().indexOf(part);
        ModPartOsIdTxt.setText(String.valueOf(part.getId()));
        ModPartOsNameTxt.setText(part.getName());
        ModPartOsInvTxt.setText(String.valueOf(part.getStock()));
        ModPartOsPcTxt.setText(String.valueOf(part.getPrice()));
        ModPartOsMinTxt.setText(String.valueOf(part.getMin()));
        ModPartOsMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof Outsourced)
            ModPartOsCompNameTxt.setText(((Outsourced) part).getCompanyName());

    }

    /** This function will allow us to go back and forth between InHouse and Outsourced Part forms
     * without losing the data in the text fields.
     *
     * @param id
     * @param name
     * @param inv
     * @param price
     * @param min
     * @param max
     *
     */
    public void transferPart(String id, String name, String inv, String price, String min, String max) {
        Part part = Inventory.lookupPart(Integer.parseInt(id));
        index = Inventory.getAllParts().indexOf(part);
        ModPartOsIdTxt.setText(id);
        ModPartOsNameTxt.setText(String.valueOf(name));
        ModPartOsInvTxt.setText(String.valueOf(String.valueOf(inv)));
        ModPartOsPcTxt.setText(String.valueOf(String.valueOf(price)));
        ModPartOsMinTxt.setText(String.valueOf(String.valueOf(min)));
        ModPartOsMaxTxt.setText(String.valueOf(String.valueOf(max)));
    }

    /** This function activates when we toggle the radio buttons and it will send us to the correct FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDisplayMachineId(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartInHouse.fxml"));
        loader.load();
        ModifyPartInHouse MPIHController = loader.getController();
        MPIHController.transferPart(ModPartOsIdTxt.getText(), ModPartOsNameTxt.getText(), ModPartOsInvTxt.getText(), ModPartOsPcTxt.getText(), ModPartOsMinTxt.getText(), ModPartOsMaxTxt.getText());
        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Initialize Method.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
