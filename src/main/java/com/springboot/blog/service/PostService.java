package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createDto(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deleteById(Long id);
}
