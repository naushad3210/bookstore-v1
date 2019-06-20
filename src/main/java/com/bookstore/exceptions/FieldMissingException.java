package com.bookstore.exceptions;

import java.io.Serializable;
/**
 * @author mohammadnaushad
 *
 */
public class FieldMissingException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 438523697602779080L;
	
	private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public FieldMissingException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("One of the %s from %s : '%s' is missing! Please try again!", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

}