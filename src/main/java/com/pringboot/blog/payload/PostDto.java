package com.pringboot.blog.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should at least 2 or more Characters ")
    private String title;
    @NotEmpty
    @Size(min = 2, message = "Post description should at least 10 Characters ")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comment;
}
