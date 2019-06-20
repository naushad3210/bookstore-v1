package com.bookstore.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @author mohammadnaushad
 *
 */
public class ValidationUtil {
	
	public static List<String> fromBindingErrors(BindingResult result) {
		List<String> errorsList= new ArrayList<>();
		
		for (ObjectError err : result.getAllErrors()) {
			errorsList.add(err.getDefaultMessage());
		}
		return errorsList;
	}
}
