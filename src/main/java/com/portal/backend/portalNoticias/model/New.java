package com.portal.backend.portalNoticias.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="new")
public class New implements Serializable {

    private static final long serialVersionUID = -3298837734174984545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1024)
    private String summary;
    @Column(name = "published_at")
    private String publishedAt;
    @Column(name = "news_site")
    private String newsSite;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "saved_at")
    private String savedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public String getNewsSite() {
        return newsSite;
    }
    public void setNewsSite(String newsSite) {
        this.newsSite = newsSite;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getSavedAt() {
        return savedAt;
    }
    public void setSavedAt(String savedAt) {
        this.savedAt = savedAt;
    }

}
