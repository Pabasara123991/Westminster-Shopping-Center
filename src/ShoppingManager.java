import java.io.IOException;

public interface ShoppingManager {
    void addProduct();
    void deleteProduct();
    void printProductList();
    void saveToFile() throws IOException;
    void loadFromFile();
}
