package com.stocks.sales_purchase_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.stocks.sales_purchase_service.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    /**
     * Executes the stored procedure to create a purchase.
     *
     * @param sellerId The ID of the seller.
     * @param productId The ID of the product.
     * @param quantity The quantity of the product.
     */
    @Procedure(name = "create_purchase")
	void createPurchase(
        @Param("p_seller_id") Integer sellerId,
        @Param("p_product_id") Integer productId,
        @Param("p_quantity") Integer quantity
    );
}
