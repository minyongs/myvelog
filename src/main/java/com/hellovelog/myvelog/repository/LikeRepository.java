package com.hellovelog.myvelog.repository;

import com.hellovelog.myvelog.domain.Like;
import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
}
