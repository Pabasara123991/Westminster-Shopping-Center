import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WestminsterShoppingFrame implements ActionListener {
    public static JFrame frame;    // Declare the frame as a class variable to access in methods
    public static JButton cartButton; //Declare the cart button as a class variable to access in methods
    public static JTable table = createTable(new ArrayList<>());    //create a JTable with empty arraylist
    public static String dropDownOption;
    static ShoppingCart finalshoppingCart = new ShoppingCart();
    public static void westminsterFrame(WestminsterShoppingManager manager, ShoppingCart finalshoppingCart) {
        frame = new JFrame("Westminster Shopping Center");
        frame.setSize(700, 700);
        frame.setLayout(new GridLayout(3, 1));
        Font font1 = new Font("Arial", Font.PLAIN, 14);

        JPanel topPanel = new JPanel(new GridLayout(1,3));
        JPanel centerPanel = new JPanel(new GridLayout(1,1));
        JPanel bottomPanel = new JPanel(new GridLayout(1,2));

        JPanel topPanel1 = new JPanel(new GridBagLayout());
        JPanel topPanel2 = new JPanel(new GridBagLayout());
        JPanel topPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel selectLabel = new JLabel("Select Product Category");
        selectLabel.setFont(font1);
        topPanel1.add(selectLabel);
        topPanel.add(topPanel1);

        String[] productType = {"All", "Electronic", "Clothing"};
        JComboBox comboBox = new JComboBox(productType);
        comboBox.setFont(font1);
        topPanel2.add(comboBox);
        topPanel.add(topPanel2);

        cartButton = new JButton("Shopping Cart");
        cartButton.addActionListener(new WestminsterShoppingFrame());
        cartButton.setFont(font1);
        topPanel3.add(cartButton);
        topPanel.add(topPanel3);
        frame.add(topPanel);

        table = createTable(manager.getProductList());
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane);
        frame.add(centerPanel);

        JPanel bottomPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel detailLabel = new JLabel("Selected Product - Details                                   ");
        Font font3 = new Font("Arial", Font.BOLD, 14);
        detailLabel.setFont(font3);
        bottomPanel1.add(detailLabel);
        bottomPanel.add(bottomPanel1);

        JLabel idLabel = new JLabel();
        JLabel nameLabel = new JLabel();
        JLabel categoryLabel = new JLabel();
        JLabel availItemsLabel = new JLabel();
        JLabel priceLabel = new JLabel();
        JLabel brandLabel = new JLabel();
        JLabel warrantyLabel = new JLabel();
        JLabel sizeLabel = new JLabel();
        JLabel colorLabel = new JLabel();

        Font font2 = new Font("Arial", Font.PLAIN, 13);
        idLabel.setFont(font2);
        nameLabel.setFont(font2);
        categoryLabel.setFont(font2);
        availItemsLabel.setFont(font2);
        priceLabel.setFont(font2);
        brandLabel.setFont(font2);
        warrantyLabel.setFont(font2);
        sizeLabel.setFont(font2);
        colorLabel.setFont(font2);

        bottomPanel.add(new JLabel(" "));
        bottomPanel1.add(idLabel);
        bottomPanel1.add(nameLabel);
        bottomPanel1.add(categoryLabel);
        bottomPanel1.add(availItemsLabel);
        bottomPanel1.add(priceLabel);
        bottomPanel1.add(brandLabel);
        bottomPanel1.add(warrantyLabel);
        bottomPanel1.add(sizeLabel);
        bottomPanel1.add(colorLabel);

        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setFont(font1);
        bottomPanel2.add(addToCartButton);
        bottomPanel.add(bottomPanel2);
        frame.add(bottomPanel);

        ShoppingCart finalShoppingCart = WestminsterShoppingFrame.finalshoppingCart;
        ShoppingCart finalShoppingCart1 = WestminsterShoppingFrame.finalshoppingCart;
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String productName = (String) table.getValueAt(selectedRow, 1);
                    int quantity = 1;
                    int price = (int) table.getValueAt(selectedRow, 3);

                    Product product = null;
                    int productId = (int) table.getValueAt(selectedRow, 0);
                    String category = (String) table.getValueAt(selectedRow, 2);

                    if ("Electronic".equals(category)) {
                        // Create an instance of Electronics
                        product = new Electronics(productName,quantity,price);
                    } else if ("Clothing".equals(category)) {
                        // Create an instance of Clothing
                        product = new Clothing(productName, quantity, price);
                    }

                    finalShoppingCart.addProduct(product);

                    // Update the cart table
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            finalShoppingCart1.updateCartTable();
                        }
                    });

                }
            }
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropDownOption = (String) comboBox.getSelectedItem();
                updateTable();
                centerPanel.revalidate(); //showing the changes made in the GUI
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To avoid multiple events
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                        // Get the data of the selected product
                        int productId = (int) table.getValueAt(selectedRow, 0);
                        String productName = (String) table.getValueAt(selectedRow, 1);
                        String category = (String) table.getValueAt(selectedRow, 2);
                        int price = (int) table.getValueAt(selectedRow, 3);
                        String info = (String) table.getValueAt(selectedRow, 4);

                        // Get the selected product from the productList
                        Product selectedProduct = getProductById(productId, manager.getProductList());
                        int availableItems = selectedProduct.getNumber_of_available_items();
                        if(availableItems<3){
                            table.setSelectionBackground(Color.RED);
                        }
                        else{
                            table.setSelectionBackground(null);
                        }
                        // Hide all labels by default
                        idLabel.setVisible(false);
                        nameLabel.setVisible(false);
                        categoryLabel.setVisible(false);
                        availItemsLabel.setVisible(false);
                        priceLabel.setVisible(false);
                        brandLabel.setVisible(false);
                        warrantyLabel.setVisible(false);
                        sizeLabel.setVisible(false);
                        colorLabel.setVisible(false);

                        // Show common labels
                        idLabel.setText("Product ID: " + productId);
                        nameLabel.setText("Name: " + productName);
                        categoryLabel.setText("Category: " + category);
                        availItemsLabel.setText("Items Available: " + availableItems);
                        priceLabel.setText("Price: " + price);
                        idLabel.setVisible(true);
                        nameLabel.setVisible(true);
                        categoryLabel.setVisible(true);
                        availItemsLabel.setVisible(true);
                        priceLabel.setVisible(true);

                        // Show specific labels based on product type
                        if (selectedProduct instanceof Electronics) {
                            brandLabel.setText("Brand: " + ((Electronics) selectedProduct).getBrand());
                            warrantyLabel.setText("Warranty: " + ((Electronics) selectedProduct).getWarranty_period());
                            brandLabel.setVisible(true);
                            warrantyLabel.setVisible(true);
                        } else if (selectedProduct instanceof Clothing) {
                            sizeLabel.setText("Size: " + ((Clothing) selectedProduct).getSize());
                            colorLabel.setText("Color: " + ((Clothing) selectedProduct).getColor());
                            sizeLabel.setVisible(true);
                            colorLabel.setVisible(true);
                        }

                    }
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);  //make the frame visible
    }

    /**
     * If the selected option is "All" it shows all the products
     * If the selected option is "Electronics" it shows all the Electronic products
     * If the selected option is "Clothing" it shows all the Clothing products
     */
    private static void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);   //clear the existing rows in the table

        String selectedCategory = dropDownOption.equals("All") ? "" : dropDownOption;

        // Iterate through the product list and add rows to the table based on the selected category
        for (Product product : WestminsterShoppingManager.getProductList()) {
            if (selectedCategory.isEmpty() || product.getCategory().equals(selectedCategory)) {
                //get info based on the category
                String info = " ";
                if (product instanceof Electronics) {
                    info = ((Electronics) product).getElectronicInfo();
                } else if (product instanceof Clothing) {
                    info = ((Clothing) product).getClothingInfo();
                }
                //create an array with product details and add it to the table
                Object[] row = {product.getProduct_id(), product.getProduct_name(), product.getCategory(), product.getPrice(), info};
                model.addRow(row);
            }
        }
    }



    // Add this method to find a Product by its ID in the productList
    public static Product getProductById(int productId, ArrayList<Product> productList) {
        for (Product product : productList) {
            if (product.getProduct_id() == productId) {
                return product;
            }
        }
        return null; // Handle the case when the product is not found
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cartButton && finalshoppingCart != null) {
            finalshoppingCart.newWindowFrame();
        }

    }

    public static JTable createTable(ArrayList<Product> productList) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price $", "Info"};

        // Create a DefaultTableModel with specified column names and 0 rows
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Add rows to the model based on the products
        for (Product product : productList) {
            String info = " ";
            if (product instanceof Electronics) {
                info = ((Electronics) product).getElectronicInfo();
            }
            else if (product instanceof Clothing) {
                info = ((Clothing) product).getClothingInfo();
            }

            Object[] row = {product.getProduct_id(), product.getProduct_name(), product.getCategory(), product.getPrice(),info};
            model.addRow(row);

        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        return table;
    }

}


