package com.bookstore.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * @author mohammadnaushad
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Getter @lombok.Setter @lombok.ToString
@lombok.EqualsAndHashCode
public class MediaCoverageResponseDto implements Serializable{

	private static final long serialVersionUID = -689015774170455011L;
	private String userId;
	private String id;
	private String title;
	private String body;
	
}
