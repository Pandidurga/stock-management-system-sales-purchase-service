package com.stocks.sales_purchase_service.controller;

import com.stocks.sales_purchase_service.exception.NotFoundException;
import com.stocks.sales_purchase_service.model.Purchase;
import com.stocks.sales_purchase_service.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-purchase-service/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    /**
     * Endpoint to create a new purchase.
     *
     * @param productId The ID of the product being purchased.
     * @param quantity  The quantity of the product being purchased.
     * @return ResponseEntity with a success message and status 201 if successful,
     *         or an error message and status 400 if failed.
     */
    @PostMapping("/add")
    public ResponseEntity<String> createPurchase(@RequestParam int productId, @RequestParam int quantity) {
        try {
            purchaseService.createPurchase(productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Purchase created successfully.");
          
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve a purchase by ID.
     *
     * @param purchaseId The ID of the purchase to retrieve.
     * @return ResponseEntity with the retrieved purchase and status 200 if found,
     *         or an error message and status 404 if not found.
     */
    @GetMapping("/get-by-id/{purchaseId}")
    public ResponseEntity<?> getPurchaseById(@PathVariable Integer purchaseId) {
        try {
            Purchase purchase = purchaseService.getPurchaseById(purchaseId);
            return ResponseEntity.ok(purchase);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to retrieve all purchases.
     *
     * @return ResponseEntity with the list of purchases and status 200 if found,
     *         or an error message and status 404 if no purchases found.
     */
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPurchases() {
        try {
            List<Purchase> purchases = purchaseService.getAllPurchases();
            if (!purchases.isEmpty()) {
                return ResponseEntity.ok(purchases);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No purchases found.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to delete a purchase by ID.
     *
     * @param purchaseId The ID of the purchase to delete.
     * @return ResponseEntity with a success message and status 200 if deleted,
     *         or an error message and status 404 if not found.
     */
    @DeleteMapping("/delete/{purchaseId}")
    public ResponseEntity<?> deletePurchaseById(@PathVariable Integer purchaseId) {
        try {
            purchaseService.deletePurchaseById(purchaseId);
            return ResponseEntity.ok("Purchase deleted successfully with ID: " + purchaseId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
