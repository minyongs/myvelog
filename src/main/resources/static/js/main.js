$(document).ready(function() {
    let page = 0;
    const size = 12;
    let isLoading = false;

    function loadMorePosts() {
        if (isLoading) return;
        isLoading = true;

        $.ajax({
            url: '/postapi/api/posts', // 확인 필요
            method: 'GET',
            data: {
                page: page,
                size: size
            },
            beforeSend: function() {
                $('#loader').show();
            },
            success: function(data) {
                console.log(data); // 응답 데이터 확인을 위한 로그 출력
                const postsDiv = $('#posts');
                data.content.forEach(post => {
                    postsDiv.append(`
                        <div class="col-md-3">
                            <div class="card">
                                ${post.thumbnailUrl ? `<img src="${post.thumbnailUrl}" alt="썸네일 이미지" class="card-img-top">` : ''}
                                <div class="card-body">
                                    <div class="card-content">
                                        <h5 class="card-title">
                                            <a href="/myvelog/detail/${post.postId}" style="color: #ffffff; text-decoration: none;">${post.title}</a>
                                        </h5>
                                        <p class="card-text">${post.strippedContent}</p>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <small class="text-muted">${new Date(post.createdAt).toLocaleDateString()}</small>
                                            <small class="text-muted">${post.commentCount} 댓글</small>
                                            <br>
                                            <small class="text-muted">
                                                <a href="/myvelog/@${post.author}" style="color: #b0b0b0; text-decoration: none;">by ${post.author}</a>
                                            </small>
                                        </div>
                                        <div>
                                            <i class="fa fa-heart"></i> <span>${post.likeCount}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `);
                });
                if (data.last) {
                    $(window).off('scroll', handleScroll);
                }
                $('#loader').hide();
                isLoading = false;
            },
            error: function() {
                $('#loader').hide();
                alert('더 많은 게시물을 불러오는 데 실패했습니다.');
                isLoading = false;
            }
        });
    }

    function handleScroll() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            page++;
            loadMorePosts();
        }
    }

    $(window).on('scroll', handleScroll);

    // 페이지 로드 시 초기 데이터 로드
    loadMorePosts();
});
