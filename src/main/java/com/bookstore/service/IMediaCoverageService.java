package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.entity.BookDetailsEntity;

public interface IMediaCoverageService {
	List<MediaCoverageResponseDto> getMediaCoverageFromApi(BookDetailsEntity bookDetails,String searchField, String searchValue);
}
