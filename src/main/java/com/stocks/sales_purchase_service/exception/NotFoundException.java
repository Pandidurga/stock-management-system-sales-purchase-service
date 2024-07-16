package com.stocks.sales_purchase_service.exception;



/**
 * Custom exception class for handling "not found" scenarios.
 * This exception is thrown when a requested entity is not found in the database.
 */
public class NotFoundException extends RuntimeException {
    /**
	 * 
	 */

	/**
     * Constructor for NotFoundException.
     *
     * @param message Detailed message explaining the reason for the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
