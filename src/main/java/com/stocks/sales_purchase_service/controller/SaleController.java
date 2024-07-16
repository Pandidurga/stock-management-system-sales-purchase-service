package com.stocks.sales_purchase_service.controller;

import com.stocks.sales_purchase_service.exception.NotFoundException;
import com.stocks.sales_purchase_service.model.Sale;
import com.stocks.sales_purchase_service.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-purchase-service/sales")
public class SaleController {

    @Autowired
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    /**
     * Endpoint to create a new sale.
     *
     * @param sale The sale entity to create.
     * @return ResponseEntity with a success message and status 201 if successful,
     *         or an error message and status 400 if failed.
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createSale(
            @RequestParam int customerId,
            @RequestParam int productId,
            @RequestParam int requestedQuantity
    ) {
        try {
            saleService.createSale(customerId, productId, requestedQuantity);
            return ResponseEntity.ok("Sale created successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    /**
     * Endpoint to retrieve a sale by ID.
     *
     * @param saleId The ID of the sale to retrieve.
     * @return ResponseEntity with the retrieved sale and status 200 if found,
     *         or an error message and status 404 if not found.
     */
    @GetMapping("/get-by-id/{saleId}")
    public ResponseEntity<?> getSaleById(@PathVariable Integer saleId) {
        try {
            Sale sale = saleService.getSaleById(saleId);
            return ResponseEntity.ok(sale);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve all sales.
     *
     * @return ResponseEntity with the list of sales and status 200 if found,
     *         or an error message and status 404 if no sales found.
     */
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllSales() {
        try {
            List<Sale> sales = saleService.getAllSales();
            if (!sales.isEmpty()) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sales found.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to delete a sale by ID.
     *
     * @param saleId The ID of the sale to delete.
     * @return ResponseEntity with a success message and status 200 if deleted,
     *         or an error message and status 404 if not found.
     */
    @DeleteMapping("/delete/{saleId}")
    public ResponseEntity<?> deleteSaleById(@PathVariable Integer saleId) {
        try {
                saleService.deleteSaleById(saleId);
                return ResponseEntity.ok("Sale deleted successfully with ID: " + saleId); 
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
		
    }
    
    
    /**
     * Endpoint to retrieve sales by customer ID.
     *
     * @param userId The ID of the customer (user) to retrieve sales for.
     * @return ResponseEntity with the list of sales and status 200 if found,
     *         or an error message and status 404 if no sales found for the customer.
     */
    @GetMapping("/get-by-user/{userId}")
    public ResponseEntity<?> getSalesByUserId(@PathVariable Integer userId) {
        try {
            List<Sale> sales = saleService.getSalesByUserId(userId);
            if (!sales.isEmpty()) {
                return ResponseEntity.ok(sales);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sales found for customer with ID: " + userId);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
