package com.pringboot.blog.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PostDto {
    @Id
    private Long id;
    private String title;
    private String description;
    private String content;
}