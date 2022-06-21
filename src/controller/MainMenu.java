package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main Menu controller class that will allow the user to navigate to all parts of the program
 */
public class MainMenu implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Integer> MmPartInvLvlCol;

    @FXML
    private TableColumn<Part, Integer> MmPartIdCol;

    @FXML
    private TableColumn<Part, String> MmPartNameCol;

    @FXML
    private TextField MmPartSearch;

    @FXML
    private Button MmPartsAddBtn;

    @FXML
    private TableColumn<Part, Double> MmPartsCpuCol;

    @FXML
    private Button MmPartsDelBtn;

    @FXML
    private Button MmPartsModBtn;

    @FXML
    private TableView<Part> MmPartsTbl;

    @FXML
    private Button MmProdAddBtn;

    @FXML
    private TableColumn<Product, Double> MmProdCpuCol;

    @FXML
    private Button MmProdDelBtn;

    @FXML
    private TableColumn<Product, Integer> MmProdIdCol;

    @FXML
    private TableColumn<Product, Integer> MmProdInvLvlCol;

    @FXML
    private Button MmProdModBtn;

    @FXML
    private TableColumn<Product, String> MmProdNameCol;

    @FXML
    private TextField MmProdSearch;

    @FXML
    private TableView<Product> MmProdTbl;

    /** This function will open the AddPartInHouse FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartInHouse.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This function will open the AddProductForm FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionAddProd(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /** Searches for product and displays part in Product table on main menu. If nothing is found in observable list, dialog box is
     * displayed to let user know of error and then clears the search field.
     * @param event
     */
    @FXML
    void searchForProduct(KeyEvent event) {
        if (Inventory.lookupProduct(MmProdSearch.getText()).isEmpty()) {
            dialogBox("No products match your search. Please enter a valid input.", "No results", "No results");
            MmProdSearch.clear();
            MmProdTbl.setItems(Inventory.getAllProducts());
        } else {
            MmProdTbl.setItems(Inventory.lookupProduct(MmProdSearch.getText()));
        }
    }

    /** Searches for part and displays part in Part table on main menu. If nothing is found in observable list, dialog box is
     * displayed to let user know of error and then clears the search field.
     * @param event
     */
    @FXML
    void searchForPart(KeyEvent event) {
        if (Inventory.lookupPart(MmPartSearch.getText()).isEmpty()) {
            dialogBox("No parts match your search. Please enter a valid input.", "No results", "No results");
            MmPartSearch.clear();
            MmPartsTbl.setItems(Inventory.getAllParts());
        } else {
            MmPartsTbl.setItems(Inventory.lookupPart(MmPartSearch.getText()));
        }
    }
    /** This function will be called when the user selects a part to be deleted and then deletes it
     *
     * RUNTIME ERROR: I was having an issue with deleting parts when none was selected. It was still
     * going through the same process of displaying the confirmation message and after you select "OK"
     * it would display an error. To Fix this, I added an if statement to check if the selection was
     * empty. If it is, I displayed a dialog box to let the user know and the rest of the code was not
     * run. This effectively resolve the issue.
     *
     * FUTURE ENHANCEMENT: I think a great future enhancement would be to create a deletedParts/deletedProducts observable list
     * that would add any deleted parts/products to it. We could create a button to display a new FXML file that would
     * display any and all deleted parts/products and allow us to recover them back to the allParts/allProducts observable list.
     *
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDelPart(ActionEvent event) throws IOException {
        if (MmPartsTbl.getSelectionModel().isEmpty()) {
            dialogBox("Error. No part is selected. Please select a part to delete.", "Error Message", "Deletion error!");
        } else {
            String titleBar = "Confirmation Message";
            String headerMessage = "Confirmation Message";
            String infoMessage = "Are you sure you want to delete?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(infoMessage);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            Optional<ButtonType> choice = alert.showAndWait();

            if (choice.get() == ButtonType.OK) {
                Inventory.deletePart(MmPartsTbl.getSelectionModel().getSelectedItem());
            } else {

            }

        }
    }

    /**
     * This function will be called when the user selects a product to be deleted and then deletes it.
     *
     * FUTURE ENHANCEMENT: I think a great future enhancement would be to create a deletedParts/deletedProducts observable list
     * that would add any deleted parts/products to it. We could create a button to display a new FXML file that would
     * display any and all deleted parts/products and allow us to recover them back to the allParts/allProducts observable list.
     *
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionDelProd(ActionEvent event) throws IOException {
        if (MmProdTbl.getSelectionModel().isEmpty()) {
            dialogBox("Error. No product is selected. Please select a product to delete.", "Error Message", "Deletion error!");
        } else {
            if (MmProdTbl.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {
            String titleBar = "Confirmation Message";
            String headerMessage = "Confirmation Message";
            String infoMessage = "Are you sure you want to delete?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(infoMessage);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK) {
                Inventory.deleteProduct(MmProdTbl.getSelectionModel().getSelectedItem());
                }

            } else {
                dialogBox("Can't delete. This product has an associated part.", "Deletion Error", "Deletion Error");
            }
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

    /**
     * This function will open the ModifyPartInHouse FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionModPart(ActionEvent event) throws IOException {
        if (MmPartsTbl.getSelectionModel().isEmpty()) {
            dialogBox("Error. No part is selected. Please select a part to modify.", "Error Message", "Modify error!");
        } else {

            FXMLLoader loader = new FXMLLoader();


            if (MmPartsTbl.getSelectionModel().getSelectedItem() instanceof InHouse) {
                loader.setLocation(getClass().getResource("/view/ModifyPartInHouse.fxml"));
                loader.load();
                ModifyPartInHouse MPIHController = loader.getController();
                MPIHController.sendPart(MmPartsTbl.getSelectionModel().getSelectedItem());
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

            } else if (MmPartsTbl.getSelectionModel().getSelectedItem() instanceof Outsourced) {
                loader.setLocation(getClass().getResource("/view/ModifyPartOutsourced.fxml"));
                loader.load();
                ModifyPartOutsourced MPOSController = loader.getController();
                MPOSController.sendPart(MmPartsTbl.getSelectionModel().getSelectedItem());
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * This function will open the ModifyProductForm FXML form
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionModProd(ActionEvent event) throws IOException {

        if (MmProdTbl.getSelectionModel().isEmpty()) {
            dialogBox("Error. No product is selected. Please select a product to modify.", "Error Message", "Modify error!");
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();
            ModifyProductForm MPFController = loader.getController();
            MPFController.sendProd(MmProdTbl.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /** This function exits the program entirely
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionExitProgram(ActionEvent event) throws IOException {

        System.exit(0);

    }

    /** Initialize function. This function will populate the tables with data, and allow us to user search functions in each table
     * @param url
     * @param resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MmPartsTbl.setItems(Inventory.getAllParts());
        MmProdTbl.setItems(Inventory.getAllProducts());

        MmPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MmPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MmPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MmPartsCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        MmProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MmProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MmProdInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MmProdCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
