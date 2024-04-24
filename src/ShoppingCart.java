import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    private List<Product> productList;
    private JTable cartTable;
    private JLabel totalnumLabel;
    private JLabel firstnumLabel;
    private JLabel threeItemsnumLabel;
    private JLabel finalTotalnumLabel;
    public ShoppingCart() {

        this.productList = new ArrayList<>();
        this.cartTable = createCartTable();
    }

    public void newWindowFrame(){
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(700,700);  //sets the width and height of the frame
        frame.setLayout(new GridLayout(2,1));
        Font font1 = new Font("Arial", Font.PLAIN, 14);
        Font font2 = new Font("Arial", Font.BOLD, 14);

        //create the table
        cartTable = new JTable();
        cartTable = createCartTable();
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        frame.add(cartScrollPane);

        //create panels
        JPanel calculationPanel = new JPanel(new GridLayout(1,2));
        JPanel infoPanel = new JPanel();
        JPanel pricePanel = new JPanel();

        //setting layouts for infoPanel and pricePanel
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        pricePanel.setLayout(new BoxLayout(pricePanel,BoxLayout.Y_AXIS));

        // Labels for displaying information
        JLabel totalLabel = new JLabel("Total");
        totalLabel.setFont(font1);
        JLabel firstLabel = new JLabel("First Purchase Discount (10%)");
        firstLabel.setFont(font1);
        JLabel threeItemsLabel = new JLabel("Three Items in same Category Discount (20%)");
        threeItemsLabel.setFont(font1);
        JLabel finalLabel = new JLabel("Final Total");
        finalLabel.setFont(font2);

        totalnumLabel = new JLabel();
        totalnumLabel.setFont(font1);
        firstnumLabel = new JLabel();
        firstnumLabel.setFont(font1);
        threeItemsnumLabel = new JLabel();
        threeItemsnumLabel.setFont(font1);
        finalTotalnumLabel = new JLabel();
        finalTotalnumLabel.setFont(font2);

        // Add labels to the first information panel
        infoPanel.add(totalLabel);
        infoPanel.add(firstLabel);
        infoPanel.add(threeItemsLabel);
        infoPanel.add(finalLabel);

        // Add labels to the second information panel
        pricePanel.add(totalnumLabel);
        pricePanel.add(firstnumLabel);
        pricePanel.add(threeItemsnumLabel);
        pricePanel.add(finalTotalnumLabel);

        calculationPanel.add(infoPanel);
        calculationPanel.add(pricePanel);
        frame.add(calculationPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); //make the frame visible
    }

    public static JTable createCartTable(){
        String[] columnNames = {"Product", "Quantity" , "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        return table;
    }

    public void updateCartTable() {
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Product product : productList) {
            int totalProductPrice = product.getPrice() * product.getQuantity();
            Object[] rowData = {product.getProduct_name(),product.getQuantity(), totalProductPrice};
            model.addRow(rowData);
        }

    }

    public void addProduct(Product product) {
        boolean productFound = false;

        // Check if the product is already in the cart
        for (Product cartProduct : productList) {
            if (areProductsEqual(cartProduct,product)) {
                // Product already in the cart, increase the quantity
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                productFound = true;
                break;
            }
        }
        if (!productFound) {
            // Product not in the cart, add it to the cart with quantity 1
            product.setQuantity(1);
            productList.add(product);
        }

        updateCartTable();
        updateCartLabels();
    }
    private boolean areProductsEqual(Product product1, Product product2) {
        // Compare products based on ID and other relevant attributes
        boolean idMatch = product1.getProduct_id() == product2.getProduct_id();
        boolean nameMatch = Objects.equals(product1.getProduct_name(), product2.getProduct_name());
        boolean priceMatch = product1.getPrice() == product2.getPrice();

        // Handle null categories
        boolean categoryMatch;
        if (product1.getCategory() == null && product2.getCategory() == null) {
            categoryMatch = true;
        } else if (product1.getCategory() != null) {
            categoryMatch = product1.getCategory().equals(product2.getCategory());
        } else {
            categoryMatch = false;
        }

        return idMatch && nameMatch && priceMatch && categoryMatch;
    }
    private int updateTotalNumLabel(){
        int totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice()* product.getQuantity();
        }
        return totalPrice;
    }

    private int updateFirstNumLabel(){
        int firstDiscount = 0;
        for (Product product : productList) {
            firstDiscount = (updateTotalNumLabel() * 10)/100 ;
        }
        return firstDiscount;
    }

    private int updateThreeItemsLabel() {
        int threeItemDiscount = 0;

        // Iterate through the products
        for (Product product1 : productList) {
            int categoryCount = product1.getQuantity();  //quantity of the product

            // Count the current product
            for (Product product2 : productList) {
                if (product1 != product2 && Objects.equals(product1.getCategory(), product2.getCategory())) {
                    categoryCount += product2.getQuantity();
                }
            }

            // Check if category count reaches three or more
            if (categoryCount >=3) {  // Use 2 instead of 3 since we're counting the current product as well
                threeItemDiscount = (updateTotalNumLabel() * 20) / 100;
                break;
            }
        }

        return threeItemDiscount;
    }


    private int updateFinalLabel(){
        int finalPrice = updateTotalNumLabel()-(updateFirstNumLabel()+updateThreeItemsLabel());
        return finalPrice;
    }
    public void updateCartLabels() {
        totalnumLabel.setText(String.valueOf(updateTotalNumLabel()));
        firstnumLabel.setText("-" + String.valueOf(updateFirstNumLabel()));
        threeItemsnumLabel.setText("-" + String.valueOf(updateThreeItemsLabel()));
        finalTotalnumLabel.setText(String.valueOf(updateFinalLabel()));
    }

}
