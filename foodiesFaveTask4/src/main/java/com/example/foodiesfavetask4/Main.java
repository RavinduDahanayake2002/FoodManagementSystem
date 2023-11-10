package com.example.foodiesfavetask4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main extends Application {

    private static int burgersStock = 50;
    private static final double burgerPrice = 650;
    private static boolean isExit = false;
    private static final String INPUT_FILE_PATH = "E:\\foodie-out.txt";
    private static final String OUTPUT_FILE_PATH = "E:\\foodie-out.txt";
    static final FoodQueue FoodQueueOne = new FoodQueue(2);
    static final FoodQueue FoodQueueTwo = new FoodQueue(3);
    static final FoodQueue FoodQueueThree = new FoodQueue(5);
    private static final FoodQueue FoodQueueWaiting = new FoodQueue(100);

    private static double [] incomeArray = {0,0,0};

    private double xOffset = 0;
    private double yOffset = 0;

    public int getBurger(){
        return burgersStock;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("foodFave.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        Pane rootPane = new Pane();
        rootPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        rootPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        // For testing purposes must comment when uploading

        do {
            //print menu
            showMenu();

            //take selected item from menu
            Scanner in = new Scanner(System.in);
            String menuSelection = in.nextLine();

            //pass selected item to switch statement to execute.
            selectMenuItem(menuSelection);

        } while (!isExit);
    }

    public static void showMenu() {
        System.out.println("\n\t\tMenu");
        System.out.println("\t------------");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customer Sorted in alphabetical order (Do not use library sort routine)");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data form file");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("110 or IFQ: View income of queues.");
        System.out.println("112 or GUI : Launch the GUI");
        System.out.println("999 or EXT: Exit the Program");

        System.out.print("\nPlease select a item from the Menu: ");
    }

    public static boolean Exit(String exitCode) {
        //check exit code
        if (Objects.equals(exitCode, "999") || Objects.equals(exitCode, "EXT")) {
            System.out.println("Thank you for using fast foodie system. Bye.......");
            return true;
        }
        return false;
    }

    public static void selectMenuItem(String menuItem) throws IOException {

        Scanner in = new Scanner(System.in);

        switch (menuItem) {
            case "100", "VFQ" -> viewAllQueues();
            case "101", "VEQ" -> viewEmptyQueues();
            case "102", "ACQ" -> {

                System.out.print("\nEnter customer first name: ");
                String firstName = in.nextLine();

                System.out.print("\nEnter customer second name: ");
                String secondName = in.nextLine();

                System.out.print("\nEnter no of burgers required: ");
                int noOfBurgers = Integer.parseInt(in.nextLine());

                Customer newCustomer = new Customer(firstName, secondName, noOfBurgers);
                addCustomer(newCustomer);
                validateStock();
            }
            case "103", "RCQ" -> {

                System.out.print("\nPlease enter the Queue no: [1,2 or 3] ");
                int selectedQueueNo = Integer.parseInt(in.nextLine());
                System.out.println("\nPlease enter place [1, 2]: ");
                int selectedPlaceNo = Integer.parseInt(in.nextLine());
                removeCustomer(selectedQueueNo,selectedPlaceNo);
            }
            case "104", "PCQ" -> {

                System.out.print("\nPlease enter the Queue no: [1,2 or 3] ");
                int selectedQueueNo = Integer.parseInt(in.nextLine());
                removeServedCustomer(selectedQueueNo);
            }
            case "105", "VCS" -> sortCustomers();
            case "106", "SPD" -> writeToFile();
            case "107", "LPD" -> readFromFile();
            case "108", "STK" -> viewRemainingBurgers();
            case "109", "AFS" -> {

                System.out.print("\nHow many burgers do you need to add to the stock? ");
                String suppliedStock = in.nextLine();
                addBurgersToStock(Integer.parseInt(suppliedStock));
            }
            case "110", "IFQ" -> income();
            case "112", "GUI" ->  launch();
            case "999", "EXT" -> isExit = Exit(menuItem);
            default -> System.out.println("Wrong number.");
        }
    }

    public static void viewAllQueues() {
        System.out.println("\t*****************");
        System.out.println("\t*\tCashiers\t*");
        System.out.println("\t*****************");

        for (int i = 0; i < 5; i++) {
            if (FoodQueueOne.getQueueLength() > i) {
                if (FoodQueueOne.customers[i] == null) {
                    System.out.print("\t\t" + "X");
                } else {
                    System.out.print("\t\t" + "O");
                }

            } else {
                System.out.print("\t\t");
            }
            if (FoodQueueTwo.getQueueLength() > i) {
                if (FoodQueueTwo.customers[i] == null) {
                    System.out.print("\t" + "X");
                } else {
                    System.out.print("\t" + "O");
                }

            } else {
                System.out.print("\t");
            }
            if (FoodQueueThree.getQueueLength() > i) {
                if (FoodQueueThree.customers[i] == null) {
                    System.out.print("\t" + "X");
                } else {
                    System.out.print("\t" + "O");
                }

            } else {
                System.out.print("\t");
            }
            System.out.println("  ");
        }
        System.out.println("X - Not Occupied O - Occupied");
        System.out.println();

    }

    public static void viewEmptyQueues() {
        boolean isEmpty = true;
        for (Customer customer : FoodQueueOne.customers) {
            if (customer != null) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            System.out.println("Queue One is empty.");
        }
        isEmpty = true;

        for (Customer customer : FoodQueueTwo.customers) {
            if (customer != null) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            System.out.println("Queue Two is empty.");
        }
        isEmpty = true;

        for (Customer customer : FoodQueueThree.customers) {
            if (customer != null) {
                isEmpty = false;
                break;
            }
        }
        if (isEmpty) {
            System.out.println("Queue Three is empty.");
        }
    }

    public static int addBurgersToStock(int suppliedStock) {
        burgersStock += suppliedStock;
        System.out.println("New burger Stock: " + burgersStock + "\n");
        return burgersStock;
    }

    public static void viewRemainingBurgers() {
        System.out.println("\nRemaining burger Stock: " + burgersStock);
    }

    public static int addCustomer(Customer newCustomer) {

        int [] array = arrayLength();
        int returnValue = 0;

        if(burgersStock < newCustomer.getNoOfBurgers()){
            System.out.println("Not so much burgers");
        }else {

            String queueAddStatus;

            if (array[0] <= array[1] && array[0] <= array[2] && array[0] != FoodQueueOne.customers.length) {
                FoodQueueOne.addCustomers(newCustomer);
                queueAddStatus = "Added to Queue One.";
                returnValue = 1;
            } else if (array[1] <= array[0] && array[1] <= array[2] && array[1] != FoodQueueTwo.customers.length) {
                FoodQueueTwo.addCustomers(newCustomer);
                queueAddStatus = "Added to Queue Two.";
                returnValue = 2;
            } else if (array[2] != FoodQueueThree.customers.length) {
                FoodQueueThree.addCustomers(newCustomer);
                queueAddStatus = "Added to Queue Three.";
                returnValue = 3;
            } else {
                FoodQueueWaiting.addCustomers(newCustomer);
                queueAddStatus = "All queue are full. Added to waiting queue.";
                returnValue = 4;

            }

            System.out.println(queueAddStatus);
        }
return returnValue;
    }

    public static int[] arrayLength(){

        int array1 = 0;
        int array2 = 0;
        int array3 = 0;

        for(int i = 0; FoodQueueOne.customers.length > i; i++){
            if(FoodQueueOne.customers[i] != null){
                array1 += 1;
            }
        }

        for(int i = 0; FoodQueueTwo.customers.length > i; i++){
            if(FoodQueueTwo.customers[i] != null){
                array2 += 1;
            }
        }

        for(int i = 0; FoodQueueThree.customers.length > i; i++){
            if(FoodQueueThree.customers[i] != null){
                array3 += 1;
            }
        }

        int [] array = {array1,array2,array3};
        return array;

    }

    public static boolean validateStock() {

        boolean returnValue = false;

        if (burgersStock <= 10) {
            System.out.println("\nStock is going low!!!!: Available stock: " + burgersStock);
            returnValue = true;
        }
        return returnValue;
    }

    public static int removeCustomer(int selectedQueueNo, int selectedPlaceNo) {

        String message = null;
        int returnValue = 0;

        if (selectedQueueNo == 1) {
//            System.out.println("\nPlease enter place [1, 2]: ");
//            selectedPlaceNo = in.nextInt();
            if(FoodQueueOne.customers[selectedPlaceNo - 1] != null) {
                message = FoodQueueOne.removeCustomer(selectedPlaceNo);
                AllocateCustomersFromWaitingQueue();
                returnValue = 1;
            }else{
                System.out.println("No customer in this place");
            }
        }
        if (selectedQueueNo == 2) {
//            System.out.println("\nPlease enter place [1, 2 or 3]: ");
//            selectedPlaceNo = in.nextInt();
            if(FoodQueueTwo.customers[selectedPlaceNo - 1] != null) {
                message = FoodQueueTwo.removeCustomer(selectedPlaceNo);
                AllocateCustomersFromWaitingQueue();
                returnValue = 1;
            }else{
                System.out.println("No customer in this place");
            }
        }
        if (selectedQueueNo == 3) {
//            System.out.println("\nPlease enter place [1, 2, 3, 4 or 5]: ");
//            selectedPlaceNo = in.nextInt();
            if(FoodQueueThree.customers[selectedPlaceNo - 1] != null) {
                message = FoodQueueThree.removeCustomer(selectedPlaceNo);
                AllocateCustomersFromWaitingQueue();
                returnValue = 1;
            }else{
                System.out.println("No customer in this place");
            }
        }

        System.out.println(message);

        return returnValue;
    }

    public static int removeServedCustomer(int selectedQueueNo) {
        String message = null;

        int returnValue = 0;

        if (selectedQueueNo == 1) {
            if(FoodQueueOne.customers[0] != null){

                if(burgersStock >=  FoodQueueOne.customers[0].getNoOfBurgers()) {
                    burgersStock -= FoodQueueOne.customers[0].getNoOfBurgers();
                    incomeArray[0] += FoodQueueOne.customers[0].getNoOfBurgers();
                    message = FoodQueueOne.removeCustomer();
                    AllocateCustomersFromWaitingQueue();
                    returnValue = 1;
                }else{
                    returnValue = 2;
                    System.out.println("Not so much burgers");
                }
            }else{
                System.out.println("No Customer in this queue");
            }

        }
        else if (selectedQueueNo == 2) {

            if(FoodQueueTwo.customers[0] != null) {
                if(burgersStock >=  FoodQueueTwo.customers[0].getNoOfBurgers()) {
                    burgersStock -= FoodQueueTwo.customers[0].getNoOfBurgers();
                    incomeArray[1] += FoodQueueTwo.customers[0].getNoOfBurgers();
                    message = FoodQueueTwo.removeCustomer();
                    AllocateCustomersFromWaitingQueue();
                    returnValue = 1;
                }else{
                    returnValue = 2;
                    System.out.println("Not so much burgers");
                }
            }else{
                System.out.println("No Customer in this queue");
            }
        }
        else if (selectedQueueNo == 3) {
            if(FoodQueueThree.customers[0] != null) {
                if(burgersStock >=  FoodQueueThree.customers[0].getNoOfBurgers()) {
                    burgersStock -= FoodQueueThree.customers[0].getNoOfBurgers();
                    incomeArray[2] += FoodQueueThree.customers[0].getNoOfBurgers();
                    message = FoodQueueThree.removeCustomer();
                    AllocateCustomersFromWaitingQueue();
                    returnValue = 1;
                }else{
                    returnValue = 2;
                    System.out.println("Not so much burgers");
                }
            }else{
                System.out.println("No Customer in this queue");
            }
        }
        else{
            message = "wrong input!. Please check your input and try again.";
        }

        System.out.println(message);

        return returnValue;
    }

    public static List<Customer> sortCustomers() {

        List<Customer> allCustomers = new ArrayList<>();

        for (Customer customer : FoodQueueOne.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueTwo.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueThree.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        allCustomers.sort((c1, c2) -> c1.getFirstName().compareToIgnoreCase(c2.getFirstName()));

        printCustomerQueues(allCustomers);

    return allCustomers;

    }

    public static List<Customer> allCustomer(){
        List<Customer> allCustomers = new ArrayList<>();

        for (Customer customer : FoodQueueOne.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueTwo.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueThree.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        return allCustomers;
    }


    public static void writeToFile() throws IOException {

        List<Customer> allCustomers = new ArrayList<>();

        for (Customer customer : FoodQueueOne.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueTwo.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        for (Customer customer : FoodQueueThree.customers) {
            if (customer != null) {
                allCustomers.add(customer);
            }
        }

        FileWriter fileWriter = new FileWriter(OUTPUT_FILE_PATH);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (Customer customer : allCustomers) {
            String customerData = customer.getFirstName() + "," + customer.getSecondName() +
                    "," + customer.getNoOfBurgers();
            bufferedWriter.write(customerData);
            bufferedWriter.newLine();
        }

        System.out.println("Saved Data!.");
        bufferedWriter.close();
        fileWriter.close();

//            return true;

    }

    public static List<Customer> readFromFile() throws FileNotFoundException {
        List<Customer> allCustomers = new ArrayList<>();

        File file = new File(INPUT_FILE_PATH);

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH));

                String thisLine = null;
                while ((thisLine = br.readLine()) != null) {
                    String[] words = thisLine.split(",");

                    Customer customer = new Customer(words[0], words[1], Integer.parseInt(words[2]));
                    allCustomers.add(customer);

                    addCustomer(customer);

                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Saved data is not displayed");
        }

        printCustomerQueues(allCustomers);

        return allCustomers;
    }

    public static void income(){
        System.out.println("First Queue income: " + incomeArray[0] * burgerPrice);
        System.out.println("Second Queue income: " + incomeArray[1] * burgerPrice);
        System.out.println("Third Queue income: "+ incomeArray[2] * burgerPrice);

    }

    public static double[] getIncome(){

        double[] income = {0,0,0};

        income[0] = incomeArray[0] * burgerPrice;
        income[1] = incomeArray[1] * burgerPrice;
        income[2] = incomeArray[2] * burgerPrice;

        return income;
    }

    public static double getBurgerPrice(){
        return burgerPrice;
    }

    private static void printCustomerQueues(List<Customer> customers){

        for (Customer element : customers) {
            System.out.println("");
            System.out.println("First Name: " + element.getFirstName());
            System.out.println("Last Name: " + element.getSecondName());
            System.out.println("Number Of Burgers: " + element.getNoOfBurgers());
        }
    }

    private static void AllocateCustomersFromWaitingQueue(){

        if(FoodQueueWaiting.customers[0] != null){
            addCustomer(FoodQueueWaiting.customers[0]);
//            System.out.println(FoodQueueWaiting.customers[0].getFirstName());
            FoodQueueWaiting.customers[0] = null;
        }

    }


}