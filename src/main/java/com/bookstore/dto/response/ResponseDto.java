package com.bookstore.dto.response;

import java.io.Serializable;

/**
 * @author mohammadnaushad
 *
 */
@lombok.Getter @lombok.Setter @lombok.ToString
@lombok.EqualsAndHashCode
public class ResponseDto<T> implements Serializable {

	private static final long serialVersionUID = 2590085546362795552L;
	
	T body;

}
