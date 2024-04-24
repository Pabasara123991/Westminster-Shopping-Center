public abstract class Product {
    private int product_id;
    private String product_name;
    private int number_of_available_items;
    private int price;
    private String category;
    private int quantity;

    public Product(int product_id, String product_name, int number_of_available_items, int price,String category){
        this.product_id = product_id;
        this.product_name = product_name;
        this.number_of_available_items = number_of_available_items;
        this.price = price;
        this.category = category;
    }

    public Product(String productName, int quantity, int price) {
        this.product_name  = productName;
        this.quantity = quantity;
        this.price = price;}

    public int getProduct_id(){
        return product_id;
    }
    public String getProduct_name(){
        return product_name;
    }
    public  int getNumber_of_available_items(){
        return number_of_available_items;
    }
    public void setNumber_of_available_items(int number_of_available_items){
        this.number_of_available_items = number_of_available_items;
    }
    public int getPrice(){
        return price;
    }
    public String getCategory(){
        return category;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    //  creating  a toString method to save product details in a file as String
    public String toString() {
        if (this instanceof Electronics) {      //check if the product is an instance of Electronics
            Electronics electronicProduct = (Electronics) this;     //casting the Product type to Electronic type
            return String.format("%d, %s, %d, %d, %s, %s, %s",      //format the string with product details
                    product_id, product_name, number_of_available_items, price, category,
                    electronicProduct.getBrand(), electronicProduct.getWarranty_period());
        } else if (this instanceof Clothing) {      //check if the product is an instance of Clothing
            Clothing clothingProduct = (Clothing) this;
            return String.format("%d, %s, %d, %d, %s, %s, %s",
                    product_id, product_name, number_of_available_items, price, category,
                    clothingProduct.getSize(), clothingProduct.getColor());
        } else {
            // Handle unknown category differently based on the type of product
            return String.format("%d, %s, %d, %d, %s, N/A, N/A",
                    product_id, product_name, number_of_available_items, price, category);
        }
    }


    public abstract void displayDetails();
}