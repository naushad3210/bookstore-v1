package com.bookstore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.bookstore.datastub.BookStoreDataStub;
import com.bookstore.datastub.PurchaseDataStub;
import com.bookstore.dto.request.PurchaseBookRequestDto;
import com.bookstore.entity.BookDetailsEntity;
import com.bookstore.entity.PurchaseHistoryEntity;
import com.bookstore.exceptions.DuplicateRecordException;
import com.bookstore.exceptions.FieldMissingException;
import com.bookstore.exceptions.RecordNotFoundException;
import com.bookstore.exceptions.ResourceException;
import com.bookstore.repository.BookDetailsRepository;
import com.bookstore.service.impl.BookStoreServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BookStoreServiceTest{
	
	@Mock
	private BookDetailsRepository bookDetailsRepository;
	
	@InjectMocks
	private BookStoreServiceImpl bookStoreServiceImpl;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
    public void addBookTest_isbn_noDuplicate() {
		
		bookStoreServiceImpl.addBook(BookStoreDataStub.bookStoreRequestDto());
		
		ArgumentCaptor<BookDetailsEntity> captor = ArgumentCaptor.forClass(BookDetailsEntity.class);
		verify(bookDetailsRepository, times(1)).save(captor.capture());
		verify(bookDetailsRepository, times(1)).findByIsbn(any());
		verify(bookDetailsRepository, times(1)).findByAuthorAndTitle(any(),any());
		final BookDetailsEntity bookDetailsEntity = captor.getValue();
		
		assertEquals("TEST_AUTHOR", bookDetailsEntity.getAuthor());
		assertEquals("123", bookDetailsEntity.getIsbn());
		assertEquals("TEST_TITLE", bookDetailsEntity.getTitle());
		assertEquals(new Double("100.0"), bookDetailsEntity.getPrice());
		assertEquals(new Integer("50"), bookDetailsEntity.getQuantity());
    }
	
	@Test
    public void addBookTest_isbn_duplicate() {
		when(bookDetailsRepository.findByIsbn(any())).thenReturn(BookStoreDataStub.bookDetails());
		expectedEx.expect(DuplicateRecordException.class);
        expectedEx.expectMessage("Book with ISBN : '123' already exist! Please try with different values!");
		bookStoreServiceImpl.addBook(BookStoreDataStub.bookStoreRequestDto());
		
		verify(bookDetailsRepository, times(1)).findByIsbn(any());
		verify(bookDetailsRepository, times(0)).save(any());
		verify(bookDetailsRepository, times(0)).findByAuthorAndTitle(any(),any());
    }
	
	
	@Test
    public void addBookTest_authorAndTitle_duplicate() {
		when(bookDetailsRepository.findByAuthorAndTitle(any(),any())).thenReturn(BookStoreDataStub.bookDetails());
		expectedEx.expect(DuplicateRecordException.class);
        expectedEx.expectMessage("Book with Title : 'TEST_TITLE by Author TEST_AUTHOR' already exist! Please try with different values!");
		bookStoreServiceImpl.addBook(BookStoreDataStub.bookStoreRequestDto());
		
		verify(bookDetailsRepository, times(1)).findByIsbn(any());
		verify(bookDetailsRepository, times(0)).save(any());
		verify(bookDetailsRepository, times(1)).findByAuthorAndTitle(any(),any());
    }
	
	
	@Test
    public void getBookTest_by_isbn() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		List<BookDetailsEntity> list = bookStoreServiceImpl.getBook("isbn", "123");
		BookDetailsEntity book = list.get(0);
		
		verify(bookDetailsRepository, times(1)).findAll();
		
		assertNotNull(list);
		assertEquals("TEST_AUTHOR", book.getAuthor());
		assertEquals("123", book.getIsbn());
		assertEquals("TEST_TITLE", book.getTitle());
		assertEquals(new Double("100.0"), book.getPrice());
		assertEquals(new Integer("50"), book.getQuantity());
	}
	
	@Test
    public void getBookTest_by_author() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		List<BookDetailsEntity> list = bookStoreServiceImpl.getBook("author", "TEST_AUTHOR");
		BookDetailsEntity book = list.get(0);
		
		verify(bookDetailsRepository, times(1)).findAll();
		
		assertNotNull(list);
		assertEquals("TEST_AUTHOR", book.getAuthor());
		assertEquals("123", book.getIsbn());
		assertEquals("TEST_TITLE", book.getTitle());
		assertEquals(new Double("100.0"), book.getPrice());
		assertEquals(new Integer("50"), book.getQuantity());
	}
	
	@Test
    public void getBookTest_by_title() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		List<BookDetailsEntity> list = bookStoreServiceImpl.getBook("title", "TEST_TITLE");
		BookDetailsEntity book = list.get(0);
		
		verify(bookDetailsRepository, times(1)).findAll();
		
		assertNotNull(list);
		assertEquals("TEST_AUTHOR", book.getAuthor());
		assertEquals("123", book.getIsbn());
		assertEquals("TEST_TITLE", book.getTitle());
		assertEquals(new Double("100.0"), book.getPrice());
		assertEquals(new Integer("50"), book.getQuantity());
	}
	
	@Test
    public void getBookTest_isbn_exception() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		expectedEx.expect(RecordNotFoundException.class);
        expectedEx.expectMessage("Book not found with isbn : '000'");
		bookStoreServiceImpl.getBook("isbn", "000");
		
		verify(bookDetailsRepository, times(1)).findAll();
    }
	
	@Test
    public void getBookTest_title_exception() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		expectedEx.expect(RecordNotFoundException.class);
        expectedEx.expectMessage("Book not found with title : '000'");
		bookStoreServiceImpl.getBook("title", "000");
		
		verify(bookDetailsRepository, times(1)).findAll();
    }
	
	@Test
    public void getBookTest_author_exception() {
		
		when(bookDetailsRepository.findAll()).thenReturn(BookStoreDataStub.bookDetailsList());
		
		expectedEx.expect(RecordNotFoundException.class);
        expectedEx.expectMessage("Book not found with author : '000'");
		bookStoreServiceImpl.getBook("author", "000");
		
		verify(bookDetailsRepository, times(1)).findAll();
    }
	
	
	@Test
	public void purchaseBookTest_isbn() {
		
		when(bookDetailsRepository.findByIsbn(any())).thenReturn(BookStoreDataStub.bookDetails());
		bookStoreServiceImpl.purchaseBook(PurchaseDataStub.purchaseBookDto());
		
		ArgumentCaptor<BookDetailsEntity> captor = ArgumentCaptor.forClass(BookDetailsEntity.class);
		verify(bookDetailsRepository, times(1)).save(captor.capture());
		verify(bookDetailsRepository, times(1)).findByIsbn(any());
		verify(bookDetailsRepository, times(0)).findByAuthorAndTitle(any(),any());
		final BookDetailsEntity bookDetailsEntity = captor.getValue();
		
		Set<PurchaseHistoryEntity>  set  = bookDetailsEntity.getPurchaseHistory();	
		PurchaseHistoryEntity firstElement = set.stream().findFirst().get();
		
		assertEquals("TEST_AUTHOR", bookDetailsEntity.getAuthor());
		assertEquals("123", bookDetailsEntity.getIsbn());
		assertEquals("TEST_TITLE", bookDetailsEntity.getTitle());
		assertEquals(new Double("100.0"), bookDetailsEntity.getPrice());
		assertEquals(new Integer("48"), bookDetailsEntity.getQuantity());
		assertEquals(new Integer("50"), firstElement.getOriginalQuantity() );
		assertEquals(new Integer("48"), firstElement.getUpdatedQuantity() );
	}
	
	
	@Test
	public void purchaseBookTest_authorAndtitle() {
		
		when(bookDetailsRepository.findByAuthorAndTitle(any(),any())).thenReturn(BookStoreDataStub.bookDetails());
		PurchaseBookRequestDto dto = PurchaseDataStub.purchaseBookDto();
		dto.setIsbn(null);
		
		bookStoreServiceImpl.purchaseBook(dto);
		
		ArgumentCaptor<BookDetailsEntity> captor = ArgumentCaptor.forClass(BookDetailsEntity.class);
		verify(bookDetailsRepository, times(1)).save(captor.capture());
		verify(bookDetailsRepository, times(0)).findByIsbn(any());
		verify(bookDetailsRepository, times(1)).findByAuthorAndTitle(any(),any());
		final BookDetailsEntity bookDetailsEntity = captor.getValue();
		
		Set<PurchaseHistoryEntity>  set  = bookDetailsEntity.getPurchaseHistory();	
		PurchaseHistoryEntity firstElement = set.stream().findFirst().get();
		
		assertEquals("TEST_AUTHOR", bookDetailsEntity.getAuthor());
		assertEquals("123", bookDetailsEntity.getIsbn());
		assertEquals("TEST_TITLE", bookDetailsEntity.getTitle());
		assertEquals(new Double("100.0"), bookDetailsEntity.getPrice());
		assertEquals(new Integer("48"), bookDetailsEntity.getQuantity());
		assertEquals(new Integer("50"), firstElement.getOriginalQuantity() );
		assertEquals(new Integer("48"), firstElement.getUpdatedQuantity() );
	}
	
	@Test
	public void purchaseBookTest_author_and_title_null() {
		
		PurchaseBookRequestDto dto = PurchaseDataStub.purchaseBookDto();
		dto.setIsbn(null);
		dto.setTitle(null);
		
		expectedEx.expect(FieldMissingException.class);
        expectedEx.expectMessage("One of the Mandatory Fields from Author, Title, ISBN : 'null' is missing! Please try again!");
		bookStoreServiceImpl.purchaseBook(dto);
		
		verify(bookDetailsRepository, times(0)).save(any());
		verify(bookDetailsRepository, times(0)).findByIsbn(any());
		verify(bookDetailsRepository, times(0)).findByAuthorAndTitle(any(),any());
		
	}
	
	@Test
	public void purchaseBookTest_isbn_notFound() {
		
		when(bookDetailsRepository.findByIsbn(any())).thenReturn(null);
		PurchaseBookRequestDto dto = PurchaseDataStub.purchaseBookDto();
		
		expectedEx.expect(ResourceException.class);
        expectedEx.expectMessage("Book with ISBN | Author | Title  : '123 , TEST_AUTHOR , TEST_TITLE' is Out of Stock or Unavailable! Please try again later!");
		bookStoreServiceImpl.purchaseBook(dto);
		
		verify(bookDetailsRepository, times(0)).save(any());
		verify(bookDetailsRepository, times(1)).findByIsbn(any());
		verify(bookDetailsRepository, times(0)).findByAuthorAndTitle(any(),any());
		
	}
	
	@Test
	public void purchaseBookTest_authorAndTitle_notFound() {
		
		PurchaseBookRequestDto dto = PurchaseDataStub.purchaseBookDto();
		dto.setIsbn(null);
		
		expectedEx.expect(ResourceException.class);
        expectedEx.expectMessage("Book with ISBN | Author | Title  : 'null , TEST_AUTHOR , TEST_TITLE' is Out of Stock or Unavailable! Please try again later!");
		bookStoreServiceImpl.purchaseBook(dto);
		
		verify(bookDetailsRepository, times(0)).save(any());
		verify(bookDetailsRepository, times(0)).findByIsbn(any());
		verify(bookDetailsRepository, times(1)).findByAuthorAndTitle(any(),any());
		
	}

	@Test
	public void checkBookQuantityTest() {
		expectedEx.expect(ResourceException.class);
        expectedEx.expectMessage("Book with ISBN | Author | Title  : '123 , TEST_AUTHOR , TEST_TITLE' is Out of Stock or Unavailable! Please try again later!");
        bookStoreServiceImpl.checkBookQuantity(PurchaseDataStub.purchaseBookDto(), 1);
	}

}
