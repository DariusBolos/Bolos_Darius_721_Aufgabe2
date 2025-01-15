package ex2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a character in the application.
 */
public class Character {
    private int id;
    private String name;
    private String location;
    private List<Product> products;

    public Character(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.products = new ArrayList<>();
    }

    public Character() {
        this.products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", products=" + products +
                '}';
    }
}
