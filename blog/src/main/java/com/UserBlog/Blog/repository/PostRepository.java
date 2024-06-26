package com.UserBlog.Blog.repository;

import com.UserBlog.Blog.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    List<Post> findByContentContaining(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllPostsOrderByCreatedAtDesc();
}
