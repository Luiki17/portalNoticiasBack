package com.portal.backend.portalNoticias.controller;

import com.portal.backend.portalNoticias.model.New;
import com.portal.backend.portalNoticias.model.NewResponseRest;
import com.portal.backend.portalNoticias.response.ErrorResponse;
import com.portal.backend.portalNoticias.service.INewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewControllerTest {

    @Mock
    private INewService newService;

    @InjectMocks
    private NewController newController;

    private New news;
    private NewResponseRest newResponseRest;

    @BeforeEach
    void setUp() {
        news = new New();
        news.setId(1L);
        news.setTitle("Test News");
        news.setSummary("Test Content");

        newResponseRest = new NewResponseRest();
        newResponseRest.getNewResponse().setNews(Collections.singletonList(news));
    }

    @Test
    void save_ShouldReturnOkResponse_WhenNewsIsSavedSuccessfully() {
        // Arrange
        ResponseEntity<NewResponseRest> serviceResponse = ResponseEntity.ok(newResponseRest);
        when(newService.save(any(New.class))).thenReturn(serviceResponse);

        // Act
        ResponseEntity<?> response = newController.save(news);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(serviceResponse, response.getBody());
    }

    @Test
    void save_ShouldReturnNotFound_WhenNewsListIsEmpty() {
        // Arrange
        newResponseRest.getNewResponse().setNews(Collections.emptyList());
        ResponseEntity<NewResponseRest> serviceResponse = ResponseEntity.ok(newResponseRest);
        when(newService.save(any(New.class))).thenReturn(serviceResponse);

        // Act
        ResponseEntity<?> response = newController.save(news);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(404, errorResponse.getCode());
        assertEquals("No news found", errorResponse.getDescription());
    }

    @Test
    void save_ShouldReturnInternalServerError_WhenExceptionIsThrown() {
        // Arrange
        when(newService.save(any(New.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = newController.save(news);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(500, errorResponse.getCode());
    }

    @Test
    void deleteById_ShouldReturnOkResponse_WhenDeletionIsSuccessful() {
        // Arrange
        ResponseEntity<NewResponseRest> serviceResponse = ResponseEntity.ok(newResponseRest);
        when(newService.deleteById(anyLong())).thenReturn(serviceResponse);

        // Act
        ResponseEntity<?> response = newController.deleteById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(serviceResponse, response.getBody());
    }

    @Test
    void deleteById_ShouldReturnInternalServerError_WhenExceptionIsThrown() {
        // Arrange
        when(newService.deleteById(anyLong())).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = newController.deleteById(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(500, errorResponse.getCode());
        assertEquals("Internal Server Error", errorResponse.getDescription());
    }

    @Test
    void getNews_ShouldReturnPageOfNews() {
        // Arrange
        Page<New> expectedPage = new PageImpl<>(Collections.singletonList(news));
        when(newService.findAll(any(PageRequest.class))).thenReturn(expectedPage);

        // Act
        Page<New> result = newController.getNews(0);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(news, result.getContent().get(0));
    }

    @Test
    void getNewsByTitle_ShouldReturnFilteredPageOfNews() {
        // Arrange
        Page<New> expectedPage = new PageImpl<>(Collections.singletonList(news));
        when(newService.findByTitleContainingIgnoreCase(anyString(), any(PageRequest.class))).thenReturn(expectedPage);

        // Act
        Page<New> result = newController.getNewsByTitle("test", 0);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(news, result.getContent().get(0));
    }
}