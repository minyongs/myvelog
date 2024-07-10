package com.hellovelog.myvelog.dto;

import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.util.HtmlUtils;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Long postId;
    private String username; // User의 username
    private Long blogId;
    private String title;
    private String content;
    private Boolean temporaryPost;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String author;
    private int likeCount;
    private String strippedContent;

    public static PostDTO fromEntity(Post post) {
        return PostDTO.builder()
                .postId(post.getPostId())
//                .username(post.getUser().getUsername())
                .blogId(post.getBlog().getBlogId())
                .title(post.getTitle())
                .content(post.getContent())
                .temporaryPost(post.getTemporaryPost())
                .imageUrl(post.getImageUrl())
                .strippedContent(HtmlUtils.stripHtml(post.getContent()))
                .createdAt(post.getCreatedAt())
                .author(post.getBlog().getUser().getUsername())
                .likeCount(post.getLikeCount())
                .build();
    }
}
