package ex2.ui;

import ex2.controller.Controller;
import ex2.model.Character;
import ex2.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private final Controller appController = new Controller();
    Scanner scanner = new Scanner(System.in);

    /**
     * This method starts the user interface of the application.
     * and handels all the user input.
     */
    public void startUi() {
        while (true) {
            System.out.println("1. Product Operations");
            System.out.println("2. Character Operations");
            System.out.println("3. Filter, Adding and Sorting Methods");
            System.out.println("0. Exit");

            int option;

            option = scanner.nextInt();
            scanner.nextLine();

            if(option == 0) {
                System.out.println("Exiting....");
                break;
            }

            try {
                switch (option) {
                    case 1: displayProductOperations();
                        break;
                    case 2: displayCharacterOperations();
                        break;
                    case 3: displayFilterSortingFunctions();
                        break;
                    default: System.out.println("Invalid option, try again");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option");
            }
        }
    }

    private void displayProductOperations() {
        while (true) {
            System.out.println("1. Get All Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Remove Product");
            System.out.println("0. Exit");

            int option;

            option = scanner.nextInt();
            scanner.nextLine();

            if(option == 0) {
                System.out.println("Exiting....");
                break;
            }

            try {
                switch (option) {
                    case 1: getAllProducts();
                        break;
                    case 2: addProduct();
                        break;
                    case 3: updateProduct();
                        break;
                    case 4: deleteProduct();
                        break;
                    default: System.out.println("Invalid option, try again");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option");
            }
        }
    }

    private void deleteProduct() {
        this.getAllProducts();
        System.out.println("Select id of the product you would like to remove: ");
        int id;

        try {
            id = scanner.nextInt();
            scanner.nextLine();
            this.appController.removeProduct(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Id must be a number");
        }
    }

    private void updateProduct() {
        this.getAllProducts();

        System.out.println("Select id of the product you would like to update: ");

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter new product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter new product season: (Spring, Summer, Autumn, Winter): ");
        String season = scanner.nextLine();

        try {
            appController.updateProduct(id, name, price, season);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addProduct() {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter product region ");
        String season = scanner.nextLine();

        try {
            appController.addProduct(name, price, season);
            System.out.println("Product added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayFilterSortingFunctions() {
        while (true) {
            System.out.println("1. Filter Characters by Location");
            System.out.println("2. Display Characters Who Bought Products from a given Region");
            System.out.println("3. Sort Products Bought by Character");
            System.out.println("4. Add Product to Character");
            System.out.println("0. Exit");

            int option;

            option = scanner.nextInt();
            scanner.nextLine();

            if(option == 0) {
                System.out.println("Exiting....");
                break;
            }

            try {
                switch (option) {
                    case 1: filterCharactersByLocation();
                        break;
                    case 2: displayCharactersWhoBoughtProducts();
                        break;
                    case 3: displaySortProductsMenu();
                        break;
                    case 4: displayAddProductToCharacter();
                        break;
                    default: System.out.println("Invalid option, try again");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option");
            }
        }
    }

    private void displayAddProductToCharacter() {
        this.getAllCharacters();
        System.out.println("Select id of the client you would like to add a product to: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();

        this.getAllProducts();
        System.out.println("Select id of the product you would like to add: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        try {
            this.appController.addProductToClient(clientId, productId);
            System.out.println("Product added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void displaySortProductsMenu() {
        this.getAllCharacters();
        System.out.println("Select id of the client you would like to sort products for: ");

        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("1. Ascending Order");
            System.out.println("2. Descending Order");
            System.out.println("0. Exit");

            int option;
            option = scanner.nextInt();
            scanner.nextLine();

            if(option == 0) {
                System.out.println("Exiting....");
                return ;
            }

            switch (option) {
                case 1: displaySortedAscendingOrder(id);
                    break;
                case 2: displaySortedDescendingOrder(id);
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displaySortedDescendingOrder(int userId) {
        List<Product> productList = this.appController.getProductsDescending(userId);

        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private void displaySortedAscendingOrder(int userId) {
        List<Product> productList = this.appController.getProductsAscending(userId);

        for (Product product : productList) {
            System.out.println(product);
        }
    }

    private void displayCharactersWhoBoughtProducts() {
        System.out.println("Type a region: ");

        List<Character> filteredCharacters = new ArrayList<>();

        try {
            String season = scanner.nextLine();

            filteredCharacters = this.appController.getClientsWhoBoughtProducts(season);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (filteredCharacters.isEmpty()) {
            System.out.println("No clients found");
        }

        for (Character character : filteredCharacters) {
            System.out.println(character);
        }
    }

    private void filterCharactersByLocation() {
        List<Character> filteredCharacters = new ArrayList<>();

        System.out.println("Enter location: ");
        String location = scanner.nextLine();

        try {
            filteredCharacters = this.appController.getFilteredCharactersByLocation(location);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (filteredCharacters.isEmpty()) {
            System.out.println("No clients found");
        }

        for (Character character : filteredCharacters) {
            System.out.println(character);
        }
    }

    private void displayCharacterOperations() {
        while (true) {
            System.out.println("1. Get All Characters");
            System.out.println("2. Add Character");
            System.out.println("3. Update Character");
            System.out.println("4. Remove Character");
            System.out.println("0. Exit");

            int option;
            option = scanner.nextInt();
            scanner.nextLine();

            if(option == 0) {
                System.out.println("Exiting....");
                break;
            }

            try {
                switch (option) {
                    case 1: getAllCharacters();
                        break;
                    case 2: addCharacter();
                        break;
                    case 3: updateCharacter();
                        break;
                    case 4: deleteCharacter();
                        break;
                    default: System.out.println("Invalid option, try again");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option");
            }
        }
    }

    private void deleteCharacter() {
        this.getAllCharacters();
        System.out.println("Select id of the client you would like to remove: ");
        int id;

        try {
            id = scanner.nextInt();
            scanner.nextLine();
            this.appController.removeCharacter(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Id must be a number");
        }
    }

    private void updateCharacter() {
        this.getAllCharacters();
        System.out.println("Select id of the client you would like to update: ");

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new client name: ");
        String name = scanner.nextLine();

        System.out.println("Enter new client location: ");
        String location = scanner.nextLine();

        try {
            appController.updateCharacter(id, name, location);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addCharacter() {
        System.out.println("Enter client name: ");
        String name = scanner.nextLine();

        System.out.println("Enter client location: ");
        String location = scanner.nextLine();

        try {
            appController.addCharacter(name, location);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getAllCharacters() {
        List<Character> characters = this.appController.getAllClients();

        if (characters.isEmpty()) {
            System.out.println("No clients found");
            return ;
        }

        for (Character character : characters) {
            System.out.println(character);
        }
    }

    private void getAllProducts() {
        List<Product> products = this.appController.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products found");
            return ;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }
}
