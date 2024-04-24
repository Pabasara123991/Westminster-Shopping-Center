public class Electronics extends Product{
    private String brand;
    private String warranty_period;

    public Electronics(int product_id, String product_name, int number_of_available_items, int price,String category, String brand, String warranty_period){
        super(product_id, product_name, number_of_available_items, price, category);
        this.brand = brand;
        this.warranty_period = warranty_period;
    }

    public Electronics(String productName, int quantity, int price) {
        super(productName, quantity, price);
    }

    public String getBrand(){
        return brand;
    }
    public String getWarranty_period(){
        return warranty_period;
    }

    public String getElectronicInfo(){
        String eInfo = getBrand()+ "," + getWarranty_period();
        return eInfo;
    }


    @Override
    public void displayDetails(){
        System.out.println("Electronic Product Details: ");
        System.out.println("Product ID: " + getProduct_id());
        System.out.println("Product Name: " + getProduct_name());
        System.out.println("Brand: " + brand);
        System.out.println("Product Price: " + getPrice());
        System.out.println("Warranty Period: " + warranty_period);
        System.out.println("Stock Count: " + getNumber_of_available_items());
    }
}
