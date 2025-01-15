package ex2.repository;

import ex2.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a singleton repository for the Product model which performs the basic CRUD operations.
 */
public class ProductRepository {
    public static ProductRepository instance;
    private List<Product> products;

    private ProductRepository() {
        this.products = new ArrayList<>();
        products.add(new Product(1, "Sword", 300, "Finland"));
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }

        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        product.setId(products.size() + 1);
        this.products.add(product);
    }

    public void updateProduct(Product product) {
        this.products.set(products.indexOf(product), product);
    }

    public void removeProduct(int productId) {
        List<Product> newProducts = this.products
                .stream()
                .filter(p -> p.getId() != productId)
                .collect(Collectors.toCollection(ArrayList::new));

        this.setProducts(newProducts);
    }
}
