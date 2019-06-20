package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.request.BookStoreRequestDto;
import com.bookstore.dto.request.PurchaseBookRequestDto;
import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.entity.BookDetailsEntity;

/**
 * @author mohammadnaushad
 *
 */
public interface IBookStoreService {
	
	 List<BookDetailsEntity> getBook(String searchField,String searchValue);
	 BookDetailsEntity addBook(BookStoreRequestDto bookStoreDto);
	 List<MediaCoverageResponseDto> getMediaCoverage(String searchField,String searchValue);
	 BookDetailsEntity purchaseBook(PurchaseBookRequestDto purchaseDto);

}
