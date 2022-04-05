package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostDtoV2;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.model.Post;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequestMapping()
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // @RequestMapping(method = RequestMethod.POST)
   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createDto(postDto), HttpStatus.CREATED);
    }

    // get all posts
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByid(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping("/api/v2/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByidV2(@PathVariable(name = "id") long id){
        PostDto post = postService.getById(id);

        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(post.getId());
        postDtoV2.setContent(post.getContent());
        postDtoV2.setComments(post.getComments());
        postDtoV2.setDescription(post.getDescription());
        postDtoV2.setTitle(post.getTitle());
        postDtoV2.setTags(Arrays.asList(new String[]{"java","Spring"}));

        return ResponseEntity.ok(postDtoV2);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

}
