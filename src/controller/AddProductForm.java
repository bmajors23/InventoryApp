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
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class to add products
 */
public class AddProductForm implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> bottomTable  = FXCollections.observableArrayList();

    @FXML
    private Button AddProdAddBtn;

    @FXML
    private TableColumn<Part, Double> AddProdBtmCpuCol;

    @FXML
    private TableColumn<Part, Integer> AddProdBtmInvLvlCol;

    @FXML
    private TableColumn<Part, Integer> AddProdBtmPartIdCol;

    @FXML
    private TableColumn<Part, String> AddProdBtmPartNameCol;

    @FXML
    private TableView<Part> AddProdBtmTbl;

    @FXML
    private Button AddProdCancBtn;

    @FXML
    private TextField AddProdIdTxt;

    @FXML
    private TextField AddProdInvTxt;

    @FXML
    private TextField AddProdMaxTxt;

    @FXML
    private TextField AddProdMinTxt;

    @FXML
    private TextField AddProdNameTxt;

    @FXML
    private TextField AddProdPcTxt;

    @FXML
    private Button AddProdRmvPartBtn;

    @FXML
    private Button AddProdSaveBtn;

    @FXML
    private TextField AddProdSearch;

    @FXML
    private TableColumn<Part, Double> AddProdTopCpuCol;

    @FXML
    private TableColumn<Part, Integer> AddProdTopInvLvlCol;

    @FXML
    private TableColumn<Part, Integer> AddProdTopPartIdCol;

    @FXML
    private TableColumn<Part, String> AddProdTopPartNameCol;

    @FXML
    private TableView<Part> AddProdTopTbl;

    /** This function will take the parts from the top table and bring them down to the bottom table and become an associated part
     * @param event
     *
     */
    @FXML
    void OnActionAddProdAddProd(ActionEvent event) {
        Part selectedPart = AddProdTopTbl.getSelectionModel().getSelectedItem();
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
        AddProdBtmTbl.setItems(bottomTable);
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

    /** This function will remove the associated part from the bottom table
     * @param event
     *
     */
    @FXML
    void OnActionAddProdRmvPart(ActionEvent event) {
        if (!(AddProdBtmTbl.getItems().isEmpty())) {
            String titleBar = "Confirmation Message";
            String headerMessage = "Confirmation Message";
            String infoMessage = "Are you sure you want to remove?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(titleBar);
            alert.setHeaderText(headerMessage);
            alert.setContentText(infoMessage);
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.get() == ButtonType.OK) {
                Part selectedPart = AddProdBtmTbl.getSelectionModel().getSelectedItem();

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
    void OnActionAddProdSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(AddProdIdTxt.getText());
            String name = AddProdNameTxt.getText();
            double price = Double.parseDouble(AddProdPcTxt.getText());
            int stock = Integer.parseInt(AddProdInvTxt.getText());
            int min = Integer.parseInt(AddProdMinTxt.getText());
            int max = Integer.parseInt(AddProdMaxTxt.getText());
            ObservableList<Part> associatedParts = bottomTable;

            if (price <= 0 || stock <= 0 || min <= 0 || max <= 0) {
                dialogBox("Do not enter negative numbers or zero", "Input Error", "Input Error");
            } else {

                if ((min > 0) && (min <= stock) && (stock <= max)) {

                    Product newProduct = new Product();
                    newProduct.setId(id);
                    newProduct.setName(name);
                    newProduct.setPrice(price);
                    newProduct.setStock(stock);
                    newProduct.setMin(min);
                    newProduct.setMax(max);
                    newProduct.setAssociatedParts(associatedParts);

                    Inventory.addProduct(newProduct);

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


    /** Searches for part and displays part in the top part table in the addProductForm. If nothing is found in observable list, dialog box is
     * displayed to let user know of error and then clears the search field.
     * @param event
     */
    @FXML
    void SearchForPart(KeyEvent event) {
        if (Inventory.lookupPart(AddProdSearch.getText()).isEmpty()) {
            dialogBox("No parts match your search. Please enter a valid input.", "No results", "No results");
            AddProdSearch.clear();
            AddProdTopTbl.setItems(Inventory.getAllParts());
        } else {
            AddProdTopTbl.setItems(Inventory.lookupPart(AddProdSearch.getText()));
        }
    }

    /** Initialize function. User will use this to set the parts from the Parts table in the main menu to the top table.
     * This function also will allow user to search for parts in the top table
     * @param url
     * @param resourceBundle
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AddProdTopTbl.setItems(Inventory.getAllParts());

        AddProdTopPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdTopPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdTopInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdTopCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProdBtmPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdBtmPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdBtmInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdBtmCpuCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        int maxId = 1;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() > maxId) {
                maxId = product.getId();
            } else {
                continue;
            }
        }
        AddProdIdTxt.setText(String.valueOf(maxId + 1));
    }
    }
