package com.softserve.edu.dto;

import com.softserve.edu.entity.UserItemEntity;

public class UserItemDto {

    private Long id;
    private String title;
    private String description;

    public UserItemDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public UserItemDto(UserItemEntity userItemEntity) {
        id = userItemEntity.getId();
        title = userItemEntity.getTitle();
        description = userItemEntity.getDescription();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
