package com.portal.backend.portalNoticias.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.*;

import java.lang.reflect.Field;

class NewTest {

    @Test
    void testEntityAnnotation() {
        // Verifica que la clase tenga la anotación @Entity
        assertNotNull(New.class.getAnnotation(Entity.class));
    }

    @Test
    void testTableAnnotation() {
        // Verifica la anotación @Table con el nombre correcto
        Table tableAnnotation = New.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("new", tableAnnotation.name());
    }

    @Test
    void testSerialVersionUID() throws NoSuchFieldException, IllegalAccessException {
        // Verifica el serialVersionUID
        Field field = New.class.getDeclaredField("serialVersionUID");
        field.setAccessible(true);
        long serialVersionUID = field.getLong(null);
        assertEquals(-3298837734174984545L, serialVersionUID);
    }

    @Test
    void testIdAnnotation() throws NoSuchFieldException {
        // Verifica la anotación @Id en el campo id
        Field idField = New.class.getDeclaredField("id");
        assertNotNull(idField.getAnnotation(Id.class));
        assertNotNull(idField.getAnnotation(GeneratedValue.class));
        assertEquals(GenerationType.IDENTITY,
                idField.getAnnotation(GeneratedValue.class).strategy());
    }

    @Test
    void testColumnAnnotations() throws NoSuchFieldException {
        // Verifica las anotaciones @Column en los campos
        Field summaryField = New.class.getDeclaredField("summary");
        Column summaryColumn = summaryField.getAnnotation(Column.class);
        assertNotNull(summaryColumn);
        assertEquals(1024, summaryColumn.length());

        Field publishedAtField = New.class.getDeclaredField("publishedAt");
        Column publishedAtColumn = publishedAtField.getAnnotation(Column.class);
        assertNotNull(publishedAtColumn);
        assertEquals("published_at", publishedAtColumn.name());

        Field newsSiteField = New.class.getDeclaredField("newsSite");
        Column newsSiteColumn = newsSiteField.getAnnotation(Column.class);
        assertNotNull(newsSiteColumn);
        assertEquals("news_site", newsSiteColumn.name());

        Field imageUrlField = New.class.getDeclaredField("imageUrl");
        Column imageUrlColumn = imageUrlField.getAnnotation(Column.class);
        assertNotNull(imageUrlColumn);
        assertEquals("image_url", imageUrlColumn.name());

        Field savedAtField = New.class.getDeclaredField("savedAt");
        Column savedAtColumn = savedAtField.getAnnotation(Column.class);
        assertNotNull(savedAtColumn);
        assertEquals("saved_at", savedAtColumn.name());
    }

    @Test
    void testGettersAndSetters() {
        // Prueba de getters y setters
        New news = new New();

        // Test ID
        news.setId(1L);
        assertEquals(1L, news.getId());

        // Test Title
        news.setTitle("Breaking News");
        assertEquals("Breaking News", news.getTitle());

        // Test Summary
        news.setSummary("This is a summary of the breaking news");
        assertEquals("This is a summary of the breaking news", news.getSummary());

        // Test PublishedAt
        news.setPublishedAt("2023-01-01");
        assertEquals("2023-01-01", news.getPublishedAt());

        // Test NewsSite
        news.setNewsSite("CNN");
        assertEquals("CNN", news.getNewsSite());

        // Test ImageUrl
        news.setImageUrl("http://example.com/image.jpg");
        assertEquals("http://example.com/image.jpg", news.getImageUrl());

        // Test SavedAt
        news.setSavedAt("2023-01-01T12:00:00");
        assertEquals("2023-01-01T12:00:00", news.getSavedAt());
    }

    @Test
    void testNoArgsConstructor() {
        // Verifica que se pueda crear una instancia sin argumentos
        New news = new New();
        assertNotNull(news);
    }

    @Test
    void testFieldDefaults() {
        // Verifica los valores por defecto de los campos
        New news = new New();
        assertNull(news.getId());
        assertNull(news.getTitle());
        assertNull(news.getSummary());
        assertNull(news.getPublishedAt());
        assertNull(news.getNewsSite());
        assertNull(news.getImageUrl());
        assertNull(news.getSavedAt());
    }
}