package com.stocks.sales_purchase_service.service;

import com.stocks.product_service.model.Product;
import com.stocks.sales_purchase_service.exception.NotFoundException;
import com.stocks.sales_purchase_service.model.Purchase;
import com.stocks.sales_purchase_service.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing purchase transactions.
 * This class provides methods for creating, retrieving, updating, and deleting purchase records.
 */
@Service
public class PurchaseService {
	
	private static final int sellerId = 1;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Base URLs for microservices
     */
    private final String productBaseUrl = "http://localhost:8083/api/products/";
    
    
    /**
     * Creates a new purchase transaction.
     *
     * @param productId The ID of the product being purchased.
     * @param quantity  The quantity of the product being purchased.
     * @return True if the purchase was successfully created; false otherwise.
     * @throws NotFoundException        If the product is not found.
     * @throws IllegalArgumentException If the requested quantity exceeds the available quantity in stock.
     * @throws RuntimeException         If there is an issue executing the stored procedure.
     */
    public boolean createPurchase(int productId, int quantity) {
        try {
            // Fetch product from Product microservice
            Product product = restTemplate.getForObject(productBaseUrl + "get-by-id/" + productId, Product.class);
            if (product == null) {
                throw new NotFoundException("Product not found with ID: " + productId);
            }

            // Check if the requested quantity is valid
            if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid quantity requested: " + quantity);
            }

            // Proceed to create purchase
            purchaseRepository.createPurchase(sellerId, productId, quantity);
            return true;
        } catch (RuntimeException ex) {
            // Handle any exceptions that occur during the stored procedure call
            throw new RuntimeException("Failed to create purchase: " + ex.getMessage());
        }
    }
    
    
    
    /**
     * Retrieves a purchase record by its ID.
     *
     * @param purchaseId The ID of the purchase to be retrieved.
     * @return The purchase entity with the specified ID.
     * @throws NotFoundException If no purchase is found with the specified ID.
     * @throws RuntimeException  If there is an error while retrieving the purchase.
     */
    public Purchase getPurchaseById(Integer purchaseId) {
        try {
            Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
            if (purchaseOptional.isPresent()) {
                return purchaseOptional.get();
            } else {
                throw new NotFoundException("Purchase not found with ID: " + purchaseId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve purchase: " + e.getMessage());
        }
    }
    
    

    /**
     * Retrieves all purchase records.
     *
     * @return A list of all purchase entities.
     * @throws RuntimeException If there is an error while retrieving the purchases.
     */
    public List<Purchase> getAllPurchases() {
        try {
            return purchaseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all purchases: " + e.getMessage());
        }
    }
    
    

    /**
     * Deletes a purchase record by its ID.
     *
     * @param purchaseId The ID of the purchase to be deleted.
     * @return True if the purchase was successfully deleted, false otherwise.
     * @throws NotFoundException If no purchase is found with the specified ID.
     * @throws RuntimeException  If there is an error while deleting the purchase.
     */
    public boolean deletePurchaseById(Integer purchaseId) {
        try {
            Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
            if (purchaseOptional.isPresent()) {
                purchaseRepository.deleteById(purchaseId);
                return true; // Purchase deleted successfully
            } else {
                throw new NotFoundException("Purchase not found with ID: " + purchaseId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete purchase: " + e.getMessage());
        }
    }

}
