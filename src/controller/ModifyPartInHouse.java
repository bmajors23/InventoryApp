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

import javax.imageio.IIOParam;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Modify Part In House controller class
 */
public class ModifyPartInHouse implements Initializable {

    Stage stage;
    Parent scene;
    int index;

    @FXML
    private Button ModPartIhCancBtn;

    @FXML
    private TextField ModPartIhIdTxt;

    @FXML
    private RadioButton ModPartIhIhRdBtn;

    @FXML
    private TextField ModPartIhInvTxt;

    @FXML
    private TextField ModPartIhMachIdTxt;

    @FXML
    private TextField ModPartIhMaxTxt;

    @FXML
    private TextField ModPartIhMinTxt;

    @FXML
    private TextField ModPartIhNameTxt;

    @FXML
    private RadioButton ModPartIhOsRdBtn;

    @FXML
    private TextField ModPartIhPcTxt;

    @FXML
    private Button ModPartIhSaveBtn;

    /**
     * This function will return the user to the main menu
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

    /**
     * This function will save the data entered into the form and create a new part and add it to the Part Observable list
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionModPartSave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(ModPartIhIdTxt.getText());
            String name = ModPartIhNameTxt.getText();
            double price = Double.parseDouble(ModPartIhPcTxt.getText());
            int stock = Integer.parseInt(ModPartIhInvTxt.getText());
            int min = Integer.parseInt(ModPartIhMinTxt.getText());
            int max = Integer.parseInt(ModPartIhMaxTxt.getText());
            int machineId = Integer.parseInt(ModPartIhMachIdTxt.getText());

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0 || machineId <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {

                if (min <= stock && stock <= max) {

                    Part updatedInHouse = new InHouse(id, name, price, stock, min, max, machineId);

                    Inventory.updatePart(index, updatedInHouse);

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

    /**
     * This function will be called whenever we want to create a dialog box to convey information to the user
     * @param infoMessage
     * @param titleBar
     * @param headerMessage
     *
     */
    public static void dialogBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    /**
     * This function will take data from the part table in the main menu and transfer the data to populate the fields in the modify part in house form
     * @param part
     * @throws IOException
     *
     */
    public void sendPart(Part part) throws IOException {

        index = Inventory.getAllParts().indexOf(part);
        ModPartIhIdTxt.setText(String.valueOf(part.getId()));
        ModPartIhNameTxt.setText(part.getName());
        ModPartIhInvTxt.setText(String.valueOf(part.getStock()));
        ModPartIhPcTxt.setText(String.valueOf(part.getPrice()));
        ModPartIhMinTxt.setText(String.valueOf(part.getMin()));
        ModPartIhMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse)
            ModPartIhMachIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));

    }

    /**
     * This function will allow us to go back and forth between InHouse and Outsourced Part forms without losing the data in the text fields
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
        ModPartIhIdTxt.setText(String.valueOf(part.getId()));
        ModPartIhNameTxt.setText(String.valueOf(name));
        ModPartIhInvTxt.setText(String.valueOf(String.valueOf(inv)));
        ModPartIhPcTxt.setText(String.valueOf(String.valueOf(price)));
        ModPartIhMinTxt.setText(String.valueOf(String.valueOf(min)));
        ModPartIhMaxTxt.setText(String.valueOf(String.valueOf(max)));
    }

    /**
     * This function activates when we toggle the radio buttons and it will send us to the correct FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDisplayCompanyName(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartOutsourced.fxml"));
        loader.load();
        ModifyPartOutsourced MPOSController = loader.getController();
        MPOSController.transferPart(ModPartIhIdTxt.getText(), ModPartIhNameTxt.getText(), ModPartIhInvTxt.getText(), ModPartIhPcTxt.getText(), ModPartIhMinTxt.getText(), ModPartIhMaxTxt.getText());
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
