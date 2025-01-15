package ex2.controller;

import ex2.model.Character;
import ex2.model.Product;
import ex2.repository.CharacterRepository;
import ex2.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is the controller of the application which handles the business logic.
 */
public class Controller {
    private final CharacterRepository characterRepository = CharacterRepository.getInstance();
    private final ProductRepository productRepository = ProductRepository.getInstance();

    public Controller() {}

    public List<Product> getAllProducts() {
        return this.productRepository.getProducts();
    }

    /**
     * @param name
     * @param price
     * @param region
     */
    public void addProduct(String name, double price, String region) {

        if(name == null || price < 0 || region == null) {
            throw new IllegalArgumentException("Please enter valid data");
        }

        try {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setRegion(region);
            productRepository.addProduct(product);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Product could not be added");
        }
    }

    public void updateProduct(int id, String name, double price, String region) {
        this.removeProduct(id);
        this.addProduct(name, price, region);
    }

    public void removeProduct(int id) {
        this.productRepository.removeProduct(id);
    }

    public List<Character> getAllClients() {
        return this.characterRepository.getCharacters();
    }

    public void addCharacter(String name, String location) {
        if(name == null || location == null) {
            throw new IllegalArgumentException("Please enter valid data");
        }

        try {
            Character character = new Character();
            character.setName(name);
            character.setLocation(location);
            characterRepository.addCharacter(character);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Client could not be added");
        }
    }

    public void updateCharacter(int id, String name, String location) {
        this.removeCharacter(id);
        this.addCharacter(name, location);
    }

    public void removeCharacter(int id) {
        this.characterRepository.removeCharacter(id);
    }

    /**
     * This method returns a list of clients filtered by location.
     * @param location location of the characters
     * @return list of clients
     */
    public List<Character> getFilteredCharactersByLocation(String location) {
        if (location == null) {
            throw new IllegalArgumentException("Please enter valid location");
        }

        return this.characterRepository.getCharacters()
                .stream()
                .filter(character -> Objects.equals(character.getLocation(), location))
                .collect(Collectors.toList());
    }

    /**
     * This method returns a list of clients who bought products from the specified region.
     * @param region region of the product
     * @return list of clients who bought products from the specified region
     */
    public List<Character> getClientsWhoBoughtProducts(String region) {
        if (region == null) {
            throw new IllegalArgumentException("Please enter a valid region");
        }

        return this.characterRepository.getCharacters()
                .stream()
                .filter(c -> c.getProducts()
                        .stream()
                        .anyMatch(p -> p.getRegion() == region))
                .collect(Collectors.toList());
    }

    /**
     * @param userId id of the characters
     * @return
     */
    public List<Product> getProductsAscending(int userId) {
        return this.characterRepository.getCharacters()
                .stream()
                .filter(character -> character.getId() == userId)
                .findFirst()
                .map(Character::getProducts)
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .toList();
    }

    /**
     * This method returns a list of products in descending order.
     * @param userId id of the characters
     * @return list of products
     */
    public List<Product> getProductsDescending(int userId) {
        return this.characterRepository.getCharacters()
                .stream()
                .filter(character -> character.getId() == userId)
                .findFirst()
                .map(Character::getProducts)
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .toList();
    }

    /**
     * This method adds a product to a client.
     * @param clientId id of the characters
     * @param productId id of the product
     */
    public void addProductToClient(int clientId, int productId) {
        this.characterRepository.getCharacters()
                .stream()
                .filter(character -> character.getId() == clientId)
                .findFirst()
                .ifPresent(character -> {
                    Product productToAdd = this.productRepository.getProducts()
                            .stream()
                            .filter(product -> product.getId() == productId)
                            .findFirst()
                            .orElse(null);

                    if (productToAdd != null) {
                        List<Product> clientProducts = character.getProducts();
                        clientProducts.add(productToAdd);
                    } else {
                        System.out.println("Product not found with ID: " + productId);
                    }
                });
    }
}
