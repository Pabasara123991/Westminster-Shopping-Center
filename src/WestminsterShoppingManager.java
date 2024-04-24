import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class WestminsterShoppingManager implements ShoppingManager {
    protected static ArrayList<Product> productList;  // ArrayList to store products
    static Scanner input = new Scanner(System.in);

    public WestminsterShoppingManager() {   // Create a constructor to initialize the productList ArrayList
        productList = new ArrayList<>();
    }

    public static ArrayList<Product> getProductList() {     //method to get the productList
        return productList;
    }

    public static void main(String[] args) throws IOException {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        ShoppingCart shoppingCart = new ShoppingCart();     //create a shopping cart object to handle the cart
        WestminsterShoppingFrame westminsterFrame = null;   //Initialize the WestminsterShoppingFrame

        int option = 0;
        while (option != 7) {
            printMenu();
            try {
                System.out.println("Enter your Option: ");
                option = input.nextInt();
            } catch (InputMismatchException e) {
                // Handle the exception by clearing the invalid input
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    manager.addProduct();
                    break;
                case 2:
                    manager.deleteProduct();
                    break;
                case 3:
                    manager.printProductList();
                    break;
                case 4:
                    manager.saveToFile();
                    break;
                case 5:
                    manager.loadFromFile();
                    break;
                case 6:
                    // Only create the WestminsterShoppingFrame when option 6 is chosen
                    if (westminsterFrame == null) {
                        westminsterFrame = new WestminsterShoppingFrame();
                        WestminsterShoppingFrame.westminsterFrame(manager, WestminsterShoppingFrame.finalshoppingCart);
                    }
                    break;
                case 7:
                    System.out.println("\nPROGRAM EXITED");
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please try again!!!");
            }
        }
    }


    public static void printMenu() {
        System.out.println("-".repeat(10) + "MENU" + "-".repeat(10));
        System.out.println("Enter 1: Add Product");
        System.out.println("Enter 2: Delete Product");
        System.out.println("Enter 3: Print the List of Products");
        System.out.println("Enter 4: Save List of Products to a File");
        System.out.println("Enter 5: Load Product Details From The File");
        System.out.println("Enter 6: Open the Westminster Shopping Center");
        System.out.println("Enter 7: Exit");
    }

    @Override
    public void addProduct() {
        if (productList.size() >= 50) {
            System.out.println("Cannot add more products. Maximum limit is 50. You have added 50 products to the system");
            return;
        }

        do {
            String category;
            System.out.println("Dou you want to add an Electronic or a Clothing product to the system? ");
            System.out.println("Enter E to add an Electronic product");
            System.out.println("Enter C to add a Clothing product");
            System.out.println("Enter your option: ");
            category = input.next().toUpperCase();

            //check whether the user input is correct
            while (!category.equals("E") && !category.equals("C")) {
                System.out.println("Invalid option. Please enter E or C.");
                System.out.println("Enter E to add an Electronic product");
                System.out.println("Enter C to add a Clothing product");
                System.out.println("Enter your option: ");
                category = input.next().toUpperCase();
            }

            int product_id;
            while (true) {
                System.out.println("Enter the Product ID: ");
                String productID = input.next();
                input.nextLine();
                if (isNumeric(productID)) {
                    product_id = Integer.parseInt(productID);

                    // Check if a product with the same ID already exists
                    if (isProductIdUnique(product_id)) {
                        break;
                    } else {
                        System.out.println("Product with ID " + product_id + " already exists. Please enter a unique ID.");
                    }
                } else {
                    System.out.println("Invalid input, Product ID must be an integer. Please try again!!!");
                }

            }

            String product_name;
            while (true) {
                System.out.println("Enter the Product Name: ");
                product_name = input.nextLine().trim();  // allow the user to add more than one words and reads the user input without considering the spaces
                if (!isNumeric(product_name)) {  // check the whether the input contains letters
                    break;
                } else {
                    System.out.println("Invalid input, Product Name cannot ba a numeric value. Please try again!!!");
                }
            }

            int price;
            while (true) {
                System.out.println("Enter the Price of the Product: ");
                String productPrice = input.next();
                if (isNumeric(productPrice)) {
                    price = Integer.parseInt(productPrice);
                    break;
                } else {
                    System.out.println("Invalid input, Product Price must be an integer. Please try again!!!");
                }
            }
            int number_of_available_items;
            while (true) {
                System.out.println("Enter the Number of Items: ");
                String availableItems = input.next();
                input.nextLine();
                if (isNumeric(availableItems)) {
                    number_of_available_items = Integer.parseInt(availableItems);
                    break;
                } else {
                    System.out.println("Invalid input, Number of Items must be an integer. Please try again!!!");
                }
            }

            Product product;  // create a variable to store the product

            if (category.equals("E")) {

                String brand;
                while (true) {
                    System.out.println("Enter the Product Brand: ");
                    brand = input.nextLine().trim();
                    ;
                    if (!isNumeric(brand)) {
                        break;
                    } else {
                        System.out.println("Invalid input, Product Brand cannot ba a numeric value. Please try again!!!");
                    }
                }

                String warranty_period;
                while (true) {
                    System.out.println("Enter the Product Warranty Period: ");
                    warranty_period = input.nextLine().trim();
                    if (!isNumeric(warranty_period)) {
                        break;
                    } else {
                        System.out.println("Invalid input, Warranty Period should clearly mentioned with months and years. Please try again!!!");
                    }
                }

                product = new Electronics(product_id, product_name, number_of_available_items, price, "Electronic", brand, warranty_period); //create a new product
                productList.add(product); // add it to the productList
                System.out.println("Product has been successfully added to the system.");


            } else if (category.equals("C")) {

                String size;
                while (true) {
                    System.out.println("Enter the Product Size: ");
                    size = input.nextLine().trim();
                    if (!isNumeric(size)) {
                        break;
                    } else {
                        System.out.println("Invalid input, Product size must be mentioned in UK. Please try again!!!");
                    }
                }

                String color;
                while (true) {
                    System.out.println("Enter the Color of the Product: ");
                    color = input.nextLine().trim();
                    if (!isNumeric(color)) {
                        break;
                    } else {
                        System.out.println("Invalid input, Product Color cannot ba a numeric value. Please try again!!!");
                    }
                }
                product = new Clothing(product_id, product_name, number_of_available_items, price, "Clothing", size, color);
                productList.add(product);
                System.out.println("Product has been successfully added to the system.");

            } else {
                System.out.println("Invalid option, Please try again!!!");
                continue;
            }
            System.out.println("Do you want to add another product to the system?");
            System.out.println("If YES, Enter: Y");
            System.out.println("If NO, Enter: N");
            System.out.println("Enter your option: ");
            String addAnotherProduct = input.next().toUpperCase();

            if (!addAnotherProduct.equals("Y")) {
                break;
            }

        } while (true);

    }


    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);  // parse the string to an integer
            return true;
        } catch (NumberFormatException e) {
            return false;       // if an exception happens, string is not numeric
        }
    }

    // Method to check if a product with the given ID already exists
    private boolean isProductIdUnique(int productId) {
        for (Product product : productList) {
            if (product.getProduct_id() == productId) {
                return false; // Product with the same ID already exists
            }
        }
        return true; // Product ID is unique
    }

    @Override
    public void deleteProduct() {
        int productToDelete;

        while (true) {
            System.out.println("Enter the Product ID to delete: ");
            String productDelete = input.next();

            // check the input is an integer
            if (isNumeric(productDelete)) {
                productToDelete = Integer.parseInt(productDelete);
                break;
            } else {
                System.out.println("Invalid input, Product ID must be an integer. Please try again!!!");
            }
        }

        // Search for the product ID in the productList
        boolean productFound = false;

        for (Product product : productList) {
            if (product.getProduct_id() == productToDelete) {
                while (true) {
                    System.out.println("Enter the number of items to delete: ");
                    String itemDelete = input.next();

                    if (isNumeric(itemDelete)) {
                        int itemsToDelete = Integer.parseInt(itemDelete);

                        // check there are enough items in the stock to delete
                        if (itemsToDelete <= product.getNumber_of_available_items()) {
                            // Set the available items after deleting items from the product
                            product.setNumber_of_available_items(product.getNumber_of_available_items() - itemsToDelete);
                            System.out.println(itemsToDelete + " items were successfully deleted from product ID" + productToDelete + ".");
                            break; // Exit the loop after setting the number of available items
                        } else {
                            System.out.println("Invalid input. There aren't enough items in stock to delete. There are only " + product.getNumber_of_available_items() + " items in the stock.");

                            if (product.getNumber_of_available_items() > 0) {   //check if the stock is zero
                                continue;
                            } else {
                                // If stock is zero, print the message and exit the loop
                                System.out.println("Stock is zero. Unable to delete items.");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid input, please enter a valid number. Please try again!!!");
                    }
                }

                productFound = true;
                break;
            }
        }
        // print an error message if the product is not found
        if (!productFound) {
            System.out.println("Product with ID " + productToDelete + " not found.");
        }
    }

    @Override
    public void printProductList() {
        System.out.println("-".repeat(10) + "List of Products" + "-".repeat(10));

        if (productList.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : productList) {
                product.displayDetails();
                System.out.println();
            }
        }
    }

    @Override
    public void saveToFile() throws IOException {
        //create a fileWriter to write to the file
        FileWriter saveFile = new FileWriter("Product_Details.txt", false);  // false to overwrite the file
        for (Product product : productList) {   //iterate through each product and write them as strings to the file
            saveFile.write(product.toString() + "\n");
        }
        saveFile.close();
        System.out.println("Successfully saved the data into a file");
    }

    @Override
    public void loadFromFile() {
        try {
            File file = new File("Product_Details.txt");    //create a File object
            Scanner file_reader = new Scanner(file);    //create a scanner to read the file

            // Clear the existing productList to load the updated data
            productList.clear();

            while (file_reader.hasNextLine()) { //this checks whether there is another line to read next. This continues if there are lines to read
                String productData = file_reader.nextLine();    // reads the next line and returns it a s a string and store it in a variable
                String[] data = productData.split(", ");    //take a line from file and split it when reach a "," , and store it in a array

                // get the data from the array
                int product_id = Integer.parseInt(data[0]);
                String product_name = data[1];
                int number_of_available_items = Integer.parseInt(data[2]);
                int price = Integer.parseInt(data[3]);
                String category = data[4];

                Product product;

                //check the category and store the product
                if (category.equals("Electronic")) {
                    String brand = data[5];
                    String warranty_period = data[6];
                    product = new Electronics(product_id, product_name, number_of_available_items, price, category, brand, warranty_period);
                } else if (category.equals("Clothing")) {
                    String size = data[5];
                    String color = data[6];
                    product = new Clothing(product_id, product_name, number_of_available_items, price, category, size, color);
                } else {
                    // Handle unknown category differently based on the type of product
                    product = null;
                }

                // Add the created product to productList
                if (product != null) {
                    productList.add(product);
                }
            }

            file_reader.close();
            System.out.println("Data loaded successfully!");
        } catch (Exception e) {         //handle any exception error
            System.out.println("Error in Loading Data: " + e.getMessage());
        }
    }

}

