package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookstore.dto.response.MediaCoverageResponseDto;
import com.bookstore.entity.BookDetailsEntity;
import com.bookstore.exceptions.RecordNotFoundException;
import com.bookstore.exceptions.ThirdPartyApiException;
import com.bookstore.service.IMediaCoverageService;

@Service
public class MediaCoverageServiceImpl implements IMediaCoverageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MediaCoverageServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	public List<MediaCoverageResponseDto> getMediaCoverageFromApi(BookDetailsEntity bookDetails,String searchField, String searchValue) {
		List<MediaCoverageResponseDto> mediaCovResponse = new ArrayList<>();
        
		ResponseEntity<List<MediaCoverageResponseDto>> response = restTemplate.exchange(env.getProperty("media.coverage.api.uri"), 
        													HttpMethod.GET, null, new ParameterizedTypeReference<List<MediaCoverageResponseDto>>() {});
        
        HttpStatus statusCode = response.getStatusCode();
        LOGGER.info("Response Satus Code: {}", statusCode);
 
        if (statusCode == HttpStatus.OK) {
        	List<MediaCoverageResponseDto> list = response.getBody();
 
            if (!list.isEmpty()) {
            	list.stream().filter(f->f.getTitle().toUpperCase().contains(bookDetails.getTitle().toUpperCase()) || f.getBody().toUpperCase().contains(bookDetails.getTitle().toUpperCase()))
            			.forEach(media->{
            				MediaCoverageResponseDto dto = new MediaCoverageResponseDto();
            				dto.setTitle(media.getTitle());
            				mediaCovResponse.add(dto);
            			});
            }
        }else {
        	LOGGER.info("Media Coverage Api Issue");
        	throw new ThirdPartyApiException("Media Coverage Api ", " [ http-code : " , statusCode+"]");
        }
		
        	if(mediaCovResponse.isEmpty()) {
        		LOGGER.info("Media Coverage Not Found");
        		throw new RecordNotFoundException("Media Coverage",searchField,searchValue);
        	}
        	
       	return mediaCovResponse;
	}

}
