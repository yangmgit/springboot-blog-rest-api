package com.springboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "email should not be null or empty")
    @Email
    private String email;

    @NotNull
    @Size(min = 10, message = "body should have at least 10 characters")
    private String body;
}
