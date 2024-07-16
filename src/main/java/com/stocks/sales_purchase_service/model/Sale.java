package com.stocks.sales_purchase_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.stocks.product_service.model.Product;
import com.stocks.user_service.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;

/**
 * Represents a sale transaction in the stock management system.
 */

@NamedStoredProcedureQuery(
	    name = "insert_new_sale",
	    procedureName = "insert_new_sale",
	    parameters = {
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_seller_id", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_customer_id", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_product_id", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_requested_quantity", type = Integer.class),
	        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_error_message", type = String.class)
	    }
	)

@Entity
@Table(name = "Sales")
public class Sale {

    /**
     * Unique identifier for the sale.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer saleId;

    /**
     * The product involved in the sale.
     * 
     * @return The product involved in the sale.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * The user who made the purchase.
     * 
     * @return The user who made the purchase.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The quantity of the product sold.
     * 
     * @return The quantity of the product sold.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * The unit price of the product at the time of sale.
     * 
     * @return The unit price of the product at the time of sale.
     */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /**
     * The Central Goods and Services Tax (CGST) amount for the sale.
     * 
     * @return The CGST amount for the sale.
     */
    @Column(name = "cgst", nullable = false, precision = 10, scale = 2)
    private BigDecimal cgst;

    /**
     * The State Goods and Services Tax (SGST) amount for the sale.
     * 
     * @return The SGST amount for the sale.
     */
    @Column(name = "sgst", nullable = false, precision = 10, scale = 2)
    private BigDecimal sgst;

    /**
     * The Integrated Goods and Services Tax (IGST) amount for the sale.
     * 
     * @return The IGST amount for the sale.
     */
    @Column(name = "igst", nullable = false, precision = 10, scale = 2)
    private BigDecimal igst;

    /**
     * The net amount of the sale before GST.
     * 
     * @return The net amount of the sale before GST.
     */
    @Column(name = "net_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal netAmount;

    /**
     * The gross amount of the sale after adding GST.
     * 
     * @return The gross amount of the sale after adding GST.
     */
    @Column(name = "gross_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal grossAmount;

    /**
     * The date and time when the sale was made.
     * 
     * @return The date and time when the sale was made.
     */
    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    // Constructors

    /**
     * Default constructor for the Sale entity.
     */
    public Sale() {
    }

    /**
     * Constructor to initialize a Sale with all attributes.
     * 
     * @param product     The product involved in the sale.
     * @param user        The user who made the purchase.
     * @param quantity    The quantity of the product sold.
     * @param unitPrice   The unit price of the product at the time of sale.
     * @param cgst        The Central Goods and Services Tax (CGST) amount for the sale.
     * @param sgst        The State Goods and Services Tax (SGST) amount for the sale.
     * @param igst        The Integrated Goods and Services Tax (IGST) amount for the sale.
     * @param netAmount   The net amount of the sale before GST.
     * @param grossAmount The gross amount of the sale after adding GST.
     * @param saleDate    The date and time when the sale was made.
     */
    public Sale(Product product, User user, Integer quantity, BigDecimal unitPrice, BigDecimal cgst, BigDecimal sgst, BigDecimal igst, BigDecimal netAmount, BigDecimal grossAmount, LocalDateTime saleDate) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.netAmount = netAmount;
        this.grossAmount = grossAmount;
        this.saleDate = saleDate;
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier for the sale.
     * 
     * @return The sale identifier.
     */
    public Integer getSaleId() {
        return saleId;
    }

    /**
     * Sets the unique identifier for the sale.
     * 
     * @param saleId The sale identifier to set.
     */
    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    /**
     * Retrieves the product involved in the sale.
     * 
     * @return The product involved in the sale.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product involved in the sale.
     * 
     * @param product The product involved in the sale.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Retrieves the user who made the purchase.
     * 
     * @return The user who made the purchase.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the purchase.
     * 
     * @param user The user who made the purchase.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the quantity of the product sold.
     * 
     * @return The quantity of the product sold.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product sold.
     * 
     * @param quantity The quantity of the product sold.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the unit price of the product at the time of sale.
     * 
     * @return The unit price of the product at the time of sale.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the product at the time of sale.
     * 
     * @param unitPrice The unit price of the product at the time of sale.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Retrieves the Central Goods and Services Tax (CGST) amount for the sale.
     * 
     * @return The CGST amount for the sale.
     */
    public BigDecimal getCgst() {
        return cgst;
    }

    /**
     * Sets the Central Goods and Services Tax (CGST) amount for the sale.
     * 
     * @param cgst The CGST amount for the sale.
     */
    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    /**
     * Retrieves the State Goods and Services Tax (SGST) amount for the sale.
     * 
     * @return The SGST amount for the sale.
     */
    public BigDecimal getSgst() {
        return sgst;
    }

    /**
     * Sets the State Goods and Services Tax (SGST) amount for the sale.
     * 
     * @param sgst The SGST amount for the sale.
     */
    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    /**
     * Retrieves the Integrated Goods and Services Tax (IGST) amount for the sale.
     * 
     * @return The IGST amount for the sale.
     */
    public BigDecimal getIgst() {
        return igst;
    }

    /**
     * Sets the Integrated Goods and Services Tax (IGST) amount for the sale.
     * 
     * @param igst The IGST amount for the sale.
     */
    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    /**
     * Retrieves the net amount of the sale before GST.
     * 
     * @return The net amount of the sale before GST.
     */
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    /**
     * Sets the net amount of the sale before GST.
     * 
     * @param netAmount The net amount of the sale before GST.
     */
    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Retrieves the gross amount of the sale after adding GST.
     * 
     * @return The gross amount of the sale after adding GST.
     */
    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the gross amount of the sale after adding GST.
     * 
     * @param grossAmount The gross amount of the sale after adding GST.
     */
    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }
    
    /**
     * Retrieves the date and time when the sale was made.
     *
     * @return The date and time when the sale was made.
     */
    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    /**
     * Sets the date and time when the sale was made.
     *
     * @param saleDate The date and time when the sale was made.
     */
    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

}
    
