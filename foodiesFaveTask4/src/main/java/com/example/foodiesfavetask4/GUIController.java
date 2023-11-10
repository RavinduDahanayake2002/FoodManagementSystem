package com.example.foodiesfavetask4;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUIController extends Main{

    @FXML
    Button menuButton1,menuButton2,menuButton3,menuButton4,menuButton5,menuButton6,menuButton7,menuButton8,
            button9,button10;

    @FXML
    Pane addCustomerpanel,addBurgerPanel,removeCustomerPanel,removeServedCustomerPanel;

    @FXML
    ImageView c1c1,c1c2,c2c1,c2c2,c2c3,c3c1,c3c2,c3c3,c3c4,c3c5;

    @FXML
    TextField addCustomerFirstName,addCustomerSecondName,noOfBurgers,removeQueueNo,removePlace,removedServedQueueNo,
            addBurgerNumberOfBurger,searchFirstName;

    @FXML
    Label firstQueueIncome,secondQueueIncome,thirdQueueIncome,noOfBurgerLabel;

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> tableFirstName;
    @FXML
    private TableColumn<ObservableList<String>, String> tableLastName;
    @FXML
    private TableColumn<ObservableList<String>, String> tableBurgers;

    int menuButton = 1;

    @FXML
    void initialize() {

        update();

    }


    public void exitButton(){
        Exit("999");
        System.exit(0);
//        menuButton1.setVisible(true);
    }

    public void addCustomerButton(){
        menuButton = 1;
        addCustomerpanel.setVisible(true);
        addBurgerPanel.setVisible(false);
        removeCustomerPanel.setVisible(false);
        removeServedCustomerPanel.setVisible(false);

        update();
    }

    public void addBurger(){
        menuButton = 2;
        addBurgerPanel.setVisible(true);
        addCustomerpanel.setVisible(false);
        removeCustomerPanel.setVisible(false);
        removeServedCustomerPanel.setVisible(false);
        update();

    }

    public void removeCustomer1(){
        menuButton = 3;
        removeCustomerPanel.setVisible(true);
        addCustomerpanel.setVisible(false);
        addBurgerPanel.setVisible(false);
        removeServedCustomerPanel.setVisible(false);
        update();
    }

    public void removeServedCustomer1(){
        menuButton = 4;
        removeServedCustomerPanel.setVisible(true);
        removeCustomerPanel.setVisible(false);
        addCustomerpanel.setVisible(false);
        addBurgerPanel.setVisible(false);
        update();
    }

    public void alphabetical(){
//        menuButton = 5;
//        removeServedCustomerPanel.setVisible(true);
//        removeCustomerPanel.setVisible(false);
//        addCustomerpanel.setVisible(false);
//        addBurgerPanel.setVisible(false);
        update();
//
        clearTable();

//        set table data

        tableFirstName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        tableLastName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        tableBurgers.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

        List<Customer> allCustomers  = sortCustomers();

        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            if (customer != null) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        customer.getFirstName(),
                        customer.getSecondName(),
                        String.valueOf(customer.getNoOfBurgers())
                );
                data1.add(rowData);
            }
        }

        tableView.setItems(data1);
    }

    public void storeData() throws IOException {
        writeToFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Saved Data");
        alert.showAndWait();
    }

    public void loadData() throws FileNotFoundException {


        clearTable();

//        set table data

        tableFirstName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        tableLastName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        tableBurgers.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

        List<Customer> allCustomers  = readFromFile();

        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            if (customer != null) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        customer.getFirstName(),
                        customer.getSecondName(),
                        String.valueOf(customer.getNoOfBurgers())
                );
                data1.add(rowData);
            }
        }

        tableView.setItems(data1);
        update();

    }

    public void newCustomerAdd(){
        if(!addCustomerFirstName.getText().isEmpty()){
            if(!addCustomerSecondName.getText().isEmpty()){
                if(!noOfBurgers.getText().isEmpty()){
                    Customer newCustomer = new Customer(addCustomerFirstName.getText(), addCustomerSecondName.getText(), Integer.parseInt(noOfBurgers.getText()));
                    int returnValue =  addCustomer(newCustomer);

                    if(returnValue == 0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Not so much burgers");
                        alert.showAndWait();
                    }else if(returnValue == 1){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Added to Queue One");
                        alert.showAndWait();
                    }else if(returnValue == 2){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Added to Queue Two");
                        alert.showAndWait();
                    }else if(returnValue == 3){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Added to Queue Three");
                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("All queue are full. Added to waiting queue");
                        alert.showAndWait();
                    }
                }else{
                    textFieldEmptyMessage();
                }
            }else{
                textFieldEmptyMessage();
            }
        }else{
            textFieldEmptyMessage();
        }

        update();

    }

    public void removeCustomer2(){

        if(!removeQueueNo.getText().isEmpty()){
            if(!removePlace.getText().isEmpty()) {
               int returnValue = removeCustomer(Integer.parseInt(removeQueueNo.getText()),Integer.parseInt(removePlace.getText()));

            if(returnValue == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("No customer in this place");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Removed Successfully");
                alert.showAndWait();
            }
            }else{
                textFieldEmptyMessage();
            }
        }else{
            textFieldEmptyMessage();
        }

        update();

    }

    public void removedServedCustomer(){

        if(!removedServedQueueNo.getText().isEmpty()) {

           int returnValue =  removeServedCustomer(Integer.parseInt(removedServedQueueNo.getText()));

           if(returnValue == 0){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle(null);
               alert.setHeaderText(null);
               alert.setContentText("No customer in this place");
               alert.showAndWait();
           } else if(returnValue == 2){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Not so much burgers");
                alert.showAndWait();
            }else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle(null);
               alert.setHeaderText(null);
               alert.setContentText("Served Successfully");
               alert.showAndWait();
           }
            System.out.println("Not so much burgers");

        }else{
            textFieldEmptyMessage();
        }

        update();
    }

    public void addBurgersGUI(){
        if(!addBurgerNumberOfBurger.getText().isEmpty()) {

            int newStock = addBurgersToStock(Integer.parseInt(addBurgerNumberOfBurger.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("New burger Stock: "+ newStock);
                alert.showAndWait();

        }else{
            textFieldEmptyMessage();
        }
        update();
    }

    private void textFieldEmptyMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all the fields");
        alert.showAndWait();
    }

    @FXML
    private void searchCustomer(){

        if(!searchFirstName.getText().isEmpty()) {

            String searchCustomer = searchFirstName.getText();

            if(!searchCustomer.isEmpty()) {

                tableFirstName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
                tableLastName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
                tableBurgers.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

                List<Customer> allCustomers = allCustomer();

                ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

                for (Customer customer : allCustomers) {
                    if (customer != null) {
                        ObservableList<String> rowData = FXCollections.observableArrayList(
                                customer.getFirstName(),
                                customer.getSecondName(),
                                String.valueOf(customer.getNoOfBurgers())
                        );

                        if (customer.getFirstName().toLowerCase().equals(searchCustomer.toLowerCase())) {
                            data1.add(rowData);
                        }
                    }
                }
                tableView.setItems(data1);
            }
        }else{
            update();
        }
    }

    public void update(){
        mouseExit1();
        mouseExit2();
        mouseExit3();
        mouseExit4();
        mouseExit5();
        mouseExit6();
        mouseExit7();

        double [] income =  getIncome();

        firstQueueIncome.setText(String.valueOf(income[0]));
        secondQueueIncome.setText(String.valueOf(income[1]));
        thirdQueueIncome.setText(String.valueOf(income[2]));
        noOfBurgerLabel.setText(String.valueOf(getBurger()));

        if(validateStock()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Stock is going low!!!!: Available stock: " + getBurger());
            alert.showAndWait();
        }

        Image man= new Image(getClass().getResourceAsStream("man.png"));
        Image feet = new Image(getClass().getResourceAsStream("feet.png"));

        for (int i = 0; i < 5; i++) {
            if (FoodQueueOne.getQueueLength() > i) {
                if (FoodQueueOne.customers[i] == null) {
                    if(i == 0){
                        c1c1.setImage(feet);
                    }else if(i == 1){
                        c1c2.setImage(feet);
                    }

                } else {
                    if(i == 0){
                        c1c1.setImage(man);
                    }else if(i == 1){
                        c1c2.setImage(man);
                    }
                }

            }

            if (FoodQueueTwo.getQueueLength() > i) {
                if (FoodQueueTwo.customers[i] == null) {
                    if(i == 0){
                        c2c1.setImage(feet);
                    }else if(i == 1){
                        c2c2.setImage(feet);
                    }else if(i == 2){
                        c2c3.setImage(feet);
                    }
                } else {
                    if(i == 0){
                        c2c1.setImage(man);
                    }else if(i == 1){
                        c2c2.setImage(man);
                    }else if(i == 2){
                        c2c3.setImage(man);
                    }
                }

            }

            if (FoodQueueThree.getQueueLength() > i) {
                if (FoodQueueThree.customers[i] == null) {

                    if(i == 0){
                        c3c1.setImage(feet);
                    }else if(i == 1){
                        c3c2.setImage(feet);
                    }else if(i == 2){
                        c3c3.setImage(feet);
                    }else if(i == 3){
                        c3c4.setImage(feet);
                    }else if(i == 4){
                        c3c5.setImage(feet);
                    }
                } else {
                    if(i == 0){
                        c3c1.setImage(man);
                    }else if(i == 1){
                        c3c2.setImage(man);
                    }else if(i == 2){
                        c3c3.setImage(man);
                    }else if(i == 3){
                        c3c4.setImage(man);
                    }else if(i == 4){
                        c3c5.setImage(man);
                    }
                }

            }
        }

        emptyAll();

        clearTable();

//        set table data

        tableFirstName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        tableLastName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        tableBurgers.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));

        List<Customer> allCustomers  = allCustomer();

        ObservableList<ObservableList<String>> data1 = FXCollections.observableArrayList();

        for (Customer customer : allCustomers) {
            if (customer != null) {
                ObservableList<String> rowData = FXCollections.observableArrayList(
                        customer.getFirstName(),
                        customer.getSecondName(),
                        String.valueOf(customer.getNoOfBurgers())
                );
                data1.add(rowData);
            }
        }

        tableView.setItems(data1);

    }


    public void clearTable(){
        ObservableList<ObservableList<String>> data = tableView.getItems();
        data.clear();
    }



    private void emptyAll(){
        addCustomerFirstName.setText("");
        addCustomerSecondName.setText("");
        noOfBurgers.setText("");
        removeQueueNo.setText("");
        removePlace.setText("");
        removedServedQueueNo.setText("");
        addBurgerNumberOfBurger.setText("");
        searchFirstName.setText("");
    }

    @FXML
    public void mouseEnter1() {
        menuButton1.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter2() {
        menuButton2.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter3() {
        menuButton3.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter4() {
        menuButton4.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter5() {
        menuButton5.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter6() {
        menuButton6.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter7() {
        menuButton7.setStyle("-fx-background-color: #28025c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter8() {
        menuButton8.setStyle("-fx-background-color: #28025c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter9() {
        button9.setStyle("-fx-background-color: #28025c; -fx-text-fill: white; -fx-cursor: hand;");
    }

    @FXML
    public void mouseEnter10() {
        button10.setStyle("-fx-background-color: #28025c; -fx-text-fill: white; -fx-cursor: hand;");
    }

    @FXML
    public void mouseExit1() {

        if(menuButton != 1){
            menuButton1.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }

    }

    @FXML
    public void mouseExit2() {
        if(menuButton != 2){
            menuButton2.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit3() {
        if(menuButton != 3){
            menuButton3.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit4() {
        if(menuButton != 4){
            menuButton4.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit5() {
        if(menuButton != 5){
            menuButton5.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit6() {
        if(menuButton != 6){
            menuButton6.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit7() {
        if(menuButton != 7){
            menuButton7.setStyle("-fx-background-color: #41008c; -fx-text-fill: white;-fx-font-size: 15;-fx-cursor: hand;");
        }
    }

    @FXML
    public void mouseExit8() {
            menuButton8.setStyle("-fx-background-color: #34017a; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

    }

    @FXML
    public void mouseExit9() {
        button9.setStyle("-fx-background-color: #34017a; -fx-text-fill: white;  -fx-cursor: hand;");

    }

    @FXML
    public void mouseExit10() {
        button10.setStyle("-fx-background-color: #34017a; -fx-text-fill: white; -fx-cursor: hand;");

    }
}