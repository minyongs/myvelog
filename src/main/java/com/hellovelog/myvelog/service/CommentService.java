package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.domain.Comment;
import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.CommentDTO;
import com.hellovelog.myvelog.repository.CommentRepository;
import com.hellovelog.myvelog.repository.PostRepository;
import com.hellovelog.myvelog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_PostIdAndParentIsNullOrderByCreatedAtDesc(postId)
                .stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO addComment(Long postId, CommentDTO commentDTO, String username) {
        User user = userRepository.findByUsername(username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Comment parent = commentDTO.getParentId() != null ? findById(commentDTO.getParentId()) : null;

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .user(user)
                .post(post)
                .parent(parent)
                .build();

        return CommentDTO.fromEntity(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글 찾을수 없음."));
    }
}
