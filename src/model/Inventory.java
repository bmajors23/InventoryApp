package model;

import controller.MainMenu;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.awt.*;
import java.util.Locale;

/**
 * Inventory class that stores all of the Parts and Products in observable lists
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Adds part to allParts
     * @param part
     *
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** Adds product to allProducts
     * @param product
     *
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** Adds part to associatedParts
     * @param part
     *
     */
    public static void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Updates part that is being modified and replaces it in allParts
     * @param index
     * @param selectedPart
     *
     */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }

    /** Updates product that is being modified and replaces it in allProducts
     * @param index
     * @param selectedProduct
     *
     */
    public static void updateProduct(int index, Product selectedProduct) {
        Inventory.getAllProducts().set(index, selectedProduct);
    }

    /** Deletes part from allParts
     * @param selectedPart
     *
     */
    public static boolean deletePart(Part selectedPart) {
        return Inventory.getAllParts().remove(selectedPart);
    }

    /** Deletes product from allProducts
     * @param selectedProduct
     *
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return Inventory.getAllProducts().remove(selectedProduct);
    }


    /** Looks up Part by partId and returns corresponding part
     * @param partId
     * @return corresponding part
     */
    public static Part lookupPart(int partId) {
        Part lookedUpPart = null;
        for (Part part : allParts) {
            if (partId == part.getId()) {
                lookedUpPart = part;
            }
        }
        return lookedUpPart;
    }

    /** Looks up Product by productId and returns corresponding product
     * @param productId
     * @return corresponding product
     */
    public static Product lookupProduct(int productId) {
        Product lookedUpProduct = null;
        for (Product product : allProducts) {
            if (productId == product.getId()) {
                lookedUpProduct = product;
            }
        }
        return lookedUpProduct;
    }

    /** Looks up Part by partName and returns the new filtered observable list. This also will look up by Id and do the same.
     * @param partName
     * @return filtered observable list
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                try {
                if (allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())) {
                    filteredParts.add(allParts.get(i));
                } else if (allParts.get(i).getId() == Integer.parseInt(partName)) {
                    filteredParts.add(lookupPart(allParts.get(i).getId()));
                }
            } catch (Exception e) {
                    continue;
                }
                }
        } else {
            return filteredParts;
        }
        return filteredParts;
    }

    /** Look up Product by productName and returns the new filtered observable list. This will also look up by Id and do the same.
     * @param productName
     * @return filtered observable list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                try {
                    if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())) {
                        filteredProducts.add(allProducts.get(i));
                    } else if (allProducts.get(i).getId() == Integer.parseInt(productName)) {
                        filteredProducts.add(lookupProduct(allProducts.get(i).getId()));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } else {
            return filteredProducts;
        }
        return filteredProducts;
    }

    /** Removes associated part from associatedParts
     * @param selectedPart
     *
     *
     */
    public static void removeAssociatedPart(Part selectedPart) {
        Inventory.getAllAssociatedParts().remove(selectedPart);
    }

    /** Returns allParts
     * @return allParts
     *
     *
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** returns AllProducts
     * @return associatedProducts
     *
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Returns associatedParts
     * @return associatedParts
     *
     */
    public static ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
