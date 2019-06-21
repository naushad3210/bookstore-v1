package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.OptimisticLockException;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.dto.request.BookStoreRequestDto;
import com.bookstore.dto.request.PurchaseBookRequestDto;
import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.entity.BookDetailsEntity;
import com.bookstore.entity.PurchaseHistoryEntity;
import com.bookstore.exceptions.ConcurrentUpdateException;
import com.bookstore.exceptions.FieldMissingException;
import com.bookstore.exceptions.RecordNotFoundException;
import com.bookstore.exceptions.ResourceException;
import com.bookstore.repository.BookDetailsRepository;
import com.bookstore.service.IBookStoreService;
import com.bookstore.service.IMediaCoverageService;

/**
 * @author mohammadnaushad The {@code ExceptionHandlingController} is used to
 *         handle exceptions globally.
 *
 */
@Service("bookStoreService")
public class BookStoreServiceImpl implements IBookStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreServiceImpl.class);

	@Autowired
	private BookDetailsRepository bookDetailsRepository;

	@Autowired
	private IMediaCoverageService iMediaCoverageService;

	@Override
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public BookDetailsEntity addBook(BookStoreRequestDto bookStoreDto) {
		LOGGER.info("-- Inside [BookStoreServiceImpl] [Method: addBook()] with [Data:{}]", bookStoreDto);
		String isbn = bookStoreDto.getIsbn();

		BookDetailsEntity bookDetails = bookDetailsRepository.findByIsbn(isbn);
		if (bookDetails == null) {
			bookDetails = new BookDetailsEntity();
		}
		BeanUtils.copyProperties(bookStoreDto, bookDetails);
		return bookDetailsRepository.save(bookDetails);
	}

	@Override
	@Transactional
	public List<BookDetailsEntity> getBook(String searchField, String searchValue) {
		LOGGER.info("-- Inside [BookStoreServiceImpl] [Method: getBook()] with [Data:{} = {} ", searchField,
				searchValue);
		List<BookDetailsEntity> bookDetailsList = bookDetailsRepository.findAll();
		List<BookDetailsEntity> bookDetails = new ArrayList<>();

		if (searchField.equalsIgnoreCase("author")) {
			bookDetails = bookDetailsList.stream()
					.filter(book -> book.getAuthor().toUpperCase().contains(searchValue.toUpperCase()))
					.collect(Collectors.toList());
		} else if (searchField.equalsIgnoreCase("title")) {
			bookDetails = bookDetailsList.stream()
					.filter(book -> book.getTitle().toUpperCase().contains(searchValue.toUpperCase()))
					.collect(Collectors.toList());
		} else if (searchField.equalsIgnoreCase("isbn")) {
			bookDetails = bookDetailsList.stream()
					.filter(book -> book.getIsbn().toUpperCase().equalsIgnoreCase(searchValue.toUpperCase()))
					.collect(Collectors.toList());
		}

		if (bookDetails.isEmpty()) {
			LOGGER.info("Book details not present with {} = {}", searchField, searchValue);
			throw new RecordNotFoundException("Book", searchField, searchValue);
		}

		return bookDetails;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public BookDetailsEntity purchaseBook(PurchaseBookRequestDto purchaseDto) {
		LOGGER.info("-- Inside [PurchaseBookServiceImpl] [Method: purchaseBook()] with [Data:{}]", purchaseDto);
		int bookQuantity = 0;

		BookDetailsEntity book = findBook(purchaseDto);

		PurchaseHistoryEntity purchaseHistory = new PurchaseHistoryEntity();
		Set<PurchaseHistoryEntity> purchaseHistorySet = new HashSet<>();

		bookQuantity = book.getQuantity();
		purchaseHistory.setOriginalQuantity(bookQuantity);

		// checking the availabilty of the book
		checkBookQuantity(purchaseDto, bookQuantity);

		book.setQuantity(book.getQuantity() - purchaseDto.getQuantity());
		purchaseHistory.setUpdatedQuantity(book.getQuantity());
		purchaseHistorySet.add(purchaseHistory);

		book.setPurchaseHistory(purchaseHistorySet);
		purchaseHistory.setBookdetails(book);
		try {
			return bookDetailsRepository.save(book);
		} catch (StaleObjectStateException | OptimisticLockException e) {
			LOGGER.error(" OptimisticLockException Occured !! for the book with isbn", purchaseDto.getIsbn());
			throw new ConcurrentUpdateException("Book", "isbn", purchaseDto.getIsbn());
		}
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.MANDATORY)
	public BookDetailsEntity findBook(PurchaseBookRequestDto purchaseDto) {
		BookDetailsEntity book = null;
		if (purchaseDto.getIsbn() != null) {
			book = bookDetailsRepository.findByIsbn(purchaseDto.getIsbn());
		} else {
			if ((purchaseDto.getAuthor() != null && purchaseDto.getTitle() != null)) {
				book = bookDetailsRepository.findByAuthorAndTitle(purchaseDto.getAuthor(), purchaseDto.getTitle());
			} else {
				LOGGER.info("Mandatory Fields Missing !!!");
				throw new FieldMissingException("Mandatory Fields", "Author, Title, ISBN", null);
			}
		}

		if (book == null) {
			LOGGER.info("Book Not Found with with details ISBN = {}, Author = {} or Title = {} !!!",
					purchaseDto.getIsbn(), purchaseDto.getAuthor(), purchaseDto.getTitle());
			throw new ResourceException("Book", "ISBN | Author | Title ",
					purchaseDto.getIsbn() + " , " + purchaseDto.getAuthor() + " , " + purchaseDto.getTitle());
		}

		return book;
	}

	public void checkBookQuantity(PurchaseBookRequestDto purchaseDto, int bookQuantity) {
		if (bookQuantity <= 0 || bookQuantity < purchaseDto.getQuantity()) {
			LOGGER.info(
					"Book quantity with ISBN = {}, Author = {}, Title = {} is not sufficient! Quantity avialble = {} !",
					purchaseDto.getIsbn(), purchaseDto.getAuthor(), purchaseDto.getTitle(), bookQuantity);
			throw new ResourceException("Book", "ISBN | Author | Title ",
					purchaseDto.getIsbn() + " , " + purchaseDto.getAuthor() + " , " + purchaseDto.getTitle());
		}
	}

	@Override
	public List<MediaCoverageResponseDto> getMediaCoverage(String searchField, String searchValue) {
		LOGGER.info("-- Inside [BookStoreServiceImpl] [Method: getBook()] with [Data:{} = {} ", searchField,
				searchValue);

		BookDetailsEntity bookDetails = bookDetailsRepository.findByIsbn(searchValue);

		if (bookDetails != null) {
			return iMediaCoverageService.getMediaCoverageFromApi(bookDetails, searchField, searchValue);
		} else {
			LOGGER.info("Book details not present with {} = {}", searchField, searchValue);
			throw new RecordNotFoundException("Book", searchField, searchValue);
		}

	}
}
