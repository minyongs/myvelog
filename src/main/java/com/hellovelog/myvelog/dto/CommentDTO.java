package com.hellovelog.myvelog.dto;

import com.hellovelog.myvelog.domain.Comment;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private String content;
    private String username;
    private Long postId;
    private Long parentId;
    private List<CommentDTO> replies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDTO fromEntity(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .postId(comment.getPost().getPostId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .replies(comment.getReplies() != null ? comment.getReplies().stream()
                        .map(CommentDTO::fromEntity)
                        .collect(Collectors.toList()) : null)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<CommentDTO> fromEntityList(List<Comment> comments) {
        return comments.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
