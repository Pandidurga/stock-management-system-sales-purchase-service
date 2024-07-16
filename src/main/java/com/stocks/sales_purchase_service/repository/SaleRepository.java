package com.stocks.sales_purchase_service.repository;

import com.stocks.sales_purchase_service.model.Sale;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Sale entity.
 * Extends JpaRepository to provide CRUD operations for Sale entities.
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    // JpaRepository provides built-in methods for CRUD operations, such as save, findAll, findById, deleteById, etc.
    // Custom query methods (if needed) can be added here.

	/**
     * Executes a stored procedure named "insert_new_sale".
     *
     * @param sellerId          The ID of the seller initiating the sale.
     * @param customerId        The ID of the customer making the purchase.
     * @param productId         The ID of the product being sold.
     * @param requestedQuantity The quantity of the product requested in the sale.
     * @param errorMessage      Output parameter for error message if any.
     */
    @Transactional
    @Procedure(name = "insert_new_sale")
    void insertNewSale(
            @Param("p_seller_id") Integer sellerId,
            @Param("p_customer_id") Integer customerId,
            @Param("p_product_id") Integer productId,
            @Param("p_requested_quantity") Integer requestedQuantity,
            @Param("p_error_message") String errorMessage
    );
	 
	 
	 /**
     * Retrieves a list of sales associated with a given user ID.
     *
     * @param userId The ID of the customer.
     * @return A list of sales associated with the specified user ID.
     */
	 List<Sale> findByUserUserId(Integer userId);

}
