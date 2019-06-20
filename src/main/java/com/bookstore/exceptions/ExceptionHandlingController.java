package com.bookstore.exceptions;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bookstore.dto.response.ExceptionResponseDto;
import com.bookstore.util.ValidationUtil;
/**
 * @author mohammadnaushad
 * @category Global Exception Handling Controller
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> invalidInput(MethodArgumentNotValidException ex) {
    	LOGGER.error("-- Inside [ExceptionHandlingController] [invalidInput()] with [data:{}]",ex.getParameter());
        BindingResult result = ex.getBindingResult();
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorCode("Validation Error");
        response.setErrorMessage("Invalid inputs.");
        response.setErrors(ValidationUtil.fromBindingErrors(result));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ResourceNotFoundException.class,RecordNotFoundException.class})
    public ResponseEntity<ExceptionResponseDto> resourceNotFound(Exception ex) {
    	LOGGER.error("-- Inside [ExceptionHandlingController] [resourceNotFound()] with [data:{}]",ex.getMessage());
    	ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorCode("Not Found");
        response.setErrorMessage(ex.getMessage());
 
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({ThirdPartyApiException.class})
    public ResponseEntity<ExceptionResponseDto> thirdPartyApiException(Exception ex) {
    	LOGGER.error("-- Inside [ExceptionHandlingController] [thirdPartyApiException()] with [data:{}]",ex.getMessage());
    	ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorCode("Third Party Api Issue");
        response.setErrorMessage(ex.getMessage());
 
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({SQLException.class,HibernateException.class,DuplicateRecordException.class})
	public  ResponseEntity<ExceptionResponseDto> handleSQLException(Exception ex){
    	LOGGER.error("-- Inside [ExceptionHandlingController] [handleSQLException()] with [data:{}]",ex.getMessage());
    	ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorCode("Database Error");
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
    @ExceptionHandler({Exception.class})
	public  ResponseEntity<ExceptionResponseDto> handleException(Exception ex){
    	LOGGER.error("-- Inside [ExceptionHandlingController] [handleException()] with [data:{}]",ex.getMessage());
    	ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorCode("There is a problem! Please contact administrator!");
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
    
}