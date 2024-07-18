package com.hellovelog.myvelog.service;

import com.hellovelog.myvelog.config.FileStorageProperties;
import com.hellovelog.myvelog.domain.*;
import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TagsRepository tagsRepository;
    private final FileStorageProperties fileStorageProperties;

    @Transactional
    public void savePost(String username, PostDTO postDTO) {
        Blog blog = blogRepository.findByUserUsername(username);
        String thumbnailUrl = saveThumbnail(postDTO.getThumbnailFile());

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .blog(blog)
                .temporaryPost(false)
                .thumbnailUrl(thumbnailUrl)
                .visibility(postDTO.getVisibility())
                .build();

        postRepository.save(post);

        // 태그 저장 로직
        Set<PostTag> postTags = postDTO.getTags().stream().map(tagName -> {
            Tags tag = tagsRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tags();
                tag.setName(tagName);
                tagsRepository.save(tag);
            }

            PostTag postTag = new PostTag();
            postTag.setPost(post);
            postTag.setTags(tag);
            return postTag;
        }).collect(Collectors.toSet());

        post.setPostTags(postTags);
        postRepository.save(post);
    }


    @Transactional
    public void saveTemporaryPost(String username, PostDTO postDTO) {
        Blog blog = blogRepository.findByUserUsername(username);
        String thumbnailUrl = saveThumbnail(postDTO.getThumbnailFile());

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .blog(blog)
                .temporaryPost(true)
                .thumbnailUrl(thumbnailUrl)
                .visibility(postDTO.getVisibility())
                .build();

        postRepository.save(post);
    }

    private String saveThumbnail(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String directoryPath = fileStorageProperties.getUploadDir();
        Path directory = Paths.get(directoryPath);

        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw new RuntimeException("디렉토리 생성 실패", e);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = directory.resolve(fileName);

        try {
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("썸네일 이미지 저장 실패", e);
        }

        return "/uploads/thumbnails/" + fileName;
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
    public void deletePost(Long id) {
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

            String thumbnailUrl = saveThumbnail(postDTO.getThumbnailFile());
            if (thumbnailUrl != null) {
                post.setThumbnailUrl(thumbnailUrl);
            }

            post.setVisibility(postDTO.getVisibility());
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

        Page<PostDTO> postPage = postRepository.findLatestPosts(pageable).map(PostDTO::fromEntity);
        return postPage;
    }
}
