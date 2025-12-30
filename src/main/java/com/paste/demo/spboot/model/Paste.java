package com.paste.demo.spboot.model;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
public class Paste {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private String id;
    @Column(columnDefinition="TEXT", nullable=false)
    private String content;
    private LocalDateTime createdAt;
    private Long expiresAt;
    private Integer maxViews;
    private Integer viewCount;
    @PrePersist
    public void onCreate() {
    	createdAt=LocalDateTime.now();
    	viewCount=0;
    
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Long getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(long l) {
		this.expiresAt = l;
	}
	public Integer getMaxViews() {
		return maxViews;
	}
	public void setMaxViews(Integer maxViews) {
		this.maxViews = maxViews;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
}
