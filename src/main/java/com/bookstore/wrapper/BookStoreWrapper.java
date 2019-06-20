package com.bookstore.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.bookstore.dto.response.BookStoreResponseDto;
import com.bookstore.entity.BookDetailsEntity;

/**
 * @author mohammadnaushad
 *
 */
public class BookStoreWrapper {
	
	public static BookStoreResponseDto createBookStoreResponse(BookDetailsEntity bookDetailsResponse) {
		
		return populateBookStoreDto(bookDetailsResponse);
	}
	
	public static List<BookStoreResponseDto> createBookStoreResponseList(List<BookDetailsEntity> bookDetailsResponse) {
		
		List<BookStoreResponseDto> bookStoreResponse = new ArrayList<>();
		
		bookDetailsResponse.stream().forEach(book->{
			bookStoreResponse.add(populateBookStoreDto(book));
		} );
		
		return bookStoreResponse;
	}
	
	public static BookStoreResponseDto createPurchaseResponse(BookDetailsEntity bookDetailsResponse) {
		return populateBookStoreDto(bookDetailsResponse);
	}
	
	
	private static BookStoreResponseDto populateBookStoreDto(BookDetailsEntity book) {
		BookStoreResponseDto bookStoreResponseDto = new BookStoreResponseDto();
		if(book.getIsbn()!=null)
			bookStoreResponseDto.setIsbn(book.getIsbn());
		if(book.getAuthor()!=null)
			bookStoreResponseDto.setAuthor(book.getAuthor());
		if(book.getTitle()!=null)
			bookStoreResponseDto.setTitle(book.getTitle());
		if(book.getPrice()!=null)
			bookStoreResponseDto.setPrice(book.getPrice());
		if(Long.valueOf(book.getId())!=null)
			bookStoreResponseDto.setId(book.getId());
		if(book.getQuantity()!=null)
			bookStoreResponseDto.setQuantity(book.getQuantity());
		return bookStoreResponseDto;
	}
	
}
