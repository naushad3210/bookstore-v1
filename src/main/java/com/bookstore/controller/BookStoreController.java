package com.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.request.BookStoreRequestDto;
import com.bookstore.dto.request.PurchaseBookRequestDto;
import com.bookstore.dto.response.BookStoreResponseDto;
import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.dto.response.ResponseDto;
import com.bookstore.entity.BookDetailsEntity;
import com.bookstore.service.IBookStoreService;
import com.bookstore.wrapper.BookStoreWrapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author mohammadnaushad
 *
 */
@RestController
@RequestMapping("/v1")
public class BookStoreController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);

	@Autowired
	private IBookStoreService iBookService;

	@ApiOperation(value = "Adds the book to the store", notes = "valid `isbn`, `title`, `author`, `price` and `quantity` needs to be passed")
	@PostMapping(value = "/book", headers = "Accept=application/json")
	public ResponseDto<BookStoreResponseDto> addBook(@RequestBody @Valid BookStoreRequestDto bookStoreDto) {
		LOGGER.info("-- Inside [BookStoreController] [addBook()] with [Data:{}]", bookStoreDto);

		BookDetailsEntity bookDetailsResponse = iBookService.addBook(bookStoreDto);
		ResponseDto<BookStoreResponseDto> bookStoreResponse = new ResponseDto<>();
		bookStoreResponse.setBody(BookStoreWrapper.createBookStoreResponse(bookDetailsResponse));
		return bookStoreResponse;
	}

	@ApiOperation(value = "Purchases the book from the store", notes = "valid `isbn` or `title and author` needs to be passed")
	@PostMapping(value = "/purchase", headers = "Accept=application/json")
	public ResponseDto<BookStoreResponseDto> purchaseBook(@RequestBody @Valid PurchaseBookRequestDto purchaseBookDto) {
		LOGGER.info("-- Inside [PurchaseController] [purchaseBook()] with [Data:{}]", purchaseBookDto);

		BookDetailsEntity purchaseBookResponse = iBookService.purchaseBook(purchaseBookDto);
		ResponseDto<BookStoreResponseDto> purchaseResponse = new ResponseDto<>();
		purchaseResponse.setBody(BookStoreWrapper.createBookStoreResponse(purchaseBookResponse));
		return purchaseResponse;
	}

	@ApiOperation(value = "Retrieves the book information")
	@GetMapping(value = "/book/{searchField:isbn|title|author}/{searchValue}", headers = "Accept=application/json")
	public ResponseDto<List<BookStoreResponseDto>> getBook(
			@ApiParam(value = "searchField can be one of `isbn|title|author`") @PathVariable("searchField") String searchField,
			@ApiParam(value = "book data to be searched upon") @PathVariable("searchValue") String searchValue) {
		LOGGER.info("-- Inside [BookStoreController] [Method: getUserByUserId()] with [Data:{},{}]", searchField,
				searchValue);
		List<BookDetailsEntity> bookDetailsResponse = iBookService.getBook(searchField, searchValue);
		ResponseDto<List<BookStoreResponseDto>> bookStoreResponse = new ResponseDto<>();
		bookStoreResponse.setBody(BookStoreWrapper.createBookStoreResponseList(bookDetailsResponse));
		return bookStoreResponse;
	}

	@ApiOperation(value = "Retrieves the media coverage information from ther internet")
	@GetMapping(value = "/mediacoverage/{searchField:isbn}/{searchValue}", headers = "Accept=application/json")
	public ResponseDto<List<MediaCoverageResponseDto>> getMediaCoverage(
			@ApiParam(value = "searchField can be `isbn`") @PathVariable("searchField") String searchField,
			@ApiParam(value = "isbn to be searched upon") @PathVariable("searchValue") String searchValue) {
		LOGGER.info("-- Inside [BookStoreController] [Method: getMediaCoverage()] with [Data:{},{}]", searchField,
				searchValue);
		List<MediaCoverageResponseDto> mediaCoverageResponseList = iBookService.getMediaCoverage(searchField,
				searchValue);
		ResponseDto<List<MediaCoverageResponseDto>> mediaCoverageResponse = new ResponseDto<>();
		mediaCoverageResponse.setBody(mediaCoverageResponseList);
		return mediaCoverageResponse;
	}

}