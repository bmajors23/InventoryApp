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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class to add parts that are made Outsourced
 */
public class AddPartOutsourced implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button AddPartOsCancBtn;

    @FXML
    private TextField AddPartOsCompNameTxt;

    @FXML
    private TextField AddPartOsIdTxt;

    @FXML
    private RadioButton AddPartOsIhRdBtn;

    @FXML
    private TextField AddPartOsInvTxt;

    @FXML
    private TextField AddPartOsMaxTxt;

    @FXML
    private TextField AddPartOsMinTxt;

    @FXML
    private TextField AddPartOsNameTxt;

    @FXML
    private RadioButton AddPartOsOsRdBtn;

    @FXML
    private TextField AddPartOsPcTxt;

    @FXML
    private Button AddPartOsSaveBtn;

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
            int id = Integer.parseInt(AddPartOsIdTxt.getText());
            String name = AddPartOsNameTxt.getText();
            double price = Double.parseDouble(AddPartOsPcTxt.getText());
            int stock = Integer.parseInt(AddPartOsInvTxt.getText());
            int min = Integer.parseInt(AddPartOsMinTxt.getText());
            int max = Integer.parseInt(AddPartOsMaxTxt.getText());
            String companyName = AddPartOsCompNameTxt.getText();

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {

                if (min <= stock && stock <= max) {

                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));

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

    /** This function activates when we toggle the radio buttons and it will send us to the correct FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDisplayMachineId(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddPartInHouse.fxml"));
        loader.load();
        AddPartInHouse APIHController = loader.getController();
        APIHController.transferPart(AddPartOsIdTxt.getText(), AddPartOsNameTxt.getText(), AddPartOsInvTxt.getText(), AddPartOsPcTxt.getText(), AddPartOsMinTxt.getText(), AddPartOsMaxTxt.getText());
        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

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
        AddPartOsIdTxt.setText(String.valueOf(id));
        AddPartOsNameTxt.setText(String.valueOf(name));
        AddPartOsInvTxt.setText(String.valueOf(String.valueOf(inv)));
        AddPartOsPcTxt.setText(String.valueOf(String.valueOf(price)));
        AddPartOsMinTxt.setText(String.valueOf(String.valueOf(min)));
        AddPartOsMaxTxt.setText(String.valueOf(String.valueOf(max)));
    }


    /** Initialize Method
     * @param url
     * @param resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
