
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author luoan
 */
public class Order extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        
        Label selectBagStyleLabel = new Label("Select Bag Style : ");
        ListView<String> bagTypesListView = new ListView<>();
        bagTypesListView.setPrefSize(120,100);
        bagTypesListView.getItems().addAll("Full Decorative", "Beaded", "Pirate Deisgn", "Fringed", "Leather", "Plain");
        bagTypesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       
     
        //combo box
        ComboBox<Integer> numberBox = new ComboBox<>();
        numberBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        HBox hbox = new HBox(10, selectBagStyleLabel, bagTypesListView, numberBox);
        
        //radio buttons
        RadioButton small = new RadioButton("small bag");
        RadioButton medium = new RadioButton("medium bag");
        RadioButton large = new RadioButton("large bag");
        
        ToggleGroup sizeGroup = new ToggleGroup();
        small.setToggleGroup(sizeGroup);
        medium.setToggleGroup(sizeGroup);
        large.setToggleGroup(sizeGroup);
        
        VBox vbox = new VBox(10, small,medium,large);
        
        Label outputLabel = new Label();
        
        //buttons
        Button placeOrderBtn = new Button("Place Order");
        Button clearSelectionsBtn = new Button("Clear Selecrions");
        HBox buttons = new HBox(10, placeOrderBtn, clearSelectionsBtn);
        
        placeOrderBtn.setOnAction(e -> 
            {
             String bagType = bagTypesListView.getSelectionModel().getSelectedItem();
             Integer quantity = numberBox.getValue();
             RadioButton selectedSize = (RadioButton) sizeGroup.getSelectedToggle();
             
             if (bagType == null || quantity == null || selectedSize == null) { 
                 outputLabel.setText("PLEASE FILL OUT EVERYTHING");
             } else { 
                 outputLabel.setText("You ordered : "  + quantity  + " bags of " + bagType + " with size " + selectedSize.getText());
             }
            });
        
        clearSelectionsBtn.setOnAction(e ->
            {
                bagTypesListView.getSelectionModel().clearSelection();
                numberBox.getSelectionModel().clearSelection();
                sizeGroup.selectToggle(null);
                outputLabel.setText("CLEARED");
            });
        
        //root and show scene
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.add(hbox, 0, 0);
        root.add(vbox, 1, 0);
        root.add(buttons, 1, 1);
        root.add(outputLabel, 0, 1);
        Scene scene = new Scene(root,500,500);
        stage.setScene(scene);
        stage.setTitle("Order bags");
        stage.show();
    }
    
}
