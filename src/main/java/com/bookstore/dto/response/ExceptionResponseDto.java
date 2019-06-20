package com.bookstore.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author mohammadnaushad
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Getter @lombok.Setter @lombok.ToString
@lombok.EqualsAndHashCode
public class ExceptionResponseDto implements Serializable{
	 
	private static final long serialVersionUID = 639078131209743803L;
	private String errorCode;
    private String errorMessage;
    private List<String> errors;
	
}