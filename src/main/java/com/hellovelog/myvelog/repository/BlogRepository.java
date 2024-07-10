package com.hellovelog.myvelog.repository;


import com.hellovelog.myvelog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findByUserUsername(String username);
}

