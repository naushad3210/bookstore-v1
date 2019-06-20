package com.bookstore.datastub;

import org.springframework.stereotype.Component;

import com.bookstore.dto.request.PurchaseBookRequestDto;
import com.bookstore.dto.response.BookStoreResponseDto;
import com.bookstore.dto.response.ResponseDto;
import com.bookstore.entity.BookDetailsEntity;
/**
 * @author mohammadnaushad
 *
 */
@Component
public class PurchaseDataStub {
	
	public static PurchaseBookRequestDto purchaseBookDto() {
		PurchaseBookRequestDto dto = new PurchaseBookRequestDto();
		dto.setAuthor("TEST_AUTHOR");
		dto.setIsbn("123");
		dto.setQuantity(2);
		dto.setTitle("TEST_TITLE");
		return dto;
	}
	
	public static ResponseDto<BookStoreResponseDto> purchaseResponse(){
		ResponseDto<BookStoreResponseDto> bookStoreResponseDto= new ResponseDto<>();
		bookStoreResponseDto.setBody(purchaseResponseDto());
		return bookStoreResponseDto;
	}
	
	public static  BookStoreResponseDto purchaseResponseDto() {
		BookStoreResponseDto bookDetails = new BookStoreResponseDto();
		bookDetails.setIsbn("123");
		bookDetails.setAuthor("TEST_AUTHOR");
		bookDetails.setTitle("TEST_TITLE");
		bookDetails.setId(1001l);
		bookDetails.setQuantity(48);
		return bookDetails;
	}

	public static BookDetailsEntity purchaseBookDetails() {
		BookDetailsEntity bookDetails = new BookDetailsEntity();
		bookDetails.setIsbn("123");
		bookDetails.setAuthor("TEST_AUTHOR");
		bookDetails.setTitle("TEST_TITLE");
		bookDetails.setId(1001l);
		bookDetails.setQuantity(48);
		return bookDetails;
	}
}

