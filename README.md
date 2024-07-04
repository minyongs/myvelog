# myvelog
## ERD

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/ae7dbfcd-81ec-4e1a-a66e-93ee856e1f69/07bdb016-ad38-49d9-8ffa-e0562fce2ae1/Untitled.png)

```sql
-- Roles 
CREATE TABLE Roles (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(45) NOT NULL UNIQUE
);

-- Users
CREATE TABLE Users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    user_email VARCHAR(45) NOT NULL,
    role_id BIGINT,
    updated_at DATETIME NULL,
    profile_pic VARCHAR(45) NULL,
    registration_date DATETIME NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

-- Blogs 
CREATE TABLE Blogs (
    blog_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    blog_name VARCHAR(45) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Posts 
CREATE TABLE Posts (
    post_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    blog_id BIGINT,
    title VARCHAR(45) NOT NULL,
    content VARCHAR(45) NOT NULL,
    temporary_post BOOLEAN NULL,
    image_url VARCHAR(45) NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (blog_id) REFERENCES Blogs(blog_id)
);

-- Comments 
CREATE TABLE Comments (
    comment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    post_id BIGINT,
    created_at DATETIME NOT NULL,
    content LONGTEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);

-- Likes 
CREATE TABLE Likes (
    like_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT,
    like_count INT DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);

-- Tags 
CREATE TABLE Tags (
    tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(45) NOT NULL UNIQUE
);

-- Post_Tags 
CREATE TABLE Post_Tags (
    post_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES Posts(post_id),
    FOREIGN KEY (tag_id) REFERENCES Tags(tag_id)
);

-- Followers 
CREATE TABLE Followers (
    follower_id BIGINT,
    followee_id BIGINT,
    PRIMARY KEY (follower_id, followee_id),
    FOREIGN KEY (follower_id) REFERENCES Users(user_id),
    FOREIGN KEY (followee_id) REFERENCES Users(user_id)
);

-- Roles 
INSERT INTO Roles (role_name) VALUES ('user'), ('admin');

```

## 기능 목록

### 요구사항 (블로그)

---

<aside>
🎁 * 회원 1명은 1개의 Blog를 가질 수 있습니다.

- 회원가입, 로그인 기능을 제공합니다.
    - 회원가입시 중복 유효성 체크 미완성.
- http://도메인 는 사용자들이 작성한 최신 글, 인기 글을 볼 수 있습니다. (정렬방식에 따라 최신, 인기글을 볼 수 있습니다.)
- http://도메인에서 보여주는 블로그 글에는 제목, 내용일부, 작성자사진, 작성자 아이디, 좋아요 수가 보여집니다.
- http://도메인/@carami 는 carami 아이디의 사용자의 블로그 페이지입니다.
- http://도메인/@아이디 를 가면 좌측에 tag목록이 나오고 tag에 글의 수가 보여집니다.
- http://도메인/@아이디 를 가면 글 목록, 시리즈 목록, 소개를 볼 수 있습니다.
</aside>

### 요구사항 (블로그 포스트)

---

<aside>
🎁 * 사용자는 임시 글을 작성할 수 있습니다.

- 사용자는 글을 작성하자 마자 즉시 출간할 수 있습니다.
- 사용자는 임시 글 목록을 볼 수 있습니다.
- 글을 작성할 때 이미지, URL등을 포함시킬 수 있습니다.
- 글은 제목, 내용, 태그들을 작성할 수 있고 자동으로 작성일이 지정됩니다. (제목, 내용은 필수 입니다.)
- 임시 글 목록에서 임시 글을 삭제할 수 있습니다.
- 임시 글은 수정할 수 있습니다. 수정 후 바로 출간할 수 있습니다.
</aside>

### 요구사항 (좋아요, 팔로우)

---

<aside>
🎁 * 사용자는 블로그에서 “좋아하기”를 할 수 있습니다.

- 좋아하기를 선택한 블로그 글들만 모아서 볼 수 있습니다.
- 사용자가 읽은 글들만 모아서 볼 수 있습니다.

팔로우 한 사용자 목록을 볼 수 있습니다. 언팔로우 할 수 있습니다

</aside>

### 요구사항 (개인정보)

---

<aside>
🎁 * 사용자는 본인의 프로필 이미지를 등록할 수 있습니다.

- 본인이 등록한 프로필을 삭제할 수 있습니다.
- 블로그 제목을 설정할 수 있습니다. 설정하지 않으면 기본적으로 사용자 아이디가 됩니다.
- 이메일 주소를 변경할 수 있습니다. (회원 가입시 등록할 수 있습니다.)
- 이메일 수신 설정을 변경할 수 있습니다. (댓글 알림, 업데이트 소식 알림)
- 회원 탈퇴 기능을 제공합니다.
</aside>

### 요구사항 (출간)

---

<aside>
🎁 * 임시 글을 출간하거나, 바로 출간하기를 하게 되면 포스트 미리보기 이미지를 등록할 수 있습니다.

- 임시 글을 출간하거나, 바로 출간하기를 하게 될 때 글의 제목과 내용을 일부 보여줍니다. 전체공개로 올릴지 비공개로 올릴지를 결정할 수 있습니다.
- 해당 글의 URL은 /@아이디/posts/제목이 됩니다. 제목은 URLEncoding으로 인코딩 되어 있어야 합니다.
- 임시 글을 출간하거나, 바로 출간하기를 하게 될 때 시리즈에 추가할 수 있습니다
</aside>

### 요구사항 (블로그 글보기)

---

<aside>
🎁 * 블로그 글 보기 기능을 제공합니다.

- 비공개 글의 경우 비공개 표시를 제공합니다.
- 내 글이 아닌 경우 좋아요를 할 수 있습니다.
- 내 글의 경우 통계정보를 볼 수 있습니다.
- 내 글의 경우 수정할 수 있습니다.
- 내 글의 경우 삭제할 수 있습니다.
- 블로그를 작성한 사람의 프로필 이미지와 아이디를 하단에 보여줍니다.
- 다른 사람의 글의 경우 팔로우를 할 수 있습니다.
- 해당 사용자의 이전 블로그글, 이후 블로그글에 대한 링크를 볼 수 있습니다.
</aside>

### 요구사항 (댓글)

---

<aside>
🎁 * 블로그에 사용자는 댓글을 작성할 수 있습니다.

- 블로그 글보기를 하면 댓글의 수가 표시됩니다.
- 댓글에 답글을 작성할 수 있습니다.
- 댓글을 삭제할 수 있습니다.
</aside>

### 요구사항 (관리자)

---

<aside>
🎁 * 관리자 페이지로 가면 모든 포스팅된 글 목록을 볼 수 있습니다.
* 관리자는 어떤 글이든 삭제할 수 있습니다

</aside>

## 컨트롤러 정리

### UserController

```java
@Controller
@RequestMapping("/users")
public class UserController {
    
    // 회원 가입 페이지
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 회원 가입 처리
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // 사용자 등록 로직
        return "redirect:/users/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequest loginRequest, Model model) {
        // 로그인 로직
        return "redirect:/users/profile";
    }

    // 사용자 프로필 페이지
    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        // 프로필 정보 로드
        return "profile";
    }

    // 사용자 프로필 수정 페이지
    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model) {
        // 프로필 정보 로드
        return "edit-profile";
    }

    // 사용자 프로필 수정 처리
    @PostMapping("/profile/edit")
    public String updateUserProfile(@ModelAttribute User user, Model model) {
        // 프로필 수정 로직
        return "redirect:/users/profile";
    }
}

```

### BlogController

```java
@Controller
@RequestMapping("/blogs")
public class BlogController {

    // 블로그 목록 페이지
    @GetMapping
    public String getAllBlogs(Model model) {
        // 블로그 목록 로드
        return "blog-list";
    }

    // 블로그 생성 페이지
    @GetMapping("/create")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blog", new Blog());
        return "create-blog";
    }

    // 블로그 생성 처리
    @PostMapping("/create")
    public String createBlog(@ModelAttribute Blog blog, Model model) {
        // 블로그 생성 로직
        return "redirect:/blogs";
    }

    // 블로그 수정 페이지
    @GetMapping("/edit/{blogId}")
    public String showEditBlogForm(@PathVariable Long blogId, Model model) {
        // 블로그 정보 로드
        return "edit-blog";
    }

    // 블로그 수정 처리
    @PostMapping("/edit/{blogId}")
    public String updateBlog(@PathVariable Long blogId, @ModelAttribute Blog blog, Model model) {
        // 블로그 수정 로직
        return "redirect:/blogs";
    }

    // 블로그 삭제 처리
    @PostMapping("/delete/{blogId}")
    public String deleteBlog(@PathVariable Long blogId, Model model) {
        // 블로그 삭제 로직
        return "redirect:/blogs";
    }

    // 특정 블로그 페이지
    @GetMapping("/{blogId}")
    public String getBlogById(@PathVariable Long blogId, Model model) {
        // 블로그 정보 로드
        return "blog-details";
    }
}

```

### postController

```java
@Controller
@RequestMapping("/posts")
public class PostController {

    // 포스트 목록 페이지
    @GetMapping
    public String getAllPosts(Model model) {
        // 포스트 목록 로드
        return "post-list";
    }

    // 포스트 생성 페이지
    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    // 포스트 생성 처리
    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, Model model) {
        // 포스트 생성 로직
        return "redirect:/posts";
    }

    // 포스트 수정 페이지
    @GetMapping("/edit/{postId}")
    public String showEditPostForm(@PathVariable Long postId, Model model) {
        // 포스트 정보 로드
        return "edit-post";
    }

    // 포스트 수정 처리
    @PostMapping("/edit/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post post, Model model) {
        // 포스트 수정 로직
        return "redirect:/posts";
    }

    // 포스트 삭제 처리
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId, Model model) {
        // 포스트 삭제 로직
        return "redirect:/posts";
    }

    // 특정 포스트 페이지
    @GetMapping("/{postId}")
    public String getPostById(@PathVariable Long postId, Model model) {
        // 포스트 정보 로드
        return "post-details";
    }
}

```

### CommentController

```java
@Controller
@RequestMapping("/comments")
public class CommentController {

    // 댓글 작성 처리
    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment comment, Model model) {
        // 댓글 작성 로직
        return "redirect:/posts/" + comment.getPostId();
    }

    // 댓글 수정 페이지
    @GetMapping("/edit/{commentId}")
    public String showEditCommentForm(@PathVariable Long commentId, Model model) {
        // 댓글 정보 로드
        return "edit-comment";
    }

    // 댓글 수정 처리
    @PostMapping("/edit/{commentId}")
    public String updateComment(@PathVariable Long commentId, @ModelAttribute Comment comment, Model model) {
        // 댓글 수정 로직
        return "redirect:/posts/" + comment.getPostId();
    }

    // 댓글 삭제 처리
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Model model) {
        // 댓글 삭제 로직
        return "redirect:/posts"; // 적절한 리디렉션 경로 설정
    }
}

```

### LikeController

```java
@Controller
@RequestMapping("/likes")
public class LikeController {

    // 좋아요 추가 처리
    @PostMapping("/add")
    public String addLike(@RequestParam Long postId, Model model) {
        // 좋아요 추가 로직
        return "redirect:/posts/" + postId;
    }

    // 좋아요 수 조회
    @GetMapping("/count/{postId}")
    public String getLikeCount(@PathVariable Long postId, Model model) {
        // 좋아요 수 조회 로직
        return "post-details"; // 적절한 뷰 이름 설정
    }
}

```

### TagController

```java
@Controller
@RequestMapping("/tags")
public class TagController {

    // 태그 목록 페이지
    @GetMapping
    public String getAllTags(Model model) {
        // 태그 목록 로드
        return "tag-list";
    }

    // 태그 생성 페이지
    @GetMapping("/create")
    public String showCreateTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "create-tag";
    }

    // 태그 생성 처리
    @PostMapping("/create")
    public String createTag(@ModelAttribute Tag tag, Model model) {
        // 태그 생성 로직
        return "redirect:/tags";
    }

    // 태그 수정 페이지
    @GetMapping("/edit/{tagId}")
    public String showEditTagForm(@PathVariable Long tagId, Model model) {
        // 태그 정보 로드
        return "edit-tag";
    }

    // 태그 수정 처리
    @PostMapping("/edit/{tagId}")
    public String updateTag(@PathVariable Long tagId, @ModelAttribute Tag tag, Model model) {
        // 태그 수정 로직
        return "redirect:/tags";
    }

    // 태그 삭제 처리
    @PostMapping("/delete/{tagId}")
    public String deleteTag(@PathVariable Long tagId, Model model) {
        // 태그 삭제 로직
        return "redirect:/tags";
    }
}

```

### FollowController

```java
@Controller
@RequestMapping("/follows")
public class FollowController {

    // 팔로우 추가 처리
    @PostMapping("/add")
    public String addFollow(@RequestParam Long followeeId, Model model) {
        // 팔로우 추가 로직
        return "redirect:/users/profile/" + followeeId;
    }

    // 팔로잉 목록 페이지
    @GetMapping("/following/{userId}")
    public String getFollowing(@PathVariable Long userId, Model model) {
        // 팔로잉 목록 로드
        return "following-list";
    }

    // 팔로워 목록 페이지
    @GetMapping("/followers/{userId}")
    public String getFollowers(@PathVariable Long userId, Model model) {
        // 팔로워 목록 로드
        return "followers-list";
    }
}

```

### RoleController (관리자 용도)

```java
@Controller
@RequestMapping("/roles")
public class RoleController {

    // 역할 목록 페이지
    @GetMapping
    public String getAllRoles(Model model) {
        // 역할 목록 로드
        return "role-list";
    }

    // 역할 생성 페이지
    @GetMapping("/create")

```
