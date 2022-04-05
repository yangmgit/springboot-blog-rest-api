package com.springboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
@Data
public class PostDtoV2 {

        private long id;
        // title can not be null or empty
        // title should have at least  2 characters
        @NotNull
        @Size(min = 2, message = "title should have at least 2 characters")
        private String title;

        @NotNull
        @Size(min = 10, message = "description should have at least 10 characters")
        private String description;

        @NotNull
        private String content;
        private Set<CommentDto> comments;

        private List<String> tags;


}
