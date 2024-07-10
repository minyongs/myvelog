package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.domain.Blog;
import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.BlogDTO;
import com.hellovelog.myvelog.repository.BlogRepository;
import com.hellovelog.myvelog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public void makeUserBlog(User user) {
        Blog blog = new Blog();
        blog.setUser(user);
        blogRepository.save(blog);
        user.setBlog(blog);
        userRepository.save(user);
    }

    @Transactional
    public BlogDTO getUserBlog(String username) {
        User user = userRepository.findByUsernameWithBlog(username);
        if (user.getBlog() == null) {
            makeUserBlog(user);
        }
        return BlogDTO.fromEntity(user.getBlog());
    }
}