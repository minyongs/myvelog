package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.domain.Blog;
import com.hellovelog.myvelog.domain.Like;
import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.repository.BlogRepository;
import com.hellovelog.myvelog.repository.LikeRepository;
import com.hellovelog.myvelog.repository.PostRepository;
import com.hellovelog.myvelog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void savePost(String username, PostDTO postDTO){
        Blog blog = blogRepository.findByUserUsername(username);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .blog(blog)
                .temporaryPost(false)
                .build();

        postRepository.save(post);
    }

    @Transactional
    public void saveTemporaryPost(String username,PostDTO postDTO){
        Blog blog = blogRepository.findByUserUsername(username);

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .blog(blog)
                .temporaryPost(true)
                .build();

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAllWithBlogAndUser(pageable)
                .map(PostDTO::fromEntity);

    }

    @Transactional(readOnly = true)
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findByIdWithBlogAndUser(id);
        return PostDTO.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    @Transactional
    public int toggleLike(Long postId, String username) {
        User user = userRepository.findByUsername(username);
        Post post = postRepository.findByIdWithBlogAndUser(postId);

        Optional<Like> likeOpt = likeRepository.findByUserAndPost(user, post);
        if (likeOpt.isPresent()) {
            likeRepository.delete(likeOpt.get());
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            Like like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);
        }
        postRepository.save(post);
        return post.getLikeCount();
    }


    @Transactional
    public void editPost(Long id, PostDTO postDTO) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            postRepository.save(post);
        } else {
            throw new RuntimeException("게시물 찾을 수 없음" + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> getTrendingPosts(LocalDateTime startDate, Pageable pageable) {
        return postRepository.findTrendingPosts(startDate, pageable).map(PostDTO::fromEntity);
    }
    @Transactional(readOnly = true)
    public Page<PostDTO> getLatestPosts(Pageable pageable) {
        return postRepository.findLatestPosts(pageable).map(PostDTO::fromEntity);
    }


}
