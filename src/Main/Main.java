package Main;

import controller.ModifyProductForm;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Main Function Class / JavaDoc folder is located in Project folder
 */
public class Main extends Application {

    /** Starts and loads FXML
     * @param primaryStage
     * @throws Exception
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 1175, 450));
        primaryStage.show();
    }


    /** Main function. Initializes original variables
     * @param args
     *
     */
    public static void main(String[] args) {

        InHouse part1 = new InHouse(1, "P93", 12.95, 879, 200, 1000, 7);
        InHouse part2 = new InHouse(2, "Lx1293", 8.95, 302, 200, 1000, 12);
        Outsourced part3 = new Outsourced(3, "QP5", 4.95, 274, 200, 1000, "Sampson & Son");
        InHouse part4 = new InHouse(4, "KOPU12", 12.95, 521, 200, 1000, 42);
        InHouse part5 = new InHouse(5, "I04", 9.95, 410, 200, 1000, 31);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        Product prod1 = new Product(1, "RoboBoy", 46.95, 66, 20, 100);
        Product prod2 = new Product(2, "CrayonMan", 48.95, 77, 20, 100);
        Product prod3 = new Product(3, "ShippySponge", 55.95, 31, 20, 100);
        Product prod4 = new Product(4, "Flutter", 74.95, 77, 20, 100);
        Product prod5 = new Product(5, "Terragnome", 67.95, 87, 20, 100);

        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);
        Inventory.addProduct(prod5);

        launch(args);

    }
}
