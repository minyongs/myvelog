package com.hellovelog.myvelog.dto;

import com.hellovelog.myvelog.domain.Blog;
import com.hellovelog.myvelog.domain.Post;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDTO {
    private Long blogId;
    private String username;
    private List<PostDTO> posts;


    public static BlogDTO fromEntity(Blog blog) {
        return BlogDTO.builder()
                .blogId(blog.getBlogId())
                .username(blog.getUser() != null ? blog.getUser().getUsername() : null)
                .posts(blog.getPosts() != null ? blog.getPosts().stream()
                        .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                        .map(PostDTO::fromEntity)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }
}