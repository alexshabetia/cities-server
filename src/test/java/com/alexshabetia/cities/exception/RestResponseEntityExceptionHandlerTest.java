package com.alexshabetia.cities.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestResponseEntityExceptionHandlerTest {

  @Mock HttpHeaders headers;
  @Mock HttpStatus status;
  @Mock FieldError objectError;
  @Autowired RestResponseEntityExceptionHandler exceptionHandler;
  @Mock private NotFoundException notFoundException;
  @Mock private MethodArgumentNotValidException methodArgumentNotValidException;
  @Mock private WebRequest webRequest;
  @Mock private BindingResult bindingResult;

  @Test
  public void handleNotFound_returnsNotFound() {
    var response = exceptionHandler.handleNotFound(notFoundException, webRequest);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void handleMethodArgumentNotValid_returnsBadRequest() {
    var fieldName = "fieldName";
    var errorMessage = "errorMessage";
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(objectError));
    when(objectError.getField()).thenReturn(fieldName);
    when(objectError.getDefaultMessage()).thenReturn(errorMessage);
    Map<String, String> expected = new HashMap<>();
    expected.put(fieldName, errorMessage);

    var response =
        exceptionHandler.handleMethodArgumentNotValid(
            methodArgumentNotValidException, headers, status, webRequest);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(expected, response.getBody());
  }
}
