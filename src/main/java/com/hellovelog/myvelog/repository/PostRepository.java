package com.hellovelog.myvelog.repository;

import com.hellovelog.myvelog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "JOIN FETCH p.blog b " +
            "JOIN FETCH b.user u " +
            "WHERE p.postId = :postId")
    Post findByIdWithBlogAndUser(@Param("postId") Long postId);

    @Query("SELECT p FROM Post p " +
            "JOIN FETCH p.blog b " +
            "JOIN FETCH b.user u")
    Page<Post> findAllWithBlogAndUser(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.createdAt >= :startDate ORDER BY p.likeCount DESC")
    Page<Post> findTrendingPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findLatestPosts(Pageable pageable);

}
