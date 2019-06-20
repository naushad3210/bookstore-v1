package com.bookstore.exceptions;

import java.io.Serializable;
/**
 * @author mohammadnaushad
 *
 */
public class ResourceException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 438523697602779080L;
	
	private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceException( String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s with %s : '%s' is Out of Stock or Unavailable! Please try again later!", resourceName, fieldName, fieldValue));
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