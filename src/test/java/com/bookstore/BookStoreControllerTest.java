package com.bookstore;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.controller.BookStoreController;
import com.bookstore.datastub.BookStoreDataStub;
import com.bookstore.dto.request.BookStoreRequestDto;
import com.bookstore.service.IBookStoreService;

/**
 * @author mohammadnaushad
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookStoreControllerTest{
	private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreControllerTest.class);
	
	@Mock
	private IBookStoreService bookStoreServiceMock;

	@InjectMocks
	private BookStoreController bookStoreControllerMock;
	
	@Test
	public void addBookTest() {
		LOGGER.info("-- Testing [BillControllerTest] [Method: addBookTest()]");
		when(bookStoreServiceMock.addBook(Mockito.any(BookStoreRequestDto.class))).thenReturn(BookStoreDataStub.bookDetails());
		assertEquals(BookStoreDataStub.bookStoreResponse(),bookStoreControllerMock.addBook(BookStoreDataStub.bookStoreRequestDto()));
    }
	
	@Test
	public void getBookTestIsbn() {
		LOGGER.info("-- Testing [BillControllerTest] [Method: getBookTestIsbn()]");
		when(bookStoreServiceMock.getBook(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(BookStoreDataStub.bookDetailsList());
		assertEquals(BookStoreDataStub.bookStoreResponseList(),bookStoreControllerMock.getBook("isbn","123"));
    }
	
	@Test
	public void getBookTestTitle() {
		LOGGER.info("-- Testing [BillControllerTest] [Method: getBookTestTitle()]");
		when(bookStoreServiceMock.getBook(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(BookStoreDataStub.bookDetailsList());
		assertEquals(BookStoreDataStub.bookStoreResponseList(),bookStoreControllerMock.getBook("title","TEST_TITLE"));
    }
	
	@Test
	public void getBookTestAuthor() {
		LOGGER.info("-- Testing [BillControllerTest] [Method: getBookTestAuthor()]");
		when(bookStoreServiceMock.getBook(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(BookStoreDataStub.bookDetailsList());
		assertEquals(BookStoreDataStub.bookStoreResponseList(),bookStoreControllerMock.getBook("author","TEST_AUTHOR"));
    }
	
	@Test
	public void getMediaCoverageTest() {
		LOGGER.info("-- Testing [BillControllerTest] [Method: getMediaCoverageTest()]");
		when(bookStoreServiceMock.getMediaCoverage(Mockito.any(String.class), Mockito.any(String.class))).thenReturn(BookStoreDataStub.mediaCoverageList());
		assertEquals(BookStoreDataStub.mediaCoverageResponseList(),bookStoreControllerMock.getMediaCoverage("isbn","123"));
    }
}
