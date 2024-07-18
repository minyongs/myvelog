package com.hellovelog.myvelog.dto;

import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.domain.PostTag;
import com.hellovelog.myvelog.util.HtmlUtils;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private MultipartFile thumbnailFile; // 썸네일 이미지 파일
    private String thumbnailUrl; // 썸네일 이미지 URL
    private String visibility; // 공개/비공개 상태
    private LocalDateTime createdAt;
    private String author;
    private int likeCount;
    private String strippedContent;
    private Set<String> tags;


    public static PostDTO fromEntity(Post post) {
        return PostDTO.builder()
                .postId(post.getPostId())
//                .username(post.getUser().getUsername())
                .blogId(post.getBlog().getBlogId())
                .title(post.getTitle())
                .content(post.getContent())
                .temporaryPost(post.getTemporaryPost())
                .thumbnailUrl(post.getThumbnailUrl())
                .visibility(post.getVisibility())
                .strippedContent(HtmlUtils.stripHtml(post.getContent()))
                .createdAt(post.getCreatedAt())
                .author(post.getBlog().getUser().getUsername())
                .likeCount(post.getLikeCount())
                .tags(post.getPostTags().stream()
                        .map(postTag -> postTag.getTags().getName())
                        .collect(Collectors.toSet()))
                .build();
    }
}
