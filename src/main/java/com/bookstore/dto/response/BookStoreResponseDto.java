package com.bookstore.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author mohammadnaushad
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Getter @lombok.Setter @lombok.ToString
@lombok.EqualsAndHashCode
public class BookStoreResponseDto implements Serializable {

	private static final long serialVersionUID = 7746022943419698556L;
	private Long id;
	private String isbn;
	private String title;
	private String author;
	private Double price;
	private Integer quantity;
	private String mediaCoverageTitle;
	
}
