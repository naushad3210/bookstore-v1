package com.bookstore.datastub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bookstore.dto.request.BookStoreRequestDto;
import com.bookstore.dto.response.BookStoreResponseDto;
import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.dto.response.ResponseDto;
import com.bookstore.entity.BookDetailsEntity;
/**
 * @author mohammadnaushad
 *
 */
@Component
public class BookStoreDataStub {

	public static BookDetailsEntity bookDetails() {
		BookDetailsEntity bookDetails = new BookDetailsEntity();
		bookDetails.setIsbn("123");
		bookDetails.setAuthor("TEST_AUTHOR");
		bookDetails.setTitle("TEST_TITLE");
		bookDetails.setPrice(100.0);
		bookDetails.setId(10001);
		bookDetails.setQuantity(50);
		return bookDetails;
	}
	
	public static List<BookDetailsEntity> bookDetailsList(){
		List<BookDetailsEntity> list = new ArrayList<>();
		BookDetailsEntity bookDetails = bookDetails();
		list.add(bookDetails);
		return list;
	}
	
	public static BookStoreRequestDto bookStoreRequestDto() {
		BookStoreRequestDto bookStoreReqDto = new BookStoreRequestDto(); 
		bookStoreReqDto.setIsbn("123");
		bookStoreReqDto.setAuthor("TEST_AUTHOR");
		bookStoreReqDto.setTitle("TEST_TITLE");
		bookStoreReqDto.setPrice(100.0);
		bookStoreReqDto.setQuantity(50);
		return bookStoreReqDto;
	}
	
	public static BookStoreResponseDto bookStoreResponseDto() {
		BookStoreResponseDto bookDetails = new BookStoreResponseDto();
		bookDetails.setIsbn("123");
		bookDetails.setAuthor("TEST_AUTHOR");
		bookDetails.setTitle("TEST_TITLE");
		bookDetails.setPrice(100.0);
		bookDetails.setQuantity(50);
		bookDetails.setId(10001l);
		return bookDetails;
	}
	
	public static ResponseDto<BookStoreResponseDto> bookStoreResponse(){
		ResponseDto<BookStoreResponseDto> bookStoreResponseDto= new ResponseDto<>();
		bookStoreResponseDto.setBody(bookStoreResponseDto());
		return bookStoreResponseDto;
	}

	public static ResponseDto<List<BookStoreResponseDto>> bookStoreResponseList(){
		ResponseDto<List<BookStoreResponseDto>> bookStoreResponseDto= new ResponseDto<>();
		List<BookStoreResponseDto> list = new ArrayList<>();
		list.add(bookStoreResponseDto());
		bookStoreResponseDto.setBody(list);
		return bookStoreResponseDto;
	}
	
	public static ResponseDto<List<MediaCoverageResponseDto>> mediaCoverageResponseList(){
		ResponseDto<List<MediaCoverageResponseDto>> bookStoreResponseDto= new ResponseDto<>();
		List<MediaCoverageResponseDto> list = new ArrayList<>();
		MediaCoverageResponseDto dto = new MediaCoverageResponseDto();
		dto.setTitle("MEDIA_COVERAGE_TITLE_TEST");
		dto.setBody("Test Body of Media coverage. TEST_TITLE is present. ");
		list.add(dto);
		bookStoreResponseDto.setBody(list);
		return bookStoreResponseDto;
	}
	public static List<MediaCoverageResponseDto> mediaCoverageList(){
		List<MediaCoverageResponseDto> list = new ArrayList<>();
		MediaCoverageResponseDto dto = new MediaCoverageResponseDto();
		dto.setTitle("MEDIA_COVERAGE_TITLE_TEST");
		dto.setBody("Test Body of Media coverage. TEST_TITLE is present. ");
		list.add(dto);
		return list;
	}
	
	
	public static ResponseEntity<List<MediaCoverageResponseDto>> mediaCoverageResponse(){
		return new ResponseEntity<List<MediaCoverageResponseDto>>(mediaCoverageList(), HttpStatus.OK);
		
	}
}
