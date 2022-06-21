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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class to modify products
 */
public class ModifyProductForm implements Initializable {

    Stage stage;
    Parent scene;
    int index;
    private Product prodInUse;
    private Product modProduct;
    private ObservableList<Part> bottomTable  = FXCollections.observableArrayList();

    @FXML
    private Button ModProdAddBtn;

    @FXML
    private TableColumn<Part, Double> ModProdBtmCpuCol;

    @FXML
    private TableColumn<Part, Integer> ModProdBtmInvLvlCol;

    @FXML
    private TableColumn<Part, Integer> ModProdBtmPartIdCol;

    @FXML
    private TableColumn<Part, String> ModProdBtmPartNameCol;

    @FXML
    private TableView<Part> ModProdBtmTbl;

    @FXML
    private Button ModProdCancBtn;

    @FXML
    private TextField ModProdIdTxt;

    @FXML
    private TextField ModProdInvTxt;

    @FXML
    private TextField ModProdMaxTxt;

    @FXML
    private TextField ModProdMinTxt;

    @FXML
    private TextField ModProdNameTxt;

    @FXML
    private TextField ModProdPcTxt;

    @FXML
    private Button ModProdRmvPartBtn;

    @FXML
    private Button ModProdSaveBtn;

    @FXML
    private TextField ModProdSearch;

    @FXML
    private TableColumn<Part, Double> ModProdTopCpuCol;

    @FXML
    private TableColumn<Part, Integer> ModProdTopInvLvlCol;

    @FXML
    private TableColumn<Part, Integer> ModProdTopPartIdCol;

    @FXML
    private TableColumn<Part, String> ModProdTopPartNameCol;

    @FXML
    private TableView<Part> ModProdTopTbl;

    /** This function will take the parts from the top table and bring them down to the bottom table and become an associated part
     * @param event
     *
     */
    @FXML
    void OnActionModProdAddPart(ActionEvent event) {
        Part selectedPart = ModProdTopTbl.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            bottomTable.add(selectedPart);
            updateAssociatedPartTable();
        } else {
            dialogBox("Please select a part before attempting to add to associated parts.", "Add Error", "Add Error");
        }
    }

    /** This function will repopulate the bottom table with current data
     *
     */
    private void updateAssociatedPartTable() {
        ModProdBtmTbl.setItems(bottomTable);
    }

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

    /** Searches for part and displays part in Part table on ModifyProductForm. If nothing is found in observable list, dialog box is
     * displayed to let user know of error and then clears the search field.
     * @param event
     */
    @FXML
    void SearchForPart(KeyEvent event) {
        if (Inventory.lookupPart(ModProdSearch.getText()).isEmpty()) {
            dialogBox("No parts match your search. Please enter a valid input.", "No results", "No results");
            ModProdSearch.clear();
            ModProdTopTbl.setItems(Inventory.getAllParts());
        } else {
            ModProdTopTbl.setItems(Inventory.lookupPart(ModProdSearch.getText()));
        }
    }

    /** This function will remove the associated part from the bottom table
     * @param event
     *
     */
    @FXML
    void OnActionModProdRmvPart(ActionEvent event) {
        if (!(ModProdBtmTbl.getItems().isEmpty())) {
            String titleBar = "Confirmation Message";
            String headerMessage = "Confirmation Message";
            String infoMessage = "Are you sure you want to remove?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(infoMessage);
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK) {
                Part selectedPart = ModProdBtmTbl.getSelectionModel().getSelectedItem();

                bottomTable.remove(selectedPart);
                updateAssociatedPartTable();
            } else {

            }
        } else {
            String titleBar = "Error Message";
            String headerMessage = "Error!";
            String infoMessage = "There is no associated part to remove";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(infoMessage);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.showAndWait();
        }
    }

    /** This function will save all of the information entered into the text fields and will create a new Product object
     * @param event
     * @throws IOException
     *
     */
    @FXML
    void OnActionModProdSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(ModProdIdTxt.getText());
            String name = ModProdNameTxt.getText();
            double price = Double.parseDouble(ModProdPcTxt.getText());
            int stock = Integer.parseInt(ModProdInvTxt.getText());
            int min = Integer.parseInt(ModProdMinTxt.getText());
            int max = Integer.parseInt(ModProdMaxTxt.getText());
            ObservableList<Part> associatedParts = ModProdBtmTbl.getItems();

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {
                prodInUse = Inventory.lookupProduct(id);
                prodInUse.setId(id);
                prodInUse.setName(name);
                prodInUse.setStock(stock);
                prodInUse.setPrice(price);
                prodInUse.setMin(min);
                prodInUse.setMax(max);
                prodInUse.getAssociatedParts().clear();
                prodInUse.setAssociatedParts(bottomTable);

                if (min <= stock && stock <= max) {

                    Inventory.updateProduct(index, prodInUse);

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

    /** This function will take data from the product table in the main menu and transfer the data to populate the fields in the modify product form
     * @param prod
     *
     */
    public void sendProd(Product prod) {
        this.prodInUse = prod;
        index = Inventory.getAllProducts().indexOf(prod);
        ModProdIdTxt.setText(String.valueOf(prod.getId()));
        ModProdNameTxt.setText(prod.getName());
        ModProdInvTxt.setText(String.valueOf(prod.getStock()));
        ModProdPcTxt.setText(String.valueOf(prod.getPrice()));
        ModProdMinTxt.setText(String.valueOf(prod.getMin()));
        ModProdMaxTxt.setText(String.valueOf(prod.getMax()));

        bottomTable.addAll(prodInUse.getAssociatedParts());

    }

    /** Initialize function. User will use this to set the parts from the Parts table in the main menu to the top table.
     * This function also will allow user to search for parts in the top table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ModProdTopTbl.setItems(Inventory.getAllParts());
        updateAssociatedPartTable();

        ModProdTopPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdTopPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdTopInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdTopCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModProdBtmPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdBtmPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdBtmInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdBtmCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}

