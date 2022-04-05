package com.springboot.blog.repository;

import com.springboot.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/*********no need of @Repository
 * since <SimpleJpaRepository> has already does so
 * **********/
public interface PostRepository extends JpaRepository<Post, Long> {
}
