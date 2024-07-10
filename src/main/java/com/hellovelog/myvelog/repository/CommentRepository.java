package com.hellovelog.myvelog.repository;

import com.hellovelog.myvelog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.post p " +
            "JOIN FETCH c.user u " +
            "LEFT JOIN FETCH c.replies r " +
            "WHERE c.post.postId = :postId " +
            "AND c.parent IS NULL " +
            "ORDER BY c.createdAt DESC")
    List<Comment> findByPost_PostIdAndParentIsNullOrderByCreatedAtDesc(@Param("postId") Long postId); // N+1 해결
}
