public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(int product_id, String product_name, int number_of_available_items, int price, String category, String size, String color){
        super(product_id, product_name, number_of_available_items, price, category);
        this.size = size;
        this.color = color;
    }

    public Clothing(String productName, int quantity, int price) {
        super(productName, quantity, price);
    }

    public String getSize(){
        size.toUpperCase();
        return size;
    }
    public String getColor(){
        return color;
    }

    public String getClothingInfo(){
        String cInfo = getSize()+ "," + getColor();
        return cInfo;
    }

    @Override
    public void displayDetails(){
        System.out.println("Clothing Product Details: ");
        System.out.println("Product ID: " + getProduct_id());
        System.out.println("Product Name: " + getProduct_name());
        System.out.println("Size: " + size);
        System.out.println("color: " + color);
        System.out.println("Product Price: " + getPrice());
        System.out.println("Stock Count: " + getNumber_of_available_items());
    }
}
