
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author luoan
 */
public class RestaurantBill extends Application{

    private double subtotal = 0.0;
    private final double TaxRate = 0.13;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
                
        //add whole food item for user to choose
        ComboBox<String> beverageComboBox = new ComboBox<>();
        beverageComboBox.getItems().addAll("Coffee", "Tea", "Soft Drink", "Water", "Milk", " Juice");
        ComboBox<String> appetizerComboBox = new ComboBox<>();
        appetizerComboBox.getItems().addAll("Soup", "Salad", "Spring Roll", "Garlic Bread", "Chips and Salsa");
        ComboBox<String> mainCourseComboBox = new ComboBox<>();
        mainCourseComboBox.getItems().addAll("Steak", "Grilled Chiken", "Chicken Alfredo" , "Turkey Club", "Shrimp Scampi", " Pasta" ,"Fish and Chips");
        ComboBox<String> dessertComboBox = new ComboBox<>();
        dessertComboBox.getItems().addAll("Apple Pie", "Carrot Cake", " Mud Pie", "Pudding", "Apple Crisp");
        
        //Map
        Map<String, Double> items = new HashMap<>();
        //beverages
        items.put("Coffee", 2.50);
        items.put("Tea", 2.00);
        items.put("Soft Drink", 1.75);
        items.put("Water", 2.95);
        items.put("Milk", 1.50);
        items.put("Juice", 2.50);
        //appetizer
        items.put("Soup", 4.50);
        items.put("Salad", 3.75);
        items.put("Spring Rolls", 5.25);
        items.put("Garlic Bread", 3.00);
        items.put("Chips and Salsa", 6.95);
        
        //main
        items.put("Steak", 15.00);
        items.put("Grilled Chikcen", 13.50);
        items.put("Chicken Alfredo", 13.95);
        items.put("Turkey Club", 11.90);
        items.put("Shrimp Scampi", 18.99);
        items.put("Pasta", 11.75);
        items.put("Fish and Chips", 12.25);

        //dessert
        items.put("Apple Pie", 5.95);
        items.put("Carrot Cake", 4.50);
        items.put("Mud Pie", 4.75);
        items.put("Pudding", 3.25);
        items.put("Apple Crisp", 5.98);
        
        
        //Labels
        Label bevLabel = new Label("Beverages: ");
        Label appetLabel = new Label("Appetizer: ");
        Label mainLabel = new Label("Main Dishes: ");
        Label dessertLabel = new Label("Desserts: ");
        
        //slider for tip%
        Slider tipSlider = new Slider(0.0,20.0,10.0);
        tipSlider.setShowTickLabels(true);
        tipSlider.setShowTickMarks(true);
        tipSlider.setMajorTickUnit(5);
        tipSlider.setBlockIncrement(1);
        tipSlider.setOrientation(Orientation.HORIZONTAL);
        
        //calculation Label
        Label subtotalLabel = new Label("Subtotal : $0.00");
        Label taxLabel = new Label("Tax : $0.00");
        Label tipLabel = new Label("Tip : $0.00");
        Label totalLabel = new Label("Total : $0.00");
        
        //add from combo boxes
        beverageComboBox.setOnAction(e -> addItems(beverageComboBox, items, subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider));
        appetizerComboBox.setOnAction(e -> addItems(appetizerComboBox, items, subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider));
        mainCourseComboBox.setOnAction(e -> addItems(mainCourseComboBox, items, subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider));
        dessertComboBox.setOnAction(e -> addItems(dessertComboBox, items, subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider));
                
        //slider
        tipSlider.valueProperty().addListener((obs , oldVal, newVal) ->  { 
            tipLabel.setText("Tip : "  +  newVal.doubleValue());
            updateBill(subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider.getValue());
        });
        
        Button clearButton = new Button("Clear EVERYTHING");
        clearButton.setOnAction(e -> {
            subtotal = 0.0;
            beverageComboBox.getSelectionModel().clearSelection();
            appetizerComboBox.getSelectionModel().clearSelection();
            mainCourseComboBox.getSelectionModel().clearSelection();
            dessertComboBox.getSelectionModel().clearSelection();
            tipSlider.setValue(10);
            subtotalLabel.setText("Subtotal: $0.00");
            tipLabel.setText("Tip: $0.00");
            taxLabel.setText("Tax: $0.00");
            totalLabel.setText("Total: $0.00");
        });
        
        GridPane root = new GridPane();
        //add labels
        root.add(bevLabel, 0, 0);
        root.add(appetLabel, 0, 1);
        root.add(mainLabel, 0, 2);
        root.add(dessertLabel, 0, 3);
        //add Combo Box
        root.add(beverageComboBox, 1, 0);
        root.add(appetizerComboBox, 1, 1);
        root.add(mainCourseComboBox, 1, 2);
        root.add(dessertComboBox, 1, 3);
        
        root.add(tipSlider, 0, 4);
        
        root.add(subtotalLabel, 2, 0);
        root.add(taxLabel, 2, 1);
        root.add(tipLabel, 2, 2);
        root.add(totalLabel, 2, 3);
        
        root.add(clearButton, 2, 4);
        root.setHgap(10);
        root.setVgap(10);
        
        
        
        Scene scene = new Scene(root,500,500);
        stage.setScene(scene);
        stage.setTitle("Restaurant bill");
        stage.show();
    }
    
    private void addItems(ComboBox<String> box , Map<String, Double> items, Label subtotalLabel, Label taxLabel, 
                            Label tipLabel, Label totalLabel, Slider tipSlider) { 
        
        String item = box.getSelectionModel().getSelectedItem();
        if (item != null) {
            subtotal += items.getOrDefault(item, 0.0);
            updateBill(subtotalLabel, taxLabel, tipLabel, totalLabel, tipSlider.getValue());
        }   
    }
    
    private void updateBill ( Label subtotalLabel, Label taxLabel, Label tipLabel, Label totalLabel, double tipPercent) { 
        double tax = subtotal * TaxRate;
        double tip = subtotal * (tipPercent/100);
        double total = subtotal + tip + tax;
        
        subtotalLabel.setText("Subtotal : " + subtotal);
        taxLabel.setText("Tax: " + tax);
        tipLabel.setText("Tip: " + tip);;
        totalLabel.setText("Total: " + total);
    }
}
