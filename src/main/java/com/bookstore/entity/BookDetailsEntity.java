package com.bookstore.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/**
 * @author mohammadnaushad
 *
 */
/*
 * This is our model class and it corresponds to User table in database
 */
@Entity
@Table(name="BOOK_DETAILS",uniqueConstraints = @UniqueConstraint(columnNames = {"title","author"}))
@lombok.Getter @lombok.Setter
public class BookDetailsEntity extends AuditEntity{
	
	private static final long serialVersionUID = 6337667652250745029L;

	@Id
	@Column(name="BOOK_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ISBN",unique=true)
	private String isbn;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="AUTHOR")
	private String author;
	
	@Column(name="PRICE")
	private Double price;
	
	@Column(name="QUANTITY")
	private Integer quantity;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bookdetails", cascade = CascadeType.ALL)
    private Set<PurchaseHistoryEntity> purchaseHistory;
	
	@PrePersist
	public void prePersist() {
		if(this.quantity==null)
			this.quantity=0;
	}
	
}