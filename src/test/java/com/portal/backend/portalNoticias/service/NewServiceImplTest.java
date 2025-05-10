package com.portal.backend.portalNoticias.service;

import com.portal.backend.portalNoticias.dao.INewDao;
import com.portal.backend.portalNoticias.model.New;
import com.portal.backend.portalNoticias.model.NewResponse;
import com.portal.backend.portalNoticias.model.NewResponseRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewServiceImplTest {

    @Mock
    private INewDao newDao;

    @InjectMocks
    private NewServiceImpl newService;

    private New news;
    private NewResponseRest responseRest;

    @BeforeEach
    void setUp() {
        news = new New();
        news.setId(1L);
        news.setTitle("Test News");
        news.setSummary("Test Content");

        responseRest = new NewResponseRest();
        NewResponse newResponse = new NewResponse();
        newResponse.setNews(new ArrayList<>());
        responseRest.setNewResponse(newResponse);
    }

    @Test
    void save_ShouldReturnOkResponse_WhenNewsIsSavedSuccessfully() {
        // Arrange
        when(newDao.save(any(New.class))).thenReturn(news);

        // Act
        ResponseEntity<NewResponseRest> response = newService.save(news);
        NewResponseRest responseBody = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getNewResponse().getNews().size());
        assertEquals("Response ok", responseBody.getMetadata().get(0).get("type"));
        assertEquals("00", responseBody.getMetadata().get(0).get("code"));
        assertEquals("New saved successfully", responseBody.getMetadata().get(0).get("date"));

        verify(newDao, times(1)).save(any(New.class));
    }

    @Test
    void save_ShouldReturnBadRequest_WhenNewsIsNotSaved() {
        // Arrange
        New unsavedNews = new New(); // News without ID (not saved)
        when(newDao.save(any(New.class))).thenReturn(unsavedNews);

        // Act
        ResponseEntity<NewResponseRest> response = newService.save(news);
        NewResponseRest responseBody = response.getBody();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals("Response error", responseBody.getMetadata().get(0).get("type"));
        assertEquals("-1", responseBody.getMetadata().get(0).get("code"));
        assertEquals("Error saving the new", responseBody.getMetadata().get(0).get("date"));

        verify(newDao, times(1)).save(any(New.class));
    }

    @Test
    void save_ShouldReturnInternalServerError_WhenExceptionIsThrown() {
        // Arrange
        when(newDao.save(any(New.class))).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<NewResponseRest> response = newService.save(news);
        NewResponseRest responseBody = response.getBody();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals("Response error", responseBody.getMetadata().get(0).get("type"));
        assertEquals("-1", responseBody.getMetadata().get(0).get("code"));
        assertEquals("Error saving the new", responseBody.getMetadata().get(0).get("date"));

        verify(newDao, times(1)).save(any(New.class));
    }

    @Test
    void deleteById_ShouldReturnOkResponse_WhenDeletionIsSuccessful() {
        // Arrange
        doNothing().when(newDao).deleteById(anyLong());

        // Act
        ResponseEntity<NewResponseRest> response = newService.deleteById(1L);
        NewResponseRest responseBody = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals("Response ok", responseBody.getMetadata().get(0).get("type"));
        assertEquals("00", responseBody.getMetadata().get(0).get("code"));
        assertEquals("New deleted successfully", responseBody.getMetadata().get(0).get("date"));

        verify(newDao, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteById_ShouldReturnInternalServerError_WhenExceptionIsThrown() {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(newDao).deleteById(anyLong());

        // Act
        ResponseEntity<NewResponseRest> response = newService.deleteById(1L);
        NewResponseRest responseBody = response.getBody();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals("Response error", responseBody.getMetadata().get(0).get("type"));
        assertEquals("-1", responseBody.getMetadata().get(0).get("code"));
        assertEquals("Error deleting the new", responseBody.getMetadata().get(0).get("date"));

        verify(newDao, times(1)).deleteById(anyLong());
    }

    @Test
    void findAll_ShouldReturnPageOfNews() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<New> expectedPage = new PageImpl<>(Collections.singletonList(news));
        when(newDao.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<New> result = newService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(news, result.getContent().get(0));

        verify(newDao, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByTitleContainingIgnoreCase_ShouldReturnFilteredPageOfNews() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<New> expectedPage = new PageImpl<>(Collections.singletonList(news));
        when(newDao.findByTitleContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<New> result = newService.findByTitleContainingIgnoreCase("test", pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(news, result.getContent().get(0));

        verify(newDao, times(1)).findByTitleContainingIgnoreCase(anyString(), any(Pageable.class));
    }
}