package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.BookDetailsEntity;


/**
 * @author mohammadnaushad
 *
 */
@Repository
public interface BookDetailsRepository  extends JpaRepository<BookDetailsEntity, Long>{
	
	BookDetailsEntity findByIsbn(String isbn);
	BookDetailsEntity findByAuthorAndTitle(String author, String title);
	
}
