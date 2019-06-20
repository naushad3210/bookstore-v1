package com.bookstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @author mohammadnaushad
 *
 */
/*
 * This is our model class and it corresponds to User table in database
 */
@Entity
@Table(name="PURCHASE_HISTORY")
@lombok.Getter @lombok.Setter
public class PurchaseHistoryEntity extends AuditEntity{

	private static final long serialVersionUID = -2077316216426995368L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ORIGINAL_QUANTITY")
	private Integer originalQuantity;
	
	@Column(name="UPDATED_QUANTITY")
	private Integer updatedQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_ID", nullable = false)
    private BookDetailsEntity bookdetails;

}