package com.stocks.sales_purchase_service.service;

import com.stocks.product_service.model.Product;
import com.stocks.product_service.model.Stock;
import com.stocks.sales_purchase_service.exception.NotFoundException;
import com.stocks.sales_purchase_service.model.Sale;
import com.stocks.sales_purchase_service.repository.SaleRepository;
import com.stocks.user_service.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing sales transactions.
 * This class provides methods for creating, retrieving, updating, and deleting sales records.
 */
@Service
public class SaleService {
	
	private static final int sellerId = 1;

    @Autowired
    private SaleRepository saleRepository;
    
    private final RestTemplate restTemplate;
     
   
    SaleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
 // Base URLs for microservices
    private final String userBaseUrl = "http://localhost:8083/api/users/";
    private final String productBaseUrl = "http://localhost:8083/api/products/";
    private final String stockBaseUrl = "http://localhost:8083/api/stocks/";


    
    
    /**
     * Creates a new sale transaction.
     *
     * @param sellerId         The ID of the seller initiating the sale.
     * @param customerId       The ID of the customer making the purchase.
     * @param productId        The ID of the product being sold.
     * @param requestedQuantity The quantity of the product requested by the customer.
     * @return True if the sale was successfully created; false otherwise.
     * @throws NotFoundException        If the customer, product, or stock is not found.
     * @throws IllegalArgumentException If the requested quantity exceeds the available quantity in stock,
     *                                   or if the sale creation fails.
     * @throws RuntimeException         If there is an issue fetching data from microservices.
     */
    public boolean createSale(int customerId, int productId, int requestedQuantity) {
        try {
            // Fetch customer from User microservice
            User customer = restTemplate.getForObject(userBaseUrl + "get-by-id/" + customerId, User.class);
            if (customer == null) {
                throw new NotFoundException("Customer not found with id: " + customerId);
            }

            // Fetch product from Product microservice
            Product product = restTemplate.getForObject(productBaseUrl + "get-by-id/" + productId, Product.class);
            if (product == null) {
                throw new NotFoundException("Product not found with id: " + productId);
            }

            // Fetch stock from Stock microservice
            Stock stock = restTemplate.getForObject(stockBaseUrl + "get-by-id/" + productId, Stock.class);
            if (stock == null) {
                throw new NotFoundException("Stock not found with product id: " + productId);
            }
            // Check if requested quantity is available in stock
            int availableQuantity = stock.getAvailableQuantity();
            
            // Proceed to create sale
//            boolean success = saleRepository.insertNewSale(sellerId, customerId, productId, requestedQuantity);
//            if (!success) {
//            	throw new IllegalArgumentException("Insufficient stock available for product ID " + productId +
//                        ".\nAvailable quantity: " + availableQuantity + ", Requested quantity: " + requestedQuantity);
//            }

            return true;

        } catch (RestClientException e) {
            // Handle any RestClientException (e.g., connection issues, server errors)
            throw new RuntimeException("Failed to create sale \n"+e.getMessage());
        }
    }
    
    

    /**
     * Retrieves a sale record by its ID.
     *
     * @param saleId The ID of the sale to be retrieved.
     * @return The sale entity with the specified ID.
     * @throws NotFoundException If no sale is found with the specified ID.
     * @throws RuntimeException If there is an error while retrieving the sale.
     */
    public Sale getSaleById(Integer saleId) {
        try {
            Optional<Sale> saleOptional = saleRepository.findById(saleId);
            if (saleOptional.isPresent()) {
                return saleOptional.get();
            } else {
                throw new NotFoundException("Sale not found with ID: " + saleId);
            }
        } catch (NotFoundException e) {
            // Handle case where Optional.get() throws NoSuchElementException
            throw new NotFoundException(e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            throw new RuntimeException("Failed to retrieve sale with ID: " + saleId+ e.getMessage());
        }
        
      }
    

    /**
     * Retrieves all sale records.
     *
     * @return A list of all sale entities.
     * @throws RuntimeException If there is an error while retrieving the sales.
     */
    public List<Sale> getAllSales() {
        try {
            return saleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all sales: " + e.getMessage());
        }
    }
    
    
    /**
     * Deletes a sale record by its ID.
     *
     * @param saleId The ID of the sale to be deleted.
     * @return True if the sale was successfully deleted, false otherwise.
     * @throws NotFoundException If no sale is found with the specified ID.
     * @throws RuntimeException If there is an error while deleting the sale.
     */
    public boolean deleteSaleById(Integer saleId) {
        try {
            Optional<Sale> saleOptional = saleRepository.findById(saleId);
            if (saleOptional.isPresent()) {
                saleRepository.deleteById(saleId);
                return true; // Sale deleted successfully
            } else {
                throw new NotFoundException("Sale not found with ID: " + saleId);
            }
        } catch (NotFoundException e) {
            // Handle case where deleteById throws EmptyResultDataAccessException
            throw new NotFoundException("Sale not found with ID: " + saleId);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            throw new RuntimeException("Failed to delete sale: " + e.getMessage(), e);
        }
    }

    
    /**
     * Retrieves a list of sales for a given customer ID.
     *
     * @param userId The ID of the customer whose sales are to be retrieved.
     * @return A list of sales associated with the specified customer ID.
     * @throws RuntimeException If an unexpected error occurs while retrieving sales.
     */
    public List<Sale> getSalesByUserId(Integer userId) {
        try {
            return saleRepository.findByUserUserId(userId);
        } catch (Exception e) {
            // Catch any unexpected exceptions and wrap them in a RuntimeException for centralized error handling
            throw new RuntimeException("Error occurred while retrieving sales for customer with ID: " + userId+e.getMessage());
        }
    }

}
