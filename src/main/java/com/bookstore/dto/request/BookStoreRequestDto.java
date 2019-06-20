package com.bookstore.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @author mohammadnaushad
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class BookStoreRequestDto implements Serializable{

	private static final long serialVersionUID = 3607184522856699049L;
	
	@NotNull
	@NotEmpty
	private String isbn;
	
	@NotNull
	@NotEmpty
	private String title;
	
	@NotNull
	@NotEmpty
	private String author;
	
	@NotNull
	@Positive
	private Double price;
	
	@Positive
	private Integer quantity;


}
